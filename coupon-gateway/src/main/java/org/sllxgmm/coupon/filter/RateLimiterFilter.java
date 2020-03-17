package org.sllxgmm.coupon.filter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> RateLimiterFilter </h1>
 * @date 2020/3/14 20:52
 * @邮箱 1558078764@qq.com
 */
@Component
@Slf4j
@SuppressWarnings("all")
public class RateLimiterFilter extends AbstractPreZuulFilter {
    /** 每秒可以获取到两个令牌 */
    RateLimiter rateLimiter = RateLimiter.create(2.0);
    @Override
    protected Object cRun() {

        HttpServletRequest request = requestContext.getRequest();

        if (rateLimiter.tryAcquire()) {
            log.info("get rate token success");
            return success();
        } else {
            log.error("rate limit: {} , user: {}", request.getRequestURI(),request.getRemoteUser());
            return fail(402, "error: rate limit");
        }
    }

    @Override
    public int filterOrder() {
        return 2;
    }
}
