package com.dnslin.pay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.dnslin.pay.config.AlipayConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayService {
//    @Resource(name = "JdkRedisTemplate")
//    private RedisTemplate redisTemplate;
    public String creatAlipayOrder(String orderid,String orderprice,String orderdesc) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderid;
        //付款金额，必填
        String total_amount = orderprice;
        //订单名称，必填
        String subject = "秒杀订单";
        //商品描述，可空
        String body = orderdesc;

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    /*public String updateOrder(String orderid){
        System.out.println(orderid+"orderid");
        Object o = redisTemplate.opsForHash().get("today_orders", orderid);
        TbSeckillOrder order = new TbSeckillOrder();
        BeanUtils.copyProperties(o,order);
        // 解决消息重复消费问题

        if(order.getStatus().equals("1")==false){
            return "success";
        }
        order.setStatus("2");
        redisTemplate.opsForHash().put("today_orders",orderid,order);
        Object os = redisTemplate.opsForHash().get("today_orders", orderid);
        TbSeckillOrder orders = new TbSeckillOrder();
        BeanUtils.copyProperties(os,orders);
        System.out.println(orders+"----------****");
        return "success";
    }*/
}
