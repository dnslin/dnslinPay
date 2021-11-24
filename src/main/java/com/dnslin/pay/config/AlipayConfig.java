package com.dnslin.pay.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class AlipayConfig {

    public static final String FORMAT = "json";
    public static final String CHARSET = "utf-8";
    public static final String SIGN_TYPE = "RSA2";

    /** 创建的应用ID */
    @Value("${pay.alipay.appId}")
    private String appId;
    /** 自己生成的应用私钥 */
    @Value("${pay.alipay.privateKey}")
    private String privateKey;
    /** 支付宝提供的支付宝公钥 */
    @Value("${pay.alipay.alipayPublicKey}")
    private String alipayPublicKey;
    /** 回调地址，需要公网且链接不需要重定向的，如：https://fastboot.shaines.cn/api/myalipay/trade-notify */
    @Value("${pay.alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${pay.alipay.serverUrl}")
    private String serverUrl;

}

