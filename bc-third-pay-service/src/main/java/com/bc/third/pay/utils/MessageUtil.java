package com.bc.third.pay.utils;

import com.bc.third.pay.model.weixin.WeixinPublicPayResult;
import com.bc.third.pay.model.weixin.WeixinRefundPayResult;
import com.bc.third.pay.model.weixin.WxPayResult;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.Writer;

/**
 * 消息工具类，提供各种消息类型和封装回复消息的xml格式
 * 
 * @author BenBan
 * 
 */
public class MessageUtil {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 请求消息类型：视频
	 */
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	/**
	 * 多客服事件类型
	 */
	public static final String CUSTOME_SERVICE_TYPE = "transfer_customer_service";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	/**
	 * 微信客服发送url
	 */
	public static final String CUSTOME_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";




	/**
	 * 将对象转换成xml
	 * @author banchun	
	 * @date 2015年8月13日 下午3:35:38 
	 * @param payResult
	 * @return
	 */
	public static String messageToXml(WeixinPublicPayResult payResult) {
		xstream.alias("xml",WeixinPublicPayResult.class);
		String xml = xstream.toXML(payResult);
		//TODO 要把__ 替换成_
		xml = xml.replaceAll("__", "_");
		return xml;
	}

	/**
	 * 将对象转换成xml
	 * @author banchun
	 * @date 2015年8月13日 下午3:35:38
	 * @param payResult
	 * @return
	 */
	public static String messageToXml(WeixinRefundPayResult payResult) {
		xstream.alias("xml", WeixinRefundPayResult.class);
		String xml = xstream.toXML(payResult);
		//TODO 要把__ 替换成_
		xml = xml.replaceAll("__", "_");
		return xml;
	}
	/**
	 * 将对象转换成xml
	 * @author banchun	
	 * @date 2015年8月13日 下午3:35:38 
	 * @param payResult
	 * @return
	 */
	public static String messageToXml(WxPayResult payResult) {
		xstream.alias("xml", WxPayResult.class);
		String xml = xstream.toXML(payResult);
		//TODO 要把__ 替换成_
		xml = xml.replaceAll("__", "_");
		return xml;
	}


	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name,
						@SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
