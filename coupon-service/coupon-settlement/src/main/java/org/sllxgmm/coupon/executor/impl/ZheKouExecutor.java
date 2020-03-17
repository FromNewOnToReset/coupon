package org.sllxgmm.coupon.executor.impl;

import lombok.extern.slf4j.Slf4j;
import org.sllxgmm.coupon.constant.RuleFlag;
import org.sllxgmm.coupon.executor.AbstractExecutor;
import org.sllxgmm.coupon.executor.RuleExecutor;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.sllxgmm.coupon.vo.SettlementInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> ZheKouExecutor </h1>
 * @date 2020/3/16 21:46
 * @email 1558078764@qq.com
 */
@Slf4j
@Component
public class ZheKouExecutor extends AbstractExecutor implements RuleExecutor {
    /**
     * <h2>规则类型标记</h2>
     *
     * @return {@link RuleFlag}
     */
    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.ZHEKOU;
    }

    /**
     * <h2>优惠券规则的计算</h2>
     *
     * @param settlement {@link SettlementInfo} 包含了选择的优惠券
     * @return {@link SettlementInfo} 修正过的结算信息
     */
    @Override
    public SettlementInfo computeRule(SettlementInfo settlement) {
        double goodsSum = retain2Decimals(goodsCostSum(
                settlement.getGoodsInfos()
        ));
        SettlementInfo probability = processGoodsTypeNotSatisfy(
                settlement, new BigDecimal(goodsSum)
        );
        if (null != probability) {
            log.debug("ZheKou Template Is Not Match GoodsType!");
            return probability;
        }

        // 折扣优惠券可以直接使用, 没有门槛
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos()
                .get(0).getTemplateSDK();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // 计算使用优惠券之后的价格
        settlement.setCost(BigDecimal.valueOf(Math.max(((goodsSum * (quota * 1.0 / 100))), minCost()))

        );
        log.debug("Use ZheKou Coupon Make Goods Cost From {} To {}",
                goodsSum, settlement.getCost());

        return settlement;
    }
}
