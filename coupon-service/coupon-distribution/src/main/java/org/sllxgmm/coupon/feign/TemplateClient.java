package org.sllxgmm.coupon.feign;

import org.sllxgmm.coupon.vo.CommonResponse;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> TemplateClient </h1>
 * @date 2020/3/16 16:52
 * @邮箱 1558078764@qq.com
 */
@FeignClient(value = "eureka-client-coupon-template")
public interface TemplateClient {
    /**
     * <h1>查找所有的可用模板</h1>
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/coupon-template/template/sdk/all")
    CommonResponse<List<CouponTemplateSDK>> findAllUsableTemplate();

    @RequestMapping(method = RequestMethod.GET,value = "/coupon-template/template/sdk/infos")
    CommonResponse<Map<Integer, CouponTemplateSDK>> findIds2TemplateSDK( @RequestParam("ids") Collection<Integer> ids);

}
