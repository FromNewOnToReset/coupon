package org.sllxgmm.coupon.converter;

import org.sllxgmm.coupon.constant.DistributeTarget;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> DistributeTargetConverter </h1>
 * @date 2020/3/15 16:13
 * @邮箱 1558078764@qq.com
 */
@Converter
public class DistributeTargetConverter implements AttributeConverter<DistributeTarget,Integer> {
    @Override
    public Integer convertToDatabaseColumn(DistributeTarget distributeTarget) {
        return distributeTarget.getCode();
    }

    @Override
    public DistributeTarget convertToEntityAttribute(Integer code) {
        return DistributeTarget.ofValue(code);
    }
}
