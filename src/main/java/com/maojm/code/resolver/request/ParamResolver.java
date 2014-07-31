/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.resolver.request;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maojm.code.annotation.Param;
import com.maojm.code.json.JacksonHelper;
import com.maojm.code.util.IpUtil;
import com.maojm.code.vo.ContextModel;
import com.maojm.code.vo.RequestHeader;

/**
 * 参数解析
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月22日 下午1:23:44
 */
public class ParamResolver {
	private Method method;
	private Object[] args;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ContextModel model;
	private String contentType; 
	private List<ParamInfo> paramInfoList = new ArrayList<ParamInfo>();
	
	private static final Log log = LogFactory.getLog(ParamResolver.class);
	
	public ParamResolver(Method method,Object[]args){
		this.method = method;
		this.args = args;
		resolveParams();
		resolveRequestHeader();
	}
	
	public boolean shouldSkip(){
		if(request!=null && response!=null && model!=null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 解析方法参数
	 */
	private void resolveParams(){
		Class<?>[] claszs  = method.getParameterTypes();
		for(int i=0;i<args.length;i++){
			Param param = getParamAnnotation(method,i);
			if(param!=null){
				paramInfoList.add(new ParamInfo(claszs[i],param, i));
			}
			if(args[i] instanceof HttpServletRequest){
				request = (HttpServletRequest)args[i];
        	}else if(args[i] instanceof HttpServletResponse){
        		response = (HttpServletResponse)args[i];
        	}
        	else if(args[i] instanceof ContextModel){
        		model = (ContextModel)args[i];
        	}
		}
	}
	
	/**
	 * 解析请求报头
	 * @param request
	 * @return
	 */
	private void resolveRequestHeader()
	{
		RequestHeader requestHeader = new RequestHeader();
		// 报文头信息
		requestHeader.setClientVersion( request.getHeader( "client_version" ) );
		requestHeader.setCua( request.getHeader( "cua" ) );
		requestHeader.setImei( request.getHeader( "imei" ) );
		requestHeader.setMobNum( request.getHeader( "mob_num" ) );
		requestHeader.setToken( request.getHeader( "token" ) );
		requestHeader.setTransId( request.getHeader( "trans_id" ) );
		requestHeader.setUserId( request.getHeader( "user_id" ) );
		// 城市编号
		requestHeader.setCityId( request.getHeader( "city_id" ) );
		// 设置IP地址
		requestHeader.setIp( IpUtil.getIpAddress( request ) );
		contentType = request.getHeader("content-type");
		if(contentType!=null){
			contentType = contentType.toLowerCase();
		}
		model.setRequestHeader(requestHeader);
		log.info("request header status. requestUri:"+request.getRequestURI()+". headers:"+JacksonHelper.toJson(requestHeader));
	}
	
	public boolean isMultiPartContentType(){
		if(contentType!=null && contentType.indexOf("multipart/form-data")!=-1){
			log.info("requestUri:"+request.getRequestURI()+" is multipartRequest.");
			return true;
		}
		return false;
	}
	
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	private Param getParamAnnotation(Method method,int paramterIndex){
		Annotation[][] annotationArray = method.getParameterAnnotations();
		Annotation[] annos = annotationArray[paramterIndex];
		for(Annotation anno : annos){
			if(Param.class.isInstance(anno)){
				return (Param)anno;
			}
		}
		return null;
	}
	
	public void invokeParamValue(int paramIndex,Object value){
		args[paramIndex] = value;
	}
	
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the model
	 */
	public ContextModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(ContextModel model) {
		this.model = model;
	}

	/**
	 * @return the paramInfoList
	 */
	public List<ParamInfo> getParamInfoList() {
		return paramInfoList;
	}

	/**
	 * @param paramInfoList the paramInfoList to set
	 */
	public void setParamInfoList(List<ParamInfo> paramInfoList) {
		this.paramInfoList = paramInfoList;
	}

	class ParamInfo{
		private Param param;
		private int paramIndex;
		private Class<?> paramType;
		
		public ParamInfo(Class<?> paramType,Param param,int paramIndex){
			this.param = param;
			this.paramIndex = paramIndex;
			this.paramType = paramType;
		}

		/**
		 * @return the param
		 */
		public Param getParam() {
			return param;
		}

		/**
		 * @return the paramIndex
		 */
		public int getParamIndex() {
			return paramIndex;
		}

		/**
		 * @return the paramType
		 */
		public Class<?> getParamType() {
			return paramType;
		}
	}
}
