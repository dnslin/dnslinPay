package com.dnslin.pay.controller;

import cn.hutool.core.util.RandomUtil;
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
   *
   * @param:
   * @return com.dnslin.pay.result.R
   * @author DnsLin
   * @date 2021/11/26 22:47
   */
  public R payDmf(@RequestBody GoodsDto goodsDto) {
    log.info("===进入pay接口===");
    goodsDto.setGoodName("CPU");
    goodsDto.setOutTradeNo(RandomUtil.randomString(10));
    goodsDto.setGoodId(RandomUtil.randomString(5));
    goodsDto.setQuantity(5);
    String url = "";
    log.info("订单实体类-->{}", goodsDto);
    if (goodsDto != null) {
      url = service.createOrder(goodsDto);
    } else {
      log.error("订单实体类-->{}", goodsDto);
      return new R("400", "订单信息为空");
    }
    return new R(ResponseEnum.SUCCESS, url);
  }
}
