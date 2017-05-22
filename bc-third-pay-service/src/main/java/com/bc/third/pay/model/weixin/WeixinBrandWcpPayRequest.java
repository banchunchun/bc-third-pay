/**
 * 
 */
package com.bc.third.pay.model.weixin;

import java.io.Serializable;

/**
 * 微信公众号调起微信支付发起请求参数
 * 
 * @author banchun
 * @date 2015年10月6日 下午4:21:06
 */
public class WeixinBrandWcpPayRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1561139460640862107L;

	private String appId; // 公众号的appid，在开发者中心可找到

	private String timeStamp; // 商户生成，从1970年1月1日00：00：00至今的秒数，即当前的时间，且最终需要转换为字符串形式

	private String nonceStr; // 商户生成的随机字符串

	private String ackage; // 由于package 是关键字 有ackage代替，内容为: prepay_id=u802345jgfjsdfgsdg888",

	private String signType; // 签名方式，默认MD5

	private String paySign; // 支付签名

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getAckage() {
		return ackage;
	}

	public void setAckage(String ackage) {
		this.ackage = ackage;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	@Override
	public String toString() {
		return "WeixinBrandWcpPayRequest [appId=" + appId + ", timeStamp="
				+ timeStamp + ", nonceStr=" + nonceStr + ", ackage=" + ackage
				+ ", signType=" + signType + ", paySign=" + paySign + "]";
	}

}
