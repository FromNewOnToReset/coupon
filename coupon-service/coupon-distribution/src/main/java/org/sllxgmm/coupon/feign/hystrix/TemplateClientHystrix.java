package org.sllxgmm.coupon.feign.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.sllxgmm.coupon.feign.TemplateClient;
import org.sllxgmm.coupon.vo.CommonResponse;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> TemplateClientHystrix  模板的熔断机制</h1>
 * @date 2020/3/16 17:06
 * @email 1558078764@qq.com
 */
@Component
@Slf4j
public class TemplateClientHystrix implements TemplateClient {
    @Override
    public CommonResponse<List<CouponTemplateSDK>> findAllUsableTemplate() {
        log.error("[eureka-client-coupon-template] findAllUsableTemplate " +
                "request error");
        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-template] request error",
                Collections.emptyList()
        );
    }

    @Override
    public CommonResponse<Map<Integer, CouponTemplateSDK>> findIds2TemplateSDK(Collection<Integer> ids) {

        log.error("[eureka-client-coupon-template] findIds2TemplateSDK" +
                "request error");

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-template] request error",
                new HashMap<>()
        );
    }
}
