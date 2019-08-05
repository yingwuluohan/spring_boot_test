package com.unisound.iot.common.util;

import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.chat.client.DefaultHttpClient;


/**
 * 封装常用的HTTP请求的GET/POST方法
 */
public class HttpRequestUtil {
	private static final int defaultConnectTimeout = 3000;
	private static final int defaultSocketTimeout = 5000;
	/**
	 * 向指定URL发送GET方法的请求(连接超时时间为30秒)
	 * 
	 * @param url
	 *            发送请求的URL（包括请求参数）
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendGet(String url) {
		// 连接超时时间为30秒
		return sendGet(url, 30 * 1000);
	}

	/**
	 * 向指定 URL 发送POST方法的请求(连接超时时间为30秒)
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param) {
		// 连接超时时间为30秒
		return sendPost(url, param, 30 * 1000, 30 * 1000);
	}

	/**
	 * 向指定 URL 发送POST方法的请求 (读取超时时间为30秒)
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param conTimeout
	 *            连接超时时间
	 * @return
	 */
	public String sendPost(String url, String param, int conTimeout) {
		// 连接超时时间为30秒
		return sendPost(url, param, conTimeout, 30 * 1000);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL（包括请求参数）
	 * @param timeout
	 *            连接超时时间
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendGet(String url, int timeout) {
		if (url == null || url.trim().length() == 0) {
			return null;
		}
		BufferedReader in = null;
		HttpURLConnection conn = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");
			conn.setConnectTimeout(timeout);
			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
//			logger.error("发送GET请求过程中出错：" + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param
	 *
	 * @return URL所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param, int conTimeout, int readTimeout) {
		if (url == null || url.trim().length() == 0) {
			return null;
		}

		PrintWriter out = null;
		BufferedReader in = null;
		HttpURLConnection conn = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestMethod("POST");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "close");

			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
			conn.setRequestProperty("contentType", "GBK");

			conn.setConnectTimeout(conTimeout);
			conn.setReadTimeout(readTimeout);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
//			logger.error("发送POST请求过程中出错：" + e.getMessage(), e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return result.toString();
	}

	private static List<BasicNameValuePair> mapToNameValuePairs(Map<String, String> parmas) {
		if (null == parmas || parmas.size() == 0) {
			return null;
		}

		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : parmas.entrySet()) {
			if (null == entry) {
				continue;
			}
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		return nameValuePairs;
	}


//	public static String httpPostWithJson(JSONObject jsonObj, String url ){
//		String result = "";
//		HttpPost post = null;
//		try {
//			HttpClient httpClient = new DefaultHttpClient();
//			// 设置超时时间
//			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
//			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
//			post = new HttpPost(url);
//			// 构造消息头
//			post.setHeader("Content-type", "application/json; charset=utf-8");
//			post.setHeader("Connection", "Close");
//			// 构建消息实体
//			logger.info( "HTTP 请求的内容:" +  jsonObj.toString() );
//			StringEntity entity = new StringEntity(jsonObj.toString(), Charset.forName("UTF-8"));
//			entity.setContentEncoding("UTF-8");
//			// 发送Json格式的数据请求
//			entity.setContentType("application/json");
//			post.setEntity(entity);
//			HttpResponse response = httpClient.execute(post);
//			// 检验返回码
//			int statusCode = response.getStatusLine().getStatusCode();
//			if(statusCode != HttpStatus.SC_OK){
//				logger.info("请求出错: "+statusCode);
//				return "fail";
//			}else{
//				int retCode = 0;
//				String sessendId = "";
//				// 返回码中包含retCode及会话Id
//				/*
//				for(Header header : response.getAllHeaders()){
//					if(header.getName().equals("retcode")){
//						retCode = Integer.parseInt(header.getValue());
//					}
//				}*/
//				String responseResult= EntityUtils.toString(response.getEntity());
//				//得到返回的字符串
//				logger.info("芯片接口请求结果:" + responseResult);
//				result = responseResult;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = "fail";
//			logger.info( "芯片接口异常" + e.getMessage() ,e );
//		}finally{
//			if(post != null){
//				try {
//					post.releaseConnection();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return result;
//	}
}