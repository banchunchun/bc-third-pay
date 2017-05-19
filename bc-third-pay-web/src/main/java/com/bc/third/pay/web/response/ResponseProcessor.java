package com.bc.third.pay.web.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bc.third.pay.web.error.PayError;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author banchun
 * @createTime 2015年6月29日 下午5:53:12
 * @description 
 */
public class ResponseProcessor {
	protected static final Logger logger = LoggerFactory.getLogger(ResponseProcessor.class.getName());

	/**
	 * 不要再调用这个方法了，返回成功请使用responseSuccessJson()
	 * @param response
	 * @param jsonStr
	 */
	public void responseJson(HttpServletResponse response,String jsonStr) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		try(PrintWriter out = response.getWriter()) {
			out.write(jsonStr);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	public void responseJsonP(HttpServletResponse response,String jsonStr,String callback) {
		if(StringUtils.isNotBlank(callback)){
			jsonStr = callback+"("+jsonStr+")";
		}
		responseJson(response,jsonStr);
	}
	public void responseSuccessJson(HttpServletResponse response,JSONObject data) {
		data.put("success_code",200);
		data.put("msg","执行成功");
		responseJson(response, JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue));
	}
	public void responseSuccessJson(HttpServletResponse response,String jsonStr) {
		responseJson(response, jsonStr);
	}
	public void responseSuccessJsonParse(HttpServletResponse response,String jsonStr) {
		JsonObject jo = new JsonParser().parse(jsonStr).getAsJsonObject();
		jo.addProperty("success_code", 200);
		jo.addProperty("msg", "执行成功");
		responseJson(response, jo.toString());
	}
	public void responseErrorJson(HttpServletResponse response,PayError apiError) {
		JSONObject data = new JSONObject();
		data.put("error_code", apiError.getCode());
		data.put("error", apiError.getDesc());
		responseJson(response, data.toJSONString());
	}
	public void responseErrorJsonP(HttpServletResponse response,PayError apiError,String callback) {
		JSONObject data = new JSONObject();
		data.put("error_code", apiError.getCode());
		data.put("error", apiError.getDesc());
		String jsonStr = data.toJSONString();
		if(StringUtils.isNotBlank(callback)){
			jsonStr = callback+"("+jsonStr+")";
		}
		responseJson(response, jsonStr);
	}
	public void responseError404(HttpServletResponse response) {
		JSONObject data = new JSONObject();
		data.put("error_code", 404);
		data.put("error", "你请求的地址不存在");
		responseJson(response, data.toJSONString());
	}
	public void responseError400(HttpServletResponse response) {
		JSONObject data = new JSONObject();
		data.put("error_code", 400);
		data.put("error", "请求不正确");
		responseJson(response, data.toJSONString());
	}
	public void responseErrorJson(HttpServletResponse response,String msg){
		JsonObject json = new JsonObject();
		json.addProperty("error_code", -22);
		json.addProperty("error", msg);
		responseJson( response, json.toString());
	}
	public void responseErrorJson(HttpServletResponse response,int erroCode,String msg){
		JsonObject json = new JsonObject();
		json.addProperty("error_code", erroCode);
		json.addProperty("error", msg);
		responseJson( response, json.toString());
	}

	public void responseToStlJson(HttpServletResponse response, JsonObject jsonObject){
		responseJson( response, jsonObject.toString());
	}

	public void responseSuccessJson(HttpServletResponse response, JsonObject jsonObject){
		jsonObject.addProperty("success_code", 200);
		jsonObject.addProperty("msg", "执行成功");
		responseJson( response, jsonObject.toString());
	}

	public void responseSuccessJson(HttpServletResponse response){
		JSONObject data = new JSONObject();
		data.put("success_code", 200);
		data.put("msg", "执行成功");
		responseJson( response, data.toJSONString());
	}
	/**
	 * 返回成功信息，并带回json对象
	 * @author banchun	
	 * @date 2016年1月11日 下午2:41:02 
	 * @param response
	 * @param json
	 */
	public void responseSuccessJsonData(HttpServletResponse response,String json){
		JSONObject data = new JSONObject();
		data.put("success_code", 200);
		data.put("msg", "执行成功");
		data.put("data", json);
		responseJson(response, data.toJSONString());
	}
	/**
	 * 自定义错误
	 * @param response
	 * @param apiError			系统错误码和错误信息
	 * @param userDefineMsg		业务自定义错误信息，可以给客户端显示用
	 */
	public void responseErrorJson(HttpServletResponse response, PayError apiError, String userDefineMsg) {
		JSONObject data = new JSONObject();
		if(apiError != null){
			data.put("error_code", apiError.getCode());
			data.put("error_des", apiError.getDesc());
			if(StringUtils.isBlank(userDefineMsg)){
				data.put("error", apiError.getDesc());
			}
		}
		if(StringUtils.isNotBlank(userDefineMsg)){
			data.put("error", userDefineMsg);
		}
		responseJson(response, data.toJSONString());
	}
	/**
	 * 响应自定错误
	 * @param response
	 * @param apiError
	 * @param format	格式化字符串（包含占位符）
	 * @param args		填充占位符的参数
	 */
	public void responseErrorJson(HttpServletResponse response,PayError apiError,String format,Object... args) {
		if(StringUtils.isNotBlank(format)){
			format = String.format(format, args);
		}
		responseErrorJson(response, apiError, format);
	}
}
