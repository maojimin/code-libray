package org.sevenstar.web.cfg.model;

import java.util.ArrayList;
import java.util.List;

public class GlobalResultsModel
{
  private List resultModelList;

  public void addResultModel(GlobalResultModel resultModel) {
     if (this.resultModelList == null) {
       this.resultModelList = new ArrayList();
    }
     getResultModelList().add(resultModel);
  }

  public List getResultModelList() {
     return this.resultModelList;
  }

  public void setResultModelList(List resultModelList) {
     this.resultModelList = resultModelList;
  }
}
