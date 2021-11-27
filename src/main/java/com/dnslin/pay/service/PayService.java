package com.dnslin.pay.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.dnslin.pay.config.AlipayConfig;
import com.dnslin.pay.config.Snowflake;
import com.dnslin.pay.exception.AppException;
import com.dnslin.pay.model.AlipayDto;
import com.dnslin.pay.model.GoodsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class PayService {

  @Autowired private AlipayConfig alipayConfig;

  private static final String DISCOUNT = "10";

  private static final String SYSTEMS_CREVICE_PROVIDER = "2088511833207846";

  Snowflake snowflake = new Snowflake(0L, 0L);
  /**
   * @Description: 发送订单请求
   *
   * @param: goodsDTO
   * @return String
   * @author DnsLin
   * @date 2021/11/26 22:58
   */
  public String createOrder(GoodsDto goodsDTO) {
    if (goodsDTO == null) {
      throw new AppException("400", "订单信息为空");
    }
    // 创建支付宝请求客户端
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
    AlipayTradePrecreateResponse response = null;
    try {
      response = alipayClient.execute(request);
    } catch (AlipayApiException e) {
      e.printStackTrace();
      throw new AppException(e.getErrMsg());
    }
    if (response.isSuccess()) {
      log.info("调用成功");
    } else {
      throw new AppException(response.getCode(), response.getMsg());
    }
    AlipayDto alipayDto = JSON.parseObject(response.getBody(), AlipayDto.class);
    if (alipayDto != null) {
      return alipayDto.getAlipayTradePrecreateResponse().getQrCode();
    } else {
      throw new AppException("400", "返回为NULL");
    }
  }

  /**
   * @Description: 创建订单信息
   *
   * @param: goodsDTO
   * @return AlipayTradePrecreateRequest
   * @author DnsLin
   * @date 2021/11/26 22:58
   */
  private AlipayTradePrecreateRequest getAlipayTradePrecreateRequest(GoodsDto goodsDTO) {
    if (goodsDTO.getPrice().compareTo(BigDecimal.valueOf(300)) != -1) {
      throw new AppException("400", "金额数目过大");
    }
    goodsDTO.setGoodName("CPU");
    goodsDTO.setOutTradeNo(snowflake.nextId().toString());
    goodsDTO.setGoodId(IdUtil.simpleUUID());
    goodsDTO.setQuantity(5);
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

    // 营销信息，按需传入
    JSONObject promoParams = new JSONObject();
    promoParams.put("promo_params_key", DISCOUNT);
    bizContent.put("promo_params", promoParams);

    // 扩展信息，按需传入
    JSONObject extendParams = new JSONObject();
    extendParams.put("sys_service_provider_id", SYSTEMS_CREVICE_PROVIDER);
    bizContent.put("extend_params", extendParams);
    request.setBizContent(bizContent.toString());
    return request;
  }
}
