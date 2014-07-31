package org.sevenstar.persistent.db.model;

import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.dialect.DialectFactory;
import org.sevenstar.persistent.db.model.db.Column;
import org.sevenstar.persistent.db.model.db.ForeignKey;

public class ManyToOne
{
  private Domain domain;
  private String name;
  private String className;
  private ForeignKey fk;
  private String table;
  private Column column;
  private String condition;
  private boolean updateNull = false;

  private boolean update = true;

  private boolean insert = true;
  private String select;
  private String parameter;
  private String jdbctype;
  private String javatype;

  public String getJavatype()
  {
    if ((this.javatype == null) || ("".equals(this.javatype))) {
      return "java.lang.Long";
    }
    return this.javatype;
  }

  public void setJavatype(String javatype) {
    this.javatype = javatype;
  }

  public boolean isUpdateNull() {
    return this.updateNull;
  }

  public void setUpdateNull(boolean updateNull) {
    this.updateNull = updateNull;
  }

  public boolean isUpdate() {
    return this.update;
  }

  public void setUpdate(boolean update) {
    this.update = update;
  }

  public boolean isInsert() {
    return this.insert;
  }

  public void setInsert(boolean insert) {
    this.insert = insert;
  }

  public String getJdbctype() {
    if ((this.jdbctype == null) || ("".equals(this.jdbctype))) {
      return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getJdbcType(getJavatype());
    }
    return this.jdbctype;
  }

  public void setJdbctype(String jdbctype) {
    this.jdbctype = jdbctype;
  }

  public String getParameter() {
    return this.parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public Domain getDomain() {
    return this.domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  private String getSimpleClassName() {
    return this.className.substring(this.className.lastIndexOf(".") + 1);
  }

  public String getSelect() {
    if ((this.select == null) || ("".equals(this.select))) {
      if ((this.table != null) && (!"".equals(this.table)) && (this.column != null) && 
        (!"".equals(this.column)))
        this.select = (getColumn() + "_" + this.table + "_" + this.column);
      else {
        this.select = (getSimpleClassName() + "_base_load_for_relation");
      }
    }
    return this.select;
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

  public ForeignKey getFk() {
    return this.fk;
  }

  public void setFk(ForeignKey fk) {
    this.fk = fk;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getTable() {
    return this.table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getCondition() {
    return this.condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public Column getColumn() {
    return this.column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }
}