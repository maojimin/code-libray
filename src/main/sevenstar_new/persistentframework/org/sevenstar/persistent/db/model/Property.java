package org.sevenstar.persistent.db.model;

import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.cfg.model.SdbModel;
import org.sevenstar.persistent.db.dialect.AbstractDialect;
import org.sevenstar.persistent.db.dialect.DialectFactory;
import org.sevenstar.persistent.db.model.db.Column;

public class Property
{
  private Domain domain;
  private String name;
  private String className;
  private Column column;
  private String parameter;
  private String jdbctype;
  private String select_type;
  private String select_property;
  private String select_min_property;
  private String select_max_property;
  private String select_sql;
  private String select_prepend;
  private boolean update = true;

  private boolean updateNull = false;

  private boolean insert = true;
  private String value;
  private String defaultValue;
  private boolean disuse = false;

  public boolean isDisuse()
  {
    return this.disuse;
  }

  public void setDisuse(boolean disuse) {
    this.disuse = disuse;
  }

  public String getParameter() {
    if ((this.parameter == null) || 
      ("".equals(this.parameter))) {
      this.parameter = this.name;
      return this.name;
    }
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

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Column getColumn() {
    return this.column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getDefaultValue() {
    return this.defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public boolean isInsert() {
    return this.insert;
  }

  public void setInsert(boolean insert) {
    this.insert = insert;
  }

  public boolean isUpdate() {
    return this.update;
  }

  public void setUpdate(boolean update) {
    this.update = update;
  }

  public String getJdbctype() {
    if ((this.jdbctype == null) || ("".equals(this.jdbctype))) {
      return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getJdbcType(this.className);
    }
    return this.jdbctype;
  }

  public void setJdbctype(String jdbctype) {
    this.jdbctype = jdbctype;
  }

  public String getSelect_type() {
    if ((this.select_type == null) || ("".equals(this.select_type))) {
      if (("java.lang.String".equalsIgnoreCase(this.className)) && 
        (getDomain() != null) && (getDomain().getSelect_type_string() != null) && (!"".equals(getDomain().getSelect_type_string()))) {
        return getDomain().getSelect_type_string();
      }

      return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getSelectType(getJdbctype());
    }
    return this.select_type;
  }

  public void setSelect_type(String select_type) {
    this.select_type = select_type;
  }

  public String getSelect_property() {
    if ((this.select_property == null) || ("".equals(this.select_property))) {
      this.select_property = getParameter();
    }
    return this.select_property;
  }

  public void setSelect_property(String select_property) {
    this.select_property = select_property;
  }

  public String getSelect_min_property() {
    return this.select_min_property;
  }

  public void setSelect_min_property(String select_min_property) {
    this.select_min_property = select_min_property;
  }

  public String getSelect_max_property() {
    return this.select_max_property;
  }

  public void setSelect_max_property(String select_max_property) {
    this.select_max_property = select_max_property;
  }

  public String getSelect_sql() {
    return this.select_sql;
  }

  public void setSelect_sql(String select_sql) {
    this.select_sql = select_sql;
  }

  public String getSelect_prepend() {
    if ((this.select_prepend == null) || ("".equals(this.select_prepend))) {
      this.select_prepend = "and";
    }
    return this.select_prepend;
  }

  public void setSelect_prepend(String select_prepend) {
    this.select_prepend = select_prepend;
  }

  public boolean isUpdateNull() {
    return this.updateNull;
  }

  public void setUpdateNull(boolean updateNull) {
    this.updateNull = updateNull;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}