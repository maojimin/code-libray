package org.sevenstar.web.cfg;

import java.util.Map;
import org.sevenstar.web.cfg.model.SwebModel;

public  interface ISwebConfigure {
  public  SwebModel getSwebModel();

  public  void setSwebModel(SwebModel paramSwebModel);

  public  void setParamMap(Map paramMap);

  public  Map getParamMap();
}

