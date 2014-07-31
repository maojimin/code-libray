/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 *
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 *
 * For further information visit:
 * 		http://www.fckeditor.net/
 *
 * File Name: SimpleUploaderServlet.java
 * 	Java File Uploader class.
 *
 * Version:  2.3
 * Modified: 2005-08-11 16:29:00
 *
 * File Authors:
 * 		Simone Chiaretta (simo@users.sourceforge.net)
 */

package org.sevenstar.component.fckeditor.uploader;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.sevenstar.util.ImageHelper;

/**
 * Servlet to upload files.<br>
 * 
 * This servlet accepts just file uploads, eventually with a parameter
 * specifying file type
 * 
 * @author Simone Chiaretta (simo@users.sourceforge.net)
 */

public class SimpleUploaderServlet extends HttpServlet {

	private static String baseDir;

	private static boolean debug = false;

	private static boolean enabled = false;

	private static Hashtable allowedExtensions;

	private static Hashtable deniedExtensions;

	// private static Map processMap = new HashMap();

	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root
	 * of the file repository:<br>
	 * If not specified the value of "/UserFiles/" will be used.<br>
	 * Also it retrieve all allowed and denied extensions to be handled.
	 * 
	 */
	public void init() throws ServletException {

		debug = (new Boolean(getInitParameter("debug"))).booleanValue();

		if (debug)
			System.out
					.println("\r\n---- SimpleUploaderServlet initialization started ----");

		baseDir = getInitParameter("baseDir");
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
		if (baseDir == null)
			baseDir = "/UserFiles/";
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdir();
		}

		allowedExtensions = new Hashtable(4);
		deniedExtensions = new Hashtable(4);

		allowedExtensions.put("File",
				stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File",
				stringToArrayList(getInitParameter("DeniedExtensionsFile")));

		allowedExtensions.put("Image",
				stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image",
				stringToArrayList(getInitParameter("DeniedExtensionsImage")));

		allowedExtensions.put("Flash",
				stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
		deniedExtensions.put("Flash",
				stringToArrayList(getInitParameter("DeniedExtensionsFlash")));
		allowedExtensions.put("Medio",
				stringToArrayList(getInitParameter("AllowedExtensionsMedio")));
		deniedExtensions.put("Medio",
				stringToArrayList(getInitParameter("DeniedExtensionsMedio")));
		if (debug)
			System.out
					.println("---- SimpleUploaderServlet initialization completed ----\r\n");

	}

