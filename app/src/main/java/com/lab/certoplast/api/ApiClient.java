package com.lab.certoplast.api;


import android.util.Log;

import com.lab.certoplast.app.AppContext;
import com.lab.certoplast.app.AppException;
import com.lab.certoplast.bean.RequestVo;
import com.lab.certoplast.bean.URLs;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * API客户端接口：用于访问网络数据
 * 
 * @author
 * @version 1.0
 * @created
 */
public class ApiClient {

//		public static final String UTF_8 = "UTF-8";
		public static final String UTF_8 = "GB2312";
		public static final String DESC = "descend";
		public static final String ASC = "ascend";

		private final static int TIMEOUT_CONNECTION = 8000;// timeout_connection
		private final static int TIMEOUT_SOCKET = 8000;// timeout_socket
		private final static int RETRY_TIME = 0;// retry_time
		private static final String TAG = "ApiClient";

		private static final String NOT_LOGIN = "12";

		private static String appCookie;
		private static String appUserAgent;

		public static void cleanCookie() {
			appCookie = "";
		}

		private static String getCookie(AppContext appContext) {
			if (appCookie == null || appCookie == "") {
				appCookie = appContext.getProperty("cookie");

			}
			return appCookie;
		}


		private static HttpClient getHttpClient() {
			HttpClient httpClient = new HttpClient();
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(
					org.apache.commons.httpclient.cookie.CookiePolicy.BROWSER_COMPATIBILITY);
			// 设置 默认的超时重试处理策略
			httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			// 设置 连接超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(TIMEOUT_CONNECTION);
			// 设置 读数据超时时间
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(TIMEOUT_SOCKET);
			// 设置 字符集
			httpClient.getParams().setContentCharset(UTF_8);
			return httpClient;
		}

		private static GetMethod getHttpGet(String url, String cookie) {
			GetMethod httpGet = new GetMethod(url);
			// 设置 请求超时时间
			httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
			httpGet.setRequestHeader("Host", URLs.HOST);
			httpGet.setRequestHeader("Connection", "Keep-Alive");
			return httpGet;
		}

		private static PostMethod getHttpPost(String url, String cookie) {
			PostMethod httpPost = new PostMethod(url);
			// 设置 请求超时时间
			httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
			httpPost.setRequestHeader("Host", URLs.HOST);
			httpPost.setRequestHeader("Connection", "Keep-Alive");
//			httpPost.setRequestHeader("Cookie", cookie);
			return httpPost;
		}


		private static String _MakeURL(String p_url, Map<String, String> params) {

			if (params == null || params.size() <= 0) {

				return p_url;
			}
			StringBuilder url = new StringBuilder(p_url);
			if (url.indexOf("?") < 0)
				url.append('?');

			for (String name : params.keySet()) {
				url.append('&');
				url.append(name);
				url.append('=');
				url.append(String.valueOf(params.get(name)));
			}

			return url.toString().replace("?&", "?");
		}


		/**
		 * get请求URL
		 *
		 * @param
		 * @throws AppException
		 * @throws JSONException
		 */
		public static Object http_get(AppContext appContext, RequestVo vo)
				throws AppException {

			String cookie = getCookie(appContext);
			String url = _MakeURL(vo.requestUrl, vo.requestDataMap);
			System.out.println("_MakeURL(url)====" + url);
			HttpClient httpClient = null;
			GetMethod httpGet = null;

			// String responseBody = "";
			int time = 0;
			Object obj = null;
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, cookie);
				int statusCode = httpClient.executeMethod(httpGet);
				Log.e(TAG, "statusCode:" + statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					throw new AppException(
							AppException.TaskError.timeout.toString());
				}

				String responseBody = httpGet.getResponseBodyAsString();

				Log.e(TAG, "responseBody:" + responseBody);

				try {
					// 检测是否登陆过期
					obj = vo.jsonParser.parse(responseBody);

				} catch (Exception e) {
					e.printStackTrace();
					throw new AppException(
							AppException.TaskError.timeout.toString());
				}

				return obj;
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} catch (ConnectTimeoutException e) {
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} catch (UnknownHostException e) {
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} catch (IOException e) {
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		}


		/**
		 * 公用post方法
		 *
		 * @param
		 * @param
		 * @param
		 * @throws AppException
		 * @throws JSONException
		 */
		public static Object _post(AppContext appContext, RequestVo vo)
				throws AppException {

			String cookie = getCookie(appContext);

			HttpClient httpClient = null;
			PostMethod httpPost = null;
			HashMap<String, String> params = vo.requestDataMap;

			// post表单参数处理
			int length = (params == null ? 0 : params.size());
			NameValuePair[] parts = new NameValuePair[length];
			int i = 0;
			if (params != null)
				for (String name : params.keySet()) {
					parts[i++] = new NameValuePair(name, String.valueOf(params
							.get(name)));
					System.out.println("post_key==> " + name + "    value==>"
							+ String.valueOf(params.get(name)));
				}

			int time = 0;
			Object obj = null;

			try {
				httpClient = getHttpClient();


				String HOST = appContext.ipPort();
				String HTTP = "http://";
				String URL_SPLITTER = "/";
				String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
				String WSDI_URI = URL_API_HOST + "REDERP_WEBSERVICE.asmx";

				httpPost = getHttpPost(WSDI_URI.concat("/" + vo.methodName), cookie);

				httpPost.setRequestBody(parts);
				int statusCode = httpClient.executeMethod(httpPost);
				System.out.println("返回结果:=============" + statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					//请求失败异常
					throw new AppException(
							AppException.TaskError.timeout.toString());
				} else if (statusCode == HttpStatus.SC_OK) {
					org.apache.commons.httpclient.Cookie[] cookies = httpClient
							.getState().getCookies();
					String tmpcookies = "";
					for (org.apache.commons.httpclient.Cookie ck : cookies) {
						tmpcookies += ck.toString() + ";";
					}
					// 保存cookie
					if (appContext != null && tmpcookies != "") {
						appContext.setProperty("cookie", tmpcookies);
						appCookie = tmpcookies;
					}
				}

				String responseBody = httpPost.getResponseBodyAsString();
				Log.e(TAG,"responseBody:" + responseBody);
				try{
					obj = vo.jsonParser.parse(responseBody);
				}catch (XmlPullParserException e){
					//解析服务器数据异常
					e.printStackTrace();
					throw new AppException(
							AppException.TaskError.timeout.toString());
				}

				return obj;

			} catch (SocketTimeoutException e) {
				//连接服务器异常
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} catch (ConnectTimeoutException e) {
				//连接服务器超时
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} catch (UnknownHostException e) {
				//未知的主机异常
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} catch (IOException e) {
				//读写数据异常
				e.printStackTrace();
				throw new AppException(AppException.TaskError.timeout.toString());
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}

		}


	//
	public static enum Status {
		Login
	}

}
