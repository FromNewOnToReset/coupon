package org.sllxgmm.coupon.advice;

import org.sllxgmm.coupon.annotation.IgnoreResponseAdvice;
import org.sllxgmm.coupon.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CommonResponseDataAdvice </h1>
 * @date 2020/3/14 21:26
 * @邮箱 1558078764@qq.com
 */
@RestControllerAdvice
@SuppressWarnings("all")
public class CommonResponseDataAdvice implements ResponseBodyAdvice {
    /**
     *<h2>判断是否需要对响应进行处理</h2>
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        // 如果当前方法所在的类标识了 @IgnoreResponseAdvice 注解, 不需要处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }
        // 如果当前方法标识了 @IgnoreResponseAdvice 注解, 不需要处理
        if (methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        //执行下面的方法
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        CommonResponse<Object> response=new CommonResponse<>(0,"");
        if(null==o){
            return response;
        }else if(o instanceof CommonResponse){
            response= (CommonResponse<Object>) o;
        }else {
            response.setData(o);
        }
        return response;
    }
}
