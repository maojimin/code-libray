package org.sevenstar.persistent.db.model.db;

import java.util.List;

public class Table
{
  private String name;
  private String schema;
  private String comment;
  private List pkList;
  private List fkList;
  private List columnList;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSchema() {
    return this.schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List getPkList() {
    return this.pkList;
  }

  public void setPkList(List pkList) {
    this.pkList = pkList;
  }

  public List getFkList() {
    return this.fkList;
  }

  public void setFkList(List fkList) {
    this.fkList = fkList;
  }

  public List getColumnList() {
    return this.columnList;
  }

  public void setColumnList(List columnList) {
    this.columnList = columnList;
  }
}