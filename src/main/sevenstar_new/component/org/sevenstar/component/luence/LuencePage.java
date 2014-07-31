package org.sevenstar.component.luence;

import java.util.ArrayList;
import java.util.List;

public class LuencePage {
	/**
	 * 记录总数
	 */
	private int total;
	/**
	 * 一页显示数目
	 */
	private int pagesize;
	/**
	 * 总的页数
	 */
	private int pagetotal;
	/**
	 * 当前显示页
	 */
	private int page;
	private int nextpage;
	private int previouspage;
	private int lastpage;
	private List dataList;

	public List getPagenumList() {
		List list = new ArrayList();
		if (page <= 20) {
			if (pagetotal <= 20) {
				for (int i = 1; i <= pagetotal; i++) {
					list.add(new Long(i));
				}
			} else {
				for (int i = 1; i <= 20; i++) {
					list.add(new Long(i));
				}
			}
		} else {
			if (pagetotal >= page + 10) {
				for (int i = page - 10; i <= page + 10; i++) {
					list.add(new Long(i));
				}
			} else {
				for (int i = page - 10; i <= pagetotal; i++) {
					list.add(new Long(i));
				}
			}
		}
		return list;
	}

	/**
	 * @return Returns the dateList.
	 */
	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return Returns the pageSize.
	 */
	public int getPagesize() {
		return pagesize;
	}
	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPagesize(int pageSize) {
		this.pagesize = pageSize;
	}
	/**
	 * @return Returns the totalNum.
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * 设置条数
	 * @param total 总共的条数
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return Returns the totalPage.
	 */
	public int getPagetotal() {
		return pagetotal;
	}
	/**
	 * @param pagetotal 总页数
	 */
	public void setPagetotal(int pagetotal) {
		this.pagetotal = pagetotal;
	}
	/**
	 * @return Returns the lastpage.
	 */
	public int getLastpage() {
		this.lastpage = this.pagetotal;
		return lastpage;
	}
	/**
	 * @return Returns the nextpage.
	 */
	public int getNextpage() {
		if(this.page > this.pagetotal){
			this.nextpage = this.lastpage;
		}else{
			this.nextpage = this.page + 1;
		}
		return nextpage;
	}
	/**
	 * @return Returns the previouspage.
	 */
	public int getPreviouspage() {
		if(this.page == 1){
			this.previouspage = 1;
		}else{
			this.previouspage = this.page - 1;
		}
		if(this.previouspage < 0){
			this.previouspage = 0;
		}
		return previouspage;
	}
}
