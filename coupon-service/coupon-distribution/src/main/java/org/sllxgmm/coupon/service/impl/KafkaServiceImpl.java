package org.sllxgmm.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.sllxgmm.coupon.constant.Constant;
import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.dao.CouponDao;
import org.sllxgmm.coupon.entity.Coupon;
import org.sllxgmm.coupon.service.IKafkaService;
import org.sllxgmm.coupon.vo.CouponKafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> KafkaServiceImpl </h1>
 * @date 2020/3/16 16:25
 * @邮箱 1558078764@qq.com
 */
@Service
@Slf4j
public class KafkaServiceImpl implements IKafkaService {
    private final CouponDao couponDao;

    public KafkaServiceImpl(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    @Override
    @KafkaListener(topics = {Constant.TOPIC},groupId = "sllxgmm_coupon_1")
    public void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage=Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()){
            Object message=kafkaMessage.get();
            CouponKafkaMessage couponInfo= JSON.parseObject(message.toString(),CouponKafkaMessage.class);
            log.info("Receive CouponKafkaMessage: {}", kafkaMessage.toString());

            CouponStatus status = CouponStatus.ofValue(couponInfo.getStatus());

            switch (status) {
                case USABLE:
                    break;
                case USED:
                    processUsedCoupons(couponInfo, status);
                    break;
                case EXPIRED:
                    processExpiredCoupons(couponInfo, status);
                    break;
            }

        }

    }

    /**
     * <h2>根据状态处理优惠券信息</h2>
     * */
    private void processCouponsByStatus(CouponKafkaMessage kafkaMessage,
                                        CouponStatus status) {
        List<Coupon> coupons = couponDao.findAllById(
                kafkaMessage.getCouponIds()
        );
        if (CollectionUtils.isEmpty(coupons)
                || coupons.size() != kafkaMessage.getCouponIds().size()) {
            log.error("Can Not Find Right Coupon Info: {}",
                    JSON.toJSONString(kafkaMessage));
            // TODO 发送邮件
            return;
        }

        coupons.forEach(c -> c.setStatus(status));
        log.info("CouponKafkaMessage Op Coupon Count: {}",
                couponDao.saveAll(coupons).size());
    }
    /**
     * <h2>处理已使用的用户优惠券</h2>
     * */
    private void processUsedCoupons(CouponKafkaMessage kafkaMessage,
                                    CouponStatus status) {
        // TODO 给用户发送短信
        processCouponsByStatus(kafkaMessage, status);
    }

    /**
     * <h2>处理过期的用户优惠券</h2>
     * */
    private void processExpiredCoupons(CouponKafkaMessage kafkaMessage,
                                       CouponStatus status) {
        // TODO 给用户发送推送
        processCouponsByStatus(kafkaMessage, status);
    }

}
