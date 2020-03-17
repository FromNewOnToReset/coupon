package org.sllxgmm.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> GoodType </h1>
 * @date 2020/3/16 11:56
 * @邮箱 1558078764@qq.com
 */
@Getter
@AllArgsConstructor
public enum GoodType {
    WENYU("文娱",1),
    SHENGXIAN("生鲜",2),
    JIAJU("家具",3),
    QITA("其他",4),
    ALL("全品类",5);
    private String desc;
    private Integer code;
    public static GoodType ofValue(Integer code){
        Objects.requireNonNull(code);
        return Stream.of(values()).filter(bean->bean.code.equals(code)).findAny().orElseThrow(()->new IllegalArgumentException(code+"is ont exist"));
    }
}
