/**
 * yutian.com.cn Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.jdbc;

import java.util.Map;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013-12-9-上午10:53:37
 * @version 1.0
 */
public class DataSourcePool {
	private Map<String, DataSource> dataSourceMap = null;
	public enum DataSourceEnum{
		MAIN_DS("mainDataSource"),
		DC_DS("dcDataSource");
		private String name;
		DataSourceEnum(String name){
			this.name = name; 
		}
		public String getName(){
			return this.name;
		}
	}
	
	public DataSourcePool(Map<String, DataSource> dataSourceMap){
		this.dataSourceMap = dataSourceMap;
	}
	
	public DataSource getMainDataSource(){
		return getDataSource(DataSourceEnum.MAIN_DS);
	}
	
	public DataSource getDataSource(DataSourceEnum dsEnum){
		return dataSourceMap.get(dsEnum.getName());
	}
	
	public DataSource getDataSource(String dsName){
		return dataSourceMap.get(dsName);
	}
	
}
