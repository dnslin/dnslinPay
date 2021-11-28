package com.dnslin.pay.result;

public enum ResponseEnum {

    SUCCESS("200","成功003"),
    SUCCESS_MAIL("202","订单生成成功,邮件发送成功"),
    USERNAME_NOT_FOUND("300","用户名不存在"),
    USERNAME_OR_PASSWORD_INVALIDATE("301","用户名或者密码错误"),
    ROLE_NO_MENUS("302","此角色没有任何菜单权限，请尽早分配"),
    HAS_NO_TOKEN("303","还没有登录"),
    TOKEN_ERROR("304","吵你麻痹"),
    TOKEN_TIMEOUT("305","对不起登录状态已经失效，请重新登录"),
    HAS_NO_RIGHT("306","权限不足"),
    SYSTEM_ERROR("500","发生未知异常。。。"),
    FEIGN_BSUY("307","系统正忙，稍后再试。。"),
    FLOW_BLOCK("308","流量被限制了，请稍后再使"),
    DEG_BLOCK("309","系统很忙，稍后再试。。。"),
    SMS_ERROR("310","短信发送失败，请稍后再试。。。"),
    SMS_SEND_ERROR_BUSINESS_LIMIT_CONTROL("311","发送短信太频繁，过会再发。。。"),
    SMS_SEND_ERROR("312","验证码不匹配"),
    ERROR_PHONE("313","手机号或验证码为空"),
    ERROR_ROLE("314","角色重复"),
    SK_TIMEOUT("315","时间未到"),
    SK_HAS_NO_GOODS("316","商品不足"),
    SK_BUSY("317","网络繁忙"),
    SK_ORDERID_NULL("318","订单ID为空"),
    SK_FLOW_SHEET("319","商品流单"),
    ID_NOTFOUND("320","状态错误"),
    ACTIVITY_DOES_NOT_EXIST("401","活动不存在"),
    GIFT_DOES_NOT_EXIST("402","礼品不存在"),
    EVENT_DATE("403","活动未开启"),
    USER_HAS_PARTICI_PATEDIN_ACTIVITIES("405","用户已参加活动，请勿重复发起"),
    PRIZEIS_INSUFFICIENT("406","奖品不足,等待补货"),
    FOR_TIMEOUT("407","抢购超时"),
    insufficient_Activation_Code("408","激活码不足,请联系管理员"),
    NUMBER_LESS_THAN("409","助力次数用完了"),
    HAVE_POWER("501","你已经助力过了");


    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
