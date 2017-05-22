package com.bc.third.pay.model.weixin.client;

import net.sf.json.JSONObject;


public class JsonUtil {

	public static String getJsonValue(String rescontent, String key) {
		JSONObject jsonObject;
		String v = null;
		try {
			jsonObject = JSONObject.fromObject(rescontent);
			v = jsonObject.getString(key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return v;
	}
}
