package org.sevenstar.persistent.db.jdbc.conn;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDecorator
{
  protected Connection conn;
  protected long registtime;

  public ConnectionDecorator(Connection conn)
  {
    try
    {
      if ((conn != null) && (!conn.isClosed())) {
        this.conn = conn;

        this.registtime = System.currentTimeMillis();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public boolean isClosed() {
    try {
      if (this.conn == null) {
        return true;
      }
      if (this.conn.isClosed()) {
        this.conn = null;
        return true;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  public void close()
  {
    try {
      if ((this.conn != null) && (!this.conn.isClosed())) {
        this.conn.close();
        this.conn = null;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}