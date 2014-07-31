package org.sevenstar.persistent.db.model;

import java.util.ArrayList;
import java.util.List;
import org.sevenstar.persistent.db.model.db.PrimaryKey;

public class Id
{
  public static final String generatetype_seq = "seq";
  public static final String generatetype_uuidhex = "uuidhex";
  public static final String generatetype_uuidstring = "uuidstring";
  private List propertyList;
  private String generateType = "seq";
  private PrimaryKey pk;
  private String seq;
  private String primaryKeyParameterType;

  public void addProperty(Property property)
  {
    getPropertyList().add(property);
  }

  public void setPrimaryKeyParameterType(String primaryKeyParameterType) {
    this.primaryKeyParameterType = primaryKeyParameterType;
  }

  public Property getFirstProperty() {
    if ((getPropertyList() != null) && (getPropertyList().size() > 0)) {
      return (Property)getPropertyList().get(0);
    }
    return null;
  }

  public String getPrimaryKeyParameterType() {
    if ((this.primaryKeyParameterType != null) && (!"".equals(this.primaryKeyParameterType.trim()))) {
      return this.primaryKeyParameterType;
    }
    if (isCompositeId()) {
      return "java.util.HashMap";
    }
    if (getFirstProperty() != null) {
      return getFirstProperty().getClassName();
    }
    return "java.lang.Long";
  }

  public List getPropertyList()
  {
    if (this.propertyList == null) {
      this.propertyList = new ArrayList();
    }
    return this.propertyList;
  }

  public void setPropertyList(List propertyList) {
    this.propertyList = propertyList;
  }

  public boolean isCompositeId()
  {
    return getPropertyList().size() >= 2;
  }

  public String getGenerateType()
  {
    if ((this.generateType == null) || ("".equals(this.generateType)))
    {
      this.generateType = "seq";
    }
    return this.generateType;
  }

  public void setGenerateType(String generateType) {
    this.generateType = generateType;
  }

  public PrimaryKey getPk() {
    return this.pk;
  }

  public void setPk(PrimaryKey pk) {
    this.pk = pk;
  }

  public String getSeq() {
    return this.seq;
  }

  public void setSeq(String seq) {
    this.seq = seq;
  }

  public static String getGeneratetype_seq() {
    return "seq";
  }
}