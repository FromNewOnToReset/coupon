package org.sllxgmm.coupon.converter;


import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.entity.Coupon;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponConverter </h1>
 * @date 2020/3/16 11:04
 * @邮箱 1558078764@qq.com
 */
@Converter
public class CouponConverter implements AttributeConverter<CouponStatus,Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus status) {
        return status.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer code) {
        return CouponStatus.ofValue(code);
    }
}

