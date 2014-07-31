package org.sevenstar.persistent.db.exception;

public class ConfigureException extends RuntimeException
{
  public ConfigureException(String e)
  {
    super(e);
  }

  public ConfigureException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigureException(Throwable cause)
  {
    super(cause);
  }
}