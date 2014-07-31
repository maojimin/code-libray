package org.sevenstar.persistent.db.model.db;

import java.util.ArrayList;
import java.util.List;

public class PrimaryKey
{
  private String name;
  private List columnList;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List getColumnList() {
    if (this.columnList == null) {
      this.columnList = new ArrayList();
    }
    return this.columnList;
  }

  public void setColumnList(List columnList) {
    this.columnList = columnList;
  }

  public boolean isCompositeKey()
  {
    return getColumnList().size() >= 2;
  }
}