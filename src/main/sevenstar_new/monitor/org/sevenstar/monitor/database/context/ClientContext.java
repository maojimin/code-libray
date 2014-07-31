package org.sevenstar.monitor.database.context;

public class ClientContext {

	
	private String protocol;
	
	private String length;
	
	private String method;
	
	private String clientName;

	
	private String clientIp;

	
	private String clientSystemInfo;

	
	private String clientLanguage;

	private static ThreadLocal clientContext = new ThreadLocal();

	public static ClientContext getCurrentContext() {
		return (ClientContext) clientContext.get();
	}

	public static void setCurrentContext(ClientContext context) {
		clientContext.set(context);
	}


	public String toString() {
		return "clientName:[" + clientName + "]   clientIp:[" + clientIp
				+ "]   clientSystemInfo:[" + clientSystemInfo
				+ "]   clientLanguage:[" + clientLanguage + "]   protocol:["
				+ protocol + "]  length:[" + length + "]   method:[" + method
				+ "]";
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientSystemInfo() {
		return clientSystemInfo;
	}

	public void setClientSystemInfo(String clientSystemInfo) {
		this.clientSystemInfo = clientSystemInfo;
	}

	public String getClientLanguage() {
		return clientLanguage;
	}

	public void setClientLanguage(String clientLanguage) {
		this.clientLanguage = clientLanguage;
	}

}
