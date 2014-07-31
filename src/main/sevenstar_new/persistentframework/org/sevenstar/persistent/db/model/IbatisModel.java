package org.sevenstar.persistent.db.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.cfg.model.SdbModel;
import org.sevenstar.persistent.db.dialect.AbstractDialect;
import org.sevenstar.persistent.db.dialect.DialectFactory;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.exception.PersistentException;
import org.sevenstar.persistent.db.model.db.Column;
import org.sevenstar.persistent.db.model.db.Table;

public class IbatisModel
{
  private Domain domain;

  public IbatisModel(Domain domain)
  {
    this.domain = domain;
  }

  private IbatisModel()
  {
  }

  public Domain getDomain() {
    return this.domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }

  public String getSelectHeader() {
    String selectAllColumn = "";
    Map columnMap = new HashMap();
    if (getDomain().getId() != null) {
      List idPropertyList = getDomain().getId().getPropertyList();
      if (idPropertyList == null) {
        idPropertyList = new ArrayList();
      }
      for (int i = 0; i < idPropertyList.size(); i++) {
        Property property = (Property)idPropertyList.get(i);
        if (columnMap.containsKey(
          property.getColumn().getName().toLowerCase())) continue;
        if ("".equals(selectAllColumn))
          selectAllColumn = selectAllColumn + 
            property.getColumn().getName();
        else {
          selectAllColumn = selectAllColumn + "," + 
            property.getColumn().getName();
        }
        columnMap.put(property.getColumn().getName().toLowerCase(), 
          "");
      }
    }

    List propertyList = getDomain().getPropertyList();
    for (int i = 0; i < propertyList.size(); i++) {
      Property property = (Property)propertyList.get(i);
      if (columnMap.containsKey(
        property.getColumn().getName().toLowerCase())) continue;
      if ("".equals(selectAllColumn))
        selectAllColumn = selectAllColumn + 
          property.getColumn().getName();
      else {
        selectAllColumn = selectAllColumn + "," + 
          property.getColumn().getName();
      }
      columnMap.put(property.getColumn().getName().toLowerCase(), "");
    }

    List manyToOneList = getDomain().getManyToOneList();
    for (int i = 0; i < manyToOneList.size(); i++) {
      ManyToOne manyToOne = (ManyToOne)manyToOneList.get(i);
      if (columnMap.containsKey(
        manyToOne.getColumn().getName().toLowerCase()))
        continue;
      if ("".equals(selectAllColumn))
        selectAllColumn = selectAllColumn + 
          manyToOne.getColumn().getName();
      else {
        selectAllColumn = selectAllColumn + "," + 
          manyToOne.getColumn().getName();
      }
      columnMap.put(manyToOne.getColumn().getName().toLowerCase(), "");
    }

    return selectAllColumn;
  }

  public String getSqlDeleteEqual() {
    StringBuffer sb = new StringBuffer();
    sb.append("\r\n\t<![CDATA[ \r\n");
    sb.append("\t\tdelete from " + getDomain().getTable().getName() + 
      " \r\n");
    sb.append("\t]]> \r\n");
    sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
      Property field = 
        (Property)getDomain().getId()
        .getPropertyList().get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
      Property field = (Property)getDomain().getPropertyList().get(
        i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
      ManyToOne field = 
        (ManyToOne)getDomain().getManyToOneList()
        .get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getParameter() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getParameter() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    sb.append("\t</dynamic> \r\n");
    return sb.toString();
  }

  public String getSqlSelectEqualCount() {
    StringBuffer sb = new StringBuffer();
    sb.append("\r\n\t<![CDATA[ \r\n");
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t\tselect count(*) from " + 
        getDomain().getTable().getName() + " \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
      sb.append("\t\tselect count(*) from " + 
        getDomain().getTable().getName() + " where " + 
        getDomain().getCondition() + " \r\n");
      sb.append("\t]]> \r\n");
    }
    for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
      Property field = 
        (Property)getDomain().getId()
        .getPropertyList().get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
      Property field = (Property)getDomain().getPropertyList().get(
        i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
      ManyToOne field = 
        (ManyToOne)getDomain().getManyToOneList()
        .get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getParameter() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getParameter() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t</dynamic> \r\n");
    }

    return sb.toString();
  }

  public String getSqlSelectEqualMaxSize()
  {
    return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getSelectEqualMaxSizeSql(this.domain);
  }

