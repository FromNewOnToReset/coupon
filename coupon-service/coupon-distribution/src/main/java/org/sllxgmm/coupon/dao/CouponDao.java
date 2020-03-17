package org.sllxgmm.coupon.dao;

import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponDao </h1>
 * @date 2020/3/16 11:21
 * @e-mial 1558078764@qq.com
 */

public interface CouponDao  extends JpaRepository<Coupon,Integer> {

    /**
     * <h1>根据用户ID和优惠券状态查询</h1>
     * @param userId 用户ID
     * @param status 使用状态
     * @return 优惠券集合
     */
    List<Coupon> findAllByUserIdAndStatus(Long userId, CouponStatus status);
}
