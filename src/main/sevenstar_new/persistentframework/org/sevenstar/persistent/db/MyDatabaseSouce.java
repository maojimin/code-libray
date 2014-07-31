package org.sevenstar.persistent.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.dbcp.BasicDataSource;

public class MyDatabaseSouce
{
  public static BasicDataSource dataSource = null;

  static { ResourceBundle rb = ResourceBundle.getBundle("conn");
    dataSource = new BasicDataSource();
    dataSource.setValidationQuery("select 1 from dual");
    dataSource.setDriverClassName(rb.getString("driver"));
    dataSource.setUrl(rb.getString("url"));
    dataSource.setUsername(rb.getString("user"));
    dataSource.setPassword(rb.getString("password"));
    dataSource.setInitialSize(3);
    dataSource.setMaxActive(10);
    dataSource.setTestOnBorrow(true);
    dataSource.setTestOnReturn(false); }

  public static Connection getConnection() {
    try {
      return dataSource.getConnection(); } catch (SQLException e) {
    }
    throw new RuntimeException();
  }

  public static void main(String[] args)
  {
    System.out.println(getConnection());
  }
}