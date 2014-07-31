package org.sevenstar.persistent.db.exception;

public class PersistentException extends RuntimeException
{
  public PersistentException(String e)
  {
    super(e);
  }

  public PersistentException(String message, Throwable cause) {
    super(message, cause);
  }

  public PersistentException(Throwable cause)
  {
    super(cause);
  }
}