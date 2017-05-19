package com.bc.third.pay.model.alipay.model;



import com.bc.third.pay.utils.CustomToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 客户端提交给支付宝封装的订单信息
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2016-09-27
 * Time:  上午 11:05.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
public class AlipayOrderInfo  implements Serializable{

    private String partner ;   //签约id

    private String seller_id;  //签约卖家支付宝帐号

    private String out_trade_no; //商户订单

    private String subject ;    // 商品名称

    private String body ;       //商品详情

    private String total_fee ;  // 商品金额(元)

    private String notify_url ; //支付宝异步回调通知地址

    private String service ;   // 客户端提交接口名称

    private String payment_type; //固定提交类型：1

    private String _input_charset; //编码方式
    // 设置未付款交易的超时时间
    // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
    // 取值范围：1m～15d。
    // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
    // 该参数数值不接受小数点，如1.5h，可转换为90m。
    private String it_b_pay;

    private String return_url; // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空

    public AlipayOrderInfo(){
        super();
    }

    public AlipayOrderInfo(String partner, String seller_id, String out_trade_no, String subject, String body, String total_fee, String notify_url, String service, String payment_type, String _input_charset, String it_b_pay, String return_url) {
        this.partner = partner;
        this.seller_id = seller_id;
        this.out_trade_no = out_trade_no;
        this.subject = subject;
        this.body = body;
        this.total_fee = total_fee;
        this.notify_url = notify_url;
        this.service = service;
        this.payment_type = payment_type;
        this._input_charset = _input_charset;
        this.it_b_pay = it_b_pay;
        this.return_url = return_url;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String get_input_charset() {
        return _input_charset;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public String getIt_b_pay() {
        return it_b_pay;
    }

    public void setIt_b_pay(String it_b_pay) {
        this.it_b_pay = it_b_pay;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,new CustomToStringStyle());
    }
}
