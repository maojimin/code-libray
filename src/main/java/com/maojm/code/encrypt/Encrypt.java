/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.encrypt;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013-7-5 - 下午03:39:26
 * @version 1.0
 */
public interface Encrypt {
	/**
	 * 加密
	 * @param text
	 * @return
	 */
	public String encode(final String text);
	
	/**
	 * 解密
	 * @param password
	 * @return
	 */
	public String decode(final String password);
}
