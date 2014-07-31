package org.sevenstar.web.cfg;

import java.util.ArrayList;
import java.util.List;

class SwebConfigModel {
  private String name;
  private String className;
  private List paramList;

  public String getName() {
     return this.name;
  }

  public void setName(String name) {
     this.name = name;
  }

  public String getClassName() {
     return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public List getParamList() {
    if (this.paramList == null) {
       this.paramList = new ArrayList();
    }
     return this.paramList;
  }

  public void setParamList(List paramList) {
    this.paramList = paramList;
  }

  public void addParamModel(SwebConfigureParamModel model) {
     getParamList().add(model);
  }
}
