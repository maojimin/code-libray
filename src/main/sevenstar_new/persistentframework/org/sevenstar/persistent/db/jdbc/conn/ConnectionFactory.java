package org.sevenstar.persistent.db.jdbc.conn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionFactory
{
  private static Log log = LogFactory.getLog(ConnectionFactory.class);
  private static ThreadLocal conn = new ThreadLocal();

  public static ThreadLocal connectionList = new ThreadLocal();

  private static ConnectionManager connectionManager = new ConnectionManager();

  static { connectionManager.start();
  }

  public static void register(Connection conn)
  {
    List list = (List)connectionList.get();
    if (list == null) {
      list = new ArrayList();
    }
    list.add(conn);
    connectionList.set(list);
    connectionManager.regisgerConnection(conn);
  }

  public static void realseAllConnectionInCurrentThread()
  {
    List list = (List)connectionList.get();
    int hasNotClosed = 0;
    int totalConnection = 0;
    if (list != null) {
      totalConnection = list.size();
      for (int i = 0; i < list.size(); i++) {
        Connection conn = (Connection)list.get(i);
        try {
          if ((conn != null) && (!conn.isClosed())) {
            hasNotClosed++;
            conn.close();
          }
        }
        catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    log.debug("realseCurrentThread (" + totalConnection + 
      ")connection,hasNotClosed (" + hasNotClosed + ")");
    connectionList.set(null);
  }

  public static Connection getCurrentConnection()
  {
    if (conn.get() == null) {
      conn.set(getNewConnection());
      log.debug("connection is null ,get new connection");
    }
    Connection connection = (Connection)conn.get();
    try {
      if (connection.isClosed()) {
        conn.set(getNewConnection());
        log.debug("connection is closed ,get new connection");
      }
      else {
        log.debug("reused  connection");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return connection;
  }

  public static void closeCurrentConnection()
  {
    if (conn.get() == null) {
      throw new RuntimeException("connection is closed");
    }
    Connection connection = (Connection)conn.get();
    try {
      if (connection.isClosed()) {
        conn.set(null);
        throw new RuntimeException("connection is closed");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      connection.close();
      connection = null;
      conn.set(null);
      log.debug("close  connection sucessed");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static Connection getNewConnection()
  {
    DatabaseConnection dbconn = new DatabaseConnection();
    Connection conn = dbconn.getConnection();
    register(conn);
    return conn;
  }
}