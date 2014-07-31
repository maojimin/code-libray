package org.sevenstar.persistent.db.jdbc.transaction;

public class TransactionManagerFactory
{
  public static TransactionManager getTransactionManager()
  {
    return new JdbcTransactionManager();
  }
}