package org.sllxgmm.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> TokenFilter </h1>
 * @date 2020/3/14 20:40
 * @邮箱 1558078764@qq.com
 * <h1>校验请求中的Token</h1>
 */
@Component
@Slf4j
public class TokenFilter  extends AbstractPreZuulFilter{
    @Override
    protected Object cRun() {
        HttpServletRequest request=requestContext.getRequest();
        log.info(String.format("%s request to %s",
                request.getMethod(), request.getRequestURL().toString()));

        Object token = request.getParameter("token");
        if (null == token) {
            log.error("error: token is empty");
            return fail(401, "error: token is empty");
        }

        return success();
    }

    @Override
    public int filterOrder() {
        return 1;
    }
}
