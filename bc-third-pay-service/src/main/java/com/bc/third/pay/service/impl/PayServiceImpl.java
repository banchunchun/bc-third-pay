package com.bc.third.pay.service.impl;

import com.bc.third.pay.model.alipay.config.AlipayConfig;
import com.bc.third.pay.model.alipay.sign.RSA;
import com.bc.third.pay.model.union.UnionPayUtil;
import com.bc.third.pay.properties.UnionPayProperties;
import com.bc.third.pay.service.PayService;
import com.bc.third.pay.utils.DateUtils;
import com.unionpay.acp.sdk.SDKConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.bc.third.pay.model.alipay.config.AlipayConfig.app_callback_url;

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

    @Autowired
    private UnionPayProperties unionPayProperties;


    private final Logger logger = LoggerFactory.getLogger(PayService.class);

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

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
        orderInfo += "&notify_url=" + "\"" + AlipayConfig.app_callback_url + "\"";
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


    /**
     * 获取银联支付tn号
     *
     * @param batchNo     订单号
     * @param reqReserved 透传区域，银联异步通知会原封返回 格式为 json {}
     * @return
     */
    @Override
    public String getTn(String batchNo,String reqReserved) {
        //TODO 这里应该根据订单号获取支付价格，订单创建时间，避免超时 默认系统当前时间，价格测试阶段默认写死，1分钱
        /**
         * 组装请求报文
         */
        Map<String, String> data = new HashMap<String, String>();
        // 版本号
        data.put("version", unionPayProperties.getVersion());
        // 字符集编码 默认"UTF-8"
        data.put("encoding", unionPayProperties.getEncoding());
        // 签名方法 01 RSA
        data.put("signMethod", unionPayProperties.getSignMethod());
        // 交易类型 01-消费
        data.put("txnType", unionPayProperties.getTxnType());
        // 交易子类型 01:自助消费 02:订购 03:分期付款
        data.put("txnSubType", unionPayProperties.getTxnSubType());
        // 业务类型
        data.put("bizType", unionPayProperties.getBizType());
        // 渠道类型，07-PC，08-手机
        data.put("channelType", unionPayProperties.getAPPChannelType());
        // 前台通知地址 ，控件接入方式无作用
        data.put("frontUrl", unionPayProperties.getAPPChannelType());
        // 后台通知地址
        data.put("backUrl", unionPayProperties.getBackUrl());
        // 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
        data.put("accessType", unionPayProperties.getAccessType());
        // 商户号码，请改成自己的商户号
        data.put("merId", unionPayProperties.getMerId());
        // 商户订单号，8-40位数字字母
        data.put("orderId", batchNo);
        // 订单发送时间，取系统时间
        data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        // 交易金额，单位分
        data.put("txnAmt", 1+"");
        // 交易币种
        data.put("currencyCode", unionPayProperties.getCurrencyCode());
        // 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
        data.put("reqReserved", reqReserved);
        // 订单描述，可不上送，上送时控件中会显示该信息
        // data.put("orderDesc", "订单描述");
        Date payTimeOutTime = DateUtils.addMinute(new Date(),29);
        //增加超时时间
        data.put("payTimeout",sdf.format(payTimeOutTime));
        logger.error("[getTn] payTimeouttime:{}",sdf.format(payTimeOutTime));
        data = UnionPayUtil.signData(data);

        logger.debug("请求报文1=["+data.toString()+"]");

        // 交易请求url 从配置文件读取
        String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();

        logger.debug("请交易请求url 从配置文件读取完成");

        Map<String, String> resmap = UnionPayUtil.submitUrl(data, requestAppUrl);

        logger.error("请求报文2=["+data.toString()+"]");
        logger.error("[getTn]应答报文3=["+resmap.toString()+"]");

        String tn = resmap.get("tn");

        logger.debug("tn={}",tn);
        // 获取预订单tn
        return tn;
    }
}
