package com.dnslin.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.result.R;
import com.dnslin.pay.result.ResponseEnum;
import com.dnslin.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    String url = "";
    String msg = "";
    log.info("订单实体类-->{}", goodsDto);
    if (BeanUtil.isNotEmpty(goodsDto)) {
      try {
        if (StringUtils.isNotBlank(goodsDto.getEmail())){
          String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
          boolean matches = goodsDto.getEmail().matches(regex);
          if(matches){
            url = service.createOrder(goodsDto);
          }else {
            log.error("邮箱格式错误");
            msg = "邮箱格式错误";
            throw new IllegalStateException();
          }
        }else{
          log.error("邮箱为空");
          msg = "邮箱信息为空不发送通知邮件";
          throw new IllegalStateException();
        }
      } catch (Exception e) {
        url = service.createOrder(goodsDto);
        return new R("201",msg, url);
      }
    } else {
      log.error("订单实体类-->{}", goodsDto);
      return new R("400", "订单信息为空",null);
    }
    return new R(ResponseEnum.SUCCESS, url);
  }
}
