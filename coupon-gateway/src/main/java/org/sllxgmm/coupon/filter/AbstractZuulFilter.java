package org.sllxgmm.coupon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> AbstractZuulFilter </h1>
 * @date 2020/3/14 20:12v
 * @邮箱 1558078764@qq.com
 * <h1>通用的抽象过滤器类</h1>
 */
public abstract class AbstractZuulFilter extends ZuulFilter {
    //过滤器传递消息
    RequestContext requestContext;

    private final static String NEXT = "next";

    @Override
    public boolean shouldFilter() {
        RequestContext ctx=RequestContext.getCurrentContext();
        return (boolean) ctx.getOrDefault(NEXT,true);
    }

    @Override
    public Object run() throws ZuulException {
        requestContext=RequestContext.getCurrentContext();
        return cRun();
    }

    protected abstract Object cRun();

    Object fail(int code,String msg){
        requestContext.set(NEXT,false);
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        requestContext.setResponseStatusCode(code);
        requestContext.setResponseBody(String.format("{\"result\" : \"%s!\"}",msg));
        return null;
    }

    Object success(){
        requestContext.set(NEXT,true);
        return null;

    }
}
