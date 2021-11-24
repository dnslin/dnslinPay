package com.dnslin.pay.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import com.dnslin.pay.config.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayService {

  @Autowired private AlipayConfig alipayConfig;

  public void createOrder(String outTradeNo, Integer price, String goodName)
      throws AlipayApiException {
    AlipayClient alipayClient =
        new DefaultAlipayClient(
            alipayConfig.getServerUrl(),
            alipayConfig.getAppId(),
            alipayConfig.getPrivateKey(),
            AlipayConfig.FORMAT,
            AlipayConfig.CHARSET,
            alipayConfig.getAlipayPublicKey(),
            AlipayConfig.SIGN_TYPE);
    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
    request.setNotifyUrl("");
    JSONObject bizContent = new JSONObject();
    bizContent.put("out_trade_no", "outTradeNo");
    bizContent.put("total_amount", 0.01);
    bizContent.put("subject", "goodName");

    //// 商品明细信息，按需传入
    JSONArray goodsDetail = new JSONArray();
    JSONObject goods1 = new JSONObject();
    goods1.put("goods_id", "goodsNo1");
    goods1.put("goods_name", "子商品1");
    goods1.put("quantity", 1);
    goods1.put("price", 0.01);
    goodsDetail.add(goods1);
    bizContent.put("goods_detail", goodsDetail);

    // 扩展信息，按需传入
    JSONObject extendParams = new JSONObject();
    extendParams.put("sys_service_provider_id", "2088511833207846");
    bizContent.put("extend_params", extendParams);

    // 结算信息，按需传入
    JSONObject settleInfo = new JSONObject();
    JSONArray settleDetailInfos = new JSONArray();
    JSONObject settleDetail = new JSONObject();
    settleDetail.put("trans_in_type", "defaultSettle");
    settleDetail.put("amount", 0.01);
    settleDetailInfos.add(settleDetail);
    settleInfo.put("settle_detail_infos", settleDetailInfos);
    bizContent.put("settle_info", settleInfo);

    // 业务参数信息，按需传入
    JSONObject businessParams = new JSONObject();
    businessParams.put("busi_params_key", "busiParamsValue");
    bizContent.put("business_params", businessParams);

    // 营销信息，按需传入
    JSONObject promoParams = new JSONObject();
    promoParams.put("promo_params_key", "promoParamsValue");
    bizContent.put("promo_params", promoParams);

    request.setBizContent(bizContent.toString());
    AlipayTradePrecreateResponse response = alipayClient.execute(request);
    if (response.isSuccess()) {
      System.out.println("调用成功");
    } else {
      System.out.println("调用失败");
    }
  }
}
