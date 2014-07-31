/**
 * user info center(uic)
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util.email;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * 
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月29日 下午1:08:46
 */
public class MailSenderImpl implements MailSender{
	private String host;
	private String from;
	private String password;
	private Properties mailProperties;
	
	private static final Logger logger = Logger.getLogger(MailSenderImpl.class);
	
	public MailSenderImpl(String host,String userName,String password){
		this.host = host;
		this.from = userName;
		this.password = password;
		mailProperties = new Properties();
		mailProperties.setProperty("host", host);
		mailProperties.setProperty("username", from);
		mailProperties.setProperty("password", password);
		mailProperties.setProperty("mail.smtp.auth", "true");
	}
	
	@Override
	public boolean sendEmail(final MailMessage message)throws MailException{
		try {
			if(message==null || message.getMailTo()==null 
					|| message.getSubject()==null || message.getContent()==null){
				return false;
			}
			JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
			javaMailSenderImpl.setJavaMailProperties(mailProperties);
			javaMailSenderImpl.setHost(host);
			javaMailSenderImpl.setUsername(from);
			javaMailSenderImpl.setPassword(password);
			JavaMailSender javaMailSender = javaMailSenderImpl;
			MimeMessagePreparator mmp = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage)
						throws MessagingException {
					MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage,true,"GBK");
					//设置收件人
					for(String to:message.getMailTo()){
						mmh.addTo(to);
					}
					//设置抄送人
					if(message.getMailCc()!=null){
						for(String cc:message.getMailCc()){
							mmh.addCc(cc);
						}
					}
					mmh.setSubject(message.getSubject());// 设置主题
					mmh.setFrom(from);// 设置发件人
					mmh.setText(message.getContent(), message.isHTML());// 内容
					//添加附件
					try{
						String fileName = null;
						String attachPath = message.getAttachPath();
						if(attachPath!=null && exists(attachPath)){
							fileName = attachPath.substring(attachPath.lastIndexOf(File.separator)+1);
							mmh.addAttachment(fileName, new File(attachPath));
						}
					}catch(Exception e){
						logger.error("addAttachment fail.", e);
					}
				}
			};
			javaMailSender.send(mmp);// 发送
			return true;
		} catch (MailException me) {
			logger.error(me.getMessage(), me);
			throw me;
		}
	}
	
	private boolean exists(String fileName){
		try{
			File f = new File(fileName);
			return f.exists();
		}catch(Exception e){
			logger.error("exists error.",e);
			return false;
		}
	}

}
