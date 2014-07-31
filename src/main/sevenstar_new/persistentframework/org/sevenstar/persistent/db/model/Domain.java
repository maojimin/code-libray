package org.sevenstar.persistent.db.model;

import java.util.ArrayList;
import java.util.List;
import org.sevenstar.persistent.db.model.db.Table;

public class Domain
{
  private String className;
  private Table table;
  private List propertyList;
  private List manyToOneList;
  private List oneToManyList;
  private List oneToOneList;
  private Id id;
  private String insertSql;
  private String updateSql;
  private String deleteSql;
  private String loadSql;
  private String condition;
  private String order;
  private String select_type_string;
  private CacheModel cacheModel;
  private boolean insert = true;

  private boolean update = true;

  private boolean delete = true;

  public boolean isInsert() {
    if (this.insert) {
      boolean hasInsertProperty = false;
      List propertyList = getPropertyList();
      for (int i = 0; i < propertyList.size(); i++) {
        Property property = (Property)propertyList.get(i);
        if (property.isInsert()) {
          hasInsertProperty = true;
        }
      }

      if (!hasInsertProperty) {
        List manyToOneList = getManyToOneList();
        for (int i = 0; i < manyToOneList.size(); i++) {
          ManyToOne mangToOne = (ManyToOne)manyToOneList.get(i);
          if (mangToOne.isInsert()) {
            hasInsertProperty = true;
          }
        }
      }

      if ((!hasInsertProperty) && 
        (getId() != null)) {
        List idPropertyList = getId().getPropertyList();
        for (int i = 0; i < idPropertyList.size(); i++) {
          Property property = (Property)idPropertyList.get(i);
          if (property.isInsert()) {
            hasInsertProperty = true;
          }
        }

      }

      return hasInsertProperty;
    }
    return this.insert;
  }

  public void setInsert(boolean insert) {
    this.insert = insert;
  }

  public boolean isUpdate() {
    if (this.update) {
      boolean hasUpdateProperty = false;
      List propertyList = getPropertyList();
      for (int i = 0; i < propertyList.size(); i++) {
        Property property = (Property)propertyList.get(i);
        if (property.isInsert()) {
          hasUpdateProperty = true;
        }
      }

      if (!hasUpdateProperty) {
        List manyToOneList = getManyToOneList();
        for (int i = 0; i < manyToOneList.size(); i++) {
          ManyToOne mangToOne = (ManyToOne)manyToOneList.get(i);
          if (mangToOne.isInsert()) {
            hasUpdateProperty = true;
          }
        }
      }

      return hasUpdateProperty;
    }
    return this.update;
  }

  public boolean isDelete()
  {
    return this.delete;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
  }

  public void setUpdate(boolean update) {
    this.update = update;
  }

  public String getSelect_type_string() {
    return this.select_type_string;
  }

  public void setSelect_type_string(String select_type_string) {
    this.select_type_string = select_type_string;
  }

  public List getOneToOneList() {
    if (this.oneToOneList == null) {
      this.oneToOneList = new ArrayList();
    }
    return this.oneToOneList;
  }

  public OneToOne getOneToOne(String oneToOneName) {
    List list = getManyToOneList();
    for (int i = 0; i < list.size(); i++) {
      OneToOne oneToOne = (OneToOne)list.get(i);
      if (oneToOneName.equals(oneToOne.getName())) {
        return oneToOne;
      }
    }
    return null;
  }

  public boolean hasOneToOne(String oneToOneName) {
    List list = getOneToOneList();
    for (int i = 0; i < list.size(); i++) {
      OneToOne oneToOne = (OneToOne)list.get(i);
      if (oneToOneName.equals(oneToOne.getName())) {
        return true;
      }
    }
    return false;
  }

  public void addOneToOne(OneToOne oneToOne) {
    getOneToOneList().add(oneToOne);
  }

  public void setOneToOneList(List oneToOneList) {
    this.oneToOneList = oneToOneList;
  }

  public IbatisModel getIbatisModel() {
    return new IbatisModel(this);
  }

  public String getSimpleClassName() {
    return this.className.substring(this.className.lastIndexOf(".") + 1);
  }

  public CacheModel getCacheModel() {
    return this.cacheModel;
  }

  public void setCacheModel(CacheModel cacheModel) {
    this.cacheModel = cacheModel;
  }

  public Id getId() {
    return this.id;
  }

