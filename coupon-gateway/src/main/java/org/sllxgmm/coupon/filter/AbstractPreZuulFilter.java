package org.sllxgmm.coupon.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> AbstractPreZuulFilter </h1>
 * @date 2020/3/14 20:36
 * @邮箱 1558078764@qq.com
 */
public abstract class AbstractPreZuulFilter extends AbstractZuulFilter{
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
}
