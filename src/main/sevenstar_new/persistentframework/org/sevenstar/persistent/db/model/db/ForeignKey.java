package org.sevenstar.persistent.db.model.db;

public class ForeignKey
{
  private String name;
  private String table;
  private String column;
  private String foreign_table;
  private String foreign_column;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTable() {
    return this.table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getColumn() {
    return this.column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getForeign_table() {
    return this.foreign_table;
  }

  public void setForeign_table(String foreign_table) {
    this.foreign_table = foreign_table;
  }

  public String getForeign_column() {
    return this.foreign_column;
  }

  public void setForeign_column(String foreign_column) {
    this.foreign_column = foreign_column;
  }
}