  public void setId(Id id) {
    this.id = id;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Table getTable() {
    return this.table;
  }

  public Table setTable(Table table) {
    this.table = table;
    return table;
  }

  public Property getProperty(String propertyName) {
    List list = getPropertyList();
    for (int i = 0; i < list.size(); i++) {
      Property property = (Property)list.get(i);
      if (propertyName.equals(property.getName())) {
        return property;
      }
    }
    if ((getId() != null) && (getId().getPropertyList() != null) && 
      (getId().getPropertyList().size() > 0)) {
      for (int i = 0; i < getId().getPropertyList().size(); i++) {
        Property property = 
          (Property)getId().getPropertyList()
          .get(i);
        if (propertyName.equals(property.getName())) {
          return property;
        }
      }
    }
    return null;
  }

  public void deleteProperty(String propertyName) {
    List list = getPropertyList();
    for (int i = 0; i < list.size(); i++) {
      Property property = (Property)list.get(i);
      if (propertyName.equals(property.getName())) {
        list.remove(i);
        i--;
      }
    }
    if ((getId() != null) && (getId().getPropertyList() != null) && 
      (getId().getPropertyList().size() > 0))
      for (int i = 0; i < getId().getPropertyList().size(); i++) {
        Property property = 
          (Property)getId().getPropertyList()
          .get(i);
        if (propertyName.equals(property.getName())) {
          getId().getPropertyList().remove(i);
          i--;
        }
      }
  }

  public boolean hasProperty(String propertyName)
  {
    List list = getPropertyList();
    for (int i = 0; i < list.size(); i++) {
      Property property = (Property)list.get(i);
      if (propertyName.equals(property.getName())) {
        return true;
      }
    }
    if ((getId() != null) && (getId().getPropertyList() != null) && 
      (getId().getPropertyList().size() > 0)) {
      for (int i = 0; i < getId().getPropertyList().size(); i++) {
        Property property = 
          (Property)getId().getPropertyList()
          .get(i);
        if (propertyName.equals(property.getName())) {
          return true;
        }
      }
    }
    return false;
  }

  public Property addProperty(Property property) {
    getPropertyList().add(property);
    return property;
  }

  public List getPropertyList() {
    if (this.propertyList == null) {
      this.propertyList = new ArrayList();
    }
    return this.propertyList;
  }

  public void setPropertyList(List propertyList) {
    this.propertyList = propertyList;
  }

  public ManyToOne getManyToOne(String manyToOneName) {
    List list = getManyToOneList();
    for (int i = 0; i < list.size(); i++) {
      ManyToOne manyToOne = (ManyToOne)list.get(i);
      if (manyToOneName.equals(manyToOne.getName())) {
        return manyToOne;
      }
    }
    return null;
  }

  public boolean hasManyToOne(String manyToOneName) {
    List list = getManyToOneList();
    for (int i = 0; i < list.size(); i++) {
      ManyToOne manyToOne = (ManyToOne)list.get(i);
      if (manyToOneName.equals(manyToOne.getName())) {
        return true;
      }
    }
    return false;
  }

  public void addManyToOne(ManyToOne mangToOne) {
    getManyToOneList().add(mangToOne);
  }

  public List getManyToOneList() {
    if (this.manyToOneList == null) {
      this.manyToOneList = new ArrayList();
    }
    return this.manyToOneList;
  }

  public void setManyToOneList(List manyToOneList) {
    manyToOneList = manyToOneList;
  }

  public String getInsertSql() {
    return this.insertSql;
  }

  public void setInsertSql(String insertSql) {
    this.insertSql = insertSql;
  }

  public String getUpdateSql() {
    return this.updateSql;
  }

  public void setUpdateSql(String updateSql) {
    this.updateSql = updateSql;
  }

  public String getDeleteSql() {
    return this.deleteSql;
  }

  public void setDeleteSql(String deleteSql) {
    this.deleteSql = deleteSql;
  }

  public String getCondition() {
    return this.condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getLoadSql() {
    return this.loadSql;
  }

  public void setLoadSql(String loadSql) {
    this.loadSql = loadSql;
  }

  public OneToMany getOneToMany(String oneToManyName) {
    List list = getOneToManyList();
    for (int i = 0; i < list.size(); i++) {
      OneToMany oneToMany = (OneToMany)list.get(i);
      if (oneToManyName.equals(oneToMany.getName())) {
        return oneToMany;
      }
    }
    return null;
  }

  public boolean hasOneToMany(String oneToManyName) {
    List list = getOneToManyList();
    for (int i = 0; i < list.size(); i++) {
      OneToMany oneToMany = (OneToMany)list.get(i);
      if (oneToManyName.equals(oneToMany.getName())) {
        return true;
      }
    }
    return false;
  }

  public void addOneToMany(OneToMany oneToMany) {
    getOneToManyList().add(oneToMany);
  }

  public List getOneToManyList() {
    if (this.oneToManyList == null) {
      this.oneToManyList = new ArrayList();
    }
    return this.oneToManyList;
  }

  public void setOneToManyList(List oneToManyList) {
    this.oneToManyList = oneToManyList;
  }

  public String getOrder() {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }
}