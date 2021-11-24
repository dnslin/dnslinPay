package com.dnslin.pay.exception;



import com.dnslin.pay.result.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  业务异常类
 */
@Data
@NoArgsConstructor
@Builder
public class AppException extends RuntimeException{

    private String code;
    private String message;

    public AppException(ResponseEnum responseEnum){
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }
    public AppException(String code,String message){
        this.code = code;
        this.message = message;
    }
}
