package org.sllxgmm.coupon.service;

import org.sllxgmm.coupon.entity.Coupon;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.vo.AcquireTemplateRequest;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.sllxgmm.coupon.vo.SettlementInfo;

import java.util.List;

/**
 * <h1>用户相关的接口定义</h1>
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> UserService </h1>
 * @date 2020/3/16 11:40
 * @邮箱 1558078764@qq.com
 */
public interface IUserService {
    /**
     * <h2>根据用户ID和优惠券状态查询</h2>
     * @param userId  用户ID
     * @param status 状态
     * @return {@link Coupon}
     * @throws {@link CouponException}
     */
    List<Coupon> findCouponByStatus(Long userId,Integer status) throws CouponException;

    /**
     * <h2>根据用户ID查询可以领取的优惠券</h2>
     * @param userId  用户ID
     * @return {@link Coupon}
     * @throws {@link CouponException}
     */
    List<CouponTemplateSDK> findAvailableTemplate(Long userId)throws CouponException;

    /**
     * <h2>用户领取优惠券</h2>
     * @param request {@link AcquireTemplateRequest}
     * @return {@link Coupon}
     * @throws {@link CouponException}
     */
    Coupon acquireTemplate(AcquireTemplateRequest request)throws CouponException;

    /**
     * @param settlementInfo {@link SettlementInfo}
     * @return {@link SettlementInfo}
     * @throws {@link CouponException}
     */
    SettlementInfo settlement(SettlementInfo settlementInfo)throws CouponException;
}
