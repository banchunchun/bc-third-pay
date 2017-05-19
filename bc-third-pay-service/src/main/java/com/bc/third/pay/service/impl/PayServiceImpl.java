package com.bc.third.pay.service.impl;

import com.bc.third.pay.model.alipay.config.AlipayConfig;
import com.bc.third.pay.model.alipay.sign.RSA;
import com.bc.third.pay.service.PayService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-19
 * Time:  下午 2:27.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PayServiceImpl implements PayService {


    /**
     * 提交支付宝APP支付信息
     *
     * @param batchNo  商户订单号
     * @param payPrice 支付价格
     *                 <pre>
     *                                 （此方法不能被单独调用），只能通过内部微服务方式提供，
     *                                 <b>本案例仅作示例，未做此方面校验，正式项目请慎用，禁止传输价格</b>
     *                                 1.尽量是由订单服务提供查询金额的方法
     *                                 2.或者才用混淆加密
     *                 @return 生成加密数据信息
     */
    @Override
    public String submitAlipayAppInfo(String batchNo, int payPrice) throws UnsupportedEncodingException {
        String orderInfo = this.packageSubmitAlipayOrderInfo(batchNo,"测试订单","",0.01);

        //根据RSA私钥对应的PKCS8格式的进行对订单信息签名，或者通过rsa_private pem格式的私钥使用RSA.sign(orderInfo,private_key)进行加密，两者均可;
        String sign = RSA.sign(orderInfo, AlipayConfig.private_key, AlipayConfig.input_charset);

        String rsa_sign = URLEncoder.encode(sign, AlipayConfig.input_charset);

        orderInfo += "&sign=\"" + rsa_sign + "\"&" + "sign_type=\""+ AlipayConfig.rsa_sign_type+"\"";

        return orderInfo;
    }

    /**
     * 组装支付宝订单信息
     * @param orderNo
     * @param itemName
     * @param notifyUrl
     * @param price
     * @return
     */
    private String packageSubmitAlipayOrderInfo(String orderNo,String itemName,String notifyUrl, Double price){
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";
        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + AlipayConfig.sell_account + "\"";
        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";
        // 商品名称
        orderInfo += "&subject=" + "\"" + "测试订单-".concat(orderNo) + "\"";
        // 商品详情
        orderInfo += "&body=" + "\"" + itemName + "\"";
        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";
        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notifyUrl + "\"";
        // 服务接口名称， 固定值
        orderInfo += "&service=\""+ AlipayConfig.mobile_pay_service+"\"";
        // 支付类型， 固定值
        orderInfo += "&payment_type=\""+ AlipayConfig.alipay_param_type+"\"";
        // 参数编码， 固定值
        orderInfo += "&_input_charset=\""+ AlipayConfig.input_charset+"\"";
        orderInfo += "&it_b_pay=\"30m\"";
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";
        return orderInfo;
    }
}
