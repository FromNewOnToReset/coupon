package org.sllxgmm.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.sllxgmm.coupon.constant.Constant;
import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.dao.CouponDao;
import org.sllxgmm.coupon.entity.Coupon;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.feign.SettlementClient;
import org.sllxgmm.coupon.feign.TemplateClient;
import org.sllxgmm.coupon.service.IRedisService;
import org.sllxgmm.coupon.service.IUserService;
import org.sllxgmm.coupon.utils.CouponClassify;
import org.sllxgmm.coupon.vo.AcquireTemplateRequest;
import org.sllxgmm.coupon.vo.CouponKafkaMessage;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.sllxgmm.coupon.vo.SettlementInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> UserServiceImpl </h1>
 * @date 2020/3/16 16:52
 * @邮箱 1558078764@qq.com
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    private final CouponDao couponDao;
    private final IRedisService redisService;
    private final TemplateClient templateClient;
    private final SettlementClient settlementClient;
    private final KafkaTemplate kafkaTemplate;



    public UserServiceImpl(CouponDao couponDao, IRedisService redisService, TemplateClient templateClient, SettlementClient settlementClient, KafkaTemplate kafkaTemplate) {
        //IDEA不能识别TemplateClient 非error
        this.couponDao = couponDao;
        this.redisService = redisService;
        this.templateClient = templateClient;
        this.settlementClient = settlementClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * <h2>根据用户ID和优惠券状态查询</h2>
     *
     * @param userId 用户ID
     * @param status 状态
     * @return {@link Coupon}
     * @throws {@link CouponException}
     */
    @Override
    public List<Coupon> findCouponByStatus(Long userId, Integer status) throws CouponException {

        List<Coupon> curCached = redisService.getCachedCoupon(userId, status);
        List<Coupon> preTarget;

        if (CollectionUtils.isNotEmpty(curCached)) {
            log.debug("coupon cache is not empty: {}, {}", userId, status);
            preTarget = curCached;
        } else {
            log.debug("coupon cache is empty, get coupon from db: {}, {}",
                    userId, status);
            List<Coupon> dbCoupons = couponDao.findAllByUserIdAndStatus(
                    userId, CouponStatus.ofValue(status)
            );
            // 如果数据库中没有记录, 直接返回就可以, Cache 中已经加入了一张无效的优惠券
            if (CollectionUtils.isEmpty(dbCoupons)) {
                log.debug("current user do not have coupon: {}, {}", userId, status);
                return dbCoupons;
            }

            // 填充 dbCoupons的 templateSDK 字段
            Map<Integer, CouponTemplateSDK> id2TemplateSDK =
                    templateClient.findIds2TemplateSDK(
                            dbCoupons.stream()
                                    .map(Coupon::getTemplateId)
                                    .collect(Collectors.toList())
                    ).getData();
            dbCoupons.forEach(
                    dc -> dc.setTemplateSDK(
                            id2TemplateSDK.get(dc.getTemplateId())
                    )
            );
            // 数据库中存在记录
            preTarget = dbCoupons;
            // 将记录写入 Cache
            redisService.addCouponToCache(userId, preTarget, status);
        }

        // 将无效优惠券剔除
        preTarget = preTarget.stream()
                .filter(c -> c.getId() != -1)
                .collect(Collectors.toList());
        // 如果当前获取的是可用优惠券, 还需要做对已过期优惠券的延迟处理
        if (CouponStatus.ofValue(status) == CouponStatus.USABLE) {
            CouponClassify classify = CouponClassify.classify(preTarget);
            // 如果已过期状态不为空, 需要做延迟处理
            if (CollectionUtils.isNotEmpty(classify.getExpiredCoupons())) {
                log.info("Add Expired Coupons To Cache From FindCouponsByStatus: " +
                        "{}, {}", userId, status);
                redisService.addCouponToCache(
                        userId, classify.getExpiredCoupons(),
                        CouponStatus.EXPIRED.getCode()
                );
                // 发送到 kafka 中做异步处理
                kafkaTemplate.send(
                        Constant.TOPIC,
                        JSON.toJSONString(new CouponKafkaMessage(
                                CouponStatus.EXPIRED.getCode(),
                                classify.getExpiredCoupons().stream()
                                        .map(Coupon::getId)
                                        .collect(Collectors.toList())
                        ))
                );
            }

            return classify.getUsableCoupons();
        }

        return preTarget;
    }

    /**
     * <h2>根据用户ID查询可以领取的优惠券</h2>
     *
     * @param userId 用户ID
     * @return {@link Coupon}
     * @throws {@link CouponException}
     */
    @Override
    public List<CouponTemplateSDK> findAvailableTemplate(Long userId) throws CouponException {

        long curTime = new Date().getTime();
        List<CouponTemplateSDK> templateSDKS =
                templateClient.findAllUsableTemplate().getData();

        log.debug("Find All Template(From TemplateClient) Count: {}",
                templateSDKS.size());

        // 过滤过期的优惠券模板
        templateSDKS = templateSDKS.stream().filter(
                t -> t.getRule().getExpiration().getDeadline() > curTime
        ).collect(Collectors.toList());

        log.info("Find Usable Template Count: {}", templateSDKS.size());

        // key 是 TemplateId
        // value 中的 left 是 Template limitation, right 是优惠券模板
        Map<Integer, Pair<Integer, CouponTemplateSDK>> limit2Template =
                new HashMap<>(templateSDKS.size());
        templateSDKS.forEach(
                t -> limit2Template.put(
                        t.getId(),
                        Pair.of(t.getRule().getLimitation(), t)
                )
        );

        List<CouponTemplateSDK> result =
                new ArrayList<>(limit2Template.size());
        List<Coupon> userUsableCoupons = findCouponByStatus(
                userId, CouponStatus.USABLE.getCode()
        );

        log.debug("Current User Has Usable Coupons: {}, {}", userId,
                userUsableCoupons.size());

        // key 是 TemplateId
        Map<Integer, List<Coupon>> templateId2Coupons = userUsableCoupons
                .stream()
                .collect(Collectors.groupingBy(Coupon::getTemplateId));

        // 根据 Template 的 Rule 判断是否可以领取优惠券模板
        limit2Template.forEach((k, v) -> {

            int limitation = v.getLeft();
            CouponTemplateSDK templateSDK = v.getRight();

            if (templateId2Coupons.containsKey(k)
                    && templateId2Coupons.get(k).size() >= limitation) {
                return;
            }

            result.add(templateSDK);

        });

        return result;
    }

    /**
     * <h2>用户领取优惠券</h2>
     *
     * @param request {@link AcquireTemplateRequest}
     * @return {@link Coupon}
     * @throws {@link CouponException}
     */
    @Override
    @SuppressWarnings("all")
    public Coupon acquireTemplate(AcquireTemplateRequest request) throws CouponException {
        Map<Integer, CouponTemplateSDK> id2Template = templateClient.findIds2TemplateSDK(
                        Collections.singletonList(
                                request.getTemplateSDK().getId()
                        )
                ).getData();

        // 优惠券模板是需要存在的
        if (id2Template.size() <= 0) {
            log.error("Can Not Acquire Template From TemplateClient: {}",
                    request.getTemplateSDK().getId());
            throw new CouponException("Can Not Acquire Template From TemplateClient");
        }

        // 用户是否可以领取这张优惠券
        List<Coupon> userUsableCoupons = findCouponByStatus(
                request.getUserId(), CouponStatus.USABLE.getCode()
        );
        Map<Integer, List<Coupon>> templateId2Coupons = userUsableCoupons
                .stream()
                .collect(Collectors.groupingBy(Coupon::getTemplateId));

        if (templateId2Coupons.containsKey(request.getTemplateSDK().getId())
                && templateId2Coupons.get(request.getTemplateSDK().getId()).size() >=
                request.getTemplateSDK().getRule().getLimitation()) {
            log.error("Exceed Template Assign Limitation: {}",
                    request.getTemplateSDK().getId());
            throw new CouponException("Exceed Template Assign Limitation");
        }

        // 尝试去获取优惠券码
        String couponCode = redisService.tryToAcquireCouponCodeFromCache(
                request.getTemplateSDK().getId()
        );
        if (StringUtils.isEmpty(couponCode)) {
            log.error("Can Not Acquire Coupon Code: {}",
                    request.getTemplateSDK().getId());
            throw new CouponException("Can Not Acquire Coupon Code");
        }

        Coupon newCoupon = new Coupon(
                request.getTemplateSDK().getId(), request.getUserId(),
                couponCode, CouponStatus.USABLE
        );
        newCoupon = couponDao.save(newCoupon);

        // 填充 Coupon 对象的 CouponTemplateSDK, 一定要在放入缓存之前去填充
        newCoupon.setTemplateSDK(request.getTemplateSDK());

        // 放入缓存中
        redisService.addCouponToCache(
                request.getUserId(),
                Collections.singletonList(newCoupon),
                CouponStatus.USABLE.getCode()
        );

        return newCoupon;
    }

    /**
     * @param settlementInfo {@link SettlementInfo}
     * @return {@link SettlementInfo}
     * @throws {@link CouponException}
     */
    @Override
    public SettlementInfo settlement(SettlementInfo settlementInfo) throws CouponException {
        return null;
    }
}
