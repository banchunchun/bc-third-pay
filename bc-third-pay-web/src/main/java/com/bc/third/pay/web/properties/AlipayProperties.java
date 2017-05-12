package com.bc.third.pay.web.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-12
 * Time:  下午 4:39.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@Component
@RefreshScope
public class AlipayProperties {

    @Value("${pay.alipay.partner}")
    private  String      partner;
    private  String      seller;
    private  String      privateKey;
    private  String      key;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
