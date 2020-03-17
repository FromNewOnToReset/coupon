package org.sllxgmm.coupon.executor.impl;

import lombok.extern.slf4j.Slf4j;
import org.sllxgmm.coupon.constant.RuleFlag;
import org.sllxgmm.coupon.executor.AbstractExecutor;
import org.sllxgmm.coupon.executor.RuleExecutor;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.sllxgmm.coupon.vo.SettlementInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> ManJianExecutor </h1>
 * @date 2020/3/16 21:39
 * @email 1558078764@qq.com
 */
@Component
@Slf4j
public class ManJianExecutor extends AbstractExecutor implements RuleExecutor {
    /**
     * <h2>规则类型标记</h2>
     *
     * @return {@link RuleFlag}
     */
    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.MANJIAN;
    }

    /**
     * <h2>优惠券规则的计算</h2>
     *
     * @param settlement {@link SettlementInfo} 包含了选择的优惠券
     * @return {@link SettlementInfo} 修正过的结算信息
     */
    @Override
    public SettlementInfo computeRule(SettlementInfo settlement) {

        double goodsSum = retain2Decimals(
                goodsCostSum(settlement.getGoodsInfos())
        );
        SettlementInfo probability = processGoodsTypeNotSatisfy(
                settlement, new BigDecimal(goodsSum)
        );
        if (null != probability) {
            log.debug("ManJian Template Is Not Match To GoodsType!");
            return probability;
        }

        // 判断满减是否符合折扣标准
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos()
                .get(0).getTemplateSDK();
        double base = (double) templateSDK.getRule().getDiscount().getBase();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // 如果不符合标准, 则直接返回商品总价
        if (goodsSum < base) {
            log.debug("Current Goods Cost Sum < ManJian Coupon Base!");
            settlement.setCost(new BigDecimal(goodsSum));
            settlement.setCouponAndTemplateInfos(Collections.emptyList());
            return settlement;
        }

        // 计算使用优惠券之后的价格 - 结算
        settlement.setCost(BigDecimal.valueOf(Math.max(goodsSum - quota, minCost()))
        );
        log.debug("Use ManJian Coupon Make Goods Cost From {} To {}",
                goodsSum, settlement.getCost());

        return settlement;
    }
}
