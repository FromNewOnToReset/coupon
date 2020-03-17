package org.sllxgmm.coupon.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> WebConfiguration </h1>
 * @date 2020/3/14 21:13
 * @邮箱 1558078764@qq.com
 * <h1>定制http消息转换器</h1>
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();

        converters.add(new MappingJackson2HttpMessageConverter());


    }
}
