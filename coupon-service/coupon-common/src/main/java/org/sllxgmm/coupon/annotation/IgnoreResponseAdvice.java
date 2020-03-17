package org.sllxgmm.coupon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> IgnoreResponseAdvice </h1>
 * @date 2020/3/14 21:24
 * @邮箱 1558078764@qq.com
 * <h1>忽略统一响应定义</h1>
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
