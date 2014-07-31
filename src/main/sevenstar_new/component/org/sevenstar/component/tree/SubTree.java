package org.sevenstar.component.tree;

import org.sevenstar.component.tag.tree.Constants;
import org.sevenstar.web.context.WebContext;

public class SubTree {
	private String root;
	private String rootname;
	private String rootid;
	private String branchurl;
	private String leafurl;
	private String imptree = Constants.IMP_TREE_CLASS;

	public String getTreeJs() {
		Constants.ROOT = WebContext.getContextPath();
		Constants.PIC_CLOSE = Constants.SERVLET+"?act=close";
		Constants.PIC_OPEN = Constants.SERVLET + "?act=open";
		Constants.PIC_FLUSH = Constants.SERVLET + "?act=flush";
		Constants.PIC_DOCS = Constants.SERVLET + "?act=docs";
		Constants.SUBTREE = Constants.SERVLET + "?act=sub";
		Constants.PIC_OPEN_ = Constants.SERVLET + "?act=open_";
		Constants.PIC_OPEND = Constants.SERVLET + "?act=opend";
		Constants.PIC_CLOSE_ = Constants.SERVLET + "?act=close_";
		Constants.PIC_CLOSED = Constants.SERVLET + "?act=closed";
		if (this.getBranchurl() != null
				&& this.getBranchurl().indexOf("${base}") != -1) {
			this.setBranchurl(this.getBranchurl().replaceAll("\\$\\{base\\}",
					WebContext.getContextPath()));
		}
		if (this.getLeafurl() != null
				&& this.getLeafurl().indexOf("${base}") != -1) {
			this.setLeafurl(this.getLeafurl().replaceAll("\\$\\{base\\}",
					WebContext.getContextPath()));
		}
		Constants.BRANCHURL = branchurl;
		Constants.LEAFURL = leafurl;
		Constants.IMP_TREE_CLASS = imptree;
		StringBuffer sb = new StringBuffer();
		sb.append("<script>\r\n<!--\r\n");
		sb.append(Constants.getTreeJS());
		sb.append("\r\n tree.outPutRoot('" + rootid + "','" + rootname + "');");
		sb.append("\r\n //-->\r\n</script>");
		return sb.toString();
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getRootname() {
		return rootname;
	}

	public void setRootname(String rootname) {
		this.rootname = rootname;
	}

	public String getRootid() {
		return rootid;
	}

	public void setRootid(String rootid) {
		this.rootid = rootid;
	}

	public String getBranchurl() {
		return branchurl;
	}

	public void setBranchurl(String branchurl) {
		this.branchurl = branchurl;
	}

	public String getLeafurl() {
		return leafurl;
	}

	public void setLeafurl(String leafurl) {
		this.leafurl = leafurl;
	}

	public String getImptree() {
		return imptree;
	}

	public void setImptree(String imptree) {
		this.imptree = imptree;
	}

}
