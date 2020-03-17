package org.sllxgmm.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponStatus </h1>
 * @date 2020/3/16 10:43
 * @邮箱 1558078764@qq.com
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CouponStatus {
    USABLE("可用的", 1),
    USED("已使用的", 2),
    EXPIRED("过期的（未使用）", 3);
    private String desc;
    private Integer code;

    public static CouponStatus ofValue(Integer code){
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean->bean.code.equals(code)).findAny().orElseThrow(()->new IllegalArgumentException(code+"not exist"));
    }
}
