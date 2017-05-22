/**
 * 
 */
package com.bc.third.pay.model.weixin;

import com.bc.third.pay.utils.CustomToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


/**
 * 微信公众号支付回调接收数据
 * 
 * @author banchun
 * @date 2015年10月6日 下午4:17:37
 */
public class WeixinPublicCallBackResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6412330744820138386L;

	private String appid; // 微信分配的公众账号ID

	private String mch_id; // 商户号

	private String nonce_str; // 随机字符串，不长于32位
	
	private String body;//商品或支付单简要描述
	
	private String attach;//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据

	private String sign; // 签名

	private String out_trade_no; // 商户系统内部的订单号

	private String out_refund_no; // 商户退款单

	private String total_fee;// 订单总金额（分）

	private String openid;// 用户在当前公众号下的唯一标识

	private String trade_type; // 支付类型 JSAPI 或者NATIVE(原生扫码付)

	private String notify_url; // 支付成功异步回调url
	
	private String spbill_create_ip;//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	
	private String bank_type;//支付银行类型
	
	private String fee_type;//货币类型 默认CNY，
	
	private String is_subscribe;// 是否关注公众号
	
	private String result_code;//业务处理结果
	
	private String return_code;//返回状态码
	
	private String sub_mch_id;//子商户号
	
	private String time_end;//交易完成时间
	
	private String transaction_id;//微信支付交易号

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				new CustomToStringStyle());
	}

}
