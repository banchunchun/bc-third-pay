/**
 * 
 */
package com.bc.third.pay.model.weixin;

import java.io.Serializable;

/**
 * 微信退款请求微信服务器数据
 * 
 * @author banchun
 * @date 2015年8月17日 下午4:46:56
 */
public class WeixinRefundPayResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6412330744820138386L;

	private String appid; // 微信分配的公众账号ID

	private String mch_id; // 商户号

	private String nonce_str; // 随机字符串，不长于32位

	private String sign; // 签名

	private String transaction_id;// 微信订单号

	private String out_trade_no; // 商户系统内部的订单号

	private String out_refund_no; // 商户退款单

	private String total_fee;// 订单总金额（分）

	private String refund_fee;// 退款金额（分）

	private String op_user_id;// 操作员id，默认商户号

	public WeixinRefundPayResult() {
		super();
	}

	public WeixinRefundPayResult(String appid, String mch_id, String nonce_str, String sign, String transaction_id, String out_refund_no, String total_fee, String refund_fee, String op_user_id) {
		this.appid = appid;
		this.mch_id = mch_id;
		this.nonce_str = nonce_str;
		this.sign = sign;
		this.transaction_id = transaction_id;
		this.out_refund_no = out_refund_no;
		this.total_fee = total_fee;
		this.refund_fee = refund_fee;
		this.op_user_id = op_user_id;
	}

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

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
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

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

}
