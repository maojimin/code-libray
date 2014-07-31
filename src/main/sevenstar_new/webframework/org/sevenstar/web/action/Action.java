package org.sevenstar.web.action;

public abstract interface Action
{
  public static final String SUCCESS = "success";
  public static final String ERROR = "error";
  public static final String REDIRECT = "redirect";

  public abstract Object execute();
}

