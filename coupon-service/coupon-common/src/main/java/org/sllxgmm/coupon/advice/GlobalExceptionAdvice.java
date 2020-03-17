package org.sllxgmm.coupon.advice;

import org.sllxgmm.coupon.exception.CouponException;
import org.sllxgmm.coupon.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sllxgmm
 * @version 1.0
 * @描述 <h1> GlobalExceptionAdvice </h1>
 * @date 2020/3/14 22:33
 * @邮箱 1558078764@qq.com
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * <h2>对 CouponException 进行统一处理</h2>
     * */
    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handlerCouponException(
            HttpServletRequest req, CouponException ex
    ) {

        CommonResponse<String> response = new CommonResponse<>(
                -1, "business error");
        response.setData(ex.getMessage());
        return response;
    }

}
