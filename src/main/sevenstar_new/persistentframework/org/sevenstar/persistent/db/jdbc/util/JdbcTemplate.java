package org.sevenstar.persistent.db.jdbc.util;

import java.lang.reflect.Field;
import org.sevenstar.persistent.db.jdbc.conn.ConnectionFactory;
import org.sevenstar.persistent.db.jdbc.conn.DatabaseConnection;
import org.sevenstar.util.BeanHelper;

public class JdbcTemplate
{
  private DatabaseConnection dbconn;
  public JdbcTemplate jdbcTemplate;

  public static JdbcTemplate getInstance()
  {
    DatabaseConnection dbconn = new DatabaseConnection(
      ConnectionFactory.getCurrentConnection());
    return new JdbcTemplate(dbconn);
  }

  private JdbcTemplate()
  {
  }

  private JdbcTemplate(DatabaseConnection dbconn) {
    this.dbconn = dbconn;
  }

  public void update(String sql)
  {
    store(sql);
  }

  public void update(String sql, Object[] params)
  {
    store(sql, params);
  }

  private void store(String sql, Object[] params)
  {
    this.dbconn.executeUpdate(sql, params);
  }

  public void insert(String sql) {
    store(sql);
  }

  public void insert(String sql, Object[] params)
  {
    store(sql, params);
  }

  public void insert(String sql, Object bean)
  {
    store(getPrepareSql(sql, bean));
  }

  private String getPrepareSql(String sql, Object bean) {
    Field[] fields = BeanHelper.getFields(bean.getClass());
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      if (sql.indexOf("#" + field.getName() + "#") != -1) {
        Object value = BeanHelper.getPropertyValue(
          field.getName(), bean);
        if (value != null)
          sql = sql.replaceAll("#" + field.getName() + "#", "'" + 
            value + "'");
        else {
          sql = sql.replaceAll("#" + field.getName() + "#", "''");
        }
      }
    }
    return sql;
  }

  public void update(String sql, Object bean) {
    store(getPrepareSql(sql, bean));
  }

  public void delete(String sql)
  {
    store(sql);
  }

  public void delete(String sql, Object bean) {
    store(getPrepareSql(sql, bean));
  }

  private void store(String sql) {
    this.dbconn.executeUpdate(sql);
  }
}