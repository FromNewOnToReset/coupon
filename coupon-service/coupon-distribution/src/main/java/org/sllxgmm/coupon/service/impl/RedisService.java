package org.sllxgmm.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.sllxgmm.coupon.constant.Constant;
import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.entity.Coupon;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.service.IRedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> RedisService </h1>
 * @date 2020/3/16 13:56
 * @邮箱 1558078764@qq.com
 */
@Slf4j
@Service
public class RedisService implements IRedisService {
    private final StringRedisTemplate redisTemplate;

    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Coupon> getCachedCoupon(Long userId, Integer statusCode) {
        log.info("Get Coupons From Cache: {}, {}", userId, statusCode);
        String redisKey = status2RedisKey(statusCode, userId);

        List<String> couponStrs = redisTemplate.opsForHash().values(redisKey)
                .stream()
                .map(o -> Objects.toString(o, null))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(couponStrs)) {
            saveEmptyCouponListToCache(userId,
                    Collections.singletonList(statusCode));
            return Collections.emptyList();
        }

        return couponStrs.stream()
                .map(cs -> JSON.parseObject(cs, Coupon.class))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("all")
    public void saveEmptyCouponListToCache(Long userId, List<Integer> status) {
        //避免缓存穿透
        log.info("save empty list to cache for User: {} ,status: {}",userId, JSON.toJSONString(status));
        Map<String,String> invalidCouponMap=new HashMap<>();
        invalidCouponMap.put("-1",JSON.toJSONString(Coupon.invalidCoupon()));

        SessionCallback<Object> sessionCallback=new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations redisOperations) throws DataAccessException {
                status.forEach(s->{
                    String key=status2RedisKey(s,userId);
                    redisOperations.opsForHash().putAll(key,invalidCouponMap);
                });
                return null;
            }

        };
        log.info("Pipeline Exe Result: {}",
                JSON.toJSONString(redisTemplate.executePipelined(sessionCallback)));


    }

    private String status2RedisKey(Integer status,Long userId){
        String redisKey=null;
        CouponStatus couponStatus=CouponStatus.ofValue(status);
        switch (couponStatus){
            case USABLE:
                 redisKey=String.format("%s%s", Constant.RedisPrefix.USER_COUPON_USABLE,userId);
                 break;
            case USED:
                redisKey=String.format("%s%s", Constant.RedisPrefix.USER_COUPON_USED,userId);
                break;
            case EXPIRED:
                redisKey=String.format("%s%s", Constant.RedisPrefix.USER_COUPON_EXPIRED,userId);
                break;
        }
        return redisKey;
    }
    @Override
    public String tryToAcquireCouponCodeFromCache(Integer templateId) {
        String redisKey=String.format("%s%s",Constant.RedisPrefix.COUPON_TEMPLATE,templateId.toString());
        String couponCode=redisTemplate.opsForList().leftPop(redisKey);
        log.info("Acquire Coupon Code: {}, {}, {}",
                templateId, redisKey, couponCode);

        return couponCode;
    }

    @Override
    public Integer addCouponToCache(Long userId, List<Coupon> coupons, Integer status) throws CouponException {

        log.info("Add Coupon To Cache: {}, {}, {}",
                userId, JSON.toJSONString(coupons), status);

        Integer result=-1;

        CouponStatus couponStatus=CouponStatus.ofValue(status);
        switch (couponStatus){
            case USABLE:
                result=addCouponToCacheForUsable(userId,coupons);
                break;
            case USED:
                result = addCouponToCacheForUsed(userId, coupons);
                break;
            case EXPIRED:
                result = addCouponToCacheForExpired(userId, coupons);
                break;
        }
        return result;
    }

    private Integer addCouponToCacheForUsable(Long userId ,List<Coupon> coupons){
        log.debug("Add Coupon To Cache For Usable.");
        Map<String, String> needCachedObject = new HashMap<>();
        coupons.forEach(c ->
                needCachedObject.put(
                        c.getId().toString(),
                        JSON.toJSONString(c)
                ));
        String redisKey = status2RedisKey(
                CouponStatus.USABLE.getCode(), userId);
        redisTemplate.opsForHash().putAll(redisKey, needCachedObject);
        log.info("Add {} Coupons To Cache: {}, {}",
                needCachedObject.size(), userId, redisKey);

        redisTemplate.expire(
                redisKey,
                getRandomExpirationTime(1, 2),
                TimeUnit.SECONDS
        );
        return needCachedObject.size();
    }

    /**
     * <h2>将已使用的优惠券加入到 Cache 中</h2>
     * */
    @SuppressWarnings("all")
    private Integer addCouponToCacheForUsed(Long userId, List<Coupon> coupons)
            throws CouponException {

        // 如果 status 是 USED, 代表用户操作是使用当前的优惠券, 影响到两个 Cache
        // USABLE, USED

        log.debug("Add Coupon To Cache For Used.");

        Map<String, String> needCachedForUsed = new HashMap<>(coupons.size());

        String redisKeyForUsable = status2RedisKey(
                CouponStatus.USABLE.getCode(), userId
        );
        String redisKeyForUsed = status2RedisKey(
                CouponStatus.USED.getCode(), userId
        );

        // 获取当前用户可用的优惠券
        List<Coupon> curUsableCoupons = getCachedCoupon(
                userId, CouponStatus.USABLE.getCode()
        );
        // 当前可用的优惠券个数一定是大于1的
        assert curUsableCoupons.size() > coupons.size();

        coupons.forEach(c -> needCachedForUsed.put(
                c.getId().toString(),
                JSON.toJSONString(c)
        ));

        // 校验当前的优惠券参数是否与 Cached 中的匹配
        List<Integer> curUsableIds = curUsableCoupons.stream()
                .map(Coupon::getId).collect(Collectors.toList());
        List<Integer> paramIds = coupons.stream()
                .map(Coupon::getId).collect(Collectors.toList());

        if (!CollectionUtils.isSubCollection(paramIds, curUsableIds)) {
            log.error("CurCoupons Is Not Equal ToCache: {}, {}, {}",
                    userId, JSON.toJSONString(curUsableIds),
                    JSON.toJSONString(paramIds));
            throw new CouponException("CurCoupons Is Not Equal To Cache!");
        }

        List<String> needCleanKey = paramIds.stream()
                .map(i -> i.toString()).collect(Collectors.toList());
        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {
            @Override
            public Objects execute(RedisOperations operations) throws DataAccessException {

                // 1. 已使用的优惠券 Cache 缓存添加
                operations.opsForHash().putAll(
                        redisKeyForUsed, needCachedForUsed
                );
                // 2. 可用的优惠券 Cache 需要清理
                operations.opsForHash().delete(
                        redisKeyForUsable, needCleanKey.toArray()
                );
                // 3. 重置过期时间
                operations.expire(
                        redisKeyForUsable,
                        getRandomExpirationTime(1, 2),
                        TimeUnit.SECONDS
                );
                operations.expire(
                        redisKeyForUsed,
                        getRandomExpirationTime(1, 2),
                        TimeUnit.SECONDS
                );

                return null;
            }
        };

        log.info("Pipeline Exe Result: {}",
                JSON.toJSONString(
                        redisTemplate.executePipelined(sessionCallback)));

        return coupons.size();
    }


    /**
     * <h2>将过期优惠券加入到 Cache 中</h2>
     * */
    @SuppressWarnings("all")
    private Integer addCouponToCacheForExpired(Long userId, List<Coupon> coupons)
            throws CouponException {

        // status 是 EXPIRED, 代表是已有的优惠券过期了, 影响到两个 Cache
        // USABLE, EXPIRED

        log.debug("Add Coupon To Cache For Expired.");

        // 最终需要保存的 Cache
        Map<String, String> needCachedForExpired = new HashMap<>(coupons.size());

        String redisKeyForUsable = status2RedisKey(
                CouponStatus.USABLE.getCode(), userId
        );
        String redisKeyForExpired = status2RedisKey(
                CouponStatus.EXPIRED.getCode(), userId
        );

        List<Coupon> curUsableCoupons = getCachedCoupon(
                userId, CouponStatus.USABLE.getCode()
        );

        // 当前可用的优惠券个数一定是大于1的
        assert curUsableCoupons.size() > coupons.size();

        coupons.forEach(c -> needCachedForExpired.put(
                c.getId().toString(),
                JSON.toJSONString(c)
        ));

        // 校验当前的优惠券参数是否与 Cached 中的匹配
        List<Integer> curUsableIds = curUsableCoupons.stream()
                .map(Coupon::getId).collect(Collectors.toList());
        List<Integer> paramIds = coupons.stream()
                .map(Coupon::getId).collect(Collectors.toList());
        if (!CollectionUtils.isSubCollection(paramIds, curUsableIds)) {
            log.error("CurCoupons Is Not Equal To Cache: {}, {}, {}",
                    userId, JSON.toJSONString(curUsableIds),
                    JSON.toJSONString(paramIds));
            throw new CouponException("CurCoupon Is Not Equal To Cache.");
        }

        List<String> needCleanKey = paramIds.stream()
                .map(i -> i.toString()).collect(Collectors.toList());

        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {
            @Override
            public Objects execute(RedisOperations operations) throws DataAccessException {

                // 1. 已过期的优惠券 Cache 缓存
                operations.opsForHash().putAll(
                        redisKeyForExpired, needCachedForExpired
                );
                // 2. 可用的优惠券 Cache 需要清理
                operations.opsForHash().delete(
                        redisKeyForUsable, needCleanKey.toArray()
                );
                // 3. 重置过期时间
                operations.expire(
                        redisKeyForUsable,
                        getRandomExpirationTime(1, 2),
                        TimeUnit.SECONDS
                );
                operations.expire(
                        redisKeyForExpired,
                        getRandomExpirationTime(1, 2),
                        TimeUnit.SECONDS
                );

                return null;
            }
        };

        log.info("Pipeline Exe Result: {}",
                JSON.toJSONString(
                        redisTemplate.executePipelined(sessionCallback)
                ));

        return coupons.size();
    }



    /**
     * <h2>获取一个随机的过期时间</h2>
     * 缓存雪崩: key 在同一时间失效
     * @param min 最小的小时数
     * @param max 最大的小时数
     * @return 返回 [min, max] 之间的随机秒数
     * */
    private Long getRandomExpirationTime(Integer min,Integer max){
        return RandomUtils.nextLong(
                min * 60 * 60,
                max * 60 * 60
        );
    }
}
