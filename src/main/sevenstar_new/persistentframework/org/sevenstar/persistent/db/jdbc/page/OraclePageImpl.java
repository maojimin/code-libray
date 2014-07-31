package org.sevenstar.persistent.db.jdbc.page;

import java.util.List;
import org.sevenstar.persistent.db.jdbc.util.JdbcQuery;

public class OraclePageImpl extends AbstractPage
{
  private String sql;
  private Class resultClazz;
  private String countsql;
  private final String startsql = " select * from ( select row_.*, rownum rownum_ from ( ";

  private final String endsql0 = " ) row_ where rownum <= ";
  private final String endsql1 = " ) where rownum_ >  ";

  public List getPageDateList(String sql, String countsql, int page) {
    return getPageDateList(sql, countsql, page, this.pagesize);
  }

  public List getPageDateList(String sql, String countsql, int page, int pagesize) {
    if (this.resultClazz == null)
      this.dateList = JdbcQuery.getInstance().queryForList(getPageSql(sql, countsql, page, pagesize));
    else {
      this.dateList = JdbcQuery.getInstance().queryForList(getPageSql(sql, countsql, page, pagesize), this.resultClazz);
    }
    return this.dateList;
  }

  public String getPageSql(String sql, String countsql, int page, int pagesize)
  {
    setPageParam(sql, countsql, page, pagesize);
    return " select * from ( select row_.*, rownum rownum_ from ( " + this.sql + " ) row_ where rownum <= " + this.lastrownum + " ) where rownum_ >  " + this.firstrownum;
  }

  public String getPageSql(String sql, String countsql, int page) {
    return getPageSql(sql, countsql, page, 15);
  }

  public void setPageParam(String sql, String countsql, int page, int pagesize) {
    this.sql = sql;
    this.countsql = countsql;
    this.pagesize = pagesize;
    this.page = page;
    this.total = Integer.parseInt(JdbcQuery.getInstance().queryForString(this.countsql));
    if (this.pagesize == 0)
    {
      this.pagesize = 15;
    }
    this.pagetotal = (this.total / this.pagesize);
    if (this.pagetotal * this.pagesize < this.total) {
      this.pagetotal += 1;
    }
    if (this.page > this.pagetotal) {
      this.page = this.pagetotal;
    }
    if (this.page <= 0)
    {
      if (this.pagetotal == 0)
        this.page = 0;
      else {
        this.page = 1;
      }
    }
    this.firstrownum = ((this.page - 1) * this.pagesize);
    if (this.firstrownum < 0) {
      this.firstrownum = 0;
    }
    this.lastrownum = (this.firstrownum + this.pagesize);
    if (this.lastrownum >= this.total)
      this.lastrownum = this.total;
  }

  public Class getResultClazz()
  {
    return this.resultClazz;
  }

  public void setResultClazz(Class resultClazz)
  {
    this.resultClazz = resultClazz;
  }

  public List getPageDateList(String sql, int page)
  {
    return getPageDateList(sql, getCountSqlFromSql(sql), page);
  }

  public List getPageDateList(String sql, int page, int pagesize)
  {
    return getPageDateList(sql, getCountSqlFromSql(sql), page, pagesize);
  }

  private String getCountSqlFromSql(String sql) {
    String countstr = "select count(*)  ";
    return countstr + sql.substring(sql.indexOf("from"));
  }

  public List getPageDateList(String sql, Class clazz, String countsql, int page)
  {
    this.resultClazz = clazz;
    return getPageDateList(sql, countsql, page);
  }

  public List getPageDateList(String sql, Class clazz, String countsql, int page, int pagesize)
  {
    this.resultClazz = clazz;
    return getPageDateList(sql, countsql, page, pagesize);
  }

  public List getPageDateList(String sql, Class clazz, int page)
  {
    this.resultClazz = clazz;
    return getPageDateList(sql, page);
  }

  public List getPageDateList(String sql, Class clazz, int page, int pagesize)
  {
    this.resultClazz = clazz;
    return getPageDateList(sql, page, pagesize);
  }
}