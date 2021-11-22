package com.dnslin.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.dnslin.pay.config.AlipayConfig;
import com.dnslin.pay.result.R;
import com.dnslin.pay.result.ResponseEnum;
import com.dnslin.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/pay")
public class PayController {
    @Autowired
    private PayService payService;
    @GetMapping("/create")
    public R createAlipayOrder(String orderid , String orderprice, String orderdesc ) throws Exception {
        String result = payService.creatAlipayOrder(orderid, orderprice, orderdesc);
        System.out.println(result);
        return  new R(ResponseEnum.SUCCESS,result);
    }

    @PostMapping("/notify")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        System.out.println("1111111111");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        System.out.println(params);

        // 验签
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        // 在比较 其他的信息     订单描述 订单价格  商品信息
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            //支付宝交易号【支付宝订单】
            String trade_no = request.getParameter("trade_no");
            //交易状态
            String trade_status = request.getParameter("trade_status");
            String result = "fail";
            if(trade_status.equals("TRADE_FINISHED")){
                System.out.println("已经付款");
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                System.out.println(out_trade_no+"out_trade_no");
                result = payService.updateOrder(out_trade_no);
            }

            response.getWriter().write(result);

        }else {//验证失败
            response.getWriter().write("fuck");

        }
    }
}
