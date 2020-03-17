package org.sllxgmm.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.sllxgmm.coupon.constant.CouponCategory;
import org.sllxgmm.coupon.constant.DistributeTarget;
import org.sllxgmm.coupon.constant.ProductLine;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> TemplateRequest </h1>
 * @date 2020/3/15 17:43
 * @邮箱 1558078764@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {
    /** 优惠券名称 */
    private String name;

    /** 优惠券 logo */
    private String logo;

    /** 优惠券描述 */
    private String desc;

    /** 优惠券分类 */
    private String category;

    /** 产品线 */
    private Integer productLine;

    /** 总数 */
    private Integer count;

    /** 创建用户 */
    private Long userId;

    /** 目标用户 */
    private Integer target;

    /** 优惠券规则 */
    private TemplateRule rule;

    /**
     * <h2>校验对象的合法性</h2>
     * */
    public boolean validate() {

        boolean stringValid = StringUtils.isNotEmpty(name)
                && StringUtils.isNotEmpty(logo)
                && StringUtils.isNotEmpty(desc);
        boolean enumValid = null != CouponCategory.ofValue(category)
                && null != ProductLine.ofValue(productLine)
                && null != DistributeTarget.ofValue(target);
        boolean numValid = count > 0 && userId > 0;

        return stringValid && enumValid && numValid && rule.validate();
    }
}
