/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月9日 下午5:20:23
 */
public class PropertiesConfig {

	private final static Logger log = Logger.getLogger(PropertiesConfig.class);
	
	private static String DEFAULT_JBOSS_HOME = "/home/alliance/jboss";
	
	private static String PREFIX = "file:";
	
	public static String getPropertiesURLFromClassPath(String propName) 
			throws FileNotFoundException{
		String urlPath = Thread.currentThread().getContextClassLoader()
				.getResource(propName).getPath();
		if(urlPath != null){
			log.info(propName + ": loading from CLASS PATH->" + urlPath);
			return PREFIX+urlPath;
		}else{
			throw new FileNotFoundException(propName +" class path not found");
		}
	}
	
	/**
	 * 从JBOSS_HOME/server/default/conf/下配置文件获取配置属性
	 * @param fileName
	 * @param propName
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static String getPropFromJBossHome(String propName){
		String serverPath = System.getenv("JBOSS_HOME");
		if(serverPath == null || serverPath.equals("")){
			log.warn( "请配置JBOSS环境变量，尝试从默认JBOSS目录->"
		      + DEFAULT_JBOSS_HOME +" 加载配置文件");
			serverPath = DEFAULT_JBOSS_HOME;
		}else{
			log.info(propName + ": loading from JBOSS ->" + serverPath);
		}
		StringBuilder filePath = new StringBuilder(PREFIX).append(serverPath).append("/")
				.append("server").append("/")
				.append("default").append("/")
				.append("conf").append("/").append(propName);	
		String path = filePath.toString();
		File file = new File(path.substring(path.indexOf(":")+1));
		if(!file.exists()){
			log.warn( "JBOSS_HOME conf下不存在配置文件"+path);
			return null;
		}
		return filePath.toString();
	}
	
	public static String getPropertiesURL(String propName) throws FileNotFoundException{
		String url = getPropFromJBossHome(propName);
		if(url == null)
			url = getPropertiesURLFromClassPath(propName);
		//判断文件是否存在
		File file = new File(url.substring(url.indexOf(":")+1));
		if(!file.exists()){
			throw new FileNotFoundException(propName + " CLASS PATH and JBOSS HOME not found");
		}
		log.info(propName + ": loading sucessfull from ->" + url);
		return url;
	}
	
	
	public static String getPropertiesFromJBossHome(String propName){
		String serverPath = System.getenv("JBOSS_HOME");
		if(serverPath == null || serverPath.equals("")){
			log.warn( "请配置JBOSS环境变量，尝试从默认JBOSS目录->"
		      + DEFAULT_JBOSS_HOME +" 加载配置文件");
			serverPath = DEFAULT_JBOSS_HOME;
		}else{
			log.info(propName + ": loading from JBOSS ->" + serverPath);
		}
		StringBuilder filePath = new StringBuilder().append(serverPath).append("/")
				.append("server").append("/")
				.append("default").append("/")
				.append("conf").append("/").append(propName);	
		String path = filePath.toString();
		File file = new File(path.substring(path.indexOf(":")+1));
		if(!file.exists()){
			log.warn( "JBOSS_HOME conf下不存在配置文件"+path);
			return null;
		}
		return filePath.toString();
	}
}
