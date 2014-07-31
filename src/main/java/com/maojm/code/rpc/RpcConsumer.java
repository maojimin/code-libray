/**
 * yutian.com.cn Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.rpc;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013年12月14日-下午5:46:59
 * @version 1.0
 */
public class RpcConsumer {
	public static void main(String[] args) throws Exception {
		HelloWorld service = RpcFramework.refer(HelloWorld.class, "127.0.0.1", 1234);
		for (int i = 0; i < Integer.MAX_VALUE; i ++) {
			String hello = service.sayHello("World" + i);  
			System.out.println(hello);  
			Thread.sleep(1000);  
		}
	}
}
