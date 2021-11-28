package com.dnslin.pay;
/**
 * @author: DnsLin @Title: test @ProjectName: dnslinPay @Description:
 * @date: 2021/11/24 21:56
 */

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.alipay.api.AlipayApiException;
import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.service.PayService;
import com.dnslin.pay.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: DnsLin @Title: test @ProjectName: dnslinPay @Description:
 * @date: 2021/11/24 21:56
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class test {
  @Autowired
  private PayService pay;

  @Autowired
  private MailUtil mailUtil;

  private final String receiver = "3164419779@qq.com";

  @Test
  public void test() throws AlipayApiException {
    GoodsDto goodsDTO = new GoodsDto().builder().goodId("dsl123456").goodName("CPU").outTradeNo("DSL123456").price(BigDecimal.ONE).quantity(5).build();

    pay.createOrder(goodsDTO);
  }
  @Test
  public void test02(){
    System.out.println(NumberUtil.generateRandomNumber(10, 100000, 3).toString());
  }

  @Test
  public void test03(){
    //发送邮件
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    String subject = "SecondGoodTrade用户注册";
    String emailTemplate = "registerTemplate";
    String code = IdUtil.simpleUUID().toString();
    Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("OrderContents", receiver);
    dataMap.put("OrderId", code);
    dataMap.put("OrderPrice", sdf.format(new Date()));
    dataMap.put("creadTime", sdf.format(new Date()));
    dataMap.put("payTime", sdf.format(new Date()));
    dataMap.put("remask", sdf.format(new Date()));
    try {
      log.info("进入方法");
      mailUtil.sendTemplateMail(receiver, subject, emailTemplate, dataMap);
    } catch (Exception e) {
      log.error("退出方法");
      e.printStackTrace();
      return;
    }
  }
}
