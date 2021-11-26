package com.dnslin.pay.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import com.dnslin.pay.config.AlipayConfig;
import com.dnslin.pay.exception.AppException;
import com.dnslin.pay.model.AlipayDto;
import com.dnslin.pay.model.GoodsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayService {

  @Autowired private AlipayConfig alipayConfig;

  private final static String DISCOUNT = "10";

  private final static String SYSTEMSERVICEPROVIDER= "2088511833207846";
  public String createOrder(GoodsDto goodsDTO) throws AlipayApiException {
    AlipayClient alipayClient =
        new DefaultAlipayClient(
            alipayConfig.getServerUrl(),
            alipayConfig.getAppId(),
            alipayConfig.getPrivateKey(),
            AlipayConfig.FORMAT,
            AlipayConfig.CHARSET,
            alipayConfig.getAlipayPublicKey(),
            AlipayConfig.SIGN_TYPE);
    AlipayTradePrecreateRequest request = getAlipayTradePrecreateRequest(goodsDTO);
    AlipayTradePrecreateResponse response = alipayClient.execute(request);
    if (response.isSuccess()) {
      log.info("调用成功");
    } else {
      throw new AppException(response.getCode(), response.getMsg());
    }
    AlipayDto alipayDto = JSON.parseObject(response.getBody(), AlipayDto.class);
    return "https://api.qrserver.com/v1/create-qr-code/?size=150×150&data="
        + alipayDto.getAlipayTradePrecreateResponse().getQrCode();
  }

  private AlipayTradePrecreateRequest getAlipayTradePrecreateRequest(GoodsDto goodsDTO) {
    AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
    request.setNotifyUrl("");
    JSONObject bizContent = new JSONObject();
    bizContent.put("out_trade_no", goodsDTO.getOutTradeNo());
    bizContent.put("total_amount", goodsDTO.getPrice());
    bizContent.put("subject", goodsDTO.getGoodName());

    // 商品明细信息，按需传入
    JSONArray goodsDetail = new JSONArray();
    JSONObject goods = new JSONObject();
    goods.put("goods_id", goodsDTO.getGoodId());
    goods.put("goods_name", goodsDTO.getGoodName());
    goods.put("quantity", goodsDTO.getQuantity());
    goods.put("price", goodsDTO.getPrice());
    goodsDetail.add(goods);
    bizContent.put("goods_detail", goodsDetail);

    // 扩展信息，按需传入
    JSONObject extendParams = new JSONObject();
    extendParams.put("sys_service_provider_id", SYSTEMSERVICEPROVIDER);
    bizContent.put("extend_params", extendParams);

    // 营销信息，按需传入
    JSONObject promoParams = new JSONObject();
    promoParams.put("promo_params_key", DISCOUNT);
    bizContent.put("promo_params", promoParams);

    request.setBizContent(bizContent.toString());
    return request;
  }
}
