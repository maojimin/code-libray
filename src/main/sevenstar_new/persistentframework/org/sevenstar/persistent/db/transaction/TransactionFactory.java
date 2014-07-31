package org.sevenstar.persistent.db.transaction;

import java.sql.SQLException;

import org.sevenstar.persistent.db.exception.PersistentException;
import org.sevenstar.persistent.db.ibatis.MyAppSqlConfig;

public class TransactionFactory
{
  public static void startTransaction()
  {
    try
    {
      MyAppSqlConfig.getSqlMapInstance().startTransaction();
    } catch (SQLException e) {
      throw new PersistentException(e);
    }
  }

  public static void commitTransaction() {
    try {
      MyAppSqlConfig.getSqlMapInstance().commitTransaction();
    } catch (SQLException e) {
      throw new PersistentException(e);
    }
  }

  public static void endTransaction() {
    try {
      MyAppSqlConfig.getSqlMapInstance().endTransaction();
    } catch (SQLException e) {
      throw new PersistentException(e);
    }
  }
}