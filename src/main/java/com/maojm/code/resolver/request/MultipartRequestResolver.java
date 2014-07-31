/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.resolver.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maojm.code.json.JacksonHelper;
import com.maojm.code.resolver.request.ParamResolver.ParamInfo;

/**
 * 解析multipart请求
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月22日 下午2:04:25
 */
public class MultipartRequestResolver extends AbstractRequestResolver implements RequestResolver {

	private Map<String, Object> paramMap = new HashMap<String, Object>();
	private final String filePrefix = "upload_file_";
	private static final Log log = LogFactory.getLog(MultipartRequestResolver.class);
	
	public MultipartRequestResolver(ParamResolver paramResolver){
		super(paramResolver);
	}
	
	@Override
	public void resolveRequest() throws Exception{
		resolveRequestBody();
		invokeParams();
	}
	
	@SuppressWarnings("unchecked")
	private void resolveRequestBody() throws Exception{
		StringBuilder sb = new StringBuilder();
		// 解析Parameters
		Map<String, Object> map = resolveRequestParameters();
		if(map!=null){
			paramMap.putAll(map);
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = null;
		items = upload.parseRequest(paramResolver.getRequest());
		sb.append("request body status. requestUri:"+paramResolver.getRequest().getRequestURI())
		  .append(",items:").append(items != null ? items.size() : 0);
		// 解析request请求
		int fileIndex = 1;
		for(FileItem item : items) {
			if (item.isFormField()) { //如果是表单域 ，就是非文件上传元素 
				paramMap.put(item.getFieldName(),item.getString());
				sb.append(",").append(item.getFieldName()).append(":").append(item.getString());
			} else {
				byte[] srcbytes = item.get();
				int len = srcbytes.length;
				byte[] bytes = Arrays.copyOf(srcbytes, len);
				String key = null;
				if(item.getFieldName()!=null){
					key = item.getFieldName().toLowerCase();
					sb.append(",").append(item.getFieldName()).append(" size:").append(bytes.length);
					paramMap.put(key, bytes);
				}
				key = filePrefix + (fileIndex++);
				sb.append(",").append(key).append(" size:").append(bytes.length);
				paramMap.put(key, bytes);
			}
			item.delete();
		}
		log.info(sb.toString());
	}
	
	private void invokeParams() throws IllegalArgumentException{
		int fileIndex = 1;
		for(ParamInfo paramInfo : paramResolver.getParamInfoList()){
			Class<?> paramType = paramInfo.getParamType();
			String key = paramInfo.getParam().value();
			Object value = null;
			if(paramType.equals(byte[].class) && (key==null || key.equals(""))){
				key = filePrefix + (fileIndex++);
			}
			if(paramInfo.getParam()!=null && paramInfo.getParam().required() 
					&& (paramMap.get(key) == null )){
				throw new IllegalArgumentException(key+" is required.");
			}
			try{
				if(key==null || "".equals(key)){
					value = null;
				}else{
					if(paramMap.get(key)!=null && paramMap.get(key) instanceof HashMap){
						value = JacksonHelper.toJson(paramMap.get(key));
					}else{
						value = paramMap.get(key);
					}
				}
				Object realVal = parseMethodParamValue(paramType, value);
				if(realVal == null){
					continue;
				}
				paramResolver.invokeParamValue(paramInfo.getParamIndex(), realVal);
			}catch(Exception e){
				throw new IllegalArgumentException("param:" + key + ",value:" + value
						+ ",type:" + paramType.getClass().getSimpleName() + " invoke fail.");
			}
		}
	}

}
