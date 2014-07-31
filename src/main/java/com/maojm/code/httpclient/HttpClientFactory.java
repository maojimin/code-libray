/*
 * 
 * yutian.com.cn Inc.
 * Copyright (c) 浙江宇天科技股份有限公司 2010-2013 All Rights Reserved.
 */
package com.maojm.code.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

/**
 *
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 下午09:50:21-2013-8-10
 * @version 1.0
 */
public class HttpClientFactory {
	
	private PoolingClientConnectionManager connManager;
	private HttpClient httpClient;
	private static HttpClientFactory sington = new HttpClientFactory(); 
	private HttpClientFactory(){
		connManager = new PoolingClientConnectionManager();
		connManager.setDefaultMaxPerRoute(20);
		connManager.setMaxTotal(200);
		httpClient = new DefaultHttpClient(connManager);
	}
	
	/**
	 * 获取线程安全的HttpClient,单例
	 * @author maojimin
	 * @return
	 */
	public static HttpClient getThreadSafeHttpClient(){
		return sington.getHttpClient();
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
	
}
