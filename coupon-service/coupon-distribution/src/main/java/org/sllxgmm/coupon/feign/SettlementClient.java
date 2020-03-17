package org.sllxgmm.coupon.feign;

import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.vo.CommonResponse;
import org.sllxgmm.coupon.vo.SettlementInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> SettlementClient </h1>
 * @date 2020/3/16 17:00
 * @邮箱 1558078764@qq.com
 */
@FeignClient(value = "eureka-client-coupon-settlement")
public interface SettlementClient {

    @RequestMapping(method = RequestMethod.POST,value = "/coupon-settlement/settlement/compute")
    CommonResponse<SettlementInfo> computeRule(@RequestBody SettlementInfo settlementInfo) throws CouponException;
}
