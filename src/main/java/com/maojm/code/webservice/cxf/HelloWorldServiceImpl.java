/**
 * 
 * yutian.com.cn Inc.
 * Copyright (c) �㽭����Ƽ��ɷ����޹�˾ 2011-2013 All Rights Reserved.
 */
package com.maojm.code.webservice.cxf;

import javax.jws.WebService;

/**
 *
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 下午05:25:44-2013-7-2
 * @version 1.0
 */
@WebService(endpointInterface="com.maojm.code.webservice.cxf.HelloWordService",
		targetNamespace="http://www.yutian.com.cn")
public class HelloWorldServiceImpl implements HelloWordService{

	
	@Override
	public String sayHi(User user) {
		System.out.println("hello,"+user.getName());
		return "OK";
	}
}
