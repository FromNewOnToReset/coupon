package org.sllxgmm.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> GoodsInfo </h1>
 * @date 2020/3/16 12:03
 * @邮箱 1558078764@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {
    /** 商品类型*/
    private Integer type;


    /** 商品价格*/
    private BigDecimal decimalPrice;

    /** 商品数量 */
    private Integer count;

}
