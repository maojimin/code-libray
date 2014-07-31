package org.sevenstar.persistent.db.model;

import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.model.db.Column;

public class OneToOne
{
  private Domain domain;
  private String name;
  private String className;
  private String table;
  private Column column;
  private String condition;
  private String ownColumn;
  private String select;

  public Domain getDomain()
  {
    return this.domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public String getSelect() {
    if ((this.table != null) && (!"".equals(this.table)) && (this.column != null) && 
      (!"".equals(this.column))) {
      this.select = (getOwnColumn() + "_" + this.table + "_" + getColumn());
    }

    return this.select;
  }

  public String getOwnColumn() {
    if ((this.ownColumn == null) || ("".equals(this.ownColumn))) {
      try {
        return getDomain().getId().getFirstProperty().getColumn()
          .getName();
      } catch (NullPointerException npe) {
        throw new ConfigureException("domain[" + getDomain().getClassName() + "] hasn't config primaryKey");
      }
    }
    return this.ownColumn;
  }

  public void setOwnColumn(String ownColumn) {
    this.ownColumn = ownColumn;
  }

  public void setSelect(String select) {
    this.select = select;
  }

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

  public String getCondition() {
    return this.condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getTable() {
    return this.table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public Column getColumn() {
    return this.column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }
}