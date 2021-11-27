package com.dnslin.pay.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
// 创建统一的返回格式
public class R<T> {

    private String code;
    private String message;
    private T data;

    public R(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }
    public R(String code, T data) {
        this.code = code;
        this.data = data;
    }
    public R(String code,String message ,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
