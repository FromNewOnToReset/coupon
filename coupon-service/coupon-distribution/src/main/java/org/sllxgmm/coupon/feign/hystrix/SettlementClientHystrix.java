package org.sllxgmm.coupon.feign.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.feign.SettlementClient;
import org.sllxgmm.coupon.vo.CommonResponse;
import org.sllxgmm.coupon.vo.SettlementInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> SettlementClientHystrix </h1>
 * @date 2020/3/16 17:10
 * @email 1558078764@qq.com
 */
@Component
@Slf4j
public class SettlementClientHystrix implements SettlementClient {
    @Override
    public CommonResponse<SettlementInfo> computeRule(SettlementInfo settlementInfo) throws CouponException {
        log.error("[eureka-client-coupon-settlement] computeRule" +
                "request error");

        settlementInfo.setEmploy(false);
        settlementInfo.setCost(BigDecimal.valueOf(-1.0));

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-settlement] request error",
                settlementInfo
        );
    }
}
