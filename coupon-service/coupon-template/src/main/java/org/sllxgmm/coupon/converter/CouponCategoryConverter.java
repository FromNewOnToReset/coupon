package org.sllxgmm.coupon.converter;

import org.sllxgmm.coupon.constant.CouponCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponCategoryConverter </h1>
 * @date 2020/3/15 16:10
 * @邮箱 1558078764@qq.com
 */
@Converter
public class CouponCategoryConverter implements AttributeConverter<CouponCategory,String> {

    @Override
    public String convertToDatabaseColumn(CouponCategory couponCategory) {
        return couponCategory.getCode();
    }

    @Override
    public CouponCategory convertToEntityAttribute(String s) {
        return CouponCategory.ofValue(s);
    }
}
