package org.sevenstar.persistent.db.ibatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.sevenstar.persistent.db.PageParam;
import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.exception.PersistentException;

public class IbatisPage
  implements Serializable
{
  private String querySqlMapId;
  private String countSqlMapId;
  private Object queryParam;
  private Class paramClazz;
  private Integer total = new Integer(0);

  private Integer pagesize = new Integer(0);

  private Integer pagetotal = new Integer(0);

  private Integer page = new Integer(0);
  private Integer nextpage = new Integer(0);
  private Integer previouspage = new Integer(0);
  private Integer lastpage = new Integer(0);
  public List dataList;

  public Class getParamClazz()
  {
    return this.paramClazz;
  }

  public void setParamClazz(Class paramClazz)
  {
    this.paramClazz = paramClazz;
  }

  public IbatisPage()
  {
  }

  public IbatisPage(String querySqlMapId, String countSqlMapId) {
    this.querySqlMapId = querySqlMapId;
    this.countSqlMapId = countSqlMapId;
  }

  public IbatisPage(String querySqlMapId, String countSqlMapId, Class paramClazz) {
    this.querySqlMapId = querySqlMapId;
    this.countSqlMapId = countSqlMapId;

    this.paramClazz = paramClazz;
  }
  public List getPageDataList(Object param, int page, int pagesize) {
    return getPageDataList(param, page, pagesize, "sqlmapconfig.xml");
  }

  public List getPageDataList(Object param, int page, int pagesize, String sqlmapfile)
  {
    if ((param == null) && (this.paramClazz != null)) {
      try {
        param = this.paramClazz.newInstance();
      } catch (InstantiationException e) {
        throw new PersistentException(e);
      } catch (IllegalAccessException e) {
        throw new PersistentException(e);
      }
    }
    this.queryParam = param;
    IbatisDao dao = IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile));
    this.pagesize = Integer.valueOf(pagesize);
    this.page = Integer.valueOf(page);
    this.total = Integer.valueOf(dao.count(this.countSqlMapId, param));
    if (this.pagesize.intValue() == 0)
    {
      this.pagesize = new Integer(SdbConfigure.getPagesize());
    }
    this.pagetotal = new Integer(this.total.intValue() / this.pagesize.intValue());
    if (this.pagetotal.intValue() * this.pagesize.intValue() < this.total.intValue()) {
      this.pagetotal = new Integer(this.pagetotal.intValue() + 1);
    }
    if (this.page.intValue() > this.pagetotal.intValue()) {
      this.page = this.pagetotal;
    }
    if (this.page.intValue() <= 0)
    {
      if (this.pagetotal.intValue() == 0)
        this.page = new Integer(0);
      else {
        this.page = new Integer(1);
      }

    }

    int firstrownum = (this.page.intValue() - 1) * this.pagesize.intValue();
    if (firstrownum < 0) {
      firstrownum = 0;
    }
    int lastrownum = firstrownum + this.pagesize.intValue();
    if (lastrownum >= this.total.intValue()) {
      lastrownum = this.total.intValue();
    }
    if ((param instanceof PageParam))
    {
      ((PageParam)param).setFirstrownum(firstrownum);
      ((PageParam)param).setLastrownum(lastrownum);

      ((PageParam)param).setPagesize(this.pagesize.intValue());
      if (this.page.intValue() <= 1)
        ((PageParam)param).setToprow(this.pagesize.intValue());
      else {
        ((PageParam)param).setToprow(this.pagesize.intValue() * this.page.intValue());
      }
    }
    if ((param instanceof Map))
    {
      ((Map)param).put("firstrownum", new Integer(firstrownum));
      ((Map)param).put("lastrownum", new Integer(lastrownum));

      ((Map)param).put("pagesize", new Integer(this.pagesize.intValue()));
      if (this.page.intValue() <= 1)
        ((Map)param).put("toprow", new Integer(this.pagesize.intValue()));
      else {
        ((Map)param).put("toprow", new Integer(this.pagesize.intValue() * this.page.intValue()));
      }

    }

    int offset = (this.page.intValue() - 1) * this.pagesize.intValue();
    if (offset < 0) {
      offset = 0;
    }
    int span = this.pagesize.intValue();
    if (offset + span >= this.total.intValue()) {
      span = this.total.intValue() - offset;
    }
    if ((param instanceof PageParam)) {
      ((PageParam)param).setOffset(offset);
      ((PageParam)param).setSpan(span);
    }
    if ((param instanceof Map)) {
      ((Map)param).put("offset", new Integer(offset));
      ((Map)param).put("span", new Integer(span));
    }
    this.dataList = dao.queryForList(this.querySqlMapId, param);
    return this.dataList;
  }

  public List getPageDataList(Object param, String sqlmapfile) {
    return getPageDataList(param, this.page.intValue(), this.pagesize.intValue(), sqlmapfile);
  }

  public List getPageDataList(Object param) {
    return getPageDataList(param, this.page.intValue(), this.pagesize.intValue());
  }

  public List getNextPageDataList(Object param)
  {
    return getPageDataList(param, this.page.intValue() + 1, this.pagesize.intValue());
  }

  public List getPreviousPageDataList(Object param)
  {
    return getPageDataList(param, this.page.intValue() - 1, this.pagesize.intValue());
  }

  public List getFirstPageDataList(Object param)
  {
    return getPageDataList(param, 1, this.pagesize.intValue());
  }

  public List getLastPageDataList(Object param)
  {
    return getPageDataList(param, 2147483647, this.pagesize.intValue());
  }

  public List getNextPageDataList(Object param, int page, int pagesize) {
    return getPageDataList(param, page + 1, pagesize);
  }

  public List getPreviousPageDataList(Object param, int page, int pagesize) {
    return getPageDataList(param, page - 1, pagesize);
  }

  public List getFirstPageDataList(Object param, int page, int pagesize) {
    return getPageDataList(param, 1, pagesize);
  }

  public List getLastPageDataList(Object param, int page, int pagesize) {
    return getPageDataList(param, 2147483647, pagesize);
  }

  public String getCountSqlMapId()
  {
    return this.countSqlMapId;
  }

  public void setCountSqlMapId(String countSqlMapId)
  {
    this.countSqlMapId = countSqlMapId;
  }

  public Object getQueryParam()
  {
    return this.queryParam;
  }

  public void setQueryParam(Object queryParam)
  {
    this.queryParam = queryParam;
  }

  public String getQuerySqlMapId()
  {
    return this.querySqlMapId;
  }

  public void setQuerySqlMapId(String querySqlMapId)
  {
    this.querySqlMapId = querySqlMapId;
  }

  public List getDataList()
  {
    return this.dataList;
  }

  public int getPage()
  {
    return this.page.intValue();
  }

  public void setPage(int page)
  {
    this.page = Integer.valueOf(page);
  }

  public int getPagesize()
  {
    return this.pagesize.intValue();
  }

  public void setPagesize(int pageSize)
  {
    this.pagesize = Integer.valueOf(pageSize);
  }

  public int getTotal()
  {
    return this.total.intValue();
  }

  public int getPagetotal()
  {
    return this.pagetotal.intValue();
  }

  public int getLastpage()
  {
    this.lastpage = this.pagetotal;
    return this.lastpage.intValue();
  }

  public int getNextpage()
  {
    if (this.page.intValue() > this.pagetotal.intValue())
      this.nextpage = this.lastpage;
    else {
      this.nextpage = Integer.valueOf(this.page.intValue() + 1);
    }
    return this.nextpage.intValue();
  }

  public int getPreviouspage()
  {
    if (this.page.intValue() == 1)
      this.previouspage = Integer.valueOf(1);
    else {
      this.previouspage = Integer.valueOf(this.page.intValue() - 1);
    }
    if (this.previouspage.intValue() < 0) {
      this.previouspage = Integer.valueOf(0);
    }
    return this.previouspage.intValue();
  }
}