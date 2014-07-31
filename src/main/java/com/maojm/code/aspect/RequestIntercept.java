/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.maojm.code.json.JacksonHelper;
import com.maojm.code.resolver.request.ParamResolver;
import com.maojm.code.resolver.request.RequestResolver;
import com.maojm.code.resolver.request.RequestResolverFactory;
import com.maojm.code.vo.ContextModel;

/**
 * Controller方法 Around方式 拦截 
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月16日 下午3:58:03
 */
@Aspect
public class RequestIntercept {

	private static final Log logger = LogFactory.getLog(RequestIntercept.class);
	
	@Pointcut("execution(public * com.yutian.sm.transfer.controller..*.*"
			+ "(..,com.yutian.sm.transfer.vo.base.ContextModel,..,"
			+ "javax.servlet.http.HttpServletRequest,..,javax.servlet.http.HttpServletResponse,..))")
	public void inControllerLayer(){}
	
	@Around("inControllerLayer()")  
    public void process(ProceedingJoinPoint jp){
		long startTime = System.currentTimeMillis();
		MethodSignature methodSignature = (MethodSignature)jp.getSignature();    
		Method targetMethod = methodSignature.getMethod();
		Method realMethod = null;
		try {
			realMethod = jp.getTarget().getClass().getDeclaredMethod(jp.getSignature().getName(), 
					targetMethod.getParameterTypes());
		} catch (Exception e) {
			logger.error("resolve method fail", e);
		}
		if(realMethod == null){
			try {
				executeTargetMethod(jp);
			} catch (Throwable e) {
				logger.error("executeTargetMethod fail", e);
			}
			return;
		}
		ParamResolver paramResolver = new ParamResolver(realMethod, jp.getArgs());
		if(paramResolver.shouldSkip()){
			logger.warn("放弃该方法aop拦截");
			try {
				executeTargetMethod(jp);
			} catch (Throwable e) {
				logger.error("executeTargetMethod fail", e);
			}
			return;
		}
		
		RequestResolver rsolver = RequestResolverFactory.createRequestResolver(paramResolver);
		try {
			rsolver.resolveRequest();
			executeTargetMethod(jp);
			rsolver.resolveResponse();
		} catch(IllegalArgumentException e){
			logger.error(null, e);
			rsolver.resolveErrorResponse(1);
		}catch(Throwable e){
			logger.error(null, e);
			rsolver.resolveErrorResponse(2);
		}finally{
			StringBuilder respInfo = new StringBuilder();
			ContextModel model = paramResolver.getModel();
			HttpServletRequest request = paramResolver.getRequest();
        	respInfo.append(">>>response trans-id:").append(model!=null?model.getTransId():null)
        		.append(",uri:").append(request.getRequestURI())
        		.append(",state_code:").append(model!=null?model.getEspState():null)
        		.append(",execute finished. spend:").append(System.currentTimeMillis()-startTime).append("ms")
        		.append(",response:").append(JacksonHelper.toJson(model!=null?model.getResponseBoday():null));
        	logger.info(Thread.currentThread()+respInfo.toString());
		}
	    
    }
	
	private void executeTargetMethod(ProceedingJoinPoint jp) throws Throwable{
		jp.proceed(jp.getArgs());
	}
}
