package org.sllxgmm.coupon.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.sllxgmm.coupon.constant.Constant;
import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.constant.PeriodType;
import org.sllxgmm.coupon.entity.Coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sllxgmm
 * @version 1.0
 * @desc <h1> CouponClassify </h1>
 * @date 2020/3/16 17:14
 * @email 1558078764@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponClassify {

    //可以使用的
    private List<Coupon> usableCoupons;
    //已经使用的
    private List<Coupon> usedCoupons;
    //过期的
    private List<Coupon> expiredCoupons;

    public static CouponClassify classify(List<Coupon> coupons){
        List<Coupon> usable=new ArrayList<>(coupons.size());
        List<Coupon> used=new ArrayList<>(coupons.size());
        List<Coupon> expired=new ArrayList<>(coupons.size());
        coupons.forEach(c->{
            boolean isTimeExpire;
            long curTime=new Date().getTime();
            if(c.getTemplateSDK().getRule().getExpiration().getPeriod().equals(PeriodType.REGULAR.getCode())){
                isTimeExpire=c.getTemplateSDK().getRule().getExpiration().getDeadline()<=curTime;
            }
            else {
                isTimeExpire= DateUtils.addDays(c.getAssignTime(),c.getTemplateSDK().getRule().getExpiration().getGap()).getTime()<=curTime;
            }
            if(c.getStatus()== CouponStatus.USED){
                used.add(c);
            }else if(c.getStatus()==CouponStatus.EXPIRED||isTimeExpire){
                expired.add(c);
            }else {
                usable.add(c);
            }
        });

        return new CouponClassify(usable,used,expired);
    }

}
