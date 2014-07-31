package org.sevenstar.component.freemarker;

import java.net.URL;

import freemarker.cache.URLTemplateLoader;

public class SevenStarClassTemplateLoader extends URLTemplateLoader {
	protected URL getURL(String name) {
		return getResource(name, getClass());
	}

	private static URL getResource(String resourceName, Class callingClass) {
		URL url = null;

		url = Thread.currentThread().getContextClassLoader().getResource(
				resourceName);

		if (url == null) {
			url = SevenStarClassTemplateLoader.class.getClassLoader()
					.getResource(resourceName);
		}

		if (url == null) {
			url = callingClass.getClassLoader().getResource(resourceName);
		}

		return url;
	}
}
