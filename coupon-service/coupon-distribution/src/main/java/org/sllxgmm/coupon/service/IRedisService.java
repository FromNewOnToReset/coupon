package org.sllxgmm.coupon.service;

import org.sllxgmm.coupon.entity.Coupon;
import org.sllxgmm.coupon.exception.CouponException;

import java.util.List;

/**
 * <h1>
 *     redis 相关的操作服务接口
 * </h1>
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> IRedisService </h1>
 * @date 2020/3/16 11:28
 * @邮箱 1558078764@qq.com
 */
public interface IRedisService {
    /**
     * <h1>根据状态找到缓存的优惠券</h1>
     * @param userId  用户ID
     * @param statusCode 优惠券状态
     * @return {@link Coupon}优惠券集合 可能会返回null ,代表没有记录
     */
    List<Coupon> getCachedCoupon(Long userId,Integer statusCode);

    /**
     * <h2>缓存穿透 。将空的优惠券列表保存到缓存中</h2>
     * @param userId 用户ID
     * @param status 优惠券状态
     */
    void saveEmptyCouponListToCache(Long userId,List<Integer> status);

    /**
     * <h2>尝试从Cache中获取一个优惠券码</h2>
     * @param templateId 优惠券模板主键
     * @return 优惠券码
     */
    String tryToAcquireCouponCodeFromCache(Integer templateId);

    /**
     * <h1>将优惠券保存到Cache中</h1>
     * @param userId 用户ID
     * @param coupons {@link Coupon}
     * @param status 优惠券状态
     * @return 保存成功的个数
     * @throws CouponException
     */
    Integer addCouponToCache(Long userId,List<Coupon> coupons,Integer status) throws CouponException;
}
