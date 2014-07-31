package org.sevenstar.component.cache.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Enumeration;
public class CacheFilter implements Filter{
	
	private FilterConfig config;
	
	private Map cacheConfigMap;

	public void destroy() {
 		this.config = null;
	}

	public void doFilter(ServletRequest reqquest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(reqquest, response);
	}

	public void init(FilterConfig config) throws ServletException {
 		this.config = config;
  		Enumeration enumeration = config.getInitParameterNames();
 		while(enumeration.hasMoreElements()){
 			String key = String.valueOf(enumeration.nextElement());
 			cacheConfigMap.put(key, config.getInitParameter(key));
 		}
	}

	public Map getCacheConfigMap() {
		if(cacheConfigMap == null){
			cacheConfigMap = new HashMap();
		}
		return cacheConfigMap;
	}

	public void setCacheConfigMap(Map cacheConfigMap) {
		this.cacheConfigMap = cacheConfigMap;
	}

}
