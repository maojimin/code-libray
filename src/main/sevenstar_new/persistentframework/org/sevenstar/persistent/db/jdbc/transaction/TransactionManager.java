package org.sevenstar.persistent.db.jdbc.transaction;

public interface TransactionManager{
  /*public abstract void startTransaction();
  public abstract void commitTransaction();
  public abstract void rollbackTransaction();
  public abstract void endTransaction();*/
  
    public void startTransaction();
	 public void commitTransaction();
	 public void rollbackTransaction();
	 public void endTransaction();
	 
}