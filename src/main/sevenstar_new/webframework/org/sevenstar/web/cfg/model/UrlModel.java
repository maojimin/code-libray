package org.sevenstar.web.cfg.model;

import java.util.ArrayList;
import java.util.List;

public class UrlModel {
  private List ruleList;

  public void addRuleModel(RuleModel ruleModel) {
     getRuleList().add(ruleModel);
  }

  public List getRuleList() {
     if (this.ruleList == null) {
       this.ruleList = new ArrayList();
    }
     return this.ruleList;
  }

  public void setRuleList(List ruleList) {
     this.ruleList = ruleList;
  }
}

