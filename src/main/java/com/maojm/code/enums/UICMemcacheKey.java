/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.enums;

import java.text.MessageFormat;

/**
 * 缓存key 枚举
 * 支持静态key和动态key
 * 静态key 调用 getKey()
 * 动态key 调用getKey(Object ...objects )
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013-7-5 - 上午01:48:58
 * @version 1.0
 */
public enum UICMemcacheKey {
	/**
	 * 根据用户Id缓存用户实体
	 */
	UIC_USER_BY_ID("uic_user_by_id:{0}"),
	/**
	 * 根据用户账号缓存用户ID
	 */
	UIC_USERID_BY_ACCOUNT("uic_userid_by_account:{0}"),
	/**
	 * 根据userId,bookId缓存系统书签实体
	 */
	UIC_SYSBK_BY_USERID_BOOKID("uic_sysbk_by_userid:{0}_bookid:{1}"),
	
	/**
	 * 根据userId,bookId缓存系统书签实体
	 */
	UIC_USERBK_BY_USERID_BOOKID_CHAPTERID("uic_sysbk_by_userid:{0}_bookid:{1}_chapterid{2}"),
	
	/**
	 * 根据userId缓存阅迹PageCacheModel
	 */
	UIC_PAGE_CACHE_READTRACE_BY_USERID("uic_page_cache_readtrace_by_userid:{0}"),
	
	/**
	 * 根据userId缓存系统书签PageCacheModel
	 */
	UIC_PAGE_CACHE_SYSBOOKMARK_BY_USERID("uic_page_cache_sysbk_by_userid:{0}"),
	
	/**
	 * 根据userId.bookId缓存用户书签PageCacheModel
	 */
	UIC_PAGE_CACHE_USERBOOKMARK_BY_USERID_BOOKID("uic_page_cache_userbk_by_userid:{0}_bookid{1}"),
	
	/**
	 * 根据userId缓存用户喜欢PageCacheModel
	 */
	UIC_PAGE_CACHE_FAVORITE_BY_USERID("uic_page_cache_favorite_by_userid:{0}"),
	
	UIC_FAVORITE_BY_USERID_BOOKID("uic_favorite_by_userid:{0}_bookid{1}"),
	
	UIC_FAVORITE_BOOKIDLIST_BY_USERID("uic_favorite_bookidlist_by_userid:{0}"),
	
	UIC_FORGET_PASSWORD_TEMPLATE("uic_forget_password_template"),
	
	/**
	 * 根据用户账号缓存验证码
	 */
	UIC_PASSWORD_CODE_BY_ACCOUNT("uic_password_code_by_account:{0}"),
	
	/**
	 * 根据imei缓存IphoneDeviceToken实体
	 */
	CACHE_IPHONE_DEVICE_TOKEN_BY_IMEI("uic_iphone_device_token_by_imei:{0}"),
	;
	
	private String key;
	private UICMemcacheKey(String key){
		this.key = key;
	}
	public String getKey(){
		return key;
	}
	public String getKey(Object ...objects ){
		return MessageFormat.format(key, objects);
	}
	
	public static void main(String[] args) {
		System.out.println(UICMemcacheKey.UIC_USER_BY_ID.getKey(11212121212L));
	}
}
