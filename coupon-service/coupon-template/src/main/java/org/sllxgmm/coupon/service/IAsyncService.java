package org.sllxgmm.coupon.service;

import org.sllxgmm.coupon.entity.CouponTemplate;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> IAsyncService </h1>
 * @date 2020/3/15 17:58
 * @邮箱 1558078764@qq.com
 */
public interface IAsyncService {
    /**
     * <h2>根据模板异步的创建优惠券码</h2>
     * @param template {@link CouponTemplate} 优惠券模板实体
     * */
    void asyncConstructCouponByTemplate(CouponTemplate template);
}
