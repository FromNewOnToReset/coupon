package org.sllxgmm.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> settlementApp </h1>
 * @date 2020/3/16 20:42
 * @email 1558078764@qq.com
 */
@EnableEurekaClient
@SpringBootApplication
public class SettlementApp {
    public static void main(String[] args) {
        SpringApplication.run(SettlementApp.class,args);
    }
}
