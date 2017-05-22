package com.bc.third.pay.model.weixin.app;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.bc.third.pay.model.weixin.app.http.HttpClientConnectionManager;
import com.google.common.collect.Maps;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 获取微信支付预支付交易ID
 * @author banchun
 * @date 2015年8月13日 下午3:19:15
 */
public class GetWxOrderno {
	
	public static DefaultHttpClient httpclient;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager
				.getSSLInstance(httpclient);
	}
	/**
	 * 获取预支付id
	 * @author banchun	
	 * @date 2015年8月13日 下午3:21:16 
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	public String getPayNo(String url, String xmlParam) {
		String prepay_id = "";
		HttpURLConnection conn = null;
		try {
			//统一下单接口提交  xml格式
			URL orderUrl = new URL(url);
			conn = (HttpURLConnection) orderUrl.openConnection();
			conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
			conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
			conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
			conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
			conn.setUseCaches(false); // Post 请求不能使用缓存
			// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
			conn.setRequestProperty("Content-Length",xmlParam.length()+"");
			String encode = "utf-8";
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encode);
			out.write(xmlParam);
			out.flush();
			out.close();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return prepay_id;
			}
			// 获取响应内容体
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = "";
			StringBuffer strBuf = new StringBuffer();
			while ((line = in.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			in.close();
			String  responseResult = strBuf.toString().trim();
			logger.info("获取的response对象:{}",responseResult);
			if (responseResult.indexOf("FAIL") != -1) {
				return prepay_id;
			}
			Map<String, String> map = doXMLParse(responseResult);
			prepay_id = (String) map.get("prepay_id");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取prepayId出错："+e.getMessage());
		}finally{
			conn.disconnect();
		}
		
		return prepay_id;
	}

	/**
	 * 获取预支付Map
	 * @author banchun
	 * @date 2015年8月13日 下午3:21:16
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	public Map<String,String> getPayMap(String url, String xmlParam) {
		String prepay_id = "";
		HttpURLConnection conn = null;
		try {
			//统一下单接口提交  xml格式
			URL orderUrl = new URL(url);
			conn = (HttpURLConnection) orderUrl.openConnection();
			conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
			conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
			conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
			conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
			conn.setUseCaches(false); // Post 请求不能使用缓存
			// 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");// 设定请求的方法为"POST"，默认是GET
			conn.setRequestProperty("Content-Length",xmlParam.length()+"");
			String encode = "utf-8";
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), encode);
			out.write(xmlParam);
			out.flush();
			out.close();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return Maps.newHashMap();
			}
			// 获取响应内容体
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = "";
			StringBuffer strBuf = new StringBuffer();
			while ((line = in.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			in.close();
			String  responseResult = strBuf.toString().trim();
			logger.info("获取的response对象:{}",responseResult);
			if (responseResult.indexOf("FAIL") != -1) {
				return Maps.newHashMap();
			}
			Map<String, String> map = doXMLParse(responseResult);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取prepayId出错："+e.getMessage());
		}finally{
			conn.disconnect();
		}
		return Maps.newHashMap();
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, String> m = new HashMap<String, String>();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<?> list = root.getChildren();
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List<?> children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List<?> children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator<?> it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List<?> list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

}