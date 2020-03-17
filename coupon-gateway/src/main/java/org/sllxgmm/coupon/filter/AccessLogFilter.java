package org.sllxgmm.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> AccessLogFilter </h1>
 * @date 2020/3/14 21:01
 * @邮箱 1558078764@qq.com
 */
@Component
@Slf4j
public class AccessLogFilter extends AbstractPostZuulFilter {
    @Override
    protected Object cRun() {
        HttpServletRequest request = requestContext.getRequest();

        // 从 PreRequestFilter 中获取设置的请求时间戳
        Long startTime = (Long) requestContext.get("startTime");
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;

        // 从网关通过的请求都会打印日志记录: uri + duration
        log.info("uri: {}, duration: {}", uri, duration);

        return success();
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }
}
