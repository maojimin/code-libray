package org.sevenstar.persistent.db;

import java.io.Serializable;

public class PageParam
  implements Serializable
{
  private int firstrownum;
  private int lastrownum;
  private int offset;
  private int span;
  private String order;
  private String sort;
  private int pagesize;
  private int toprow;

  public int getToprow()
  {
    return this.toprow;
  }

  public void setToprow(int toprow) {
    this.toprow = toprow;
  }

  public int getPagesize() {
    return this.pagesize;
  }

  public void setPagesize(int pagesize) {
    this.pagesize = pagesize;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getSort() {
    return this.sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
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

  public int getOffset() {
    return this.offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getSpan() {
    return this.span;
  }

  public void setSpan(int span) {
    this.span = span;
  }
}