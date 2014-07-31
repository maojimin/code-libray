/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.vo;


/**
 * 上下文模型
 * 持有请求报头，请求体，响应报头，响应体
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a> 
 * 2014年4月16日 下午3:50:24
 */
public class ContextModel{
	/**
	 * 请求报头
	 */
	private RequestHeader requestHeader;
	/**
	 * 请求体
	 */
	private Object requestBody;
	/**
	 * 响应报头
	 */
	private ResponseHeader responseHeader;
	/**
	 * 响应体
	 */
	private Object responseBody;
	
	public ContextModel(){
		this.responseHeader = new ResponseHeader();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getResponseBoday() {
		return (T) responseBody;
	}

	public Integer getEspState() {
		return responseHeader.getEspState();
	}

	public void setEspState(Integer stateCode) {
		this.responseHeader.setEspState(stateCode);
	}
	
	public String getContentType(){
		return this.responseHeader.getContentType();
	}
	
	public void setContentType(String contentType){
		this.responseHeader.setContentType(contentType);
	}
	
	public String getTransId(){
		return this.requestHeader.getTransId();
	}

	/**
	 * @return the responseBody
	 */
	public Object getResponseBody() {
		return responseBody;
	}

	/**
	 * @param responseBody the responseBody to set
	 */
	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

	/**
	 * @return the requestBody
	 */
	public Object getRequestBody() {
		return requestBody;
	}

	/**
	 * @param requestBody the requestBody to set
	 */
	public void setRequestBody(Object requestBody) {
		this.requestBody = requestBody;
	}

	/**
	 * @return the requestHeader
	 */
	public RequestHeader getRequestHeader() {
		return requestHeader;
	}

	/**
	 * @param requestHeader the requestHeader to set
	 */
	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	/**
	 * @return the responseHeader
	 */
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	/**
	 * @param responseHeader the responseHeader to set
	 */
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	public String getCityId()
	{
		return this.requestHeader.getCityId();
	}

	public String getClientVersion()
	{
		return this.requestHeader.getClientVersion();
	}

	public String getCua()
	{
		return this.requestHeader.getCua();
	}

	public String getToken()
	{
		return this.requestHeader.getToken();
	}

	public String getImei()
	{
		return this.requestHeader.getImei();
	}

	public String getMobNum()
	{
		return this.requestHeader.getMobNum();
	}

	public String getUserId()
	{
		return this.requestHeader.getUserId();
	}

	public String getClient()
	{
		return this.requestHeader.getClient();
	}

	public String getChannel()
	{
		return this.requestHeader.getChannel();
	}

	public String getVersion()
	{
		return this.requestHeader.getVersion();
	}
	
	public String getIp()
	{
		return this.requestHeader.getIp();
	}

	
}
