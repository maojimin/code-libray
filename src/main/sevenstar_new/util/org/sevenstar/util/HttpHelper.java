package org.sevenstar.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
 import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpHelper {
	private static final String CONTENT_CHARSET = "GBK";// httpclient读取内容时使用的字符集
	private static Log LOG = LogFactory.getLog(HttpHelper.class);

	public static String get(String url) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				CONTENT_CHARSET);
		client.setTimeout(10000);
		GetMethod method = new GetMethod(url);
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器响应错误[" + statusCode + "]");
			}
			byte[] responseBody = null;
			responseBody = method.getResponseBody();
			return new String(responseBody);
		} catch (HttpException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
	}

	public static String post(String url, Map paramMap) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				CONTENT_CHARSET);
		PostMethod method = new PostMethod(url);
		client.setTimeout(10000);
		if (paramMap != null && paramMap.size() > 0) {
			Iterator iter = paramMap.keySet().iterator();
			NameValuePair[] data = new NameValuePair[paramMap.size()];
			List paramList = new ArrayList();
			while (iter.hasNext()) {
				Object key = iter.next();
				Object value = paramMap.get(key);
				paramList.add(new NameValuePair(String.valueOf(key), String
						.valueOf(value)));
			}
			for (int i = 0; i < paramList.size(); i++) {
				data[i] = (NameValuePair) paramList.get(i);
			}
			method.setRequestBody(data);
		}
		try {
			HttpConnectionManagerParams param = new HttpConnectionManagerParams();
			param.setConnectionTimeout(1000);
			client.getHttpConnectionManager().setParams(param);
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器响应错误[" + statusCode + "]");
			}
			byte[] responseBody = null;
			responseBody = method.getResponseBody();
			return new String(responseBody);
		} catch (HttpException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
	}

	public static void main(String[] args) {
		System.out.println(Charset.defaultCharset().name());
		/*
		System.out.println(Runtime.getRuntime().freeMemory());
		System.out.println(Runtime.getRuntime().totalMemory());
		System.out.println(Runtime.getRuntime().maxMemory());
		*/
     //   System.out.println(Long.valueOf(""));
		/*
		Map map = new HashMap();
		map.put("user_id", "2");
		map.put("method", "get");
		// String result =
		// get("http://192.168.0.133/zcscache/user?method=get&user_id=2");
		String result = get("http://localhost/jbosscache/user?method=get&account_id=penglh");
		Pr_userDomain userDomain = (Pr_userDomain) SimpleHttpProtocol
				.stringToObject(result);
		String expresult = get("http://localhost/jbosscache/user_exp?method=flush&user_id=5043727");
		Pr_user_expDomain userExpDomain = (Pr_user_expDomain) SimpleHttpProtocol
				.stringToObject(expresult);
		expresult = get("http://localhost/jbosscache/user_exp?method=get&user_id=5043727");
		  userExpDomain = (Pr_user_expDomain) SimpleHttpProtocol
				.stringToObject(expresult);
		System.out.println(userExpDomain.getOrder());
		*/
	}
}
