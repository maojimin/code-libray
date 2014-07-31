/**
 * 
 */
package com.maojm.code.enums;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * @since 2014年4月26日 下午2:35:57
 */
public class MessageBuilderEnum {
	/**
	* 
	*/
	public static final MessageBuilderEnum RESET_PASSWORD_SMS = new MessageBuilderEnum("com.yutian.uic.client.service.internal.user.message.impl.ResetPwSmsMsgSendServiceImpl");
	public static final MessageBuilderEnum RESET_PASSWORD_EMAIL = new MessageBuilderEnum("com.yutian.uic.client.service.internal.user.message.impl.ResetPwMailMsgSendServiceImpl");
	public static final MessageBuilderEnum SMS_FEE = new MessageBuilderEnum("com.yutian.uic.client.service.internal.user.message.impl.SmsFeeMsgSendServiceImpl");
	public static final MessageBuilderEnum TEMP_CODE_SMS = new MessageBuilderEnum("com.yutian.uic.client.service.internal.user.message.impl.TempCodeSmsMsgSendServiceImpl");
	public static final MessageBuilderEnum UNIVERSAL_SMS = new MessageBuilderEnum("com.yutian.uic.client.service.internal.user.message.impl.BoundMsgSendServiceImpl");
	private String className;
	private MessageBuilderEnum(String className) {
	this.className = className;
	}
	public String getBuidlerClassName() {
	return className;
	}
}
