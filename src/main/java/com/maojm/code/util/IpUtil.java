package com.maojm.code.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Ip地址工具类
 * 
 * @author leon
 * @Create 2014-3-18
 */
public final class IpUtil
{

	/**
	 * 获取请求IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress( HttpServletRequest request )
	{
		String ip = request.getHeader( "x-forwarded-for" );
		if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
		{
			ip = request.getHeader( "Proxy-Client-IP" );
		}
		if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
		{
			ip = request.getHeader( "WL-Proxy-Client-IP" );
		}
		if( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
