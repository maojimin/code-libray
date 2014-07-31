/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.resolver.request;


/**
 * 解析接口
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月17日 下午3:25:29
 */
public interface RequestResolver {
	/**
	 * 解析request
	 * @throws ParameterDecodeException
	 * @throws Exception
	 */
	public void resolveRequest() throws Exception;
	
	/**
	 * 响应response
	 */
	public void resolveResponse();
	
	/**
	 * 响应错误的response
	 * @param stateCode
	 */
	public void resolveErrorResponse(int stateCode);
	
}
