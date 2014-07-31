package org.sevenstar.persistent.db.jdbc.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sevenstar.persistent.db.jdbc.conn.DatabaseConnection;

public class DBExportImport
{
  private static DatabaseConnection dbconn1 = null;

  private static DatabaseConnection dbconn2 = null;

  public static void main(String[] args) throws SQLException {
    String[] tableNames = getDbConn1().getTableNames();
    try {
      for (int i = 0; i < tableNames.length; i++)
        copy(tableNames[i], tableNames[i], getDbConn1(), getDbConn2());
    }
    finally {
      getDbConn1().close();
      getDbConn2().close();
    }
  }

  public static DatabaseConnection getDbConn1() {
    if (dbconn1 == null) {
      DatabaseConnection dbconn = new DatabaseConnection();
      dbconn.setDriver("com.p6spy.engine.spy.P6SpyDriver");
      dbconn.setDatabaseType("oracle");
      dbconn.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:ora10g");
      dbconn.setUser("app");
      dbconn.setPass("app");
      dbconn.databaseConnect();
      dbconn1 = dbconn;
    }
    return dbconn1;
  }

  public static DatabaseConnection getDbConn2() {
    if (dbconn2 == null) {
      DatabaseConnection dbconn = new DatabaseConnection();
      dbconn.setDriver("com.p6spy.engine.spy.P6SpyDriver");
      dbconn.setDatabaseType("oracle");
      dbconn.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:ora10g");
      dbconn.setUser("app");
      dbconn.setPass("app");
      dbconn.databaseConnect();
      dbconn2 = dbconn;
    }
    return dbconn2;
  }

  public static void copy(String fromTableName, String toTableName, DatabaseConnection from, DatabaseConnection to)
  {
    int firstrow = 0;
    int lastrow = 200;
    List list = new ArrayList();
    list = queryForList(fromTableName, from, firstrow, lastrow);
    while (list.size() > 0) {
      insert(list, toTableName, to);
      firstrow += 200;
      lastrow += 200;
      list = queryForList(fromTableName, from, firstrow, lastrow);
    }
  }

  public static void insert(List list, String tableName, DatabaseConnection dbconn)
  {
    Map columnType = dbconn.getColumNamesAndType(tableName);
    List keyList = new ArrayList();
    Iterator iter = columnType.keySet().iterator();
    while (iter.hasNext()) {
      keyList.add(iter.next());
    }
    for (int i = 0; i < list.size(); i++) {
      Map map = (Map)list.get(i);
      String sql = "insert into " + tableName + "(";
      for (int j = 0; j < keyList.size(); j++) {
        String key = String.valueOf(keyList.get(j));
        String type = String.valueOf(columnType.get(key));
        if (("insert into " + tableName + "(").equals(sql))
          sql = sql + key;
        else {
          sql = sql + "," + key;
        }
      }

      sql = sql + ") values(";
      for (int j = 0; j < keyList.size(); j++) {
        String key = String.valueOf(keyList.get(j));
        String type = String.valueOf(columnType.get(key));
        if ((!"java.sql.Timestamp".equals(type)) && 
          (!"java.sql.Date".equals(type))) {
          if (j == 0)
            sql = sql + "?";
          else {
            sql = sql + ",?";
          }

        }
        else if (j == 0) {
          if (map.get(key) != null)
            sql = sql + "to_date('" + map.get(key) + 
              "','yyyy-MM-dd hh24:mi:ss')";
          else {
            sql = sql + "null";
          }
        }
        else if (map.get(key) != null)
          sql = sql + ",to_date('" + map.get(key) + 
            "','yyyy-MM-dd hh24:mi:ss')";
        else {
          sql = sql + ",null";
        }

      }

      sql = sql + ")";

      PreparedStatement ps = dbconn.getPreparedStatement(sql);
      int num = 0;
      for (int j = 0; j < keyList.size(); j++) {
    	String key = String.valueOf(keyList.get(j));
        String type = String.valueOf(columnType.get(key));
        if (("java.sql.Timestamp".equals(type)) || 
          ("java.sql.Date".equals(type))) continue;
        try {
          if ("oracle.sql.CLOB".equals(type))
            ps.setString(num + 1, (String)map.get(key));
          else {
            ps.setObject(num + 1, map.get(key));
          }
          num++;
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
      try
      {
        ps.execute();
        dbconn.commit();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      } finally {
        dbconn.closeStmtAndRs();
      }
    }
  }

  public static List queryForList(String tableName, DatabaseConnection dbconn, int firstrow, int lastrow)
  {
    Map columnType = dbconn.getColumNamesAndType(tableName);
    String sql = "select ";
    Iterator iter = columnType.keySet().iterator();
    while (iter.hasNext()) {
      String key = String.valueOf(iter.next());
      String type = String.valueOf(columnType.get(key));
      if ((!"java.sql.Timestamp".equals(type)) && 
        (!"java.sql.Date".equals(type))) {
        if ("select ".equals(sql))
          sql = sql + key;
        else {
          sql = sql + "," + key;
        }
      }
      else if ("select ".equals(sql))
        sql = sql + "to_char(" + key + 
          ",'yyyy-MM-dd hh24:mi:ss') as " + key;
      else {
        sql = sql + ",to_char(" + key + 
          ",'yyyy-MM-dd hh24:mi:ss')  as " + key;
      }
    }

    sql = sql + " from " + tableName;
    sql = "select * from ( select row_.*, rownum rownum_ from ( " + sql + 
      "  ) row_ where rownum <= " + lastrow + ") where rownum_ > " + 
      firstrow;
    ResultSet rs = dbconn.executeQuery(sql);
    List list = new ArrayList();
    try {
      while (rs.next()) {
        Map map = new HashMap();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++)
        {
          if (rsmd.getColumnClassName(i + 1)
            .equals("oracle.sql.CLOB"))
            map.put(rsmd.getColumnName(i + 1).toLowerCase(), 
              rs.getString(rsmd.getColumnName(i + 1)));
          else {
            map.put(rsmd.getColumnName(i + 1).toLowerCase(), 
              rs.getObject(rsmd.getColumnName(i + 1)));
          }
        }
        list.add(map);
      }
      List localList1 = list;
      return localList1;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      dbconn.closeStmtAndRs();
    }
  }
}