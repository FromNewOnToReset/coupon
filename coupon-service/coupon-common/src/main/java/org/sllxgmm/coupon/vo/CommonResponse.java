package org.sllxgmm.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> CommonResponse </h1>
 * @date 2020/3/14 21:20
 * @邮箱 1558078764@qq.com
 * <h1>通用响应对象定义</h1>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public CommonResponse(Integer code,String message){
        this.code=code;
        this.message=message;
    }


}
