package com.dnslin.pay.controller;

import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.result.R;
import com.dnslin.pay.result.ResponseEnum;
import com.dnslin.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class PayController {
  @Autowired private PayService service;

  @PostMapping("/pay")
  /**
   * @Description: 订单接口
   * @param:
   * @return com.dnslin.pay.result.R
   * @author DnsLin
   * @date 2021/11/26 22:47
   */
  public R payDmf(@RequestBody GoodsDto goodsDto) {
    log.info("===进入pay接口===");
    String url = "";
    if (goodsDto != null) {
      url = service.createOrder(goodsDto);
    } else {
      log.error("订单实体类-->{}",goodsDto);
      return new R("订单信息为空", null);
    }
    return new R(ResponseEnum.SUCCESS, url);
  }
}
