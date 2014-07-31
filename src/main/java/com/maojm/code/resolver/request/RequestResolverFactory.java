/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.resolver.request;

/**
 * 请求解析工厂
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月22日 下午1:50:27
 */
public class RequestResolverFactory {
	
	/**
	 * 根据content-type决定使用何种解析器
	 * @param paramResolver
	 * @return
	 */
	public static RequestResolver createRequestResolver(ParamResolver paramResolver){
		if(paramResolver.isMultiPartContentType()){
			return new MultipartRequestResolver(paramResolver);
		}
		return new JsonRequestResolver(paramResolver);
	}
}
