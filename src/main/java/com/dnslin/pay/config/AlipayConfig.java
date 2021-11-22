package com.dnslin.pay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000117692768";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCaEex/et1F/kNSESX7+3xN3A07NVljyAR2Eq5hgW0AKxJ8puAmwVwA2DC4c0srJbEVlE5888tHCODRQgy5a8x0k/dxmq1sFM7iI9SKav91FALqraWCrHIr+UrbxdO9ZE+X+Pcjpu37Uvr/bx8okZHVT76WExSS53ACPEHaObg/tqvkg/wkQXICy745t2sqyXJx7U8WmPSkMiAyDdD1Mgdia+D4VdduktXMmg+SCSYKK/9FcagXbjinJwkRvb45zYWBbcIX03kgXt+O8PQeXJUp5sMrS9VRhvG3n3S6eWxlowVPIzTBg70OPRTSeT5YmKBpEqIRo6Fn/7IwNZJve3VZAgMBAAECggEAWpPJBxUze0/FQj0kqKSr6pZBDRsLJ3dS8BuBe398lom7/56XzxEERtbSpc3aVk59cmNTVXB6hJN6oGuFUYlHlQ3cfmRiKlV9X0OTU6Ed+DXAlTlrQYyiPqn70YLXPdP4Wy8aFFKyPHwHZh5tIguawd50/IPRJ6B6oEXOrT4bzRBrnLljbf//DXxOPUZ7z4asuP8KTdMwyPCAPwr5cfClwUm3JyK7GkR639q5/S6l2A3vTRm7KEOGdnTDZZu46Pgyp8oe1U5GV19w4gdv7Szs63Sn4LQ5JCHEAhQ2hiiVCaN+bEAUWLgRSoeKVGD7vIq0Uge9otgi19NTaxfKfvcPpQKBgQDfC8BKscMotkC58rsaVHTzP5l6bIE+AwOf68SCr6Y9uO5yG7WaFdoXDFT+RX81ns1gwPGm0LBqMWBoLmfO+pjMZ+XXPW0IFN0x2PN6CvYhCJV1xCgyrwExoKUTpsjhc7GjkWNsOSJfX8VTK0FvVe6WU0mCIjiAZiqk0YMzFLF82wKBgQCw1U1+8OR7j23leGGrBVechTDQIkWzleyZ6VZONK6c33qSs9Ev28px2OBfoTkyZlWsbjwO5bPvT1EhfldA2tQ/6C2fjMe6/gG2oZHnuuDqOWZGviw9ob0yTOpV86nFVz0zNLZ74oji7g4op4WFJ67/OuFwSb5D22TBKA8Vcc7S2wKBgDzNDkEOTh3l+KT3xws5ZJv+hU77DwwhdDZ+5ZWeXhnLNKzEYu+67zWDNhQOS8wJGwE592W606xPA3HC3KduZoKp+9x9sSAX1+hDtLNbE5cTqPJuY2rVi+TEXtuJho72o8Dr3AdfV8BZ8gdm7jJ0YVJ3J72iqHZP42YEethM1skrAoGAMpgk/x/vT4b/G3uw/hyeL5NQHwzfQn2v322Kmv+pwgqS6Gx4eqf5KU+zCsl3Mvtiod48YsrIC7126g1a65xwxAQrIY5HCzJnzRg3RcULWxinMbBxugSPvFeTaJo5xdOrF/6KA7ISFlOkS2963Vk4UaBIJjeDL6kTD8zlW+5fDacCgYEAlWMjjU6aw/RnaX3mhCW/xor78MjnhYpemEafnDWU4FcggFb8MEcZwIU0PeMRUx9V9O1TSEixbZc5j45LFXvIaPUW+ct0myigcLF+rPgSGBgBKFv6CfV0Xgn5IXMzvCtAplcezOcnfoqaB+r210KgeVEDn51Dxyy8yEKN6oQViCk=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmdmg50/e2XkvC418jZ7qGt/deUV0Ijnd6HHWPZ24QUM65EUyVfZdUQj8cLT4rSg/jmML6HSVBRd4OyjdFtHoi/BGpSs+8glUtIAgkzc0/aNvjxo+5/mhuJx40W1a66LZl3yHoHT2D5ZSLPp8OVGSoLdKiSha2yeMWvUf7RCsDJXOhyOkIcGDconhKVpXQ3eFd+8CFA0Ecs3T+UO1JAZWA/ohrnXkDRhkIypIcAE6gsKu4ooChAOX77RBI8qQAlZfp4dvI+WxBsNvb/jZin00cYKmNI1nculcWOL6QAelyi980UqbSNP5r+sTzzFDPIh5tyo43OC6WXieWyzP0lC4GwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://dnslin.vaiwan.com//api/pay/notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://dnslin.com";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

