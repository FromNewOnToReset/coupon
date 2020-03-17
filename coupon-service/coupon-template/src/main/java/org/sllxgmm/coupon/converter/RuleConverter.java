package org.sllxgmm.coupon.converter;

import com.alibaba.fastjson.JSON;
import org.sllxgmm.coupon.vo.TemplateRule;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> RuleConverter </h1>
 * @date 2020/3/15 16:17
 * @邮箱 1558078764@qq.com
 */
@Converter
public class RuleConverter implements AttributeConverter<TemplateRule,String> {
    @Override
    public String convertToDatabaseColumn(TemplateRule rule) {
        return JSON.toJSONString(rule);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String rule) {
        return JSON.parseObject(rule,TemplateRule.class);
    }
}
