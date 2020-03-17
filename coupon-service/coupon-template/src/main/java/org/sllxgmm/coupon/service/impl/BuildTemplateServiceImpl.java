package org.sllxgmm.coupon.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sllxgmm.coupon.dao.CouponTemplateDao;
import org.sllxgmm.coupon.entity.CouponTemplate;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.service.IAsyncService;
import org.sllxgmm.coupon.service.IBuildTemplateService;
import org.sllxgmm.coupon.vo.TemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> BuildTemplateServiceImpl </h1>
 * @date 2020/3/15 18:40
 * @邮箱 1558078764@qq.com
 */
@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {
    /** 异步服务 */
    private final IAsyncService asyncService;

    /** CouponTemplate Dao */
    private final CouponTemplateDao templateDao;

    @Autowired
    public BuildTemplateServiceImpl(IAsyncService asyncService,
                                    CouponTemplateDao templateDao) {
        this.asyncService = asyncService;
        this.templateDao = templateDao;
    }

    /**
     * <h2>创建优惠券模板</h2>
     * @param request {@link TemplateRequest} 模板信息请求对象
     * @return {@link CouponTemplate} 优惠券模板实体
     */
    @Override
    public CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException {

        // 参数合法性校验
        if (!request.validate()) {
            throw new CouponException("BuildTemplate Param Is Not Valid!");
        }

        // 判断同名的优惠券模板是否存在
        if (null != templateDao.findByName(request.getName())) {
            throw new CouponException("Exist Same Name Template!");
        }

        // 构造 CouponTemplate 并保存到数据库中
        CouponTemplate template = requestToTemplate(request);
        template = templateDao.save(template);

        // 根据优惠券模板异步生成优惠券码
        asyncService.asyncConstructCouponByTemplate(template);

        return template;
    }

    /**
     * <h2>将 TemplateRequest 转换为 CouponTemplate</h2>
     * */
    private CouponTemplate requestToTemplate(TemplateRequest request) {

        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getRule()
        );
    }
}
