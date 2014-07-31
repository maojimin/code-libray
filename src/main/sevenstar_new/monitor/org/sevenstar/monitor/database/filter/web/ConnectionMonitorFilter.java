package org.sevenstar.monitor.database.filter.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.sevenstar.monitor.database.context.ClientContext;

import java.util.Enumeration;
public class ConnectionMonitorFilter implements Filter {
	private FilterConfig config;

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		initClientContext((HttpServletRequest)req);
		chain.doFilter(req, resp);
	}

	private void initClientContext(HttpServletRequest req){
		Enumeration enumeration = req.getHeaderNames();
		ClientContext context = new ClientContext();
		context.setClientIp(req.getLocalAddr());
		context.setClientLanguage(req.getHeader("accept-language"));
		context.setClientName(req.getLocalName());
		context.setClientSystemInfo(req.getHeader("user-agent"));
		context.setLength(""+req.getContentLength());
		context.setMethod(req.getMethod());
		context.setProtocol(req.getProtocol());
		context.setCurrentContext(context);
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
