package com.bc.third.pay.model.weixin;

import com.bc.third.pay.model.weixin.app.MD5Util;
import com.bc.third.pay.model.weixin.client.JsonUtil;
import com.bc.third.pay.model.weixin.client.TenpayHttpClient;
import com.bc.third.pay.properties.WeixinAppPayProperties;
import com.bc.third.pay.properties.WeixinMpPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 微信统一请求中心，获取签名、token
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-22
 * Time:  下午 3:49.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Component
public class WeixinRequestHandler {

    @Autowired
    private WeixinAppPayProperties weixinAppPayProperties;
    @Autowired
    private WeixinMpPayProperties weixinMpPayProperties;


    public static final String CHARSET = "utf-8";

    public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url

    public static String GRANT_TYPE = "client_credential";//常量固定值

    public static String ACCESS_TOKEN = "access_token";//access_token常量值


    /**
     * 生成签名
     *
     * @param packageParams
     * @param isApp
     * @return
     */
    private String createSign(SortedMap<String, String> packageParams, boolean isApp) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        if (isApp) {
            sb.append("key=" + weixinAppPayProperties.getKey());
        } else {
//            sb.append("key=" + weixinMpPayProperties.getKey());
        }
        String sign = MD5Util.MD5Encode(sb.toString(), this.CHARSET)
                .toUpperCase();
        return sign;
    }

    /**
     * 生成token，建议缓存2个小时
     *
     * @param isApp true app token ，false 微信公众号token
     * @return
     */
    private String getToken(boolean isApp) {
        String requestUrl = this.TOKENURL + "?grant_type=" + this.GRANT_TYPE + "&appid=";
        if (isApp) {
            requestUrl += weixinAppPayProperties.getAppId() + "&secret=" + weixinAppPayProperties.getAppSecret();
        } else {
            //
        }
        String token = "";
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setMethod("GET");
        httpClient.setReqContent(requestUrl);
        if (httpClient.call()) {
            String resContent = httpClient.getResContent();
            if (resContent.indexOf(this.ACCESS_TOKEN) > 0) {
                token = JsonUtil.getJsonValue(resContent, this.ACCESS_TOKEN);
            } else {

            }
        } else {

        }
        return token;
    }
}
