package com.dnslin.pay.model;

import lombok.*;

import java.math.BigDecimal;

/**
 *
 *
 * @author: DnsLin @Title: GoodsDto @ProjectName: dnslinPay @Description:
 * @date: 2021/11/24 21:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GoodsDto {

  /**
   * 商品ID
   */
  private String goodId;

  /**
   * 订单ID
   */
  private String outTradeNo;

  /**
   * 商品单价
   */
  private BigDecimal price;

  /**
   * 商品名
   */
  private String goodName;

  /**
   * 商品数量
   */
  private Integer quantity;

  /**
   * 商品详情
   */
  private Integer goodsDetail;

  /**
   * 备注
   */
  private String remark;

  /**
   * 折扣
   */
  private String discount;

  /**
   * 邮箱
   */
  private String email;
}
