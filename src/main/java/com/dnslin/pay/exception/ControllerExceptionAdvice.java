package com.dnslin.pay.exception;



import com.dnslin.pay.result.R;
import com.dnslin.pay.result.ResponseEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

// 这是一个controller 的增强类【专门在controller 抛出异常的时候  增强】
@ControllerAdvice
public class ControllerExceptionAdvice {

    /**
     *  如果抛出的是 AppException 的增强
     *  返回值 就是返回的页面
     *  参数： controller 抛出的异常
     */
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public R appExceptionHandler(AppException ex, HttpServletRequest request){

       return new R(ex.getCode(),ex.getMessage(),null);
    }

    /**
     *  如果抛出的是Exception 的增强
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exceptionHandler(Exception ex, HttpServletRequest request){

        ex.printStackTrace();
        return new R(ResponseEnum.SYSTEM_ERROR,null);
    }
    /**
     *  抛出令牌不匹配的一场
     */
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public R signatureExceptionHandler(MalformedJwtException ex, HttpServletRequest request){
        ex.printStackTrace();
        return new R(ResponseEnum.TOKEN_ERROR,null);
    }

    /**
     *  令牌超时
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public R expiredJwtExceptionHandler(ExpiredJwtException ex, HttpServletRequest request){
        ex.printStackTrace();
        return new R(ResponseEnum.TOKEN_TIMEOUT,null);
    }

}