	/**
	 * Manage the Upload requests.<br>
	 * 
	 * The servlet accepts commands sent in the following format:<br>
	 * simpleUploader?Type=ResourceType<br>
	 * <br>
	 * It store the file (renaming it in case a file with the same name exists)
	 * and then return an HTML file with a javascript command in it.
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (debug)
			System.out.println("--- BEGIN DOPOST ---");

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String typeStr = request.getParameter("Type");
		if (typeStr == null) {
			typeStr = "Image";
		}

		String currentPath = baseDir + typeStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);

		File typeFile = new File(currentDirPath);
		if (!typeFile.exists()) {
			boolean flag = typeFile.mkdir();
		}
		currentPath = request.getContextPath() + currentPath;

		if (debug)
			System.out.println(currentDirPath);

		String retVal = "0";
		String newName = "";
		String fileUrl = "";
		String errorMessage = "";
		String APC_UPLOAD_PROGRESS = "";// niceditor
		File pathToSave = null;
		if (enabled) {
			DiskFileUpload upload = new DiskFileUpload();
			try {
				List items = upload.parseRequest(request);

				Map fields = new HashMap();

				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField())
						fields.put(item.getFieldName(), item.getString());
					else
						fields.put(item.getFieldName(), item);
				}
				if (fields.get("APC_UPLOAD_PROGRESS") != null) {
					APC_UPLOAD_PROGRESS = String.valueOf(fields
							.get("APC_UPLOAD_PROGRESS"));
					// processMap.put(APC_UPLOAD_PROGRESS, "0");
				}
				FileItem uplFile = (FileItem) fields.get("NewFile");
				String fileNameLong = uplFile.getName();
				if (fileNameLong != null && !"".equals(fileNameLong)) {
					fileNameLong = new String(fileNameLong.getBytes("gbk"),
							"utf-8");
				}
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];

				String nameWithoutExt = getNameWithoutExtension(fileName);
				String ext = getExtension(fileName);
				nameWithoutExt = nameWithoutExt.getBytes().toString() + "-"
						+ System.currentTimeMillis();
				Calendar cal = Calendar.getInstance();
				cal.setTime(new java.util.Date());
				String year = String.valueOf(cal.get(Calendar.YEAR));
				String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
				String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
				if (month.length() == 1) {
					month = "0" + month;
				}
				if (day.length() == 1) {
					day = "0" + day;
				}

				 pathToSave = new File(getRealPath(currentDirPath, year,
						month, day), nameWithoutExt + "." + ext);
				fileUrl = getLogicPath(currentPath, year, month, day) + "/"
						+ nameWithoutExt + "." + ext;
				if (extIsAllowed(typeStr, ext)) {
					newName = nameWithoutExt + "." + ext;
					fileUrl = getLogicPath(currentPath, year, month, day) + "/"
							+ nameWithoutExt + "." + ext;
					pathToSave = new File(getRealPath(currentDirPath, year,
							month, day), nameWithoutExt + "." + ext);
					uplFile.write(pathToSave);
					/**
					 * 清理临时文件
					 */
					File tmpFile = ((DiskFileItem)uplFile).getStoreLocation();
					if(tmpFile !=null && tmpFile.isFile() && !tmpFile.getPath().equals(pathToSave.getPath())){
						((DiskFileItem)uplFile).delete();
					}
				} else {
					retVal = "202";
					errorMessage = "";
					if (debug)
						System.out.println("Invalid file type: " + ext);
				}
			} catch (Exception ex) {
				if (debug)
					ex.printStackTrace();
				retVal = "203";
			}
		} else {
			retVal = "1";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
		}
		if (APC_UPLOAD_PROGRESS == null) {
			out.println("<script type=\"text/javascript\">");
			String js = "window.parent.OnUploadCompleted(" + retVal + ",'"
					+ fileUrl + "','" + newName + "','" + errorMessage + "');";
			out.println(js);
			out.println("</script>");
		} else {
			int width = 200;
			try{
				width = ImageHelper.getWidth(pathToSave);
			}catch(Exception e){
				//pass
			}
			out.println("<script type=\"text/javascript\">");
			String js = "var o = new Object();o.width="+width+";o.url='"+fileUrl+"';window.parent.nicUploadButton.statusCb(o)";
			out.println(js);
			out.println("</script>");
		}
		out.flush();
		out.close();

		if (debug)
			System.out.println("--- END DOPOST ---");

	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * Helper function to convert the configuration string to an ArrayList.
	 */

	private ArrayList stringToArrayList(String str) {

		if (debug)
			System.out.println(str);
		String[] strArr = str.split("\\|");

		ArrayList tmp = new ArrayList();
		if (str.length() > 0) {
			for (int i = 0; i < strArr.length; ++i) {
				if (debug)
					System.out.println(i + " - " + strArr[i]);
				tmp.add(strArr[i].toLowerCase());
			}
		}
		return tmp;
	}

	private String getLogicPath(String rootpath, String year, String month,
			String day) {
		return rootpath + "/" + year + "/" + month + "/" + day;
	}

	private String getRealPath(String rootpath, String year, String month,
			String day) {

		File yearFile = new File(rootpath + "/" + year);
		if (!yearFile.exists()) {
			yearFile.mkdir();
		}

		File monthFile = new File(rootpath + "/" + year + "/" + month);
		if (!monthFile.exists()) {
			monthFile.mkdir();
		}

		File dayFile = new File(rootpath + "/" + year + "/" + month + "/" + day);
		if (!dayFile.exists()) {
			dayFile.mkdir();
		}
		return rootpath + "/" + year + "/" + month + "/" + day;
	}

	/**
	 * Helper function to verify if a file extension is allowed or not allowed.
	 */

	private boolean extIsAllowed(String fileType, String ext) {

		ext = ext.toLowerCase();

		ArrayList allowList = (ArrayList) allowedExtensions.get(fileType);
		ArrayList denyList = (ArrayList) deniedExtensions.get(fileType);

		if (allowList.size() > 0)
			if (allowList.contains(ext))
				return true;
			else
				return false;

		if (denyList.size() > 0)
			if (denyList.contains(ext))
				return false;
			else
				return true;

		return true;
	}

}
