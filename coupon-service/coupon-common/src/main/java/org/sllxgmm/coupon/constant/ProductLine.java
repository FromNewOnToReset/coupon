package org.sllxgmm.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> ProductLine </h1>
 * @date 2020/3/15 15:56
 * @邮箱 1558078764@qq.com
 */
@Getter
@AllArgsConstructor
public enum ProductLine {


    DAMAO("大猫", 1),
    DABAO("大宝", 2);

    /**
     * 产品线描述
     */
    private String description;

    /**
     * 产品线编码
     */
    private Integer code;

    public static ProductLine ofValue(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
