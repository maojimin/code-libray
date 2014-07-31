package org.sevenstar.persistent.db.id;

import java.io.Serializable;
import java.util.Map;

public  interface IDGenerator
{
  public  Serializable generate(Map paramMap);
}