package org.sllxgmm.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> DistributeTarget </h1>
 * @date 2020/3/15 15:54
 * @邮箱 1558078764@qq.com
 */
@Getter
@AllArgsConstructor
public enum DistributeTarget {

    SINGLE("单用户", 1),
    MULTI("多用户", 2);

    /**
     * 分发目标描述
     */
    private String description;

    /**
     * 分发目标编码
     */
    private Integer code;

    public static DistributeTarget ofValue(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
