package org.sevenstar.web.cfg.model;

import java.util.ArrayList;
import java.util.List;

public class InvocationsModel {
  private List invocationModelList;

  public void addInvocationModel(InvocationModel invocationModel){
     getInvocationModelList().add(invocationModel);
  }

  public List getInvocationModelList() {
     if (this.invocationModelList == null) {
       this.invocationModelList = new ArrayList();
    }
     return this.invocationModelList;
  }

  public void setInvocationModelList(List invocationModelList) {
     this.invocationModelList = invocationModelList;
  }
}
