/**
 * yutian.com.cn Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.rpc;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013年12月14日-下午5:45:16
 * @version 1.0
 */
public class RpcProvider {
	public static void main(String[] args) throws Exception {
		HelloWorld hello = new HelloWorldImpl();
		RpcFramework.export(hello, 1234);
	}
}
