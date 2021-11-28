package com.dnslin.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.dnslin.pay.config.AlipayConfig;
import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.result.R;
import com.dnslin.pay.service.MallService;
import com.dnslin.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class PayController {
  @Autowired private PayService service;

  @Autowired private AlipayConfig config;

  @Autowired private MallService mallService;
  @PostMapping("/pay")
  /**
   * @Description: 订单接口
   *
   * @param:
   * @return com.dnslin.pay.result.R
   * @author DnsLin
   * @date 2021/11/26 22:47
   */
  public R payDmf(@RequestBody GoodsDto goodsDto) {
    log.info("===进入pay接口===");
    String url = "";
    String msg = "";
    log.info("订单实体类-->{}", goodsDto);
    if (BeanUtil.isNotEmpty(goodsDto)) {
      try {
        if (StringUtils.isNotBlank(goodsDto.getEmail())) {
          String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
          boolean matches = goodsDto.getEmail().matches(regex);
          if (matches) {
            url = service.createOrder(goodsDto).getOther();
            msg = mallService.senderMail(goodsDto);
          } else {
            log.error("邮箱格式错误");
            msg = "邮箱格式错误";
            throw new IllegalStateException();
          }
        } else {
          log.error("邮箱为空");
          msg = "邮箱信息为空,不发送通知邮件";
          throw new IllegalStateException();
        }
      } catch (Exception e) {
        url = service.createOrder(goodsDto).getOther();
        return new R("201", msg, url);
      }
    } else {
      log.error("订单实体类-->{}", goodsDto);
      return new R("400", "订单信息为空", null);
    }
    return new R("202",msg,url);
  }

  @PostMapping("/notify")
  public String tradeNotify(HttpServletRequest request) {
    Map<String, String[]> parameterMap = request.getParameterMap();
    Map<String, String> params = new HashMap<>(32);
    parameterMap.forEach((key, val) -> params.put(key, String.join(",", val)));
    log.info("============>>> trade-notifyparams:{}", JSON.toJSONString(params));
    boolean check;
    try {
      // 必须要验签
      check =
          AlipaySignature.rsaCheckV1(
              params, config.getAlipayPublicKey(), AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
      log.info("============>>> check: [{}]", check);
    } catch (Exception e) {
      log.warn("tradeNotify exception", e);
      return "failure";
    }
    if (!check) {
      return "rsaCheckV1 = false";
    }
    return "success";
  }
}
