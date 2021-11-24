package com.dnslin.pay.model;/**
 * @author: DnsLin
 * @Title: AlipayDto
 * @ProjectName: dnslinPay
 * @Description:
 * @date: 2021/11/24 22:29
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: DnsLin
 * @Title: AlipayDto
 * @ProjectName: dnslinPay
 * @Description:
 * @date: 2021/11/24 22:29
 */
@NoArgsConstructor
@Data
public class AlipayDto {

    @JsonProperty("alipay_trade_precreate_response")
    private AlipayTradePrecreateResponseDTO alipayTradePrecreateResponse;
    @JsonProperty("sign")
    private String sign;

    @NoArgsConstructor
    @Data
    public static class AlipayTradePrecreateResponseDTO {
        @JsonProperty("code")
        private String code;
        @JsonProperty("msg")
        private String msg;
        @JsonProperty("out_trade_no")
        private String outTradeNo;
        @JsonProperty("qr_code")
        private String qrCode;
    }
}
