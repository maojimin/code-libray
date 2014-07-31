package org.sevenstar.persistent.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sevenstar.component.lazy.LazyHelper;
import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.exception.PersistentException;
import org.sevenstar.persistent.db.ibatis.IbatisDao;
import org.sevenstar.persistent.db.ibatis.IbatisPage;
import org.sevenstar.persistent.db.ibatis.MyAppSqlConfig;
import org.sevenstar.persistent.db.id.IDGeneratorFactory;
import org.sevenstar.persistent.db.model.Domain;
import org.sevenstar.util.BeanHelper;
import org.sevenstar.util.OgnlHelper;

public class PersistentFactory
{
  public static void insert(Object object)
  {
    insert(object, "sqlmapconfig.xml");
  }

  public static void insert(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    insert(domain, object, sqlmapfile);
  }

  public static void insert(Domain domain, Object object, String sqlmapfile) {
    if (!domain.isInsert()) {
      Throwable e = new Throwable();
      throw new PersistentException("persistent class[" + 
        domain.getClassName() + "],table[" + domain.getTable() + 
        "] insert permit", e);
    }
    if ((domain.getId() != null) && (domain.getId().getFirstProperty() != null) && 
      (domain.getId().getFirstProperty().getName() != null) && 
      (domain.getId().getGenerateType() != null) && 
      (!"".equals(domain.getId().getGenerateType()))) {
      Map map = new HashMap();
      map.put("class", getRealClass(object));
      Serializable idValue = IDGeneratorFactory.generate(domain.getId()
        .getGenerateType(), map);
      if (idValue != null) {
        OgnlHelper.setValue(object, 
          domain.getId().getFirstProperty().getName(), idValue);
      }
    }
    IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).update(
      domain.getSimpleClassName() + "_base_insert", object);
  }

  public static void delete(Object object) {
    delete(object, "sqlmapconfig.xml");
  }

  public static void delete(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    delete(domain, object, sqlmapfile);
  }

  public static void delete(Domain domain, Object object, String sqlmapfile) {
    if (!domain.isDelete()) {
      Throwable e = new Throwable();
      throw new PersistentException("persistent class[" + 
        domain.getClassName() + "],table[" + domain.getTable() + 
        "] delete permit", e);
    }
    IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).update(
      domain.getSimpleClassName() + "_base_delete", object);
  }

  public static void deleteEqual(Object object) {
    deleteEqual(object, "sqlmapconfig.xml");
  }

  public static void deleteEqual(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    deleteEqual(domain, object, sqlmapfile);
  }

  public static void deleteEqual(Domain domain, Object object, String sqlmapfile)
  {
    if (!domain.isDelete()) {
      Throwable e = new Throwable();
      throw new PersistentException("persistent class[" + 
        domain.getClassName() + "],table[" + domain.getTable() + 
        "] delete permit", e);
    }
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).update(
      domain.getSimpleClassName() + "_base_delete_equal", object);
  }

  public static void update(Object object) {
    update(object, "sqlmapconfig.xml");
  }

  public static void updateNull(Object object) {
    updateNull(object, "sqlmapconfig.xml");
  }

  public static void update(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    update(domain, object, sqlmapfile);
  }

  public static void updateNull(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    updateNull(domain, object, sqlmapfile);
  }

  public static void update(Domain domain, Object object, String sqlmapfile) {
    if (domain == null) {
      Throwable e = new Throwable();
      throw new PersistentException("persistent class[" + 
        domain.getClassName() + "],table[" + domain.getTable() + 
        "] hasn't find configure", e);
    }
    if (!domain.isUpdate()) {
      Throwable e = new Throwable();
      throw new PersistentException("persistent class[" + 
        domain.getClassName() + "],table[" + domain.getTable() + 
        "] update permit", e);
    }
    IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).update(
      domain.getSimpleClassName() + "_base_update", object);
  }

  public static void updateNull(Domain domain, Object object, String sqlmapfile)
  {
    if (!domain.isUpdate()) {
      Throwable e = new Throwable();
      throw new PersistentException("persistent class[" + 
        domain.getClassName() + "],table[" + domain.getTable() + 
        "] update permit", e);
    }
    IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).update(
      domain.getSimpleClassName() + "_base_update_null", object);
  }

  public static void insertOrUpdate(Object object) {
    insertOrUpdate(object, "sqlmapconfig.xml");
  }

  public static void insertOrUpdate(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    insertOrUpdate(domain, object, sqlmapfile);
  }

  public static void insertOrUpdate(Domain domain, Object object, String sqlmapfile)
  {
    if ((domain.getId() != null) && (domain.getId().getFirstProperty() != null)) {
      if (BeanHelper.getPropertyValue(domain.getId().getFirstProperty()
        .getName(), object) != null)
        update(domain, object, sqlmapfile);
      else
        insert(domain, object, sqlmapfile);
    }
    else {
      Throwable e = new Throwable();
      throw new ConfigureException("hasn't config primaryKey for class[" + 
        object.getClass().getName() + "]", e);
    }
  }

  public static Object load(Object object) {
    return load(object, "sqlmapconfig.xml");
  }

  public static Object load(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return load(domain, object, sqlmapfile);
  }

  public static Object load(Domain domain, Object object, String sqlmapfile) {
    Object result = IbatisDao.getDao(
      MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).queryForObject(
      domain.getSimpleClassName() + "_base_load", object);
    if (result == null) {
      Throwable e = new Throwable();
      throw new PersistentException("has't data for persistent class[" + 
        domain.getClassName() + 
        "] id[" + 
        domain.getId().getFirstProperty().getName() + 
        "] value[" + 
        BeanHelper.getPropertyValue(domain.getId()
        .getFirstProperty().getName(), object) + "]", e);
    }

    LazyHelper.copy(result, object);
    return result;
  }

  public static int count(Object object)
  {
    return count(object, "sqlmapconfig.xml");
  }

  public static int count(Object object, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return count(domain, object, "sqlmapconfig.xml");
  }

  public static int count(Domain domain, Object object, String sqlmapfile) {
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .count(domain.getSimpleClassName() + "_base_page_select_count", 
      object);
  }

  public static int countEqual(Object object) {
    return countEqual(object, "sqlmapconfig.xml");
  }

  public static int countEqual(Object object, String sqlmapfile) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return countEqual(domain, object, 
      "sqlmapconfig.xml");
  }

  public static int countEqual(Domain domain, Object object, String sqlmapfile)
  {
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .count(
      domain.getSimpleClassName() + 
      "_base_select_equal_count", object);
  }

  public static boolean exist(Object object) {
    return exist(object, "sqlmapconfig.xml");
  }

  public static boolean updateExist(Object object, Serializable id) {
    return updateExist(object, id, "sqlmapconfig.xml");
  }

  public static boolean exist(Object object, String sqlmapfile) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return exist(domain, object, sqlmapfile);
  }

  public static boolean updateExist(Object object, Serializable id, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return updateExist(domain, object, id, sqlmapfile);
  }

  public static boolean exist(Domain domain, Object object, String sqlmapfile) {
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    int size = IbatisDao.getDao(
      MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).count(
      domain.getSimpleClassName() + "_base_select_equal_count", 
      object);
    return size > 0;
  }

  public static boolean updateExist(Domain domain, Object object, Serializable id, String sqlmapfile)
  {
    if ((id == null) || ("".equals(id))) {
      Throwable e = new Throwable();
      throw new PersistentException("the id of class[" + 
        domain.getClassName() + "] is empty", e);
    }
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    int size = countEqual(domain, object, sqlmapfile);
    if (size == 0) {
      return false;
    }
    if (size > 500) {
      throw new PersistentException("updateExist return too many result[" + 
        size + "]");
    }
    List list = IbatisDao.getDao(
      MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).queryForList(
      domain.getSimpleClassName() + "_base_select_equal", object);
    if (list.size() > 0) {
      if ((domain.getId() == null) || 
        (domain.getId().getFirstProperty() == null) || 
        (domain.getId().getFirstProperty().getName() == null)) {
        return false;
      }
      String idPropertyName = domain.getId().getFirstProperty().getName();
      for (int i = 0; i < list.size(); i++) {
        if (list.get(i) == null) {
          continue;
        }
        if (!id.equals(
          BeanHelper.getPropertyValue(idPropertyName, 
          list.get(i)))) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  public static Object loadEqual(Object object)
  {
    return loadEqual(object, "sqlmapconfig.xml");
  }

  public static Object loadEqual(Object object, String sqlmapfile) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return loadEqual(domain, object, sqlmapfile);
  }

  public static Object loadEqual(Domain domain, Object object, String sqlmapfile)
  {
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    List list = IbatisDao.getDao(
      MyAppSqlConfig.getSqlMapInstance(sqlmapfile)).queryForList(
      domain.getSimpleClassName() + "_base_select_equal", object);
    if (list.size() > 0) {
      if (list.size() > 1) {
        Throwable e = new Throwable();
        throw new PersistentException("return more than one row", e);
      }
      Object obj = list.get(0);

      LazyHelper.copy(obj, object);
      return obj;
    }
    Throwable e = new Throwable();
    throw new PersistentException("has't find data", e);
  }

  public static List select(Object object)
  {
    return select(object, "sqlmapconfig.xml");
  }

  public static List select(Object object, int maxsize) {
    return select(object, "sqlmapconfig.xml", maxsize);
  }

  public static List select(Object object, String sqlmapfile) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return select(domain, object, sqlmapfile);
  }

  public static List select(Object object, String sqlmapfile, int maxsize) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return select(domain, object, sqlmapfile, maxsize);
  }

  public static List select(Domain domain, Object object, String sqlmapfile) {
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .queryForList(domain.getSimpleClassName() + "_base_select", 
      object);
  }

  public static List select(Domain domain, Object object, String sqlmapfile, int maxsize) {
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }

    ((PersistentObject)object).setLastrownum(maxsize);

    ((PersistentObject)object).setToprow(maxsize);
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .queryForList(domain.getSimpleClassName() + "_base_select_maxsize", 
      object);
  }

  public static List selectEqual(Object object) {
    return selectEqual(object, "sqlmapconfig.xml");
  }

  public static List selectEqual(Object object, int maxsize) {
    return selectEqual(object, "sqlmapconfig.xml", 
      maxsize);
  }

  public static List selectEqual(Object object, String sqlmapfile) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return selectEqual(domain, object, sqlmapfile);
  }

  public static List selectEqual(Object object, String sqlmapfile, int maxsize) {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return selectEqual(domain, object, sqlmapfile, maxsize);
  }

  public static List selectEqual(Domain domain, Object object, String sqlmapfile)
  {
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .queryForList(
      domain.getSimpleClassName() + "_base_select_equal", 
      object);
  }

  public static List selectEqual(Domain domain, Object object, String sqlmapfile, int maxsize)
  {
    if (BeanHelper.isEmpty(object, 
      "firstrownum,lastrownum,offset,span,order,sort")) {
      Throwable e = new Throwable();
      throw new PersistentException("the property of class[" + 
        domain.getClassName() + "] is all empty", e);
    }
    if (!(object instanceof PersistentObject)) {
      Throwable e = new Throwable();
      throw new PersistentException(
        "object [" + object.getClass().getName() + 
        "] is not PersistentObject", e);
    }

    ((PersistentObject)object).setLastrownum(maxsize);

    ((PersistentObject)object).setToprow(maxsize);
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .queryForList(
      domain.getSimpleClassName() + 
      "_base_select_equal_maxsize", object);
  }

  public static Object selectEqualSingle(Object object) {
    List list = selectEqual(object, 1);
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  public static Object selectEqualSingle(Object object, String sqlmapfile) {
    List list = selectEqual(object, sqlmapfile, 1);
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  public static Object selectSingle(Object object) {
    List list = select(object, 1);
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  public static Object selectSingle(Object object, String sqlmapfile) {
    List list = select(object, sqlmapfile, 1);
    if (list.size() == 0) {
      return null;
    }
    return list.get(0);
  }

  public static List selectAll(Class klass)
  {
    String className = klass.getName();
    if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
      className = className.substring(0, className.indexOf("$$EnhancerByCGLIB$$"));
      klass = BeanHelper.loadClass(className);
    }
    Domain domain = SdbConfigure.getFind(klass).find(klass);
    return selectAll(domain, "sqlmapconfig.xml");
  }

  public static List selectAll(Class klass, String sqlmapfile) {
    String className = klass.getName();
    if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
      className = className.substring(0, className.indexOf("$$EnhancerByCGLIB$$"));
      klass = BeanHelper.loadClass(className);
    }
    Domain domain = SdbConfigure.getFind(klass).find(klass);
    return selectAll(domain, sqlmapfile);
  }

  public static List selectAll(Domain domain, String sqlmapfile) {
    return IbatisDao.getDao(MyAppSqlConfig.getSqlMapInstance(sqlmapfile))
      .queryForList(domain.getSimpleClassName() + "_base_selectall", 
      null);
  }

  public static IbatisPage getSelectPage(Object object, IbatisPage page) {
    return getSelectPage(object, page, 
      "sqlmapconfig.xml");
  }

  public static IbatisPage getSelectPage(Object object, IbatisPage page, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return getSelectPage(domain, object, page, sqlmapfile);
  }

  public static IbatisPage getSelectPage(Domain domain, Object object, IbatisPage page, String sqlmapfile)
  {
    if (page == null) {
      page = new IbatisPage();
    }
    page
      .setQuerySqlMapId(domain.getSimpleClassName() + 
      "_base_page_select");
    page.setCountSqlMapId(domain.getSimpleClassName() + 
      "_base_page_select_count");
    page.setParamClazz(object.getClass());
    page.getPageDataList(object, sqlmapfile);
    return page;
  }

  public static List queryPageList(Object object, IbatisPage page) {
    return queryPageList(object, page, 
      "sqlmapconfig.xml");
  }

  public static List queryPageList(Object object, IbatisPage page, String sqlmapfile)
  {
    Domain domain = SdbConfigure.getFind(getRealClass(object)).find(
      getRealClass(object));
    return queryPageList(domain, object, page, sqlmapfile);
  }

  public static List queryPageList(Domain domain, Object object, IbatisPage page, String sqlmapfile)
  {
    page = getSelectPage(domain, object, page, sqlmapfile);
    return page.getPageDataList(object, sqlmapfile);
  }
  private static Class getRealClass(Object object) {
    Class klass = object.getClass();
    String className = object.getClass().getName();
    if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
      className = className.substring(0, className.indexOf("$$EnhancerByCGLIB$$"));
      klass = BeanHelper.loadClass(className);
    }
    return klass;
  }
}