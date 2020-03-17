package org.sllxgmm.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> EurekaApp </h1>
 * @date 2020/3/14 17:52v
 * @邮箱 1558078764@qq.com
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class,args);
    }
}
