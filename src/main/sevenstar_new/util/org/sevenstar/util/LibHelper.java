package org.sevenstar.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author rtm 2008-5-8
 */
public class LibHelper {
	private static Log log = LogFactory.getLog(LibHelper.class);
	public static void copyByClassPath(String libXmlPath){
		InputStream is = LibHelper.class.getClassLoader().getResourceAsStream(libXmlPath);
		SAXReader reader = new SAXReader();
		try {
			copy(reader.read(is));
		} catch (DocumentException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	public static void copyByFilePath(String libXmlPath){
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(libXmlPath);
			copy(document);
		} catch (DocumentException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据lib.xml的配置，完成lib拷贝
	 *
	 * @param doc
	 */
	private static void copy(Document doc) {
		List liblist = new ArrayList();
		String separator = "/";
		/**
		 * 得到root节点
		 */
		Element libEle = (Element) doc.selectObject("/root/lib");
		String rootpath = libEle.attributeValue("rootpath");
		if (!rootpath.endsWith(separator)) {
			rootpath = rootpath + separator;
		}
		rootpath = rootpath.replaceAll("\\\\", separator);
		rootpath = rootpath.replaceAll(separator + separator, separator);
		String webrootpath = libEle.attributeValue("webrootpath");
		if (!webrootpath.endsWith(separator)) {
			webrootpath = webrootpath + separator;
		}
		webrootpath = webrootpath.replaceAll("\\\\", separator);
		webrootpath = webrootpath.replaceAll(separator + separator, separator);
		List childrenEleList = libEle.elements();
		if (childrenEleList == null || childrenEleList.size() == 0) {
			throw new RuntimeException("配置文件中没有配置lib");
		}
		for (int i = 0; i < childrenEleList.size(); i++) {
			/**
			 * 分类
			 */
			Element catalogEle = (Element) childrenEleList.get(i);
			List libEleList = catalogEle.elements();
			String libRootPath = rootpath + catalogEle.getName() + separator;
			if (catalogEle.attributeValue("path") != null
					&& !"".equals(catalogEle.attributeValue("path")
							.trim())) {
				libRootPath = rootpath+catalogEle.attributeValue("path").trim()+ separator;
			}
			if ("other".equalsIgnoreCase(catalogEle.getName())) {
				/**
				 * other节点特殊处理
				 */
				for (int j = 0; j < libEleList.size(); j++) {
					Element otherLibEle = (Element) libEleList.get(j);
					String otherLibBinPath = otherLibEle.attributeValue("lib");
					if (otherLibBinPath.toLowerCase().endsWith("jar")) {
						liblist.add(otherLibBinPath);
					}
					String otherLibSrcPath = otherLibEle.attributeValue("src");
					if (otherLibSrcPath.toLowerCase().endsWith("jar")) {
						liblist.add(otherLibSrcPath);
					}
				}
			} else {
				for (int j = 0; j < libEleList.size(); j++) {
					/**
					 * 具体lib
					 */
					Element detailLibEle = (Element) libEleList.get(j);
					String detailLibRootPath = libRootPath
							+ detailLibEle.getName() + separator
							+ detailLibEle.attributeValue("version")
							+ separator;
					if (detailLibEle.attributeValue("path") != null
							&& !"".equals(detailLibEle.attributeValue("path")
									.trim())) {
						detailLibRootPath = libRootPath+detailLibEle.attributeValue("path").trim()+ separator;
					}
					String detailLibBinPath = detailLibRootPath
							+ detailLibEle.attributeValue("lib");
					if (detailLibBinPath.toLowerCase().endsWith("jar")) {
						liblist.add(detailLibBinPath);
					}
					String detailLibSrcPath = detailLibRootPath
							+ detailLibEle.attributeValue("src");
					if (detailLibSrcPath.toLowerCase().endsWith("jar")) {
						liblist.add(detailLibSrcPath);
					}
					/**
					 * 添加依赖的lib库
					 */
					Element dependEle = detailLibEle.element("depend");
					if (dependEle != null) {
						if (dependEle.attributeValue("path") != null
								&& !"".equals(dependEle.attributeValue("path")
										.trim())) {
							detailLibRootPath = detailLibRootPath+dependEle.attributeValue("path").trim()+separator;
						}
						List dependEleList = dependEle.elements();
						for (int k = 0; k < dependEleList.size(); k++) {
							Element dependLibEle = (Element) dependEleList
									.get(k);
							String dependLibPath = detailLibRootPath
									+ dependLibEle.attributeValue("lib");
							if (dependLibPath.toLowerCase().endsWith("jar")) {
								liblist.add(dependLibPath);
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < liblist.size(); i++) {
			/**
			 * 路径格式化
			 */
			String path = (String) liblist.get(i);
			path = path.replaceAll("\\\\", separator);
			path = path.replaceAll(separator + separator, separator);
			liblist.set(i, path);
		}
		/**
		 * 清楚webrootpath路径下的lib
		 */
		File webrootpathFile = new File(webrootpath);
		if (!webrootpathFile.exists()) {
			/**
			 * 如果路径不存在，建立路径
			 */
			webrootpathFile.mkdir();
		}
		File[] existFileList = webrootpathFile.listFiles();
		if (existFileList != null) {
			for (int i = 0; i < existFileList.length; i++) {
				if (existFileList[i].getName().toLowerCase().endsWith("jar")) {
					log.debug("delete:"+existFileList[i].getPath());
					existFileList[i].delete();
				}
			}
		}
		for (int i = 0; i < liblist.size(); i++) {
			String jarPath = (String) liblist.get(i);
			/**
			 * 拷贝lib
			 */
			String jarFileName = jarPath.substring(jarPath
					.lastIndexOf(separator) + 1);
			String newPath = webrootpathFile + separator + jarFileName;
			log.debug("copy from:"+jarPath);
			log.debug("copy to:"+newPath);
			copy(new File(jarPath),new File(newPath));
		}
	}

	public static void main(String[] args) throws DocumentException {
	//	URL url = LibHelper.class.getClassLoader().getResource("/lib.xml");
	//	System.out.println(url.getPath());
		copyByClassPath("lib.xml");
	//	copyByFilePath("D:/workspace_jee/petstore/src/lib.xml");
	}

	public static void copy(File sourceFile, File toFile) {
		try {
			if (!toFile.exists()) {
				toFile.createNewFile();
			}else{
				log.warn("jar exist:"+toFile.getPath());
			}
			FileInputStream fis = new FileInputStream(sourceFile);
			FileOutputStream fos = new FileOutputStream(toFile);
			int i = fis.read();
			while (i != -1) {
				fos.write(i);
				i = fis.read();
			}
			fis.close();
			fos.close();
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}


}
