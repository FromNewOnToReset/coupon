package org.sllxgmm.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> SettlementInfo </h1>
 * @date 2020/3/16 12:13
 * @邮箱 1558078764@qq.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementInfo {
    /**用户ID*/
    private Long userId;
    /**商品信息*/
    private List<GoodsInfo> goodsInfos;

    /**优惠券模板*/
    private List<CouponAndTemplateInfo> couponAndTemplateInfos;

    /**结算金额*/
    private BigDecimal cost;

    /**是否是结算生效 true=核销 false=结算*/
    private boolean employ;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CouponAndTemplateInfo{
        /** coupon主键*/
        private Integer id;
        /** 优惠券对应的模板对象*/
        private CouponTemplateSDK templateSDK;
    }
}
