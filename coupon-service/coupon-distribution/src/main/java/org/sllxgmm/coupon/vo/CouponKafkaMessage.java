package org.sllxgmm.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CouponKafkaMessage </h1>
 * @date 2020/3/16 16:29
 * @邮箱 1558078764@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponKafkaMessage {

    private Integer status;

    private List<Integer> couponIds;
}
