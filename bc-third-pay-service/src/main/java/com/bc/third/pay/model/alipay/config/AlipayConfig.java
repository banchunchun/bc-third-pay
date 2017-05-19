package com.bc.third.pay.model.alipay.config;

import com.bc.third.pay.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置中心
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-12
 * Time:  下午 4:37.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AlipayConfig {

    @Autowired
    AlipayProperties alipayProperties;
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = AlipayProperties.getPartner();
    //商户身份id
    public static String seller_id = partner;
    // 商户的私钥
    public static String private_key = AlipayProperties.getPrivateKey();
    //商户设置的pid key
    public static String key = AlipayProperties.getKey();
    // 商家支付宝
    public static String sell_account = AlipayProperties.getSeller();
    //APP 支付异步通知
    public static String app_callback_url = AlipayProperties.getAppCallBackUrl();
    //wap 支付异步通知
    public static String wap_callback_url = AlipayProperties.getWapCallBackUrl();
    // 支付宝的公钥，无需修改该值
    public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "D:\\";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";

    // 签名方式 不需修改
    public static String rsa_sign_type = "RSA";
    //退款签名方式
    public static String md5_sign_type = "MD5";
    /**
     * 有密退款接口
     */
    public static String refund_pwd_interface = "refund_fastpay_by_platform_pwd";
    /**
     * 无密退款接口
     */
    public static String refund_nopwd_interface = "refund_fastpay_by_platform_nopwd";

    /**
     * 支付宝提交信息接口
     */
    public static String submit_alipay_interface = "alipay.wap.create.direct.pay.by.user";
    /**
     * 交易查询接口
     */
    public static String single_trade_query = "single_trade_query";

    /**
     * 客户端提交service接口名称
     */
    public static String mobile_pay_service  = "mobile.securitypay.pay";

    /**
     * 提交参数类型
     */
    public static String alipay_param_type = "1";
    /**
     * it_b_pay 币种
     */
    public static String it_b_pay = "30";
}
