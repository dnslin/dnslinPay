package com.dnslin.pay;
/**
 * @author: DnsLin @Title: test @ProjectName: dnslinPay @Description:
 * @date: 2021/11/24 21:56
 */
import com.alipay.api.AlipayApiException;
import com.dnslin.pay.model.GoodsDto;
import com.dnslin.pay.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author: DnsLin @Title: test @ProjectName: dnslinPay @Description:
 * @date: 2021/11/24 21:56
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class test {
  @Autowired
  private PayService pay;

  @Test
  public void test() throws AlipayApiException {
    GoodsDto goodsDTO = new GoodsDto().builder().goodId("dsl123456").goodName("CPU").outTradeNo("DSL123456").price(BigDecimal.ONE).quantity(5).build();

    pay.createOrder(goodsDTO);
  }
}
