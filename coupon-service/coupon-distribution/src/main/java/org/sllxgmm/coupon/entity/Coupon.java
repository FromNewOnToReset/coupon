package org.sllxgmm.coupon.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sllxgmm.coupon.constant.CouponStatus;
import org.sllxgmm.coupon.converter.CouponConverter;
import org.sllxgmm.coupon.serialization.CouponSerialize;
import org.sllxgmm.coupon.vo.CouponTemplateSDK;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> Coupon </h1>
 * @date 2020/3/16 10:52
 * @邮箱 1558078764@qq.com
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
@JsonSerialize(using = CouponSerialize.class)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;

    @Column(name = "template_id",nullable = false)
    private Integer templateId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    /** 优惠券码 */
    @Column(name = "coupon_code",nullable = false)
    @Convert(converter = CouponConverter.class)
    private String couponCode;

    @CreatedDate
    @Column(name = "assign_time",nullable = false)
    private Date assignTime;

    @Column(name = "status",nullable = false)
    @Basic
    private CouponStatus status;

    /** 用户优惠券对应得模板信息*/
    @Transient
    private CouponTemplateSDK templateSDK;

    /**无效的coupon对象*/
    public static Coupon invalidCoupon(){
        Coupon coupon=new Coupon();
        coupon.setId(-1);
        return coupon;
    }
    public Coupon(Integer templateId ,Long userId,String couponCode,CouponStatus status){
        this.templateId=templateId;
        this.userId=userId;
        this.couponCode=couponCode;
        this.status=status;
    }
}
