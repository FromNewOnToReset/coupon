package org.sllxgmm.coupon.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> JacksonConfig </h1>
 * @date 2020/3/14 21:17
 * @邮箱 1558078764@qq.com
 */
@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper=new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
        ));
        return mapper;
    }
}
