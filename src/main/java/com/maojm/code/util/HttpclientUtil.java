/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.maojm.code.httpclient.HttpClientFactory;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月30日 下午10:29:20
 */
public class HttpclientUtil {
	
	private static final Log log = LogFactory.getLog(HttpclientUtil.class);
	
	public static HttpResponse doPost(String url,HashMap<String, String> headerMap,HashMap<String, String> paramMap){
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			setHeaders(post, headerMap);
			MapUtil<String, String> mapUtil = new MapUtil<String, String>(paramMap);
			if(mapUtil.size()>0){
				String[] pareVals = new String[mapUtil.size()];
				int i = 0;
				while (mapUtil.hasNext()) {
					Entry<String, String> entry = mapUtil.next();
					pareVals[i++] = entry.getKey()+"="+entry.getValue(); 
				}
				StringEntity entity = new StringEntity(StringUtils.join(pareVals, "&"),"UTF-8");
				post.setEntity(entity);
			} 
			HttpResponse response = HttpClientFactory.getThreadSafeHttpClient().execute(post);
			return response;
		} catch (Exception e) {
			log.error("doPost fail", e);
			return null;
		}finally{
			post.abort();
		}
	}
	
	public static HttpResponse doMultipartPost(String url,HashMap<String, String> headerMap,HashMap<String, Object> paramMap){
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			setHeaders(post,headerMap);
			MapUtil<String, Object> mapUtil = new MapUtil<String, Object>(paramMap);
			if(mapUtil.size()>0){
				MultipartEntity entity = new MultipartEntity();
				while (mapUtil.hasNext()) {
					Entry<String, Object> entry = mapUtil.next();
					Object val = entry.getValue();
					if(val == null){
						continue;
					}
					if(val instanceof File){
						entity.addPart(entry.getKey(), new FileBody((File)val));
					}else{
						entity.addPart(entry.getKey(), new StringBody(val.toString()));
					}
				}
				post.setEntity(entity);
			} 
			HttpResponse response = HttpClientFactory.getThreadSafeHttpClient().execute(post);
			return response;
		} catch (Exception e) {
			log.error("doMultipartPost fail.", e);
			return null;
		}finally{
			post.abort();
		}
	}
	
	private static void setHeaders(HttpUriRequest request,HashMap<String, String> headMap){
		MapUtil<String, String> mapUtil = new MapUtil<String, String>(headMap);
		while(mapUtil.hasNext()){
			Entry<String, String> entry = mapUtil.next();
			request.addHeader(entry.getKey(), entry.getValue());
		}
	}
}
