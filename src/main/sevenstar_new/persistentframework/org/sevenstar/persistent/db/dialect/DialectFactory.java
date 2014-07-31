package org.sevenstar.persistent.db.dialect;

import java.util.ArrayList;
import java.util.List;
import org.sevenstar.persistent.db.exception.ConfigureException;

public final class DialectFactory
{
   private static List dialectList = new ArrayList();

  static {
     dialectList.add(new OracleDialect());
     dialectList.add(new Db2Dialect());
     dialectList.add(new SqlServerDialect());
  }
  public static AbstractDialect getDialect(String database) {
    for (int i = 0; i < dialectList.size(); i++) {
       AbstractDialect dialect = (AbstractDialect)dialectList.get(i);
      if (database.equalsIgnoreCase(dialect.getDatabase())) {
        return dialect;
      }
    }
     throw new ConfigureException("no dialect for  database[" + database + "]");
  }

  public static void addDialect(AbstractDialect dialect) {
     dialectList.add(dialect);
  }
}
