package org.sevenstar.persistent.db.dialect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.sevenstar.persistent.db.model.Domain;
import org.sevenstar.persistent.db.model.Id;
import org.sevenstar.persistent.db.model.ManyToOne;
import org.sevenstar.persistent.db.model.Property;
import org.sevenstar.persistent.db.model.db.Column;

public abstract class AbstractDialect
{
  public abstract String getDatabase();

  public abstract String getJdbcType(String paramString);

  public abstract String getSelectType(String paramString);

  public abstract String getPageBeforeSql(Domain paramDomain);

  public abstract String getPageSql(Domain paramDomain);

  public abstract String getPageAfterSql(Domain paramDomain);

  public abstract String getSequenceSql(Domain paramDomain);

  public abstract String getSelectEqualMaxSizeSql(Domain paramDomain);

  public abstract String getSelectMaxSizeSql(Domain paramDomain);

  public abstract long getCurrentDBTimeInMillis();

  public void addWhere(Domain domain, StringBuffer sb)
  {
    for (int i = 0; i < domain.getId().getPropertyList().size(); i++) {
      Property field = 
        (Property)domain.getId()
        .getPropertyList().get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    for (int i = 0; i < domain.getPropertyList().size(); i++) {
      Property field = (Property)domain.getPropertyList().get(
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
    for (int i = 0; i < domain.getManyToOneList().size(); i++) {
      ManyToOne field = 
        (ManyToOne)domain.getManyToOneList()
        .get(i);
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getParameter() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getParameter() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
    }
    if ((domain.getCondition() == null) || 
      ("".equals(domain.getCondition())))
      sb.append("\t</dynamic> \r\n");
  }

  public String getSelectHeader(Domain domain) {
    String selectAllColumn = "";
    Map columnMap = new HashMap();
    if (domain.getId() != null) {
      List idPropertyList = domain.getId().getPropertyList();
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

    List propertyList = domain.getPropertyList();
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

    List manyToOneList = domain.getManyToOneList();
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

  public String getOrder(Domain domain) {
    String order = domain.getOrder();
    if (((order == null) || ("".equals(order))) && 
      (domain.getId().getPropertyList().size() > 0)) {
      int i = 0;
      for (; i < 
        domain.getId().getPropertyList().size(); i++) {
        if (i == 0)
          order = 
            ((Property)domain.getId()
            .getPropertyList().get(i)).getColumn()
            .getName() + 
            " desc";
        else {
          order = order + 
            "," + 
            ((Property)domain.getId()
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
}