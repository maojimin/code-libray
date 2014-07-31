package org.sevenstar.persistent.db.jdbc.page;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPage
{
  protected int firstrownum;
  protected int lastrownum;
  protected int total;
  protected int pagesize;
  protected int pagetotal;
  protected int page;
  protected int nextpage;
  protected int previouspage;
  protected int lastpage;
  protected List dateList;

  public List getDateList()
  {
    if (this.dateList == null) {
      return new ArrayList();
    }
    return this.dateList; } 
  public abstract List getPageDateList(String paramString1, Class paramClass, String paramString2, int paramInt);

  public abstract List getPageDateList(String paramString1, Class paramClass, String paramString2, int paramInt1, int paramInt2);

  public abstract List getPageDateList(String paramString, int paramInt);

  public abstract List getPageDateList(String paramString, int paramInt1, int paramInt2);

  public abstract List getPageDateList(String paramString, Class paramClass, int paramInt);

  public abstract List getPageDateList(String paramString, Class paramClass, int paramInt1, int paramInt2);

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

  public int getFirstrownum()
  {
    return this.firstrownum;
  }

  public void setFirstrownum(int firstrownum)
  {
    this.firstrownum = firstrownum;
  }

  public int getLastrownum()
  {
    return this.lastrownum;
  }

  public void setLastrownum(int lastrownum)
  {
    this.lastrownum = lastrownum;
  }
}