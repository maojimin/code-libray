/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util.email;

/**
 * 
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月29日 下午1:08:05
 */
public class MailMessage {
	private String[] mailTo;
	private String[] mailCc;
	private String subject;
	private String content;
	private String attachPath;
	private boolean isHTML;
	
	public MailMessage(String mailTo,String subject,String content){
		this(new String[]{mailTo},null,subject,content,true,null);
	}
	
	public MailMessage(String[] mailTo,String[] mailCc,String subject,
			String content,boolean isHtml,String attachPath){
		this.mailTo = mailTo;
		this.mailCc = mailCc;
		this.subject = subject;
		this.content = content;
		this.isHTML = isHtml;
		this.attachPath = attachPath;
	}

	public String[] getMailTo() {
		return mailTo;
	}

	public String[] getMailCc() {
		return mailCc;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}

	public String getAttachPath() {
		return attachPath;
	}

	public boolean isHTML() {
		return isHTML;
	}
}
