package com.bc.third.pay.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信开放平台信息
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-22
 * Time:  下午 3:16.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@ConfigurationProperties(prefix = "pay.weixin.app")
@Component
public class WeixinAppPayProperties {


    private static String appId;

    private static String appSecret;

    private static String partner;

    private static String key;

    private static String cert;

    private static String callBackUrl;


    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        WeixinAppPayProperties.appId = appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static void setAppSecret(String appSecret) {
        WeixinAppPayProperties.appSecret = appSecret;
    }

    public static String getPartner() {
        return partner;
    }

    public static void setPartner(String partner) {
        WeixinAppPayProperties.partner = partner;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        WeixinAppPayProperties.key = key;
    }

    public static String getCert() {
        return cert;
    }

    public static void setCert(String cert) {
        WeixinAppPayProperties.cert = cert;
    }

    public static String getCallBackUrl() {
        return callBackUrl;
    }

    public static void setCallBackUrl(String callBackUrl) {
        WeixinAppPayProperties.callBackUrl = callBackUrl;
    }
}
