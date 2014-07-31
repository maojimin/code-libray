package org.sevenstar.persistent.db.dialect;

import org.sevenstar.persistent.db.model.Domain;

public class MysqlDialect extends AbstractDialect{
  public static void main(String[] args){
     System.out.println(System.currentTimeMillis());
  }

  public long getCurrentDBTimeInMillis()
  {
     return 0L;
  }

  public String getDatabase()
  {
     return "mysql";
  }

  public String getJdbcType(String javatype)
  {
     return null;
  }

  public String getPageAfterSql(Domain domain)
  {
     return null;
  }

  public String getPageBeforeSql(Domain domain)
  {
     return null;
  }

  public String getPageSql(Domain domain)
  {
     return null;
  }

  public String getSelectEqualMaxSizeSql(Domain domain)
  {
     return null;
  }

  public String getSelectMaxSizeSql(Domain domain)
  {
     return null;
  }

  public String getSelectType(String jdbcType)
  {
     return null;
  }

  public String getSequenceSql(Domain domain)
  {
     return null;
  }
}
