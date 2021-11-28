package com.dnslin.pay.service;

import cn.hutool.core.util.IdUtil;
import com.dnslin.pay.exception.AppException;
import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>/**
 *
 * @author: DnsLin
 * @Title: MallService
 * @ProjectName: dnslinPay
 * @Description:
 * @date: 2021/11/27 16:53
 */
@Service
@Slf4j
public class MallService {
  @Autowired private MailUtil mailUtil;

  public String senderMail(GoodsDto goodsDto) {
    // 发送邮件
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    String subject = "DnsLinPay订单通知";
    String emailTemplate = "registerTemplate";
    String code = IdUtil.simpleUUID().toString();
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("OrderContents", goodsDto.getGoodName());
    dataMap.put("OrderId", goodsDto.getGoodId());
    dataMap.put("OrderPrice", goodsDto.getPrice());
    dataMap.put("creadTime", sdf.format(new Date()));
    dataMap.put("payTime", sdf.format(new Date()));
    dataMap.put("remask", StringUtils.isNotEmpty(goodsDto.getRemark())?goodsDto.getEmail():goodsDto.getRemark());
    try {
      log.info("进入方法");
      mailUtil.sendTemplateMail(goodsDto.getEmail(), subject, emailTemplate, dataMap);
    } catch (Exception e) {
      log.error("退出方法,异常信息{}",e.getMessage());
      throw new AppException("503",e.getMessage());
    }
    return "已发送邮件至"+goodsDto.getEmail();
  }
}
