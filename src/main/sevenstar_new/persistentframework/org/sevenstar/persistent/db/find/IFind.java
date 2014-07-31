package org.sevenstar.persistent.db.find;

import java.util.Map;
import org.sevenstar.persistent.db.model.Domain;

public interface IFind
{
 /* public abstract Domain find(Class paramClass);
  public abstract void setParamMap(Map paramMap);
  public abstract Map getParamMap();*/
	  public Domain find(Class clazz);
	  public void setParamMap(Map map);
	  public Map getParamMap();
}
