/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.encrypt;

import org.apache.commons.codec.binary.Base64;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013-7-8 - 下午02:04:40
 * @version 1.0
 */
public class Base64EncryptImpl implements Encrypt{

	@Override
	public String decode(String password) {
		if(password==null){
			return null;
		}
		return new String(Base64.decodeBase64(password));
	}
	
	@Override
	public String encode(String text) {
		if(text==null){
			return null;
		}
		return new String(Base64.encodeBase64(text.getBytes()));
	}
	
	public static void main(String[] args) {
		Base64EncryptImpl base64 = new Base64EncryptImpl();
		String ret = base64.encode("/user=abcd@163.com&password=123456");
		System.out.println(ret);
		System.out.println(base64.decode(ret));
		
	}

}