  public String getSqlSelectEqual() {
    StringBuffer sb = new StringBuffer();
    sb.append("\r\n\t<![CDATA[ \r\n");
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t\tselect " + getSelectHeader() + " from " + 
        getDomain().getTable().getName() + " \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
      sb.append("\t\tselect " + getSelectHeader() + " from " + 
        getDomain().getTable().getName() + " where " + 
        getDomain().getCondition() + " \r\n");
      sb.append("\t]]> \r\n");
    }
    for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
      Property field = 
        (Property)getDomain().getId()
        .getPropertyList().get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
      Property field = (Property)getDomain().getPropertyList().get(
        i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
      ManyToOne field = 
        (ManyToOne)getDomain().getManyToOneList()
        .get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getParameter() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getParameter() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t</dynamic> \r\n");
    }
    sb.append("\t" + getOrder() + "\r\n");
    return sb.toString();
  }

  public String getOrder() {
    String order = getDomain().getOrder();
    if (((order == null) || ("".equals(order))) && 
      (getDomain().getId().getPropertyList().size() > 0)) {
      int i = 0;
      for (; i < 
        getDomain().getId().getPropertyList().size(); i++) {
        if (i == 0)
          order = 
            ((Property)getDomain().getId()
            .getPropertyList().get(i)).getColumn()
            .getName() + 
            " desc";
        else {
          order = order + 
            "," + 
            ((Property)getDomain().getId()
            .getPropertyList().get(i)).getColumn()
            .getName() + " desc";
        }
      }
    }

    StringBuffer sb = new StringBuffer();
    sb.append("\t\t <isNotEmpty property=\"order\">\r\n");
    sb.append("\t\t\t order by $order$ $sort$ ");
    if (!"".equals(order))
      sb.append(" , " + order + " \r\n");
    else {
      sb.append("\r\n");
    }
    sb.append("\t\t </isNotEmpty> \r\n");
    if (!"".equals(order)) {
      sb.append("\t\t <isEmpty property=\"order\">\r\n");
      sb.append("\t\t\t order by " + order + " \r\n");
      sb.append("\t\t </isEmpty> \r\n");
    }
    return sb.toString();
  }

  public String getPageSqlSelect() {
    return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getPageSql(this.domain);
  }

  public String getSqlSelectMaxSize() {
    return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getSelectMaxSizeSql(this.domain);
  }

  public String getSqlSelect() {
    StringBuffer sb = new StringBuffer();
    sb.append("\r\n\t<![CDATA[ \r\n");
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t\tselect " + getSelectHeader() + " from " + 
        getDomain().getTable().getName() + " \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
      sb.append("\t\tselect " + getSelectHeader() + " from " + 
        getDomain().getTable().getName() + " where " + 
        getDomain().getCondition() + " \r\n");
      sb.append("\t]]> \r\n");
    }
    for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
      Property field = 
        (Property)getDomain().getId()
        .getPropertyList().get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
      Property field = (Property)getDomain().getPropertyList().get(
        i);
      if ((field.getSelect_sql() != null) && 
        (!"".equals(field.getSelect_sql().trim()))) {
        sb.append("\t<isNotNull prepend=\"" + field.getSelect_prepend() + 
          "\" property=\"" + field.getSelect_property() + 
          "\"> \r\n");
        sb.append("\t<![CDATA[ \r\n");
        sb.append("\t\t" + field.getColumn().getName() + "  " + 
          field.getSelect_sql() + "  \r\n");
        sb.append("\t]]> \r\n");
        sb.append("\t</isNotNull>  \r\n");
      }
      else {
        if (((field.getSelect_max_property() == null) || 
          (""
          .equals(field.getSelect_max_property().trim()))) && (
          (field.getSelect_min_property() == null) || 
          ("".equals(field.getSelect_min_property())))) {
          if ("like".equals(field.getSelect_type()))
          {
            sb.append("\t<isNotEmpty prepend=\"" + 
              field.getSelect_prepend() + "\" property=\"" + 
              field.getSelect_property() + "\"> \r\n");
            sb.append("\t<![CDATA[ \r\n");
            sb.append("\t\t" + field.getColumn().getName() + 
              "  like '%$" + field.getSelect_property() + 
              "$%'  \r\n");
            sb.append("\t]]> \r\n");
            sb.append("\t</isNotEmpty>  \r\n");
          }
          if ("leftlike".equals(field.getSelect_type()))
          {
            sb.append("\t<isNotEmpty prepend=\"" + 
              field.getSelect_prepend() + "\" property=\"" + 
              field.getSelect_property() + "\"> \r\n");
            sb.append("\t<![CDATA[ \r\n");
            sb.append("\t\t" + field.getColumn().getName() + 
              "  like '%$" + field.getSelect_property() + 
              "$'  \r\n");
            sb.append("\t]]> \r\n");
            sb.append("\t</isNotEmpty>  \r\n");
          }
          if ("rightlike".equals(field.getSelect_type()))
          {
            sb.append("\t<isNotEmpty prepend=\"" + 
              field.getSelect_prepend() + "\" property=\"" + 
              field.getSelect_property() + "\"> \r\n");
            sb.append("\t<![CDATA[ \r\n");
            sb.append("\t\t" + field.getColumn().getName() + 
              "  like '$" + field.getSelect_property() + 
              "$%'  \r\n");
            sb.append("\t]]> \r\n");
            sb.append("\t</isNotEmpty>  \r\n");
          }
          if ("equal".equals(field.getSelect_type())) {
            if ("java.lang.String".equals(field.getClassName())) {
              sb.append("\t\t<isNotEmpty prepend=\"" + 
                field.getSelect_prepend() + "\" property=\"" + 
                field.getSelect_property() + "\"> \r\n");
              sb.append("\t<![CDATA[ \r\n");
              sb.append("\t\t\t" + field.getColumn().getName() + 
                "  = #" + field.getSelect_property() + 
                "#  \r\n");
              sb.append("\t]]> \r\n");
              sb.append("\t\t</isNotEmpty>  \r\n");
            }
            else {
              sb.append("\t\t<isNotNull prepend=\"" + 
                field.getSelect_prepend() + "\" property=\"" + 
                field.getSelect_property() + "\"> \r\n");
              sb.append("\t<![CDATA[ \r\n");
              sb.append("\t\t\t" + field.getColumn().getName() + 
                "  = #" + field.getSelect_property() + 
                "#  \r\n");
              sb.append("\t]]> \r\n");
              sb.append("\t\t</isNotNull>  \r\n");
            }
          }
        }
        if ((field.getSelect_min_property() != null) && 
          (!"".equals(field.getSelect_min_property()))) {
          sb.append("\t\t<isNotNull prepend=\"" + 
            field.getSelect_prepend() + "\" property=\"" + 
            field.getSelect_min_property() + "\"> \r\n");
          sb.append("\t<![CDATA[ \r\n");
          sb.append("\t\t\t" + field.getColumn().getName() + "  >= #" + 
            field.getSelect_min_property() + "#  \r\n");
          sb.append("\t]]> \r\n");
          sb.append("\t\t</isNotNull>  \r\n");
        }

        if ((field.getSelect_max_property() == null) || 
          ("".equals(field.getSelect_max_property())))
          continue;
        sb.append("\t\t<isNotNull prepend=\"" + 
          field.getSelect_prepend() + "\" property=\"" + 
          field.getSelect_max_property() + "\"> \r\n");
        sb.append("\t<![CDATA[ \r\n");
        sb.append("\t\t\t" + field.getColumn().getName() + "  <= #" + 
          field.getSelect_max_property() + "#  \r\n");
        sb.append("\t]]> \r\n");
        sb.append("\t\t</isNotNull>  \r\n");
      }
    }
    for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
      ManyToOne field = 
        (ManyToOne)getDomain().getManyToOneList()
        .get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getParameter() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getParameter() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t</dynamic> \r\n");
    }
    sb.append("\t" + getOrder() + "\r\n");
    return sb.toString();
  }

  public List getDeleteParameterList() {
    return getLoadDeleteParameterMap();
  }

  public String getSqlSelectCount() {
    StringBuffer sb = new StringBuffer();
    sb.append("\r\n\t<![CDATA[ \r\n");
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t\t select count(*) from " + 
        getDomain().getTable().getName() + " \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
      sb.append("\t\t select count(*) from " + 
        getDomain().getTable().getName() + " where " + 
        getDomain().getCondition() + " \r\n");
      sb.append("\t]]> \r\n");
    }
    for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
      Property field = 
        (Property)getDomain().getId()
        .getPropertyList().get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
      Property field = (Property)getDomain().getPropertyList().get(
        i);
      if ((field.getSelect_sql() != null) && 
        (!"".equals(field.getSelect_sql()))) {
        sb.append("\t<isNotNull prepend=\"" + field.getSelect_prepend() + 
          "\" property=\"" + field.getSelect_property() + 
          "\"> \r\n");
        sb.append("\t<![CDATA[ \r\n");
        sb.append("\t\t" + field.getColumn().getName() + "  " + 
          field.getSelect_sql() + "  \r\n");
        sb.append("\t]]> \r\n");
        sb.append("\t</isNotNull>  \r\n");
      }
      else {
        if (((field.getSelect_max_property() == null) || 
          (""
          .equals(field.getSelect_max_property().trim()))) && (
          (field.getSelect_min_property() == null) || 
          ("".equals(field.getSelect_min_property())))) {
          if ("like".equals(field.getSelect_type()))
          {
            sb.append("\t<isNotEmpty prepend=\"" + 
              field.getSelect_prepend() + "\" property=\"" + 
              field.getSelect_property() + "\"> \r\n");
            sb.append("\t<![CDATA[ \r\n");
            sb.append("\t\t" + field.getColumn().getName() + 
              "  like '%$" + field.getSelect_property() + 
              "$%'  \r\n");
            sb.append("\t]]> \r\n");
            sb.append("\t</isNotEmpty>  \r\n");
          }
          if ("leftlike".equals(field.getSelect_type()))
          {
            sb.append("\t<isNotEmpty prepend=\"" + 
              field.getSelect_prepend() + "\" property=\"" + 
              field.getSelect_property() + "\"> \r\n");
            sb.append("\t<![CDATA[ \r\n");
            sb.append("\t\t" + field.getColumn().getName() + 
              "  like '%$" + field.getSelect_property() + 
              "$'  \r\n");
            sb.append("\t]]> \r\n");
            sb.append("\t</isNotEmpty>  \r\n");
          }
          if ("rightlike".equals(field.getSelect_type()))
          {
            sb.append("\t<isNotEmpty prepend=\"" + 
              field.getSelect_prepend() + "\" property=\"" + 
              field.getSelect_property() + "\"> \r\n");
            sb.append("\t<![CDATA[ \r\n");
            sb.append("\t\t" + field.getColumn().getName() + 
              "  like '$" + field.getSelect_property() + 
              "$%'  \r\n");
            sb.append("\t]]> \r\n");
            sb.append("\t</isNotEmpty>  \r\n");
          }
          if ("equal".equals(field.getSelect_type())) {
            if ("java.lang.String".equals(field.getClassName())) {
              sb.append("\t\t<isNotEmpty prepend=\"" + 
                field.getSelect_prepend() + "\" property=\"" + 
                field.getSelect_property() + "\"> \r\n");
              sb.append("\t<![CDATA[ \r\n");
              sb.append("\t\t\t" + field.getColumn().getName() + 
                "  = #" + field.getSelect_property() + 
                "#  \r\n");
              sb.append("\t]]> \r\n");
              sb.append("\t\t</isNotEmpty>  \r\n");
            }
            else {
              sb.append("\t\t<isNotNull prepend=\"" + 
                field.getSelect_prepend() + "\" property=\"" + 
                field.getSelect_property() + "\"> \r\n");
              sb.append("\t<![CDATA[ \r\n");
              sb.append("\t\t\t" + field.getColumn().getName() + 
                "  = #" + field.getSelect_property() + 
                "#  \r\n");
              sb.append("\t]]> \r\n");
              sb.append("\t\t</isNotNull>  \r\n");
            }
          }
        }
        if ((field.getSelect_min_property() != null) && 
          (!"".equals(field.getSelect_min_property()))) {
          sb.append("\t\t<isNotNull prepend=\"" + 
            field.getSelect_prepend() + "\" property=\"" + 
            field.getSelect_min_property() + "\"> \r\n");
          sb.append("\t<![CDATA[ \r\n");
          sb.append("\t\t\t" + field.getColumn().getName() + "  >= #" + 
            field.getSelect_min_property() + "#  \r\n");
          sb.append("\t]]> \r\n");
          sb.append("\t\t</isNotNull>  \r\n");
        }

        if ((field.getSelect_max_property() == null) || 
          ("".equals(field.getSelect_max_property()))) continue;
        sb.append("\t\t<isNotNull prepend=\"" + 
          field.getSelect_prepend() + "\" property=\"" + 
          field.getSelect_max_property() + "\"> \r\n");
        sb.append("\t<![CDATA[ \r\n");
        sb.append("\t\t\t" + field.getColumn().getName() + "  <= #" + 
          field.getSelect_max_property() + "#  \r\n");
        sb.append("\t]]> \r\n");
        sb.append("\t\t</isNotNull>  \r\n");
      }
    }
    for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
      ManyToOne field = 
        (ManyToOne)getDomain().getManyToOneList()
        .get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getParameter() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getParameter() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      sb.append("\t</dynamic> \r\n");
    }

    return sb.toString();
  }

  public List getResultList() {
    List list = new ArrayList();
    if ((getDomain().getId().getPropertyList().size() > 0) || 
      (getDomain().getPropertyList().size() > 0) || 
      (getDomain().getManyToOneList().size() > 0) || 
      (getDomain().getOneToManyList().size() > 0) || 
      (getDomain().getOneToOneList().size() > 0)) {
      for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getId()
          .getPropertyList().get(i);
        list.add("<result property =\"" + field.getParameter() + 
          "\"  column=\"" + field.getColumn().getName() + 
          "\" />");
      }
      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        list.add("<result property =\"" + field.getParameter() + 
          "\"  column=\"" + field.getColumn().getName() + 
          "\" />");
      }
      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        list.add("<result property =\"" + field.getName() + 
          "\"  column=\"" + field.getColumn().getName() + 
          "\" select=\"" + field.getSelect() + "\" />");
      }

      for (int i = 0; i < getDomain().getOneToManyList().size(); i++) {
        OneToMany field = 
          (OneToMany)getDomain()
          .getOneToManyList().get(i);
        if (((field.getTable() == null) || ("".equals(field.getTable()))) && 
          (field.getClassName() != null) && 
          (!""
          .equals(field.getClassName()))) {
          String select = field.getClassName()
            .substring(field.getClassName().lastIndexOf(".") + 1) + 
            "_base_select_" + field.getColumn().getName();
          list.add("<result property =\"" + field.getName() + 
            "\"  column=\"" + field.getOwnColumn() + 
            "\" select=\"" + select + "\" />");
        } else {
          list.add("<result property =\"" + field.getName() + 
            "\"  column=\"" + field.getOwnColumn() + 
            "\" select=\"" + field.getSelect() + "\" />");
        }
      }

      for (int i = 0; i < getDomain().getOneToOneList().size(); i++) {
        OneToOne field = 
          (OneToOne)getDomain().getOneToOneList()
          .get(i);
        if (((field.getTable() == null) || ("".equals(field.getTable()))) && 
          (field.getClassName() != null) && 
          (!""
          .equals(field.getClassName()))) {
          String select = field.getClassName()
            .substring(field.getClassName().lastIndexOf(".") + 1) + 
            "_base_select_" + field.getColumn().getName();
          list.add("<result property =\"" + field.getName() + 
            "\"  column=\"" + field.getOwnColumn() + 
            "\" select=\"" + select + "\" />");
        } else {
          list.add("<result property =\"" + field.getName() + 
            "\"  column=\"" + field.getOwnColumn() + 
            "\" select=\"" + field.getSelect() + "\" />");
        }
      }
    }

    return list;
  }

  public String getSqlSelectAll() {
    if ((getDomain().getCondition() == null) || 
      ("".equals(getDomain().getCondition()))) {
      return "  select " + getSelectHeader() + " from " + 
        getDomain().getTable().getName() + " " + 
        getOrder();
    }
    return "select " + getSelectHeader() + " from " + 
      getDomain().getTable().getName() + 
      " where <![CDATA[ " + getDomain().getCondition() + 
      " ]]> " + getOrder();
  }

  public String getSqlSeq()
  {
    if ((getDomain().getId() != null) && 
      ("seq".equals(
      getDomain().getId().getGenerateType()))) {
      return DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getSequenceSql(getDomain());
    }
    return "";
  }

  public List getLoadParameterMap() {
    return getLoadDeleteParameterMap();
  }

  public String getSqlLoad() {
    StringBuffer sb = new StringBuffer();
    sb.append(" select " + getSelectHeader() + " from " + 
      getDomain().getTable().getName() + " where ");
    addWhereByPrimaryKey(sb);
    return sb.toString();
  }

  private List getLoadDeleteParameterMap()
  {
    List list = new ArrayList();
    if (getDomain().getId() != null) {
      if (getDomain().getId().isCompositeId()) {
        List keyFields = getDomain().getId().getPropertyList();
        for (int i = 0; i < keyFields.size(); i++) {
          Property keyfield = (Property)keyFields.get(i);
          list.add("<parameter property=\"" + keyfield.getParameter() + 
            "\" jdbcType=\"" + keyfield.getJdbctype() + 
            "\" javaType=\"" + keyfield.getClassName() + 
            "\"/>");
        }
      } else if (getDomain().getId().getFirstProperty() != null) {
        list.add("<parameter property=\"" + 
          getDomain().getId().getFirstProperty()
          .getParameter() + 
          "\" jdbcType=\"" + 
          getDomain().getId().getFirstProperty()
          .getJdbctype() + 
          "\" javaType=\"" + 
          getDomain().getId().getFirstProperty()
          .getClassName() + "\"/>");
      }
    }

    return list;
  }

  public String getSqlDelete() {
    if (getDomain().getId() != null) {
      StringBuffer sb = new StringBuffer();
      sb.append(" delete from " + getDomain().getTable().getName() + 
        " where ");
      if (getDomain().getId().isCompositeId()) {
        List keyFields = getDomain().getId().getPropertyList();
        for (int i = 0; i < keyFields.size(); i++) {
          sb.append(
            ((Property)keyFields.get(i)).getColumn().getName() + 
            "=?");
          if (i < keyFields.size() - 1) {
            sb.append(" and ");
          }
        }
      }
      else if (getDomain().getId().getFirstProperty() != null) {
        sb.append(
          getDomain().getId().getFirstProperty().getColumn().getName() + 
          "=?");
      }

      return sb.toString();
    }
    return "";
  }

  public List getUpdateParameterList() {
    List list = new ArrayList();
    if ((getDomain().getId() == null) || 
      (getDomain().getId().getPropertyList() == null) || 
      (getDomain().getId().getPropertyList().size() == 0)) {
      throw new PersistentException("domain[" + 
        getDomain().getClassName() + 
        "] hasn't set primarykey");
    }
    if ((getDomain().getPropertyList().size() > 0) || 
      (getDomain().getManyToOneList().size() > 0)) {
      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        if (field.isUpdate()) {
          list.add("<parameter property=\"" + field.getParameter() + 
            "\" jdbcType=\"" + field.getJdbctype() + 
            "\" javaType=\"" + field.getClassName() + "\"/>");
        }
      }
      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        list.add("<parameter property=\"" + field.getParameter() + 
          "\" jdbcType=\"" + field.getJdbctype() + 
          "\" javaType=\"" + field.getJavatype() + "\"/>");
      }
    }
    if (getDomain().getId() != null) {
      if (getDomain().getId().isCompositeId()) {
        List keyFields = getDomain().getId().getPropertyList();
        for (int i = 0; i < keyFields.size(); i++)
        {
          Property keyfield = (Property)keyFields.get(i);
          list.add("<parameter property=\"" + keyfield.getParameter() + 
            "\" jdbcType=\"" + keyfield.getJdbctype() + 
            "\" javaType=\"" + keyfield.getClassName() + 
            "\"/>");
        }
      }
      else if (getDomain().getId().getFirstProperty() != null) {
        list.add("<parameter property=\"" + 
          getDomain().getId().getFirstProperty()
          .getParameter() + 
          "\" jdbcType=\"" + 
          getDomain().getId().getFirstProperty()
          .getJdbctype() + 
          "\" javaType=\"" + 
          getDomain().getId().getFirstProperty()
          .getClassName() + "\"/>");
      }
    }

    return list;
  }

  public String getSqlUpdateNull()
  {
    if ((getDomain().getPropertyList().size() > 0) || 
      (getDomain().getManyToOneList().size() > 0)) {
      StringBuffer sb = new StringBuffer();
      sb.append("update " + getDomain().getTable().getName() + " set ");
      for (int i = 0; i < 
        getDomain().getId().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getId()
          .getPropertyList().get(i);
        if (sb.toString().indexOf("=") != -1)
          sb.append("," + field.getColumn().getName() + "=#" + 
            field.getParameter() + "#");
        else {
          sb.append(field.getColumn().getName() + "=#" + 
            field.getParameter() + "#");
        }
      }
      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        if (field.isUpdate()) {
          if (field.getValue() != null) {
            if (("java.lang.String".equals(field.getClassName())) || 
              ("String".equals(field.getClassName())))
              sb.append("\r\n\t ," + field.getColumn().getName() + 
                "='" + field.getValue() + "'");
            else
              sb.append("\r\n\t ," + field.getColumn().getName() + 
                "=" + field.getValue());
          }
          else {
            sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
              field.getParameter() + "\">");
            sb.append("\r\n\t" + field.getColumn().getName() + "=#" + 
              field.getParameter() + "#");
            sb.append("\r\n</isNotNull>");
            if (field.getDefaultValue() != null) {
              sb.append("\r\n<isNull prepend=\",\" property=\"" + 
                field.getParameter() + "\">");
              if (("java.lang.String".equals(field.getClassName())) || 
                ("String".equals(field.getClassName())))
                sb.append("\r\n\t" + 
                  field.getColumn().getName() + "='" + 
                  field.getDefaultValue() + "'");
              else {
                sb.append("\r\n\t" + 
                  field.getColumn().getName() + "=" + 
                  field.getDefaultValue());
              }
              sb.append("\r\n</isNull>");
            } else {
              sb.append("\r\n<isNull prepend=\",\" property=\"" + 
                field.getParameter() + "\">");
              sb.append("\r\n\t" + field.getColumn().getName() + 
                "=null");
              sb.append("\r\n</isNull>");
            }
          }
        }
      }
      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        if (field.isUpdate()) {
          sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
            field.getParameter() + "\">");
          sb.append("\r\n\t" + field.getColumn().getName() + "=#" + 
            field.getParameter() + "#");
          sb.append("\r\n</isNotNull>");

          sb.append("\r\n<isNull prepend=\",\" property=\"" + 
            field.getParameter() + "\">");
          sb.append("\r\n\t" + field.getColumn().getName() + "=null");
          sb.append("\r\n</isNull>");
        }
      }

      sb.append(" where ");
      if (getDomain().getId().isCompositeId()) {
        List keyFields = getDomain().getId().getPropertyList();
        for (int i = 0; i < keyFields.size(); i++) {
          sb.append(
            ((Property)keyFields.get(i)).getColumn().getName() + 
            "=#" + 
            ((Property)keyFields.get(i)).getParameter() + 
            "#");
          if (i < keyFields.size() - 1) {
            sb.append(" and ");
          }
        }
      }
      else if (getDomain().getId().getFirstProperty() != null) {
        sb.append(
          getDomain().getId().getFirstProperty().getColumn().getName() + 
          "=#" + 
          getDomain().getId().getFirstProperty()
          .getParameter() + "#");
      } else {
        throw new ConfigureException("domain[" + 
          getDomain().getClassName() + 
          "] has't config  primarkKey");
      }

      return sb.toString();
    }
    throw new ConfigureException("has't property in domain[" + 
      getDomain().getClassName() + "] ");
  }

  public String getSqlUpdate()
  {
    if ((getDomain().getPropertyList().size() > 0) || 
      (getDomain().getManyToOneList().size() > 0)) {
      StringBuffer sb = new StringBuffer();
      sb.append("update " + getDomain().getTable().getName() + 
        " set ");
      
      for (int i = 0; i < 
        getDomain().getId().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getId()
          .getPropertyList().get(i);
        if (sb.toString().indexOf("=") != -1)
          sb.append("," + field.getColumn().getName() + "=#" + 
            field.getParameter() + "#");
        else {
          sb.append(field.getColumn().getName() + "=#" + 
            field.getParameter() + "#");
        }
      }

      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        if (field.isUpdate()) {
          if (field.getValue() != null) {
            if (("java.lang.String".equals(field.getClassName())) || 
              ("String".equals(field.getClassName())))
              sb.append("\r\n\t ," + field.getColumn().getName() + 
                "='" + field.getValue() + "'");
            else
              sb.append("\r\n\t ," + field.getColumn().getName() + 
                "=" + field.getValue());
          }
          else {
            sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
              field.getParameter() + "\">");
            sb.append("\r\n\t" + field.getColumn().getName() + "=#" + 
              field.getParameter() + "#");
            sb.append("\r\n</isNotNull>");
            if (field.getDefaultValue() != null) {
              sb.append("\r\n<isNull prepend=\",\" property=\"" + 
                field.getParameter() + "\">");
              if (("java.lang.String".equals(field.getClassName())) || 
                ("String".equals(field.getClassName())))
                sb.append("\r\n\t" + 
                  field.getColumn().getName() + "='" + 
                  field.getDefaultValue() + "'");
              else {
                sb.append("\r\n\t" + 
                  field.getColumn().getName() + "=" + 
                  field.getDefaultValue());
              }
              sb.append("\r\n</isNull>");
            } else {
              if (!field.isUpdateNull()) continue;
              sb
                .append("\r\n<isNull prepend=\",\" property=\"" + 
                field.getParameter() + "\">");
              sb
                .append("\r\n\t" + 
                field.getColumn().getName() + 
                "=null");
              sb.append("\r\n</isNull>");
            }
          }
        }
      }

      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        if (field.isUpdate()) {
          sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
            field.getParameter() + "\">");
          sb.append("\r\n\t" + field.getColumn().getName() + "=#" + 
            field.getParameter() + "#");
          sb.append("\r\n</isNotNull>");
          if (field.isUpdateNull()) {
            sb.append("\r\n<isNull prepend=\",\" property=\"" + 
              field.getParameter() + "\">");
            sb.append("\r\n\t" + field.getColumn().getName() + 
              "=null");
            sb.append("\r\n</isNull>");
          }
        }
      }
      sb.append(" where ");
      if (getDomain().getId().isCompositeId()) {
        List keyFields = getDomain().getId().getPropertyList();
        for (int i = 0; i < keyFields.size(); i++) {
          sb.append(
            ((Property)keyFields.get(i)).getColumn().getName() + 
            "=#" + 
            ((Property)keyFields.get(i)).getParameter() + 
            "#");
          if (i < keyFields.size() - 1) {
            sb.append(" and ");
          }
        }
      }
      else if (getDomain().getId().getFirstProperty() != null) {
        sb.append(
          getDomain().getId().getFirstProperty().getColumn().getName() + 
          "=#" + 
          getDomain().getId().getFirstProperty()
          .getParameter() + "#");
      } else {
        throw new ConfigureException("domain[" + 
          getDomain().getClassName() + 
          "] has't config  primarkKey");
      }

      return sb.toString();
    }
    throw new ConfigureException("has't property in domain[" + 
      getDomain().getClassName() + "] ");
  }

  private void addWhereByPrimaryKey(StringBuffer sb) {
    if (getDomain().getId().isCompositeId()) {
      List keyFields = getDomain().getId().getPropertyList();
      for (int i = 0; i < keyFields.size(); i++) {
        sb.append(((Property)keyFields.get(i)).getColumn().getName() + 
          "=?");
        if (i < keyFields.size() - 1) {
          sb.append(" and ");
        }
      }
    }
    else if (getDomain().getId().getFirstProperty() != null) {
      sb.append(
        getDomain().getId().getFirstProperty().getColumn().getName() + 
        "=?");
    }
  }

  public List getInsertParameterList()
  {
    List list = new ArrayList();
    if ((getDomain().getId() == null) || 
      (getDomain().getId().getPropertyList() == null) || 
      (getDomain().getId().getPropertyList().size() == 0)) {
      throw new PersistentException("domain[" + 
        getDomain().getClassName() + 
        "] hasn't set primarykey");
    }
    if ((getDomain().getId().getPropertyList().size() > 0) || 
      (getDomain().getPropertyList().size() > 0) || 
      (getDomain().getManyToOneList().size() > 0)) {
      
      for (int i = 0; i < getDomain().getId().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getId()
          .getPropertyList().get(i);
        list.add("<parameter property=\"" + field.getParameter() + 
          "\" jdbcType=\"" + field.getJdbctype() + 
          "\" javaType=\"" + field.getClassName() + "\"/>");
      }
      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        if (field.isInsert()) {
          list.add("<parameter property=\"" + field.getParameter() + 
            "\" jdbcType=\"" + field.getJdbctype() + 
            "\" javaType=\"" + field.getClassName() + "\"/>");
        }
      }
      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        if (field.isInsert()) {
          list.add("<parameter property=\"" + field.getParameter() + 
            "\" jdbcType=\"" + field.getJdbctype() + 
            "\" javaType=\"" + field.getJavatype() + "\"/>");
        }
      }
    }
    return list;
  }

  public String getSqlInsert()
  {
    if ((getDomain().getPropertyList().size() > 0) || 
      (getDomain().getManyToOneList().size() > 0)) {
      StringBuffer sb = new StringBuffer();
      sb.append("insert into " + getDomain().getTable().getName() + 
        " (");
      
      for (int i = 0; i <  getDomain().getId().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getId()
          .getPropertyList().get(i);
        sb.append(field.getColumn().getName());
        if ((!getDomain().getId().isCompositeId()) || 
          (i >= 
          getDomain().getId().getPropertyList().size() - 1)) continue;
        sb.append(",");
      }

      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        if (field.isInsert()) {
          if ((field.getDefaultValue() == null) && 
            (field.getValue() == null)) {
            sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
              field.getParameter() + "\">");
            sb.append(field.getColumn().getName());
            sb.append("\r\n</isNotNull>");
          } else {
            sb.append("," + field.getColumn().getName());
          }
        }
      }
      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        if (field.isInsert()) {
          sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
            field.getParameter() + "\">");
          sb.append(field.getColumn().getName());
          sb.append("\r\n</isNotNull>");
        }
      }
      sb.append(") values(");
      
      for (int i = 0; i < 
        getDomain().getId().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getId()
          .getPropertyList().get(i);
        sb.append("#" + field.getName() + "#");
        if ((!getDomain().getId().isCompositeId()) || 
          (i >= 
          getDomain().getId().getPropertyList().size() - 1)) continue;
        sb.append(",");
      }

      for (int i = 0; i < getDomain().getPropertyList().size(); i++) {
        Property field = 
          (Property)getDomain().getPropertyList()
          .get(i);
        if (field.isInsert()) {
          if ((field.getValue() != null) && 
            (!"".equals(field.getValue()))) {
            if (("java.lang.String".equals(field.getClassName())) || 
              ("String".equals(field.getClassName())))
              sb.append(",'" + field.getValue() + "'");
            else
              sb.append("," + field.getValue());
          }
          else {
            sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
              field.getParameter() + "\">");
            sb.append("\r\n\t #" + field.getParameter() + "#");
            sb.append("\r\n</isNotNull>");
            if (field.getDefaultValue() != null) {
              sb.append("\r\n<isNull prepend=\",\" property=\"" + 
                field.getParameter() + "\">");
              if (("java.lang.String".equals(field.getClassName())) || 
                ("String".equals(field.getClassName())))
                sb.append("\r\n\t '" + field.getDefaultValue() + 
                  "'");
              else {
                sb.append("\r\n\t " + field.getDefaultValue());
              }
              sb.append("\r\n</isNull>");
            }
          }
        }
      }

      for (int i = 0; i < getDomain().getManyToOneList().size(); i++) {
        ManyToOne field = 
          (ManyToOne)getDomain()
          .getManyToOneList().get(i);
        if (field.isInsert()) {
          sb.append("\r\n<isNotNull prepend=\",\" property=\"" + 
            field.getParameter() + "\">");
          sb.append("\r\n\t #" + field.getParameter() + "#");
          sb.append("\r\n</isNotNull>");
        }
      }
      sb.append(")");
      return sb.toString();
    }
    return "";
  }
}