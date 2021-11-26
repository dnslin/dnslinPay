package com.dnslin.pay.controller;

import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.result.R;
import com.dnslin.pay.result.ResponseEnum;
import com.dnslin.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PayController {
  @Autowired private PayService service;

  @PostMapping("/pay")
  /**
  *
   * @Description: 订单接口
   * @param:
   * @return com.dnslin.pay.result.R
   * @author DnsLin
   * @date 2021/11/26 22:47
  */
  public R payDmf(@RequestBody GoodsDto goodsDto) {
      if (goodsDto != null){
          String url = service.createOrder(goodsDto);
      }else {
          return new R("订单信息为空", null);
      }
      return new R(ResponseEnum.SUCCESS, null);
  }
}
