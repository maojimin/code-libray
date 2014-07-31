package org.sevenstar.persistent.db.find.annotation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.find.IFind;
import org.sevenstar.persistent.db.find.annotation.model.SSCacheModel;
import org.sevenstar.persistent.db.find.annotation.model.SSDomain;
import org.sevenstar.persistent.db.find.annotation.model.SSId;
import org.sevenstar.persistent.db.find.annotation.model.SSManyToOne;
import org.sevenstar.persistent.db.find.annotation.model.SSOneToMany;
import org.sevenstar.persistent.db.find.annotation.model.SSOneToOne;
import org.sevenstar.persistent.db.find.annotation.model.SSPrimaryKey;
import org.sevenstar.persistent.db.find.annotation.model.SSProperty;
import org.sevenstar.persistent.db.model.CacheModel;
import org.sevenstar.persistent.db.model.Domain;
import org.sevenstar.persistent.db.model.Id;
import org.sevenstar.persistent.db.model.ManyToOne;
import org.sevenstar.persistent.db.model.OneToMany;
import org.sevenstar.persistent.db.model.OneToOne;
import org.sevenstar.persistent.db.model.Property;
import org.sevenstar.persistent.db.model.db.Column;
import org.sevenstar.persistent.db.model.db.Table;
import org.sevenstar.util.BeanHelper;

