package org.sevenstar.persistent.db.cfg;

import java.util.Map;
import org.sevenstar.persistent.db.cfg.model.SdbModel;

public  interface ISdbConfigure{
  public  SdbModel getSdbModel();

  public  void setSdbModel(SdbModel paramSdbModel);

  public  void setParamMap(Map paramMap);

  public  Map getParamMap();
}
