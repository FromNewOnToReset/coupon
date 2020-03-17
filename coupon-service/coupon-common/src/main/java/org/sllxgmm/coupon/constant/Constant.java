package org.sllxgmm.coupon.constant;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> Constant </h1>
 * @date 2020/3/15 15:57
 * @邮箱 1558078764@qq.com
 */
public class Constant {


    /**
     * Kafka 消息的 Topic
     */
    public static final String TOPIC = "sllxgmm_user_coupon_op";

    /**
     * <h2>Redis Key 前缀定义</h2>
     */
    public static class RedisPrefix {

        /**
         * 优惠券码 key 前缀
         */
        public static final String COUPON_TEMPLATE =
                "sllxgmm_coupon_template_code_";

        /**
         * 用户当前所有可用的优惠券 key 前缀
         */
        public static final String USER_COUPON_USABLE =
                "sllxgmm_user_coupon_usable_";

        /**
         * 用户当前所有已使用的优惠券 key 前缀
         */
        public static final String USER_COUPON_USED =
                "sllxgmm_user_coupon_used_";

        /**
         * 用户当前所有已过期的优惠券 key 前缀
         */
        public static final String USER_COUPON_EXPIRED =
                "sllxgmm_user_coupon_expired_";
    }
}
