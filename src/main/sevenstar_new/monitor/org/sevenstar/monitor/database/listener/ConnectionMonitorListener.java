package org.sevenstar.monitor.database.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.sevenstar.monitor.database.ConnectionManager;
public class ConnectionMonitorListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent contextEnvent) {

	}

	public void contextInitialized(ServletContextEvent contextEnvent) {
		ServletContext servletContext=contextEnvent.getServletContext();
		String jndiName = servletContext.getInitParameter("jndiName");
		if(jndiName != null && !"".equals(jndiName)){
			ConnectionManager.bindDataSource(jndiName);
		}else{
			ConnectionManager.enhancerOracleDriver();
		}
	}
}
