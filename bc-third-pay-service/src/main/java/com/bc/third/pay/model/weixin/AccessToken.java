/**
 * 
 */
package com.jinzai.pay.tenpay;

import java.io.Serializable;

/**
 * @author banchun
 * @date 2015年8月12日 下午6:03:05
 */
public class AccessToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1360005143184013501L;

	private String access_token;

	private Integer expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

}
