/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.resolver.request;

import java.util.HashMap;
import java.util.Map;

import com.maojm.code.encrypt.AESEncryptImpl;
import com.maojm.code.encrypt.Encrypt;
import com.maojm.code.json.JacksonHelper;
import com.maojm.code.resolver.request.ParamResolver.ParamInfo;

/**
 * 解析json格式请求
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月17日 下午10:26:14
 */
public class JsonRequestResolver extends AbstractRequestResolver implements RequestResolver{
	private final Map<String, Object> paramMap = new HashMap<String, Object>();
	private String jsonData;
	
	/**
	 * @param method
	 * @param args
	 */
	public JsonRequestResolver(ParamResolver paramResolver) {
		super(paramResolver);
	}
	
	/**
	 * 解析request
	 */
	public void resolveRequest() throws Exception {
		resolveRequestBody();
		invokeParams();
	}
	
	
	@SuppressWarnings("unchecked")
	private void resolveRequestBody() throws Exception{
		
		Map<String, Object> map = resolveRequestParameters();
		if(map!=null){
			paramMap.putAll(map);
		}
		
		// 解析Request body
//		String reqBody = RequestUtil.getBody(paramResolver.getRequest());
		String reqBody = null;
		Encrypt encrypt = new AESEncryptImpl("aeskey");
		jsonData = encrypt.decode(reqBody);
		if(jsonData!=null){
			Map<String, Object> tempMap = JacksonHelper.fromJson(jsonData, HashMap.class);
			if(tempMap!=null){
				paramMap.putAll(tempMap);
			}
		}
	}
	
	private void invokeParams() throws IllegalArgumentException{
		for(ParamInfo paramInfo : paramResolver.getParamInfoList()){
			Class<?> paramType = paramInfo.getParamType();
			String key = paramInfo.getParam().value();
			Object value = null;
			if(paramInfo.getParam()!=null && paramInfo.getParam().required() 
					&& paramMap.get(key) == null){
				throw new IllegalArgumentException(key+" is required.");
			}
			try{
				if(key==null || "".equals(key)){
					value = jsonData;
				}else{
					if(paramMap.get(key)!=null && paramMap.get(key) instanceof HashMap){
						value = JacksonHelper.toJson(paramMap.get(key));
					}else{
						value = paramMap.get(key);
					}
				}
				Object realVal = parseMethodParamValue(paramType, value);
				if(realVal == null){
					continue;
				}
				paramResolver.invokeParamValue(paramInfo.getParamIndex(), realVal);
			}catch(Exception e){
				throw new IllegalArgumentException("param:" + key + ",value:" + value
						+ ",type:" + paramType.getClass().getSimpleName() + " invoke fail.");
			}
		}
	}

}

