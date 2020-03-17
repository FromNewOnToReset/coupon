package org.sllxgmm.coupon.executor;

import org.sllxgmm.coupon.constant.RuleFlag;
import org.sllxgmm.coupon.vo.SettlementInfo;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> RuleExecutor </h1>
 * @date 2020/3/16 20:52
 * @email 1558078764@qq.com
 */
public interface RuleExecutor {
    /**
     * <h2>规则类型标记</h2>
     * @return {@link RuleFlag}
     * */
    RuleFlag ruleConfig();

    /**
     * <h2>优惠券规则的计算</h2>
     * @param settlement {@link SettlementInfo} 包含了选择的优惠券
     * @return {@link SettlementInfo} 修正过的结算信息
     * */
    SettlementInfo computeRule(SettlementInfo settlement);
}
