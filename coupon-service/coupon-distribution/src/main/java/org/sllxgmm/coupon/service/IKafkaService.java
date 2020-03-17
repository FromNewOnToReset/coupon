package org.sllxgmm.coupon.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * <h1>kafka接口定义</h1>
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> IKafkaService </h1>
 * @date 2020/3/16 11:38
 * @邮箱 1558078764@qq.com
 */
public interface IKafkaService {
    void consumeCouponKafkaMessage(ConsumerRecord<?,?>record);
}
