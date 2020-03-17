package org.sllxgmm.coupon.converter;

import org.sllxgmm.coupon.constant.ProductLine;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> ProductLineConverter </h1>
 * @date 2020/3/15 16:15
 * @邮箱 1558078764@qq.com
 */
@Converter
public class ProductLineConverter implements AttributeConverter<ProductLine,Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProductLine productLine) {
        return productLine.getCode();
    }

    @Override
    public ProductLine convertToEntityAttribute(Integer code) {
        return ProductLine.ofValue(code);
    }
}
