package org.sllxgmm.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> RuleFlag </h1>
 * @date 2020/3/16 20:48
 * @email 1558078764@qq.com
 */
@Getter
@AllArgsConstructor
public enum RuleFlag {

    //单类别
    MANJIAN("满减券的计算规则"),
    ZHEKOU("折扣券的计算规则"),
    LIJIAN("立减券的计算规则"),

    // 多类别优惠券定义
    MANJIAN_ZHEKOU("满减券 + 折扣券的计算规则");


    private String desc;

}
