package org.sevenstar.component.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 对称加解密
 * 
 * @author zxf
 * 
 */
public class AES {
	private static Log LOG = LogFactory.getLog(AES.class);
	private static String KEY = "~i6n5txn2td3q:zh";

	public static void main(String[] args) throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 */
		/*
		// 需要加密的字串
		String cSrc = "我的MSN：xxxx@hotmail.com，QQ：10000";
		// 加密
		long lStart = System.currentTimeMillis();
		String enString = null;
		for (int i = 0; i < 100; i++) {
			enString = AES.Encrypt(cSrc, KEY);
			System.out.println("加密后的字串是：" + enString);
		}
		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");
		// 解密
		lStart = System.currentTimeMillis();
		String DeString = AES.Decrypt(enString, KEY);
		System.out.println("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");
		*/
		System.out.println(Encrypt("~i6n5tx:zasssssssssss",KEY).length());
		long lStart = System.currentTimeMillis();
		String cSrc = "";
		String enString = AES.Encrypt(cSrc, KEY);
	}

	public static String Decrypt(String sSrc, String sKey) {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				LOG.error("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				LOG.error("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = hex2byte(sSrc);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static String Decrypt(String sSrc) {
		return Decrypt(sSrc, KEY);
	}

	public static String Encrypt(String sSrc) {
		return Encrypt(sSrc, KEY);
	}

	// 判断Key是否正确
	public static String Encrypt(String sSrc, String sKey) {
		try {
			if (sKey == null) {
				LOG.error("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				LOG.error("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
					16);
		}
		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
}
