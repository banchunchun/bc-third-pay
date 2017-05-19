package com.bc.third.pay.model.alipay.model;

import java.io.Serializable;

/**
 * 客户端提交的支付宝请求订单日志信息
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2016-09-28
 * Time:  下午 3:45.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class AlipayLog implements Serializable {

    private String service;

    private String batchNo;

    private String request;

    private String sign ;

    private String accessDate;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }
}
