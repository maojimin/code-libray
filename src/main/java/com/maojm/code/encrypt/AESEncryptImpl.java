/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013-7-8 - 下午03:17:43
 * @version 1.0
 */
public class AESEncryptImpl implements Encrypt{

	private String secretKey = "jzhtyyd!@###$";
	private Cipher decryptCipher;
	private Cipher encryptCipher;
	private static final Logger logger = Logger.getLogger(AESEncryptImpl.class);
	public AESEncryptImpl(String secretKey){
		this.secretKey = secretKey;
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("AES");
			//防止linux下 随机生成key
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(secretKey.getBytes());
			kgen.init(128,secureRandom);
			SecretKey secKey = kgen.generateKey();
			byte[] enCodeFormat = secKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			decryptCipher = Cipher.getInstance("AES");// 创建密码器
			decryptCipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
			
			encryptCipher = Cipher.getInstance("AES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (Exception e) {
			logger.error("aes init error", e);
		}  
       
	}

	
	@Override
	public String decode(String pwd) {
		 try {
			 byte[] content = parseHexStr2Byte(pwd);
             byte[] result = decryptCipher.doFinal(content);  
             return new String(result); // 加密   
	    } catch (Exception e) {  
	    	logger.error("decode error", e);
	        return null;
	    }  
	}

	@Override
	public String encode(String text) {
		  try {
              byte[] byteContent = text.getBytes();  
              byte[] result = encryptCipher.doFinal(byteContent);  
              return parseByte2HexStr(result);// 加密   
      } catch(Exception e){
          logger.error("encode error", e);
          return null;
      }  
	}
	
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	public String parseByte2HexStr(byte buf[]) {  
	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < buf.length; i++) {  
	                String hex = Integer.toHexString(buf[i] & 0xFF);  
	                if (hex.length() == 1) {  
	                        hex = '0' + hex;  
	                }  
	                sb.append(hex.toUpperCase());  
	        }  
	        return sb.toString();  
	}  
	
	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */  
	public byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {  
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}
	
	public static void main(String[] args) {
		AESEncryptImpl aes = new AESEncryptImpl("1111");
		String s = aes.encode("123456");
		System.out.println(s);
		System.out.println(aes.decode(s));
	}

}
