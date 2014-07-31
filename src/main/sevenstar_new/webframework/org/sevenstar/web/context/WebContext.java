package org.sevenstar.web.context;

import com.opensymphony.xwork.ActionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebContext
{
   private static ThreadLocal requestTL = new ThreadLocal();
   private static ThreadLocal responseTL = new ThreadLocal();
  private static ServletContext servletContext;
  public static final String PARAMETER = "parameter";
  public static final String ATTRIBUTE = "attribute";
  public static final String SESSION = "session";
  public static final String BASE = "SevenStarWeb_base";
  public static final String URL = "SevenStarWeb_url";
  public static final String REALURL = "SevenStarWeb_realurl";
  public static final String PAGENAME = "SevenStarWeb_pagename";
  
	

  /*public static void initActionContext(ActionContext actionContext){
     actionContext.put("SevenStarWeb_url", getUrl());
     actionContext.put("SevenStarWeb_base", getContextPath());
     actionContext.put("SevenStarWeb_pagename", getPageName());
     actionContext.setParameters(null);
     actionContext.setParameters(getRequestParameterMap());
     actionContext.setSession(null);
     actionContext.setSession(getSessionMap());
     actionContext.setApplication(null);
     actionContext.setApplication(getApplicationMap());
  }*/

  public static void initActionContext(ActionContext actionContext){
		actionContext.put(URL,getUrl());
		actionContext.put(BASE,getContextPath());
		actionContext.put(PAGENAME, getPageName());
		actionContext.setParameters(null);
		actionContext.setParameters(getRequestParameterMap());
		actionContext.setSession(null);
		actionContext.setSession( getSessionMap());
		actionContext.setApplication(null);
		actionContext.setApplication(getApplicationMap());
	}
  
  
  
  public static void registerServletContext(ServletContext context) {
     if (servletContext == null)
       servletContext = context;
  }

  
  public static void registerResource(HttpServletRequest req, HttpServletResponse resp)
  {
     requestTL.set(req);
     responseTL.set(resp);
  }

  public static void releaseResource() {
     requestTL.set(null);
     responseTL.set(null);
  }

  public static void releaseServletContext() {
     servletContext = null;
  }

  public static String getPageName(String url) {
     if (url != null) {
       String pageName = url;
       if (url.indexOf("/") != -1) {
         pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
      }
       if (pageName.indexOf(".") != -1) {
         pageName = pageName.substring(0, pageName.indexOf("."));
      }
       return pageName;
    }
     return null;
  }
  
  

  public static String getPageName() {
     if (getRequest() != null) {
       String url = getRequest().getRequestURI();
       String pageName = url;
       if (url.indexOf("/") != -1) {
         pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
      }
       if (pageName.indexOf(".") != -1) {
         pageName = pageName.substring(0, pageName.indexOf("."));
      }
       return pageName;
    }
     return null;
  }
  

  /**
	 * 取当前url
	 * @return
	 */
	

  public static String getUrl()
  {
     if (getRequest() != null) {
       String url = getRequest().getRequestURI();
       String contextpath = getRequest().getContextPath();
       if (url.startsWith(contextpath)) {
         url = url.substring(contextpath.length() + 1);
      }
       String queryString = getRequest().getQueryString();
       if (queryString != null) {
         url = url + "?" + queryString;
      }
       return url;
    }
     return null;
  }

  public static Map getRequestParameterMap() {
     if (getRequest() != null) {
       return getRequest().getParameterMap();
    }
     return new HashMap();
  }

  public static Map getRequestAttributeMap() {
     Map map = new HashMap();
     if (getRequest() != null) {
       Enumeration enumeration = getRequest().getAttributeNames();
       while (enumeration.hasMoreElements()) {
         Object key = enumeration.nextElement();
         map.put(key, getRequest().getAttribute((String)key));
      }
    }
     return map;
  }

  public static Map getSessionMap() {
     Map map = new HashMap();
     if (getSession() != null) {
       Enumeration enumeration = getSession().getAttributeNames();
       while (enumeration.hasMoreElements()) {
         Object key = enumeration.nextElement();
         map.put(key, getSession().getAttribute((String)key));
      }
    }
     return map;
  }

  public static Map getApplicationMap() {
     Map map = new HashMap();
     if (getServletContext() != null) {
       Enumeration enumeration = getServletContext().getAttributeNames();
       while (enumeration.hasMoreElements()) {
         Object key = enumeration.nextElement();
         map.put(key, getSession().getAttribute((String)key));
      }
    }
     return map;
  }

  public static ServletContext getServletContext()
  {
     return servletContext;
  }

  public static String getContextPath()
  {
     if (getRequest() != null) {
       return getRequest().getContextPath();
    }
     return null;
  }

  public static String getRealPath(String logicPath) {
     if (getRequest() != null) {
       return getRequest().getRealPath(logicPath);
    }
     return null;
  }

  public static HttpServletRequest getRequest() {
     return (HttpServletRequest)requestTL.get();
  }

  public static HttpServletResponse getResponse() {
     return (HttpServletResponse)responseTL.get();
  }

  public static HttpSession getSession() {
     HttpServletRequest req = getRequest();
     if (req != null) {
       return req.getSession();
    }
     return null;
  }
}
