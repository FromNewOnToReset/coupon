package org.sllxgmm.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>获取领取优惠券请求定义</h1>
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> AcquireTemplateRequest </h1>
 * @date 2020/3/16 11:48
 * @邮箱 1558078764@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireTemplateRequest {
    /** 用户ID*/
    private Long userId;

    /** 优惠券模板信息 */
    private CouponTemplateSDK templateSDK;
}
