package org.sevenstar.component.tag.tree;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.regex.Pattern;

import javax.servlet.http.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class TreeTag extends TagSupport {
	private String root;
	private String rootname;
	private String rootid;
	private String branchurl = "window.location=\"http://sdf?act=1&\"";
	private String leafurl = "window.location=\"http://sdf?act=1&\"";
	private String imptree = Constants.IMP_TREE_CLASS;

	// is need tobe map at web.xml,if true,then map as
	// :TreeInvoker,class=com.pub.tag.tree.SubTree
	private String servletmap = "false";

	public static void main(String[] args){
		String url = "${base}/app";
		if(url.indexOf("${base}") != -1){
 			url = url.replaceAll("\\$\\{base\\}", "test");
		}
		System.out.println(url);
	}

	public int doStartTag() {
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		if (this.getBranchurl() != null
				&& this.getBranchurl().indexOf("${base}") != -1) {
			this.setBranchurl(this.getBranchurl().replaceAll("\\$\\{base\\}",
					req.getContextPath()));
		}
		if (this.getLeafurl() != null
				&& this.getLeafurl().indexOf("${base}") != -1) {
			this.setLeafurl(this.getLeafurl().replaceAll("\\$\\{base\\}",
					req.getContextPath()));
		}
		if (root == null)
			root = ((HttpServletRequest) pageContext.getRequest())
					.getContextPath();
		if (("/").equals(root)) {
			root = "";
		}
		Constants.ROOT = root;
		if (servletmap.equals("true")) {
			Constants.PIC_CLOSE = root + "/TreeInvoker?act=close";
			Constants.PIC_OPEN = root + "/TreeInvoker?act=open";
			Constants.PIC_FLUSH = root + "/TreeInvoker?act=flush";
			Constants.PIC_DOCS = root + "/TreeInvoker?act=docs";
			Constants.SUBTREE = root + "/TreeInvoker?act=sub";
			Constants.PIC_OPEN_ = root + "/TreeInvoker?act=open_";
			Constants.PIC_OPEND = root + "/TreeInvoker?act=opend";
			Constants.PIC_CLOSE_ = root + "/TreeInvoker?act=close_";
			Constants.PIC_CLOSED = root + "/TreeInvoker?act=closed";

		} else {
			/*
			 * Constants.PIC_CLOSE = root + Constants.SERVLET + "?act=close";
			 * Constants.PIC_OPEN = root + Constants.SERVLET + "?act=open";
			 * Constants.PIC_FLUSH = root + Constants.SERVLET + "?act=flush";
			 * Constants.PIC_DOCS = root + Constants.SERVLET + "?act=docs";
			 * Constants.SUBTREE = root + Constants.SERVLET + "?act=sub";
			 * Constants.PIC_OPEN_ = root + Constants.SERVLET + "?act=open_";
			 * Constants.PIC_OPEND = root + Constants.SERVLET + "?act=opend";
			 * Constants.PIC_CLOSE_ = root + Constants.SERVLET + "?act=close_";
			 * Constants.PIC_CLOSED = root + Constants.SERVLET + "?act=closed";
			 */
			Constants.PIC_CLOSE = Constants.SERVLET + "?act=close";
			Constants.PIC_OPEN = Constants.SERVLET + "?act=open";
			Constants.PIC_FLUSH = Constants.SERVLET + "?act=flush";
			Constants.PIC_DOCS = Constants.SERVLET + "?act=docs";
			Constants.SUBTREE = Constants.SERVLET + "?act=sub";
			Constants.PIC_OPEN_ = Constants.SERVLET + "?act=open_";
			Constants.PIC_OPEND = Constants.SERVLET + "?act=opend";
			Constants.PIC_CLOSE_ = Constants.SERVLET + "?act=close_";
			Constants.PIC_CLOSED = Constants.SERVLET + "?act=closed";

		}
		Constants.BRANCHURL = branchurl;
		Constants.LEAFURL = leafurl;
		Constants.IMP_TREE_CLASS = imptree;
		JspWriter out = pageContext.getOut();
		try {
			out.print("<script>\r\n<!--\r\n");
			out.print(Constants.getTreeJS());
			out.print("\r\n tree.outPutRoot('" + rootid + "','" + rootname
					+ "');");
			out.print("\r\n //-->\r\n</script>");
			// out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return (SKIP_BODY);
	}

	/**
	 * @return
	 */
	public String getBranchurl() {
		return branchurl;
	}

	/**
	 * @return
	 */
	public String getLeafurl() {

		return leafurl;
	}

	/**
	 * @return
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @return
	 */
	public String getRootid() {
		return rootid;
	}

	/**
	 * @return
	 */
	public String getRootname() {
		return rootname;
	}

	/**
	 * @param string
	 */
	public void setBranchurl(String string) {
		branchurl = string;
	}

	/**
	 * @param string
	 */
	public void setLeafurl(String string) {
		leafurl = string;
	}

	/**
	 * @param string
	 */
	public void setRoot(String string) {
		root = string;
	}

	/**
	 * @param string
	 */
	public void setRootid(String string) {
		rootid = string;
	}

	/**
	 * @param string
	 */
	public void setRootname(String string) {
		rootname = string;
	}

	/**
	 * @return
	 */
	public String getImptree() {
		return imptree;
	}

	public String getServletmap() {
		return servletmap;
	}

	/**
	 * @param string
	 */
	public void setImptree(String string) {
		imptree = string;
	}

	public void setServletmap(String servletmap) {
		this.servletmap = servletmap;
	}

}
