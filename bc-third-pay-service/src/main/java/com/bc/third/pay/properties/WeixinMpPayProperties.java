package com.bc.third.pay.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信公众平台信息
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-22
 * Time:  下午 3:21.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Component
@ConfigurationProperties(prefix = "pay.weixin.mp")
public class WeixinMpPayProperties {
}
