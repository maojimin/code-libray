package org.sevenstar.persistent.db.find.xml;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.find.IFind;
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
import org.sevenstar.util.XmlHelper;

public class XmlFind
  implements IFind
{
  private static Log LOG = LogFactory.getLog(XmlFind.class);
  private static Map domainMap = new HashMap();
  private Map paramMap;

  public Domain find(Class clazz)
  {
    String path = clazz.getName();
    if (path.indexOf("$$EnhancerByCGLIB$$") != -1) {
      path = path.substring(0, path.indexOf("$$EnhancerByCGLIB$$"));
    }
    path = path.replaceAll("\\.", "/");
    Document doc = null;
    try {
      doc = XmlHelper.readByClassPath(path + ".xml");
    } catch (Exception e) {
      return null;
    }
    if (doc == null) {
      doc = XmlHelper.readByClassPath(path + ".XML");
      if (doc == null) {
        return null;
      }
    }
    Element root = null;
    try {
      root = (Element)doc.selectObject("/domain");
    } catch (Exception e) {
      LOG.error(e);
      return null;
    }
    Domain domain = (Domain)XmlHelper.initialize(Domain.class, root);
    String className = clazz.getName();
    if (className.indexOf("$$EnhancerByCGLIB$$") != -1) {
      className = className.substring(0, 
        className.indexOf("$$EnhancerByCGLIB$$"));
    }
    if ((domain.getClassName() == null) || ("".equals(domain.getClassName())) || 
      (!className.equals(domain.getClassName()))) {
      throw new ConfigureException(clazz.getName() + 
        " className configure error");
    }
    if (root.attribute("table") == null) {
      throw new ConfigureException(clazz.getName() + 
        " has't configure table");
    }
    Table table = new Table();
    table.setName(root.attribute("table").getValue());
    domain.setTable(table);
    List eleList = root.elements();
    for (int i = 0; i < eleList.size(); i++) {
      Element ele = (Element)eleList.get(i);
      if ("cache".equals(ele.getName())) {
        CacheModel cacheModel = (CacheModel)XmlHelper.initialize(
          CacheModel.class, ele);
        cacheModel.setSimpleClassName(BeanHelper.getSimpleName(clazz));
        domain.setCacheModel(cacheModel);
      }
      if ("id".equals(ele.getName())) {
        Id id = (Id)XmlHelper.initialize(Id.class, ele);

        List idEleList = ele.elements();
        if (idEleList.size() == 0) {
          throw new ConfigureException(clazz.getName() + 
            " has't configure id property");
        }
        for (int j = 0; j < idEleList.size(); j++) {
          Element idPropertyEle = (Element)idEleList.get(j);
          Property property = (Property)XmlHelper.initialize(
            Property.class, idPropertyEle);
          property.setDomain(domain);

          if (!BeanHelper.hasField(BeanHelper.loadClass(className), 
            property.getName())) {
            throw new ConfigureException(clazz.getName() + 
              " has't property[" + property.getName() + "]");
          }

          if (idPropertyEle.elements().size() > 0) {
            Column column = (Column)XmlHelper.initialize(
              Column.class, 
              (Element)idPropertyEle.elements().get(0));
            property.setColumn(column);
          }
          id.addProperty(property);
        }
        domain.setId(id);
      }
      if ("property".equals(ele.getName())) {
        Property property = (Property)XmlHelper.initialize(
          Property.class, ele);

        if (!BeanHelper.hasField(BeanHelper.loadClass(className), 
          property.getName())) {
          throw new ConfigureException(clazz.getName() + 
            " has't property[" + property.getName() + "]");
        }

        if (ele.elements().size() > 0) {
          Column column = (Column)XmlHelper.initialize(Column.class, 
            (Element)ele.elements().get(0));
          property.setColumn(column);
        }
        if ("".equals(property.getValue())) {
          property.setValue(null);
        }
        if ("".equals(property.getDefaultValue())) {
          property.setDefaultValue(null);
        }
        property.setDomain(domain);
        domain.addProperty(property);
      }
      if ("one-to-many".equals(ele.getName())) {
        OneToMany oneToMany = (OneToMany)XmlHelper.initialize(
          OneToMany.class, ele);

        if (!BeanHelper.hasField(BeanHelper.loadClass(className), 
          oneToMany.getName())) {
          throw new ConfigureException(clazz.getName() + 
            " has't property[" + oneToMany.getName() + "]");
        }

        if (ele.elements().size() > 0) {
          Column column = (Column)XmlHelper.initialize(Column.class, 
            (Element)ele.elements().get(0));
          oneToMany.setColumn(column);
        }
        oneToMany.setDomain(domain);
        domain.addOneToMany(oneToMany);
      }
      if ("one-to-one".equals(ele.getName())) {
        OneToOne oneToOne = (OneToOne)XmlHelper.initialize(
          OneToOne.class, ele);

        if (!BeanHelper.hasField(BeanHelper.loadClass(className), 
          oneToOne.getName())) {
          throw new ConfigureException(clazz.getName() + 
            " has't property[" + oneToOne.getName() + "]");
        }

        if (ele.elements().size() > 0) {
          Column column = (Column)XmlHelper.initialize(Column.class, 
            (Element)ele.elements().get(0));
          oneToOne.setColumn(column);
        }
        oneToOne.setDomain(domain);
        domain.addOneToOne(oneToOne);
      }
      if ("many-to-one".equals(ele.getName())) {
        ManyToOne manyToOne = (ManyToOne)XmlHelper.initialize(
          ManyToOne.class, ele);
        if (!BeanHelper.hasField(BeanHelper.loadClass(className), 
          manyToOne.getName())) {
          throw new ConfigureException(clazz.getName() + 
            " has't property[" + manyToOne.getName() + "]");
        }
        if (ele.elements().size() > 0) {
          Column column = (Column)XmlHelper.initialize(Column.class, 
            (Element)ele.elements().get(0));
          manyToOne.setColumn(column);
        }
        manyToOne.setDomain(domain);
        domain.addManyToOne(manyToOne);
      }
    }

    Field[] fields = BeanHelper.getFields(clazz);
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];

      if ((field.getModifiers() == 8) || 
        (!BeanHelper.isPrimitiveType(field.getType()))) continue;
      Property property = null;
      if (domain.hasProperty(field.getName())) {
        property = domain.getProperty(field.getName());
      } else {
        property = new Property();
        domain.addProperty(property);
      }
      property.setDomain(domain);
      property.setName(field.getName());
      if ((property.getClassName() == null) || 
        ("".equals(property.getClassName()))) {
        property.setClassName(field.getType().getName());
      }
      if (property.getColumn() == null) {
        Column column = new Column();
        column.setName(field.getName());
        property.setColumn(column);
      }
      if (property.isDisuse()) {
        domain.deleteProperty(property.getName());
      }

    }

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