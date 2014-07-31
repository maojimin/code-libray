/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.json;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

/**
 * jackson辅助类
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月16日 下午4:04:16
 */
public class JacksonHelper {

	private static final Logger logger = Logger.getLogger(JacksonHelper.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();
	static{
		// 过滤未知属性。 json转对象时，如果属性未知，忽略
		objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 过滤null属性。对象转json是，如果属性为null，不生成
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	/**
     * 常规类型的转换
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
    public static <T> T fromJson(String jsonString, Class<T>  clazz) {  
        if (jsonString==null || "".equals(jsonString)) {  
            return null;  
        }  
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (Exception e) {  
            logger.warn("parse json string error:" + jsonString, e);  
            return null;  
        }  
    }
    /**
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: 
     * eg:
     * fromJson(json,new TypeReference<List<MyBean>>() {})
     * @param <T>
     * @param jsonString
     * @param refernce
     * @return
     */
    public static <T>T fromJson(String jsonString, TypeReference<?> refernce) {  
        if (jsonString==null || "".equals(jsonString)) {  
            return null;  
        }  
        try { 
            return (T)objectMapper.readValue(jsonString, refernce);  
        } catch (Exception e) {  
            logger.error("parse json string error:" + jsonString, e);  
            return null;  
        }  
    }
    
    public static String toJson(Object object) {  
    	  
        try {  
            return objectMapper.writeValueAsString(object);  
        } catch (Exception e) {  
            logger.error("write to json string error:" + object, e);  
            return null;  
        }  
    }
    
    public static void writeValue(OutputStream out,Object value){
    	if(out==null || value==null){
    		logger.warn("'outputStream' or 'value' can't be null.");
    		return;
    	}
    	try {
			JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(out, JsonEncoding.UTF8);
			objectMapper.writeValue(jsonGenerator, value);
		} catch (Exception e) {
			logger.error("writeValue error", e);
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				logger.error("close outputstream error", e);
			}
		}
    }
    
	public static ObjectMapper getObjectmapper() {
		return objectMapper;
	}  
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		String reqBody = "{\"current_page\":1,\"item_num\":20, \"password\":\"123456\",\"mobnum\":\"15088790007\",\"quickLogin\":\"false\"}";
//		JsonHashMap<String,Object> jsonMap =  JacksonHelper.fromJson(reqBody, JsonHashMap.class);
//		Object obj = jsonMap.get("page_info");
//		System.out.println(obj.toString());
	}

}
