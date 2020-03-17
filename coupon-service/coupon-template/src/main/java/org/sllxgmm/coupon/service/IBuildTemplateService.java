package org.sllxgmm.coupon.service;

import org.sllxgmm.coupon.entity.CouponTemplate;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.vo.TemplateRequest;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> IBuildTemplateService </h1>
 * @date 2020/3/15 17:59
 * @邮箱 1558078764@qq.com
 */
public interface IBuildTemplateService {
    /**
     * <h2>创建优惠券模板</h2>
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     * */
    CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException;
}
