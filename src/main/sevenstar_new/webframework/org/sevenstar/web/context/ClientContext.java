package org.sevenstar.web.context;

public class ClientContext
{
  private String protocol;
  private String length;
  private String method;
  private String clientName;
  private String clientIp;
  private String clientSystemInfo;
  private String clientLanguage;


  public String toString()
  {
     return "clientName:[" + this.clientName + "]   clientIp:[" + this.clientIp + 
      "]   clientSystemInfo:[" + this.clientSystemInfo + 
       "]   clientLanguage:[" + this.clientLanguage + "]   protocol:[" + 
       this.protocol + "]  length:[" + this.length + "]   method:[" + this.method + 
       "]";
  }

	
  public String getProtocol() {
     return this.protocol;
  }

  public void setProtocol(String protocol) {
     this.protocol = protocol;
  }

  public String getLength() {
     return this.length;
  }

  public void setLength(String length) {
     this.length = length;
  }

  public String getMethod() {
     return this.method;
  }

  public void setMethod(String method) {
     this.method = method;
  }

  public String getClientName() {
     return this.clientName;
  }

  public void setClientName(String clientName) {
     this.clientName = clientName;
  }

  public String getClientIp() {
     return this.clientIp;
  }

  public void setClientIp(String clientIp) {
     this.clientIp = clientIp;
  }

  public String getClientSystemInfo() {
     return this.clientSystemInfo;
  }

  public void setClientSystemInfo(String clientSystemInfo) {
     this.clientSystemInfo = clientSystemInfo;
  }

  public String getClientLanguage() {
     return this.clientLanguage;
  }

  public void setClientLanguage(String clientLanguage) {
     this.clientLanguage = clientLanguage;
  }
}
