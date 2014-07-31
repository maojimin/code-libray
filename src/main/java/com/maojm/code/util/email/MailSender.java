/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util.email;

/**
 * 
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月29日 下午1:08:24
 */
public interface MailSender {
	public boolean sendEmail(final MailMessage message);
}
