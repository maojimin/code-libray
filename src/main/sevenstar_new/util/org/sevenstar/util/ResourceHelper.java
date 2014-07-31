package org.sevenstar.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.Constants;
import org.sevenstar.util.RegexpHelper;

public class ResourceHelper {

	private static Log LOG = LogFactory.getLog(ResourceHelper.class);

	public static Map unZipFileMap = new HashMap();

	private static String tmpDicPath = null;
	static {
		File tmp = null;
		try {
			tmp = File.createTempFile("SevenStarJarUnZip", "jar");
			tmpDicPath = tmp.getParent();
		} catch (Exception e) {
			LOG.error(e);
		} finally {
			if (tmp != null && tmp.exists()) {
				tmp.delete();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		File warFile = new File(
				"D:/program/jboss-5.1.0.GA/server/default/deploy/ssweb.war");
		JarFile jarFile = new JarFile(warFile);
		Enumeration enumeration = jarFile.entries();
		while (enumeration.hasMoreElements()) {
			JarEntry jarEntry = (JarEntry) enumeration.nextElement();
			if (jarEntry.getName().endsWith("SevenStarWorkflow15.jar")) {
				File tmp = unzipEntry(jarFile, jarEntry);
				/**
				 * 再读jar内的
				 */
				JarFile subJarFile = new JarFile(tmp);
				Enumeration subEnumeration = subJarFile.entries();
				while (subEnumeration.hasMoreElements()) {
					JarEntry subJarEntry = (JarEntry) subEnumeration
							.nextElement();
					System.out.println(subJarEntry.getName());
				}
			}
		}
	}

	public static File unzipEntry(JarFile jarFile, JarEntry jarEntry) {
		String fileName = jarEntry.getName();
		if (fileName.indexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		}
		if (fileName.indexOf("\\") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		}
		File tmp = new File(tmpDicPath + File.separator + fileName);
		if (tmp.exists() && tmp.isFile() && tmp.length() > 10) {
			return tmp;
		}
		try {
			InputStream in = jarFile.getInputStream(jarEntry);
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					tmp));
			byte[] buffer = new byte[2048];
			int nBytes = 0;
			try {
				while ((nBytes = in.read(buffer)) > 0) {
					out.write(buffer, 0, nBytes);
				}
			} finally {
				out.flush();
				out.close();
				in.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return tmp;
	}

	public static List findClassList(String rootPackages, String pattern) {
		pattern = pattern.replaceAll("\\\\", "/");
		pattern = pattern.replaceAll("\\.", "/");
		List resourceList = findResourceList(rootPackages, pattern);
		List classList = new ArrayList();
		for (int i = 0; i < resourceList.size(); i++) {
			String path = (String) resourceList.get(i);
			if (path.endsWith(".class")) {
				try {
					path = path.substring(0, path.lastIndexOf(".class"));
					path = path.replaceAll("/", "\\.");
					classList.add(BeanHelper.loadClass(path));
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
		return classList;
	}

	public static List findResourceList(String rootPackages, String pattern) {
		List resourceList = new ArrayList();
		if (rootPackages.indexOf(".") != -1) {
			rootPackages = rootPackages.replaceAll("\\.", "/");
		}
		if (rootPackages.indexOf(Constants.separator) != -1) {
			String[] packages = rootPackages.split(Constants.separator);
			for (int i = 0; i < packages.length; i++) {
				resourceList.addAll(findResourceList(packages[i], pattern));
			}
			List newResourceList = new ArrayList();
			for (int i = 0; i < resourceList.size(); i++) {
				if (!newResourceList.contains(resourceList.get(i))) {
					newResourceList.add(resourceList.get(i));
				}
			}
			return newResourceList;
		} else {

			try {
				Enumeration pathEnumeration = ResourceHelper.class
						.getClassLoader().getResources(rootPackages);
				if (pathEnumeration != null) {
					while (pathEnumeration.hasMoreElements()) {
						URL url = (URL) pathEnumeration.nextElement();
						String path = url.getPath();
						String protocol = url.getProtocol();
						if ("file".equalsIgnoreCase(protocol)
								|| "vfsfile".equalsIgnoreCase(protocol)) {
							try {
								File file = new File(path);
								resourceList.addAll(findResourceInFile(file,
										rootPackages, pattern));
							} catch (Throwable e) {
								LOG.error("resource load error,path=" + path);
								LOG.error(e);
							}
						}
						if ("jar".equalsIgnoreCase(protocol)) {
							LOG.debug("jarpath=" + path);
							try {
								URL jarUrl = new URL(path);
								path = jarUrl.getPath();
								if (path.endsWith("!/" + rootPackages)) {
									path = path.substring(0, path
											.lastIndexOf("!/" + rootPackages));
									JarFile jarFile = new JarFile(
											new File(path));
									Enumeration enumeration = jarFile.entries();
									while (enumeration.hasMoreElements()) {
										JarEntry jarEntry = (JarEntry) enumeration
												.nextElement();
										if (jarEntry.getName().startsWith(
												rootPackages + "/")) {
											String classPath = jarEntry
													.getName();
											if (RegexpHelper.isGlobmatches(
													classPath, pattern)) {
												resourceList
														.add(classPath
																.replaceAll(
																		"\\\\",
																		"/"));
												continue;
											}
										}
									}
								}
							} catch (Throwable e) {
								LOG.error("resource load error,path=" + path);
								LOG.error(e);
							}
						}

						if ("zip".equalsIgnoreCase(protocol)
								|| "vfszip".equalsIgnoreCase(protocol)) {
							LOG.debug("warPath=" + path);
							String warPath = null;
							if (path.lastIndexOf("!/") != -1) {
								warPath = path.substring(0, path
										.lastIndexOf("!/"));
							} else if (path.lastIndexOf("/WEB-INF") != -1) {
								/**
								 * for jboss5 vfszip
								 */
								warPath = path.substring(0, path
										.lastIndexOf("/WEB-INF"));
								/**
								 * jboss部署可能直接更改目录名为.war，而不是打包为war
								 */
								try {
									JarFile warFile = new JarFile(new File(
											warPath));
								} catch (IOException e) {
									warPath = path.substring(0, path
											.indexOf(".jar") + 4);
								}
							} else if (path.indexOf(".jar") != -1) {
								/**
								 * for jboss5 vfszip
								 */
								warPath = path.substring(0, path
										.indexOf(".jar") + 4);
							} else {
								continue;
							}
							try {
								JarFile warFile = new JarFile(new File(warPath));
								Enumeration enumeration = warFile.entries();
								while (enumeration.hasMoreElements()) {
									JarEntry jarEntry = (JarEntry) enumeration
											.nextElement();
									LOG.debug("jarEntry.getName()="
											+ jarEntry.getName());
									if (jarEntry.getName().startsWith(
											"WEB-INF/classes/")) {
										/**
										 * war WEB-INF/classes/
										 */
										String classPath = jarEntry.getName();
										classPath = classPath
												.substring("WEB-INF/classes/"
														.length());
										if (RegexpHelper.isGlobmatches(
												classPath, pattern)) {
											resourceList.add(classPath
													.replaceAll("\\\\", "/"));
											continue;
										}
									} else if (jarEntry.getName().startsWith(
											"WEB-INF/lib/")) {
										if (jarEntry.getName().endsWith("jar")) {
											File jarFile = null;
											if (unZipFileMap
													.containsKey(jarEntry
															.getName())) {
												jarFile = (File) unZipFileMap
														.get(jarEntry.getName());
											} else {
												jarFile = unzipEntry(warFile,
														jarEntry);
											}
											/**
											 * 再读jar内的
											 */
											JarFile subJarFile = new JarFile(
													jarFile);

											Enumeration subEnumeration = subJarFile
													.entries();
											while (subEnumeration
													.hasMoreElements()) {
												JarEntry subJarEntry = (JarEntry) subEnumeration
														.nextElement();
												if (subJarEntry.getName()
														.startsWith(
																rootPackages
																		+ "/")) {
													String classPath = subJarEntry
															.getName();
													if (RegexpHelper
															.isGlobmatches(
																	classPath,
																	pattern)) {
														resourceList
																.add(classPath
																		.replaceAll(
																				"\\\\",
																				"/"));
														continue;
													}
												}
											}

										} else {
											/**
											 * war包中的jar包
											 */
											String classPath = jarEntry
													.getName();
											classPath = classPath
													.substring("WEB-INF/lib/"
															.length());
											if (RegexpHelper.isGlobmatches(
													classPath, pattern)) {
												resourceList
														.add(classPath
																.replaceAll(
																		"\\\\",
																		"/"));
												continue;
											}
										}
									} else {
										/**
										 * weblogic9 class打成jar包
										 */
										String classPath = jarEntry.getName();
										if (RegexpHelper.isGlobmatches(
												classPath, pattern)) {
											resourceList.add(classPath
													.replaceAll("\\\\", "/"));
											continue;
										}
									}
								}
							} catch (Throwable e) {
								LOG.error("resource load error,path=" + path);
								LOG.error(e);
							}
						}
					}
				}
				List newResourceList = new ArrayList();
				for (int i = 0; i < resourceList.size(); i++) {
					if (!newResourceList.contains(resourceList.get(i))) {
						newResourceList.add(resourceList.get(i));
					}
				}
				return newResourceList;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private static List findResourceInFile(File file, String rootPackage,
			String pattern) {
		List list = new ArrayList();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				list.addAll(findResourceInFile(files[i], rootPackage, pattern));
			}
		} else {
			String path = file.getPath();
			path = path.replaceAll("\\\\", "/");
			if (path.indexOf("WEB-INF/classes/") != -1) {
				String prefix = "/WEB-INF/classes/";
				path = path.substring(path.indexOf(prefix) + prefix.length());
			} else {
				path = path.substring(path.indexOf(rootPackage));
			}
			if (RegexpHelper.isGlobmatches(path, pattern)) {
				list.add(path);
			}
		}
		return list;
	}
}
