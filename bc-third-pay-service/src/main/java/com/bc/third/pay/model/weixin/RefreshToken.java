package com.jinzai.pay.tenpay;

import java.io.Serializable;

/**
 * 微信OAUTH 2.0 REFRESH TOKEN
 * 
 * @author BenBan
 * 
 */
public class RefreshToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2726216297776791780L;

	private String accessToken;

	private String refreshToken;

	private int expiresIn;

	private String openId;

	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
