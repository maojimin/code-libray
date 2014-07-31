package org.sevenstar.web.view.freemarker;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

public class SpringFreemarkerIntergrade {
	private static Log log = LogFactory
			.getLog(SpringFreemarkerIntergrade.class);

	public static void inject(ServletContext servletContext,
			Configuration config) {
		if (hasSpring() && servletContext != null && config != null) {
			WebApplicationContext appCtx = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			if (appCtx != null) {
				String[] beanNames = appCtx.getBeanDefinitionNames();
				for (int i = 0; i < beanNames.length; i++) {
					try {
						if (config.getSharedVariable(beanNames[i]) == null) {
							config.setSharedVariable(beanNames[i], appCtx
									.getBean(beanNames[i]));
						}
					} catch (BeansException e) {
						log.error(e);
					} catch (TemplateModelException e) {
						log.error(e);
					}
				}
				log.debug("freemarker config  init success");
			} else {
				log.debug("spring config is null,init fail");
			}
		} else {
			log
					.debug("spring not exist or servletContext is null or config is null ,init ignore");
		}
	}

	private static boolean hasSpring() {
		try {
			Class
					.forName("org.springframework.web.context.WebApplicationContext");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
