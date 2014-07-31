package org.sevenstar.persistent.db.model.db;

public class Column
{
  private String name;
  private String type;
  private String comment;
  private String length;
  private boolean isPrimaryKey;
  private boolean isForeignKey;
  private boolean isUnique;
  private boolean isNullAble;
  private String defaultValue;

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getLength() {
    return this.length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public boolean isPrimaryKey() {
    return this.isPrimaryKey;
  }

  public void setPrimaryKey(boolean isPrimaryKey) {
    this.isPrimaryKey = isPrimaryKey;
  }

  public boolean isForeignKey() {
    return this.isForeignKey;
  }

  public void setForeignKey(boolean isForeignKey) {
    this.isForeignKey = isForeignKey;
  }

  public boolean isUnique() {
    return this.isUnique;
  }

  public void setUnique(boolean isUnique) {
    this.isUnique = isUnique;
  }

  public boolean isNullAble() {
    return this.isNullAble;
  }

  public void setNullAble(boolean isNullAble) {
    this.isNullAble = isNullAble;
  }

  public String getDefaultValue() {
    return this.defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
}