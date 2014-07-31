package org.sevenstar.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class CookieHelper {

	private final static Log LOG = LogFactory.getLog(CookieHelper.class);

	private static int maxAge = 0;

	private static String domain = null;

	private static String path = "/";

	/*
	 * public final static void setAttribute(String name, Object
	 * value,HttpServletResponse resp) { if(value == null){ throw new
	 * RuntimeException("name["+name+"] value is null"); } if (resp != null) {
	 * Cookie cookie = new Cookie(name, String.valueOf(value)); if(maxAge > 0){
	 * cookie.setMaxAge(maxAge); } if(domain != null){ cookie.setDomain(domain); }
	 * if(path != null){ cookie.setPath(path); } resp.addCookie(cookie); }else{
	 * throw new RuntimeException("http response is null"); } }
	 * 
	 */

	public final static void setAttribute(String name, Object value, HttpServletResponse resp) {
		if (value == null) {
			throw new RuntimeException("name[" + name + "] value is null");
		}
		if (resp != null) {
			Cookie cookie = new Cookie(name, String.valueOf(value));
 
			if (maxAge > 0) {
				cookie.setMaxAge(maxAge);
			}
			if (domain != null) {
				cookie.setDomain(domain);
			}
			if (path != null) {
				cookie.setPath(path);
			}
			resp.addCookie(cookie);
 		} else {
			throw new RuntimeException("http response is null");
		}
	}

	public final static Cookie getCookie(HttpServletRequest req, String name) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	public final static String getAttribute(String name, HttpServletRequest req) {
		if (req == null) {
			throw new RuntimeException("http request is null");
		}
		if (req != null) {
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					if (cookie.getName().equals(name)) {
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}
}
