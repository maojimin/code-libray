package org.sevenstar.web.cfg.model;

import java.util.ArrayList;
import java.util.List;

public class ParsesModel {
  private List parseList;

  public List getParseList() {
     if (this.parseList == null) {
       this.parseList = new ArrayList();
    }
    return this.parseList;
  }

  public void setParseList(List parseList) {
    this.parseList = parseList;
  }

  public void addParseModel(ParseModel parseModel) {
     getParseList().add(parseModel);
  }
}

