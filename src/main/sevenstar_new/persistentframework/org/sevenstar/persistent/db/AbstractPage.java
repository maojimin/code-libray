package org.sevenstar.persistent.db;

import java.util.List;

public abstract class AbstractPage
{
  protected int total;
  protected int pagesize;
  protected int pagetotal;
  protected int page;
  protected int nextpage;
  protected int previouspage;
  protected int lastpage;
  public List dataList;

  public List getDataList()
  {
    return this.dataList; } 
  public abstract List getPageDataList(Object paramObject);

  public abstract List getPageDataList(Object paramObject, int paramInt1, int paramInt2);

  public abstract List getNextPageDataList(Object paramObject);

  public abstract List getNextPageDataList(Object paramObject, int paramInt1, int paramInt2);

  public abstract List getPreviousPageDataList(Object paramObject);

  public abstract List getPreviousPageDataList(Object paramObject, int paramInt1, int paramInt2);

  public abstract List getFirstPageDataList(Object paramObject);

  public abstract List getFirstPageDataList(Object paramObject, int paramInt1, int paramInt2);

  public abstract List getLastPageDataList(Object paramObject);

  public abstract List getLastPageDataList(Object paramObject, int paramInt1, int paramInt2);

  public int getPage() { return this.page;
  }

  public void setPage(int page)
  {
    this.page = page;
  }

  public int getPagesize()
  {
    return this.pagesize;
  }

  public void setPagesize(int pageSize)
  {
    this.pagesize = pageSize;
  }

  public int getTotal()
  {
    return this.total;
  }

  public int getPagetotal()
  {
    return this.pagetotal;
  }

  public int getLastpage()
  {
    this.lastpage = this.pagetotal;
    return this.lastpage;
  }

  public int getNextpage()
  {
    if (this.page > this.pagetotal)
      this.nextpage = this.lastpage;
    else {
      this.nextpage = (this.page + 1);
    }
    return this.nextpage;
  }

  public int getPreviouspage()
  {
    if (this.page == 1)
      this.previouspage = 1;
    else {
      this.previouspage = (this.page - 1);
    }
    if (this.previouspage < 0) {
      this.previouspage = 0;
    }
    return this.previouspage;
  }
}