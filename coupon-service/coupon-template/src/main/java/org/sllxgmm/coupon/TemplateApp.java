package org.sllxgmm.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> TemplateApp </h1>
 * @date 2020/3/15 15:06
 * @邮箱 1558078764@qq.com
 */
@EnableEurekaClient
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class TemplateApp {
    public static void main(String[] args) {
        SpringApplication.run(TemplateApp.class,args);
    }
}
