package org.sevenstar.component.freemarker;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.sevenstar.util.BeanHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.component.freemarker.SevenStarBeanWrapper;
import org.sevenstar.component.freemarker.SevenStarClassTemplateLoader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.ext.dom.NodeModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.StringReader;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

public class FreemarkerHelper {
	private static Configuration CFG;
	private static Log LOG = LogFactory.getLog(FreemarkerHelper.class);
	public static String encode = "utf-8";
	private static boolean loadServletContext = false;
	static {
		CFG = new Configuration();
		try {
			InputStream in = loadFile("freemarker.properties",
					FreemarkerHelper.class);
			if (in != null) {
				Properties p = new Properties();
				p.load(in);
				CFG.setSettings(p);
			}
		} catch (IOException e) {
			LOG
					.error(
							"Error while loading freemarker settings from /freemarker.properties",
							e);
		} catch (TemplateException e) {
			LOG
					.error(
							"Error while loading freemarker settings from /freemarker.properties",
							e);
		}
		if (hasClass("org.sevenstar.web.context.WebContext")) {
			try {
				CFG
						.setTemplateLoader(new MultiTemplateLoader(
								new TemplateLoader[] {
										new WebappTemplateLoader(
												(ServletContext) BeanHelper
														.invoke(
																BeanHelper
																		.newInstance("org.sevenstar.web.context.WebContext"),
																"getServletContext",
																null)),
										new SevenStarClassTemplateLoader() }));
				loadServletContext = true;
			} catch (Exception e) {
				CFG.setTemplateLoader(new SevenStarClassTemplateLoader());
			}
		} else {
			CFG.setTemplateLoader(new SevenStarClassTemplateLoader());
		}

		CFG.setObjectWrapper(new SevenStarBeanWrapper());
		CFG.setDefaultEncoding(encode);
		CFG.setOutputEncoding(encode);
		CFG.setNumberFormat("0.####");
		if (hasClass("org.sevenstar.web.cfg.SwebConfigureFactory")) {
			Object obj = null;
			try {
				obj = BeanHelper
						.invoke(
								BeanHelper
										.newInstance("org.sevenstar.web.cfg.SwebConfigureFactory"),
								"getSwebModel", null);
			} catch (Throwable e) {
				// pass
			}
			if (obj != null) {
				try {
					encode = (String) BeanHelper
							.getPropertyValue("encode", obj);
				} catch (Throwable e) {
					// pass
				}
			}
		}
		if (encode == null || "".equals(encode)) {
			encode = "utf-8";
		}
	}

	private static boolean hasClass(String className) {
		try {
			Class.forName(className);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out
				.println(FreemarkerHelper
						.process(
								"com/ctzj/eprm/inf/prm/cabasi/template/CTZJ_SIQuery_Full_SI.xml",
								"serialNum", "18924980793"));
	}

	public static NodeModel getNodeModelFromXml(String xml) {
		try {
			/**
			 * 消除xmlns
			 */
			if (xml != null && xml.indexOf("xmlns:xsi") != -1) {
				xml = xml.replaceFirst("xmlns:xsi", "pmlns");
			}
			/**
			 * 消除xmlns
			 */
			if (xml != null && xml.indexOf("xmlns") != -1) {
				xml = xml.replaceFirst("xmlns", "pmlns");
			}
			return freemarker.ext.dom.NodeModel.parse(new InputSource(
					new StringReader(xml)));
		} catch (SAXException e) {
			throw new RuntimeException("xml格式错误");
		} catch (IOException e) {
			throw new RuntimeException("没有输入值");
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("xml格式错误");
		}
	}

	public static String process(String templatePath, String key, Object value) {
		Map root = new HashMap();
		root.put(key, value);
		return process(templatePath, root);
	}

	public static String process(String key, String content, Map root) {
		ServletContext sc = null;
		if (hasClass("org.sevenstar.web.context.WebContext")) {
			sc = (ServletContext) BeanHelper
			.invoke(
					BeanHelper
							.newInstance("org.sevenstar.web.context.WebContext"),
					"getServletContext",
					null);
		}
		if (sc != null && !loadServletContext) {
			try {
				CFG.setTemplateLoader(new MultiTemplateLoader(
						new TemplateLoader[] {
								new WebappTemplateLoader(sc),
								new SevenStarClassTemplateLoader() }));
				loadServletContext = true;
			} catch (Throwable e) {
				// pass
			}
		}
		try {
			Template template = new Template(key, new StringReader(content),
					CFG);
			StringWriter writer = new StringWriter();
			try {
				template.process(root, writer);
				writer.flush();
			} finally {
				writer.close();
			}
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}

	}

	public static String process(String templatePath, Map root) {
		Template template = null;
		ServletContext sc = null;
		if (hasClass("org.sevenstar.web.context.WebContext")) {
			sc = (ServletContext) BeanHelper
			.invoke(
					BeanHelper
							.newInstance("org.sevenstar.web.context.WebContext"),
					"getServletContext",
					null);
		}
		if (sc != null && !loadServletContext) {
			try {
				CFG.setTemplateLoader(new MultiTemplateLoader(
						new TemplateLoader[] {
								new WebappTemplateLoader(sc),
								new SevenStarClassTemplateLoader() }));
				loadServletContext = true;
			} catch (Throwable e) {
				// pass
			}
		}
		try {
			template = CFG.getTemplate(templatePath);
			template.setEncoding(encode);
			StringWriter writer = new StringWriter();
			try {
				template.process(root, writer);
				writer.flush();
			} finally {
				writer.close();
			}
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
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

	private static InputStream loadFile(String fileName, Class clazz) {
		URL fileUrl = getResource(fileName, clazz);

		if (fileUrl == null) {
			return null;
		}

		InputStream is;

		try {
			is = fileUrl.openStream();

			if (is == null) {
				throw new IllegalArgumentException("No file '" + fileName
						+ "' found as a resource");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("No file '" + fileName
					+ "' found as a resource");
		}

		return is;
	}

}
