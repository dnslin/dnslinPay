package com.dnslin.pay.controller;

import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.model.payDto;
import com.dnslin.pay.result.R;
import com.dnslin.pay.result.ResponseEnum;
import com.dnslin.pay.service.PayService;
import io.swagger.annotations.Api;
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
   * @Description:
   * @param:
   * @return com.dnslin.pay.result.R
   * @author DnsLin
   * @date 2021/11/26 22:47
  */
  public R payDmf(@RequestBody GoodsDto goodsDto) {

   return new R(ResponseEnum.SUCCESS, null);
  }
}
