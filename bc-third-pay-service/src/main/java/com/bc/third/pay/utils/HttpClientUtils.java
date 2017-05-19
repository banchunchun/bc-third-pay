package com.bc.third.pay.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Company        :   mamahao.com
 * author         :   banchun
 * Date           :   2016/1/6
 * Time           :   15:14
 * Description    :
 */
public class HttpClientUtils {
	//默认编码
	public static final String 			DEFAUL_CHARSET = "UTF-8";
	public static final String			HTTP_GET = "get";
	public static final String			HTTP_POST = "post";
	//默认重试次数
	public static final	int				MAX_RETRY_TIMES = 3;
	//默认主机最大连接数
	public static final	int 			MAX_CONNECTION_DEFAULT_ROUTE = 30;
	//最大连接数
	public static final int 			MAX_CONNECTION_MMH_API_ROUTE = 220;
	//最大活动连接数
	public static final	int				MAX_CONNECTION_TOTAL = 500;
	//SOCKET超时时间
	public static final	int				SOCKET_TIMEOUT = 60000;
	//CONNECT超时时间
	public static final	int				CONNECT_TIMEOUT = 60000;
	//CONNECT_REQUEST超时时间
	public static final	int				CONNECT_REQUEST_TIMEOUT = 60000;



	public static final String			MMH_API_HOST = "http://api.mamahao.com/";

	//连接池
	private static PoolingHttpClientConnectionManager cm 				= 			null;
	private static RequestConfig requestConfig	=			null;
	private static HttpClientBuilder clientBuilder	=			null;

	static {
		requestConfig = RequestConfig.custom()
				.setSocketTimeout(SOCKET_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
				.build();

		ConnectionSocketFactory plainsf 	= 		PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf 		= 		SSLConnectionSocketFactory.getSocketFactory();

//		Registry<ConnectionSocketFactory>		registry	= 		RegistryBuilder.<ConnectionSocketFactory>create()
//																	.register("http",plainsf)
//																	.register("https",sslsf).build();

		cm = new PoolingHttpClientConnectionManager();

		//设置最大连接数
		cm.setMaxTotal(MAX_CONNECTION_TOTAL);
		//设置每个主机默认连接数
		cm.setDefaultMaxPerRoute(MAX_CONNECTION_DEFAULT_ROUTE);

		//设置主机的连接数
		HttpHost mamhaoApiHost = new HttpHost(MMH_API_HOST);
		cm.setMaxPerRoute(new HttpRoute(mamhaoApiHost),MAX_CONNECTION_MMH_API_ROUTE);

		clientBuilder = HttpClients.custom();
		clientBuilder.setConnectionManager(cm);
		clientBuilder.setRetryHandler(new HttpRequestRetryHandler() {
			@Override public boolean retryRequest(IOException e, int executionCount, HttpContext httpContext) {
				if(executionCount >= MAX_RETRY_TIMES){
					return false;
				}
				//丢失了连接重试
				if(e instanceof NoHttpResponseException){
					return true;
				}
				//连接被中断不重试
				if(e instanceof InterruptedIOException){
					return false;
				}
				//目标不能识别不重试
				if(e instanceof UnknownHostException){
					return false;
				}
				//连接超时不重试
				if(e instanceof ConnectTimeoutException){
					return false;
				}
				//https异常不重试
				if(e instanceof SSLException){
					return false;
				}
				HttpClientContext context = HttpClientContext.adapt(httpContext);
				HttpRequest request = context.getRequest();
				//幂等的请求重试（get、delete是幂等的，post不是幂等的）
				if(!(request instanceof HttpEntityEnclosingRequest)){
					return true;
				}
				return false;
			}
		});
	}

	public static void setMaxPerRoute(String host,int max){
		HttpHost httpHost = new HttpHost(host);
		HttpRoute route = new HttpRoute(httpHost);
		cm.setMaxPerRoute(route,max);
	}

	public static PoolingHttpClientConnectionManager getConnectionManager(){
		return cm;
	}

	public static CloseableHttpClient getConnect(){
		CloseableHttpClient client = clientBuilder.build();
		return client;
	}

	public static String post(String url){
		return post(url,null,null,DEFAUL_CHARSET);
	}

	public static String post(String url,String charset){
		return post(url,null,null,charset);
	}

	public static String post(String url,Map<String,String> params){
		return post(url,params,null,DEFAUL_CHARSET);
	}

	public static String post(String url,Map<String,String> params,String charset){
		return post(url,params,null,charset);
	}

	public static String post(String url,Map<String,String> params,Map<String,String> headers){
		return post(url,params,headers,DEFAUL_CHARSET);
	}


	public static String post(String url,Map<String,String> params,Map<String,String> headers,String charset){
		CloseableHttpClient client = getConnect();
		HttpUriRequest httpPost = getRequestMethod(url,params,headers,HTTP_POST,charset);
		String responseStr = null;
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
			responseStr =  getResponse(response);
		} catch (IOException e) {
//			logger.error("请求发送异常#01：",e);
			return null;
		} catch (HttpException e) {
//			logger.error("请求发送异常#02：",e);
		} finally {
			//每次请求结束应将Entity消费掉，否则容易出现connecion leak
			if(response != null){
				EntityUtils.consumeQuietly(response.getEntity());
			}
		}
		return responseStr;
	}


