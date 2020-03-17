package org.sllxgmm.coupon.service;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sllxgmm.coupon.exception.CouponException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> TemplateBaseTest </h1>
 * @date 2020/3/15 19:43
 * @邮箱 1558078764@qq.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateBaseTest {

    @Autowired
    private ITemplateBaseService templateBaseService;


    @Test
    public void testBuildTemplateInfo() throws CouponException {

        System.out.println(JSON.toJSONString(
                templateBaseService.buildTemplateInfo( 10)));
        System.out.println(JSON.toJSONString(
                templateBaseService.buildTemplateInfo( 2)));
    }

    @Test
    public void testFindAllUsableTemplate() {

        System.out.println(JSON.toJSONString(
                templateBaseService.findAllUsableTemplate()
        ));
    }

    @Test
    public void testFindId2TemplateSDK() {

        System.out.println(JSON.toJSONString(
                templateBaseService.findIds2TemplateSDK(Arrays.asList(10, 2, 3))
        ));
    }
}
