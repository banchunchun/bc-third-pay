package com.bc.third.pay.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-12
 * Time:  下午 4:39.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@ConfigurationProperties(prefix = "pay.alipay")
@Component
public class AlipayProperties {

    private static String partner;
    private static String seller;
    private static String privateKey;
    private static String key;

    private static String appCallBackUrl;

    private static String wapCallBackUrl;

    public static String getPartner() {
        return partner;
    }

    public static void setPartner(String partner) {
        AlipayProperties.partner = partner;
    }

    public static String getSeller() {
        return seller;
    }

    public static void setSeller(String seller) {
        AlipayProperties.seller = seller;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(String privateKey) {
        AlipayProperties.privateKey = privateKey;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        AlipayProperties.key = key;
    }

    public static String getAppCallBackUrl() {
        return appCallBackUrl;
    }

    public static void setAppCallBackUrl(String appCallBackUrl) {
        AlipayProperties.appCallBackUrl = appCallBackUrl;
    }

    public static String getWapCallBackUrl() {
        return wapCallBackUrl;
    }

    public static void setWapCallBackUrl(String wapCallBackUrl) {
        AlipayProperties.wapCallBackUrl = wapCallBackUrl;
    }
}