	public static String get(String url){
		return get(url,null,null,DEFAUL_CHARSET);
	}

	public static String get(String url,String charset){
		return get(url,null,null,charset);
	}

	public static String get(String url,Map<String,String> params){
		return get(url,params,null,DEFAUL_CHARSET);
	}

	public static String get(String url,Map<String,String> params,String charset){
		return get(url,params,null,charset);
	}

	public static String get(String url,Map<String,String> params,Map<String,String> headers){
		return get(url,params,headers,DEFAUL_CHARSET);
	}

	public static String get(String url,Map<String,String> params,Map<String,String> headers,String charset){
		CloseableHttpClient client = getConnect();
		HttpUriRequest httpGet = getRequestMethod(url,params,headers,HTTP_GET,charset);
		String responseStr = null;
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			responseStr =  getResponse(response);
//			InputStream inputStream = response.getEntity().getContent();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//			String tmp = null;
//			StringBuffer buf = new StringBuffer();
//			while ((tmp = reader.readLine())  != null){
//				buf.append(tmp);
//			}
//			responseStr =  buf.toString();
		} catch (IOException e) {
//			logger.error("请求发送异常#01：",e);
			return null;
		} catch (HttpException e) {
//			logger.error("请求发送异常#02：",e);
		} finally {
			//每次请求结束应将Entity完全消费掉，否则容易出现connecion leak
			if(response != null){
				EntityUtils.consumeQuietly(response.getEntity());
			}
		}
		return responseStr;
	}

	private static String getResponse(CloseableHttpResponse response) throws IOException, HttpException {
		if(HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
			HttpEntity entity = response.getEntity();
			String str = EntityUtils.toString(entity,Charset.forName("utf-8"));
			if(StringUtils.isNotBlank(str) && "null".equalsIgnoreCase(str)){
				str = null;
			}
			return str;
		}else{
			throw new HttpException("HTTP状态码:".concat(String.valueOf(response.getStatusLine().getStatusCode())));
		}
	}

	public static HttpUriRequest getRequestMethod(String url,String method,String charset){
		return getRequestMethod(url,null,null,method,charset);
	}


	public static HttpUriRequest getRequestMethod(String url,Map<String,String> params,String method,String charset){
		return getRequestMethod(url,params,null,method,charset);
	}

	public static HttpUriRequest getRequestMethod(String url,Map<String,String> params,Map<String,String> headers,String method,String charset){
		List<NameValuePair> paramPairs = new ArrayList<>();
		if(params != null && params.size() > 0){
			Set<Map.Entry<String, String>> entrySet = params.entrySet();
			for (Map.Entry<String, String> e : entrySet) {
				String name = e.getKey();
				String value = e.getValue();
				NameValuePair pair = new BasicNameValuePair(name, value);
				paramPairs.add(pair);
			}
		}

		HttpUriRequest reqMethod = null;
		Charset cs = null;
		if(StringUtils.isNotBlank(charset)){
			cs = Charset.forName(charset);
		}else{
			cs = Charset.forName(DEFAUL_CHARSET);
		}
		if (HTTP_POST.equals(method)) {
			reqMethod = RequestBuilder.post().setUri(url)
//					.setCharset(cs)
					.addParameters(paramPairs.toArray(new BasicNameValuePair[paramPairs.size()]))
					.setConfig(HttpClientUtils.requestConfig).build();
		} else if (HTTP_GET.equals(method)) {
			reqMethod = RequestBuilder.get().setUri(url)
//					.setCharset(cs)
					.addParameters(paramPairs.toArray(new BasicNameValuePair[paramPairs.size()]))
					.setConfig(requestConfig).build();
		}
		if(headers != null && headers.size() > 0){
			Set<Map.Entry<String, String>> entrySet = headers.entrySet();
			for (Map.Entry<String, String> e : entrySet) {
				String name = e.getKey();
				String value = e.getValue();
				Header header = new BasicHeader(name,value);
				reqMethod.setHeader(header);
			}
		}
		return reqMethod;
	}

}
