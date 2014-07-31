/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.encrypt;

import java.security.MessageDigest;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a> 2013-7-5 - 下午03:43:31
 * @version 1.0
 */
public class MD5EncryptImpl implements Encrypt {

	private final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public String byteArrayToHexString(byte byteArray[]) {
		StringBuffer strHex = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++)
			strHex.append(byteToHexString(byteArray[i]));

		return strHex.toString();
	}

	private String byteToHexString(byte bt) {
		int i = bt;
		if (i < 0)
			i += 256;
		int j = i / 16;
		int k = i % 16;
		return hexDigits[j] + hexDigits[k];
	}

	@Override
	public String decode(final String password) {
		return null;
	}

	@Override
	public String encode(final String text) {
		String passwrod = null;
		try {
			passwrod = new String(text);
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			passwrod = byteArrayToHexString(messageDigest.digest(passwrod
					.getBytes()));
		} catch (Exception exception) {
			
		}
		return passwrod;
	}

}
