package org.sllxgmm.coupon.dao;

import org.sllxgmm.coupon.entity.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponTemplateDao </h1>
 * @date 2020/3/15 17:22
 * @邮箱 1558078764@qq.com
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate,Integer> {

    /**
     * <h2>根据模板名称查询模板</h2>
     * where name = ...
     * */
    CouponTemplate findByName(String name);

    /**
     * <h2>根据 available 和 expired 标记查找模板记录</h2>
     * where available = ... and expired = ...
     * */
    List<CouponTemplate> findAllByAvailableAndExpired(
            Boolean available, Boolean expired
    );

    /**
     * <h2>根据 expired 标记查找模板记录</h2>
     * where expired = ...
     * */
    List<CouponTemplate> findAllByExpired(Boolean expired);
}
