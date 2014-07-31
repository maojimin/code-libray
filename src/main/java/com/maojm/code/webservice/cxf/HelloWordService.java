/**
 * 
 * yutian.com.cn Inc.
 * Copyright (c) �㽭����Ƽ��ɷ����޹�˾ 2011-2013 All Rights Reserved.
 */
package com.maojm.code.webservice.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 下午04:20:55-2013-7-2
 * @version 1.0
 */
@WebService(targetNamespace = "http://www.yutian.com.cn", name = "HelloWordService")
public interface HelloWordService {
	@WebMethod
	public String sayHi(@WebParam(name = "user", targetNamespace = "http://www.yutian.com.cn")User user);
}
