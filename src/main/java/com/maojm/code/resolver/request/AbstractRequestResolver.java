/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.resolver.request;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maojm.code.annotation.Param;
import com.maojm.code.json.JacksonHelper;
import com.maojm.code.vo.ContextModel;

/**
 * 请求解析抽象类
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月22日 下午3:09:00
 */
public abstract class AbstractRequestResolver implements RequestResolver{
	protected ParamResolver paramResolver;
	private static Log log = LogFactory.getLog(AbstractRequestResolver.class);
	
	public AbstractRequestResolver(ParamResolver paramResolver){
		this.paramResolver = paramResolver;
	}
	
	protected Map<String, Object> resolveRequestParameters(){
		// 解析Parameters
		Enumeration<?> paramNames = paramResolver.getRequest().getParameterNames();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(paramNames!=null){
			while(paramNames.hasMoreElements()){
				String paramKey = paramNames.nextElement().toString();
				paramMap.put(paramKey, paramResolver.getRequest().getParameter(paramKey));
			}
		}
		return paramMap.size()>0 ? paramMap : null;
	}
	
	protected Object parseMethodParamValue(Class<?> paramType,Object value){
		Object result = null;
		if(value instanceof byte[]){
			return value;
		}
		if(paramType.equals(String.class) || paramType.equals(Character.class)){
			result = value.toString();
		}else if(paramType.equals(Integer.class) || paramType.equals(Byte.class) || paramType.equals(Short.class)){
			result = value.getClass().equals(Integer.class)?(Integer)value:Integer.parseInt(String.valueOf(value));
		}else if(paramType.equals(Long.class)){
			result = value.getClass().equals(Long.class)?(Long)value:Long.parseLong(String.valueOf(value)); 
		}else if(paramType.equals(Float.class) || paramType.equals(Double.class)){
			result = value.getClass().equals(Double.class)?(Double)value:Double.parseDouble(String.valueOf(value));
		}else{
			result = JacksonHelper.fromJson(String.valueOf(value), paramType);
		}
		return result;
	}
	
	protected Param getParamAnnotation(Method method,int paramterIndex){
		Annotation[][] annotationArray = method.getParameterAnnotations();
		Annotation[] annos = annotationArray[paramterIndex];
		for(Annotation anno : annos){
			if(Param.class.isInstance(anno)){
				return (Param)anno;
			}
		}
		return null;
	}
	
	@Override
	public void resolveErrorResponse(int stateCode) {
		HttpServletResponse response = paramResolver.getResponse();
		ContextModel model = paramResolver.getModel();
		model.setEspState(stateCode);
		response.setCharacterEncoding("utf-8");
		response.setHeader("resp_state", String.valueOf(model.getEspState()));
		response.setHeader("trans_id", model.getTransId());
		response.setHeader("content-type", model.getContentType());
		response.setHeader("Content-Length", String.valueOf(0));
	}
	
	public void resolveResponse(){
		try{
			HttpServletResponse response = paramResolver.getResponse();
			ContextModel model = paramResolver.getModel();
			//响应报头
			response.setCharacterEncoding("utf-8");
			response.setHeader("resp_state", String.valueOf(model.getEspState()));
			response.setHeader("trans_id", model.getTransId());
			response.setHeader("content-type", model.getContentType());
			
			byte[] responseBody = null;
			int contentLen = 0;
			if(model.getEspState()==0 && model.getResponseBoday()!=null){
				String body = JacksonHelper.toJson(model.getResponseBoday());
				if(StringUtils.isNotBlank(body)){
					responseBody = body.getBytes();
					contentLen = responseBody.length;
				}
			}
			response.setHeader("Content-Length", String.valueOf(contentLen));
			
			if(contentLen>0){
				OutputStream os = null;
				try {
					os = response.getOutputStream();
					os.write(responseBody);
					os.flush();
				} catch (IOException e) {
					log.error("response OutputStream write error",e);
				}finally{
					try {
						os.close();
					} catch (IOException e) {
						log.error("OutputStream close error",e);
					}
				}
			}
		}catch(Exception e){
			log.error("resolveResponse cause exception", e);
		}
	}
}
