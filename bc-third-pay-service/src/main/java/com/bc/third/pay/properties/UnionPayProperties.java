package com.bc.third.pay.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User; banchun
 * Date;  2017-05-22
 * Time;  下午 5;16.
 * Description;
 * To change this template use File | Settings | File Templates.
 */
@Component
@ConfigurationProperties(prefix = "pay.unionpay")
public class UnionPayProperties {


    private static String merId; //商户号
    private static String version;
    private static String encoding;
    private static String signMethod;
    private static String txnType;
    private static String txnTypeRefund;
    private static String txnSubType;
    private static String txnSubTypRefund;
    private static String bizType;
    private static String APPChannelType;
    private static String pcChannelType;
    private static String accessType;
    private static String currencyCode;
    private static String frontUrl;
    private static String backUrl;

    public static String getMerId() {
        return merId;
    }

    public static void setMerId(String merId) {
        UnionPayProperties.merId = merId;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        UnionPayProperties.version = version;
    }

    public static String getEncoding() {
        return encoding;
    }

    public static void setEncoding(String encoding) {
        UnionPayProperties.encoding = encoding;
    }

    public static String getSignMethod() {
        return signMethod;
    }

    public static void setSignMethod(String signMethod) {
        UnionPayProperties.signMethod = signMethod;
    }

    public static String getTxnType() {
        return txnType;
    }

    public static void setTxnType(String txnType) {
        UnionPayProperties.txnType = txnType;
    }

    public static String getTxnTypeRefund() {
        return txnTypeRefund;
    }

    public static void setTxnTypeRefund(String txnTypeRefund) {
        UnionPayProperties.txnTypeRefund = txnTypeRefund;
    }

    public static String getTxnSubType() {
        return txnSubType;
    }

    public static void setTxnSubType(String txnSubType) {
        UnionPayProperties.txnSubType = txnSubType;
    }

    public static String getTxnSubTypRefund() {
        return txnSubTypRefund;
    }

    public static void setTxnSubTypRefund(String txnSubTypRefund) {
        UnionPayProperties.txnSubTypRefund = txnSubTypRefund;
    }

    public static String getBizType() {
        return bizType;
    }

    public static void setBizType(String bizType) {
        UnionPayProperties.bizType = bizType;
    }

    public static String getAPPChannelType() {
        return APPChannelType;
    }

    public static void setAPPChannelType(String APPChannelType) {
        UnionPayProperties.APPChannelType = APPChannelType;
    }

    public static String getPcChannelType() {
        return pcChannelType;
    }

    public static void setPcChannelType(String pcChannelType) {
        UnionPayProperties.pcChannelType = pcChannelType;
    }

    public static String getAccessType() {
        return accessType;
    }

    public static void setAccessType(String accessType) {
        UnionPayProperties.accessType = accessType;
    }

    public static String getCurrencyCode() {
        return currencyCode;
    }

    public static void setCurrencyCode(String currencyCode) {
        UnionPayProperties.currencyCode = currencyCode;
    }

    public static String getFrontUrl() {
        return frontUrl;
    }

    public static void setFrontUrl(String frontUrl) {
        UnionPayProperties.frontUrl = frontUrl;
    }

    public static String getBackUrl() {
        return backUrl;
    }

    public static void setBackUrl(String backUrl) {
        UnionPayProperties.backUrl = backUrl;
    }
}
