package org.sllxgmm.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> ZuulApp </h1>
 * @date 2020/3/14 20:03v
 * @邮箱 1558078764@qq.com
 * <h1>网关入口</h1>
 * 1. @EnableEurekaClient 标识当前的应用是Zuul Server
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulApp {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApp.class,args);
    }
}
