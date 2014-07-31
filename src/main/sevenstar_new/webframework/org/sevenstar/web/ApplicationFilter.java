package org.sevenstar.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.sevenstar.util.RegexpHelper;
import org.sevenstar.web.cfg.SwebConfigure;
import org.sevenstar.web.context.WebContext;
import org.sevenstar.web.exception.ActionException; 

import com.opensymphony.xwork.ActionContext;

/**
 * 初始化
 *
 * @author rtm
 *
 */
public class ApplicationFilter implements Filter {

	private static Log LOG = LogFactory.getLog(ApplicationFilter.class);

	public void destroy() {
		LOG.debug("ApplicationFilter destroy()");
		WebContext.releaseServletContext();
	}

	private FilterConfig config;

	private String[] excludeUrls;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			String url = ((HttpServletRequest) request).getRequestURI();
			for(int i=0;i<excludeUrls.length;i++){
				if(!excludeUrls[i].startsWith("/")){
					excludeUrls[i] = "/"+excludeUrls[i];
				}
				if(!excludeUrls[i].startsWith(((HttpServletRequest) request).getContextPath())){
					excludeUrls[i] = ((HttpServletRequest) request).getContextPath() + excludeUrls[i];
				}
				if(RegexpHelper.isGlobmatches(url, excludeUrls[i])){
					chain.doFilter(request, response);
					return;
				}
			}
			request.setCharacterEncoding(SwebConfigure.getSwebModel().getEncode());
  			WebContext.registerResource((HttpServletRequest) request,
					(HttpServletResponse) response);
  			
			WebContext.initActionContext(ActionContext.getContext());
  			if(("".equals(WebContext.getUrl()) || WebContext.getUrl().endsWith("/") )&& SwebConfigure.getWelcomeFile() != null && !"".equals(SwebConfigure.getWelcomeFile())){
                  ((HttpServletResponse)response).sendRedirect(WebContext.getUrl()+SwebConfigure.getWelcomeFile());
                  return;
			}
			if (SwebConfigure.check(((HttpServletRequest) request).getRequestURI())) {
				try {
					ApplicationDispatcher.execute(WebContext.getUrl());
				} catch (ActionException e) {
					e.printStackTrace();
					LOG.error(e);
 					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		} finally {
			ActionContext.setContext(null);
			WebContext.releaseResource();
		}
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		LOG.debug("ApplicationFilter init(),filterName="
				+ config.getFilterName());
		WebContext.registerServletContext(config.getServletContext());
		if(config.getInitParameter("excludeUrls") != null && !"".equals(config.getInitParameter("excludeUrls"))){
			String excludeUrlss = config.getInitParameter("excludeUrls");
			if(excludeUrlss.indexOf(Constants.separator) != -1){
				excludeUrls = excludeUrlss.split(Constants.separator);
			}else{
				excludeUrls = new String[1];
				excludeUrls[0] = excludeUrlss;
			}
		}else{
			excludeUrls = new String[0];
		}
	}

	public String getInitParameter(String name) {
		return config.getInitParameter(name);
	}


}
