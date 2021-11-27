package com.dnslin.pay.config;
/**
 * @author: DnsLin
 * @Title: Snowflake
 * @ProjectName: dnslinPay
 * @Description:
 * @date: 2021/11/27 15:19
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: DnsLin
 * @Title: Snowflake
 * @ProjectName: dnslinPay
 * @Description:
 * @date: 2021/11/27 15:19
 */
public class Snowflake {
  // ==============================Fields===========================================
  /** 开始时间截 (2018-07-03) */
  private final Long twepoch = 1530607760000L;

  /** 机器id所占的位数 */
  private final Long workerIdBits = 5L;

  /** 数据标识id所占的位数 */
  private final Long datacenterIdBits = 5L;

  /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
  private final Long maxWorkerId = -1L ^ (-1L << workerIdBits);

  /** 支持的最大数据标识id，结果是31 */
  private final Long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

  /** 序列在id中占的位数 */
  private final Long sequenceBits = 12L;

  /** 机器ID向左移12位 */
  private final Long workerIdShift = sequenceBits;

  /** 数据标识id向左移17位(12+5) */
  private final Long datacenterIdShift = sequenceBits + workerIdBits;

  /** 时间截向左移22位(5+5+12) */
  private final Long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

  /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
  private final Long sequenceMask = -1L ^ (-1L << sequenceBits);

  /** 工作机器ID(0~31) */
  private Long workerId;

  /** 数据中心ID(0~31) */
  private Long datacenterId;

  /** 毫秒内序列(0~4095) */
  private Long sequence = 0L;

  /** 上次生成ID的时间截 */
  private Long lastTimestamp = -1L;

  // ==============================Constructors=====================================

  /**
   * 构造函数
   *
   * @param workerId 工作ID (0~31)
   * @param datacenterId 数据中心ID (0~31)
   */
  public Snowflake(Long workerId, Long datacenterId) {
    if (workerId > maxWorkerId || workerId < 0) {
      throw new IllegalArgumentException(
          String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
    }
    if (datacenterId > maxDatacenterId || datacenterId < 0) {
      throw new IllegalArgumentException(
          String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
  }
  /**
   * 获得下一个ID (该方法是线程安全的)
   *
   * @return SnowflakeId
   */
  public synchronized Long nextId() {
    long timestamp = timeGen();

    // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
    if (timestamp < lastTimestamp) {
      throw new RuntimeException(
          String.format(
              "Clock moved backwards.  Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }

    // 如果是同一时间生成的，则进行毫秒内序列
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      // 毫秒内序列溢出
      if (sequence == 0) {
        // 阻塞到下一个毫秒,获得新的时间戳
        timestamp = tilNextMillis(lastTimestamp);
      }
    }
    // 时间戳改变，毫秒内序列重置
    else {
      sequence = 0L;
    }

    // 上次生成ID的时间截
    lastTimestamp = timestamp;

    // 移位并通过或运算拼到一起组成64位的ID
    return (((timestamp - twepoch) << timestampLeftShift)
        | (datacenterId << datacenterIdShift)
        | (workerId << workerIdShift)
        | sequence);
  }

  /**
   * 阻塞到下一个毫秒，直到获得新的时间戳
   *
   * @param lastTimestamp 上次生成ID的时间截
   * @return 当前时间戳
   */
  protected long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  /**
   * 返回以毫秒为单位的当前时间
   *
   * @return 当前时间(毫秒)
   */
  protected long timeGen() {
    return System.currentTimeMillis();
  }

  // ==============================Test=============================================

  /** 测试 */
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    Snowflake idWorker = new Snowflake(0L, 0L);
    Set set = new HashSet();
    for (int i = 0; i < 10000000; i++) {
      long id = idWorker.nextId();
      set.add(id);
      System.out.println("id----" + i + ":" + id);
    }
    long endTime = System.currentTimeMillis();
    System.out.println("set.size():" + set.size());
    System.out.println("endTime-startTime:" + (endTime - startTime));
  }

  public static String timestampConversionDate(String param) {
    Instant timestamp = Instant.ofEpochMilli(new Long(param));
    System.out.println("timestamp:" + param);
    LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
    String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    return format;
  }
}
