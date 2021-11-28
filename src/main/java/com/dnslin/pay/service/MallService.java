package com.dnslin.pay.service;

import com.dnslin.pay.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**

import org.springframework.stereotype.Service;

/**
 * @author: DnsLin
 * @Title: MallService
 * @ProjectName: dnslinPay
 * @Description:
 * @date: 2021/11/27 16:53
 */
@Service
public class MallService {
    @Autowired
    private MailUtil mailUtil;

    private final String receiver = "wgt161110527@163.com";


}
