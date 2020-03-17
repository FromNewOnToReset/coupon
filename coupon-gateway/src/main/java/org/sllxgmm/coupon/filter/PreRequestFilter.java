package org.sllxgmm.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> PreRequestFilter </h1>
 * @date 2020/3/14 21:00
 * @邮箱 1558078764@qq.com
 */
@Slf4j
@Component
public class PreRequestFilter  extends AbstractPreZuulFilter{
    @Override
    protected Object cRun() {
        requestContext.set("startTime",System.currentTimeMillis());
        return success();
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
