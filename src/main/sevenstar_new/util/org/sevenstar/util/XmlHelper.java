package org.sevenstar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * @author rtm 2008-5-8
 */
public final class XmlHelper {

	private static Log log = LogFactory.getLog(XmlHelper.class);

	public static Object initialize(Class clazz,Element element) {
		Object bean = BeanHelper.newInstance(clazz);
		List attribs = element.attributes();
		for (int i = 0; i < attribs.size(); i++) {
			Attribute attrib = (Attribute) attribs.get(i);
			String attribname = attrib.getName();
			String attribvalue = attrib.getValue();
			try {
				//BeanHelper.setPropertyValue(bean, attribname, attribvalue);
				OgnlHelper.setValue(bean, attribname, attribvalue);
			} catch (Exception e) {
				log.error(e);
			}
		}
		return bean;
	}

	public static String getAttributeValue(Element element,String attributeName){
		List attribs = element.attributes();
		for (int i = 0; i < attribs.size(); i++) {
			Attribute attrib = (Attribute) attribs.get(i);
			String attribname = attrib.getName();
			String attribvalue = attrib.getValue();
			if(attribname != null && attribname.equals(attributeName)){
				return attribvalue;
			}
		}
		return null;
	}

	public static Document readByClassPath(String libXmlPath) {
		InputStream is = XmlHelper.class.getClassLoader()
				.getResourceAsStream(libXmlPath);
		SAXReader reader = new SAXReader();
		try {
			return reader.read(is);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Document readByFilePath(String libXmlPath) {
		SAXReader reader = new SAXReader();
		try {
			return reader.read(libXmlPath);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

}
