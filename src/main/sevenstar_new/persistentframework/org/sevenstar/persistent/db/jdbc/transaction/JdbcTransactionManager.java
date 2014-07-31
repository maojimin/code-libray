package org.sevenstar.persistent.db.jdbc.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.jdbc.conn.ConnectionFactory;

public class JdbcTransactionManager
  implements TransactionManager
{
  private static Log log = LogFactory.getLog(JdbcTransactionManager.class);

  public void startTransaction() {
    log.debug("start transaction");
    Connection connection = ConnectionFactory.getCurrentConnection();
    try {
      if (connection.getAutoCommit())
        connection.setAutoCommit(false);
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void commitTransaction() {
    log.debug("commit transaction");
    Connection connection = ConnectionFactory.getCurrentConnection();
    try {
      connection.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void endTransaction()
  {
    log.debug("end transaction");
    ConnectionFactory.closeCurrentConnection();
  }

  public void rollbackTransaction() {
    log.debug("rollback transaction");
    Connection connection = ConnectionFactory.getCurrentConnection();
    try {
      connection.rollback();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}