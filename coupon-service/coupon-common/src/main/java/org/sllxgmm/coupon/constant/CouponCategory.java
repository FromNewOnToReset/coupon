package org.sllxgmm.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponCategory </h1>
 * @date 2020/3/15 15:51
 * @邮箱 1558078764@qq.com
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {
    MANJIAN("满减券", "001"),
    ZHEKOU("折扣券", "002"),
    LIJIAN("立减券", "003");

    /**
     * 优惠券描述(分类)
     */
    private String description;

    /**
     * 优惠券分类编码
     */
    private String code;

    public static CouponCategory ofValue(String code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
