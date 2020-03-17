package org.sllxgmm.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> DistributionApp </h1>
 * @date 2020/3/16 10:22
 * @邮箱 1558078764@qq.com
 * <h1>分发系统的启动入口</h1>
 */
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableCircuitBreaker
@EnableJpaAuditing
public class DistributionApp {
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(DistributionApp.class);
    }
}
