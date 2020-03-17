package org.sllxgmm.coupon.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.executor.ExecuteManager;
import org.sllxgmm.coupon.vo.SettlementInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> SettlementController </h1>
 * @date 2020/3/16 22:37
 * @email 1558078764@qq.com
 */
@Slf4j
@RestController
public class SettlementController {

    /** 结算规则执行管理器 */
    private final ExecuteManager executeManager;

    @Autowired
    public SettlementController(ExecuteManager executeManager) {
        this.executeManager = executeManager;
    }

    /**
     * <h2>优惠券结算</h2>
     * 127.0.0.1:7050/coupon-settlement/settlement/compute
     * */
    @PostMapping("/settlement/compute")
    public SettlementInfo computeRule(@RequestBody SettlementInfo settlement)
            throws CouponException {
        log.info("settlement: {}", JSON.toJSONString(settlement));
        return executeManager.computeRule(settlement);
    }
}