public class AnnotationFind
  implements IFind
{
  private static Log LOG = LogFactory.getLog(AnnotationFind.class);
  private static Map domainMap = new HashMap();
  private Map paramMap;

  public Domain find(Class clazz)
  {
    Domain domain = new Domain();
    String className = clazz.getName();
    if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
      className = className.substring(0, 
        className.indexOf("$$EnhancerByCGLIB$$"));
    }
    domain.setClassName(className);
    if (!clazz.isAnnotationPresent(SSDomain.class))
    {
      LOG.debug(className + " hasn't annotation SSDomain");
      return null;
    }
    if (domainMap.containsKey(className)) {
      return (Domain)domainMap.get(className);
    }
    /**
	 * 类定义
	 */
    SSDomain ssDomain = (SSDomain)clazz.getAnnotation(SSDomain.class);
    domain.setCondition(ssDomain.condition());
    domain.setDeleteSql(ssDomain.deleteSql());
    domain.setInsertSql(ssDomain.insertSql());
    domain.setLoadSql(ssDomain.loadSql());
    domain.setOrder(ssDomain.order());
    domain.setSelect_type_string(ssDomain.select_type_string());
    Table table = new Table();
    table.setName(ssDomain.table());
    domain.setTable(table);
    domain.setUpdateSql(ssDomain.updateSql());
    /**
	 * 主键生成方式
	 */
    Id id = new Id();
    if (clazz.isAnnotationPresent(SSId.class)) {
      SSId ssId = (SSId)clazz.getAnnotation(SSId.class);
      id.setGenerateType(ssId.generateType());
      id.setPrimaryKeyParameterType(ssId.primaryKeyParameterType());
      id.setSeq(ssId.seq());
    }
    domain.setId(id);
    /**
	 * cache
	 */
    if (clazz.isAnnotationPresent(SSCacheModel.class)) {
      SSCacheModel ssCacheModel = 
        (SSCacheModel)clazz
        .getAnnotation(SSCacheModel.class);
      CacheModel cacheModel = new CacheModel();
      cacheModel.setCachesize(String.valueOf(ssCacheModel.cachesize()));
      cacheModel.setFlushinterval(ssCacheModel.flushinterval());
      cacheModel.setFlushonexecute(ssCacheModel.flushonexecute());
      cacheModel.setReadonly(ssCacheModel.readonly());
      cacheModel.setSelectAll(ssCacheModel.selectAll());
      cacheModel.setSerialize(ssCacheModel.serialize());
      cacheModel.setSimpleClassName(clazz.getSimpleName());
      cacheModel.setType(ssCacheModel.type());
      domain.setCacheModel(cacheModel);
    }
    /**
	 * 属性
	 */
    Field[] fields = BeanHelper.getFields(clazz);
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];

      if (field.getModifiers() != 8) {
        if (BeanHelper.isPrimitiveType(field.getType())) {
          Property property = new Property();
          property.setClassName(field.getType().getName());
          property.setName(field.getName());
          property.setDomain(domain);
          boolean isPk = false;
          if (field.isAnnotationPresent(SSPrimaryKey.class)) {
            isPk = true;
          }
          if (field.isAnnotationPresent(SSProperty.class)) {
            SSProperty ssProperty =  (SSProperty)field.getAnnotation(SSProperty.class);
            if ((ssProperty.defaultValue() != null) && (!"".equals(ssProperty.defaultValue()))) {
              property.setDefaultValue(ssProperty.defaultValue());
            }
            property.setInsert(ssProperty.insert());
            property.setJdbctype(ssProperty.jdbctype());
            property.setParameter(ssProperty.parameter());
            property.setSelect_max_property( ssProperty.select_max_property());
            property.setSelect_min_property( ssProperty.select_min_property());
            property.setSelect_prepend(ssProperty.select_prepend());
            property.setSelect_property( ssProperty.select_property());
            property.setSelect_sql(ssProperty.select_sql());
            property.setSelect_type(ssProperty.select_type());
            property.setUpdate(ssProperty.update());
            property.setUpdateNull(ssProperty.updateNull());
            
            ///此处有不同
            if ((ssProperty.value() != null) && (!"".equals(ssProperty.value()))) {
              property.setValue(ssProperty.value());
            }
            
            
            property.setDisuse(ssProperty.disuse());
            Column column = new Column();
            column.setName(ssProperty.column());
            property.setColumn(column);
            if ((property.getColumn().getName() == null) || ("".equals(property.getColumn().getName())))
              column.setName(field.getName());
          } else {
            Column column = new Column();
            column.setName(field.getName());
            property.setColumn(column);
          }
          if (!property.isDisuse())
            if (isPk){
            	id.addProperty(property);
            }else{
            	domain.addProperty(property);
            }
        } else {
          if (field.isAnnotationPresent(SSManyToOne.class)) {
            ManyToOne manyToOne = new ManyToOne();
            manyToOne.setClassName(field.getType().getName());
            manyToOne.setName(field.getName());
            manyToOne.setDomain(domain);
            SSManyToOne ssManyToOne =  (SSManyToOne)field.getAnnotation(SSManyToOne.class);
            Column column = new Column();
            column.setName(ssManyToOne.column());
            manyToOne.setColumn(column);
            manyToOne.setParameter(ssManyToOne.parameter());
            manyToOne.setSelect(ssManyToOne.select());
            manyToOne.setTable(ssManyToOne.table());
            manyToOne.setCondition(ssManyToOne.condition());
            manyToOne.setJdbctype(ssManyToOne.jdbctype());
            manyToOne.setUpdate(ssManyToOne.update());
            manyToOne.setUpdateNull(ssManyToOne.updateNull());
            manyToOne.setInsert(ssManyToOne.insert());
            manyToOne.setJavatype(ssManyToOne.javatype());
            domain.addManyToOne(manyToOne);
          }
          if (field.isAnnotationPresent(SSOneToMany.class)) {
            SSOneToMany ssOneToMany = (SSOneToMany)field.getAnnotation(SSOneToMany.class);
            OneToMany oneToMany = new OneToMany();
            oneToMany.setClassName(ssOneToMany.className());
            Column column = new Column();
            column.setName(ssOneToMany.column());
            oneToMany.setColumn(column);
            oneToMany.setCondition(ssOneToMany.condition());
            oneToMany.setDomain(domain);
            oneToMany.setName(field.getName());
            oneToMany.setOwnColumn(ssOneToMany.ownColumn());
            oneToMany.setSelect(ssOneToMany.select());
            oneToMany.setTable(ssOneToMany.table());
            domain.addOneToMany(oneToMany);
          }
          if (field.isAnnotationPresent(SSOneToOne.class)) {
            SSOneToOne ssOneToOne = (SSOneToOne)field.getAnnotation(SSOneToOne.class);
            OneToOne oneToOne = new OneToOne();
            oneToOne.setClassName(field.getType().getName());
            Column column = new Column();
            column.setName(ssOneToOne.column());
            oneToOne.setColumn(column);
            oneToOne.setCondition(ssOneToOne.condition());
            oneToOne.setDomain(domain);
            oneToOne.setName(field.getName());
            oneToOne.setOwnColumn(ssOneToOne.ownColumn());
            oneToOne.setSelect(ssOneToOne.select());
            oneToOne.setTable(ssOneToOne.table());
            domain.addOneToOne(oneToOne);
          }
        }
      }
    }
    domainMap.put(clazz.getName(), domain);
    return domain;
  }

  public Map getParamMap()
  {
    if (this.paramMap == null) {
      this.paramMap = new HashMap();
    }
    return this.paramMap;
  }

  public void setParamMap(Map map) {
    this.paramMap = map;
  }
}