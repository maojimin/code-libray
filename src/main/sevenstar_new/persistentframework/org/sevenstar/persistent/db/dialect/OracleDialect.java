package org.sevenstar.persistent.db.dialect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.ibatis.MyAppSqlConfig;
import org.sevenstar.persistent.db.model.Domain;
import org.sevenstar.persistent.db.model.ManyToOne;
import org.sevenstar.persistent.db.model.Property;

public class OracleDialect extends AbstractDialect
{
   private static Log LOG = LogFactory.getLog(OracleDialect.class);

  public String getJdbcType(String javatype) {
     if ("java.lang.String".equals(javatype)) {
       return "VARCHAR2";
    }
     if (("java.lang.Long".equals(javatype)) || 
       ("java.lang.Integer".equals(javatype)) || 
       ("java.lang.Float".equals(javatype)) || 
       ("java.lang.Double".equals(javatype))) {
       return "NUMERIC";
    }
     if ("java.util.Date".equals(javatype)) {
       return "DATE";
    }
     throw new ConfigureException("no default jdbctype for javatype [" + 
       javatype + "]");
  }

  public String getDatabase() {
     return "oracle";
  }

  public String getSelectType(String jdbcType) {
     if ("VARCHAR2".equalsIgnoreCase(jdbcType)) {
       return "like";
    }
     if ("VARCHAR".equalsIgnoreCase(jdbcType)) {
       return "like";
    }
     if ("CHAR".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("NUMERIC".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Integer".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Long".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Double".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Float".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("BigInt".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Int".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Date".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("DateTime".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("TimeStamp".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     if ("Time".equalsIgnoreCase(jdbcType)) {
       return "equal";
    }
     throw new ConfigureException("no default selecttype for jdbctype [" + 
       jdbcType + "]");
  }

  public String getPageAfterSql(Domain domain) {
     return " <![CDATA[ ) row_ where rownum <= #lastrownum#) where rownum_ > #firstrownum# ]]>";
  }

  public String getPageBeforeSql(Domain domain) {
     return " select * from ( select row_.*, rownum rownum_ from ( ";
  }

  public String getSequenceSql(Domain domain) {
     return "select " + domain.getId().getSeq() + ".nextval from dual";
  }

  public long getCurrentDBTimeInMillis() {
     Connection conn = null;
     Statement stmt = null;
     ResultSet rs = null;
    try {
       conn = MyAppSqlConfig.getSqlMapInstance().getDataSource().getConnection();
       stmt = conn.createStatement();
       rs = stmt.executeQuery("SELECT (SYSDATE - TO_DATE('1970-1-1 8', 'YYYY-MM-DD HH24')) * 24*60*60*1000 FROM DUAL");
       long currentTimes = 0L;
       if (rs.next()) {
         currentTimes = rs.getLong(1);
      }

       long l1 = currentTimes;
      return l1;
    } catch (Exception e) {
       throw new RuntimeException(e);
    } finally {
      try {
         if (rs != null) {
           rs.close();
           rs = null;
        }
      } catch (Exception e1) {
         LOG.error(e1);
      }
      try {
         if (stmt != null) {
           stmt.close();
           stmt = null;
        }
      } catch (Exception e1) {
         LOG.error(e1);
      }
      try {
         if ((conn != null) && (!conn.isClosed())) {
           conn.close();
           conn = null;
        }
      } catch (Exception e1) {
         LOG.error(e1);
      }
    }
  }

  public String getPageSql(Domain domain) {
     StringBuffer sb = new StringBuffer();
     sb.append("\r\n\t<![CDATA[ \r\n");
     if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
       sb.append("\t\tselect " + getSelectHeader(domain) + " from " + 
         domain.getTable().getName() + " \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
       sb.append("\t\tselect " + getSelectHeader(domain) + " from " + 
         domain.getTable().getName() + " where " + 
         domain.getCondition() + " \r\n");
       sb.append("\t]]> \r\n");
    }
     addWhere(domain, sb);
     sb.append("\t" + getOrder(domain) + "\r\n");
     return sb.toString();
  }

  public String getSelectEqualMaxSizeSql(Domain domain) {
     StringBuffer sb = new StringBuffer();
     sb.append("\r\n\t<![CDATA[ \r\n");
     if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
       sb.append("\t\tselect " + super.getSelectHeader(domain) + " from " + 
         domain.getTable().getName() + " \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
       sb.append("\t\tselect " + super.getSelectHeader(domain) + " from " + 
         domain.getTable().getName() + " where " + 
         domain.getCondition() + " \r\n");
       sb.append("\t]]> \r\n");
    }

     for (int i = 0; i < domain.getId().getPropertyList().size(); i++) {
       Property field = (Property)domain.getId().getPropertyList().get(i);
       sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
         field.getSelect_property() + "\"> \r\n");
       sb.append("\t<![CDATA[ \r\n");
       sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
         field.getSelect_property() + "#  \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t\t</isNotEmpty>  \r\n");
    }
     for (int i = 0; i < domain.getPropertyList().size(); i++) {
       Property field = (Property)domain.getPropertyList().get(i);
       sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
         field.getSelect_property() + "\"> \r\n");
       sb.append("\t<![CDATA[ \r\n");
       sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
         field.getSelect_property() + "#  \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t\t</isNotEmpty>  \r\n");
    }
     for (int i = 0; i < domain.getManyToOneList().size(); i++) {
       ManyToOne field = (ManyToOne)domain.getManyToOneList().get(i);
       sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
         field.getParameter() + "\"> \r\n");
       sb.append("\t<![CDATA[ \r\n");
       sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
         field.getParameter() + "#  \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t\t</isNotEmpty>  \r\n");
    }
     sb
       .append("\t\t<isNotEmpty prepend=\"and\" property=\"lastrownum\"> \r\n");
     sb.append("\t<![CDATA[ \r\n");
     sb.append("\t\t\t rownum  <= #lastrownum#  \r\n");
     sb.append("\t]]> \r\n");
     sb.append("\t\t</isNotEmpty>  \r\n");
     if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
       sb.append("\t</dynamic> \r\n");
    }
     sb.append("\t" + super.getOrder(domain) + "\r\n");
     return sb.toString();
  }

  public String getSelectMaxSizeSql(Domain domain) {
     StringBuffer sb = new StringBuffer();
     sb.append("\r\n\t<![CDATA[ \r\n");
     if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
       sb.append("\t\tselect " + getSelectHeader(domain) + " from " + 
         domain.getTable().getName() + " \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
       sb.append("\t\tselect " + getSelectHeader(domain) + " from " + 
         domain.getTable().getName() + " where " + 
         domain.getCondition() + " \r\n");
       sb.append("\t]]> \r\n");
    }
     for (int i = 0; i < domain.getId().getPropertyList().size(); i++) {
       Property field = (Property)domain.getId().getPropertyList().get(i);
       sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
         field.getSelect_property() + "\"> \r\n");
       sb.append("\t<![CDATA[ \r\n");
       sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
         field.getSelect_property() + "#  \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t\t</isNotEmpty>  \r\n");
    }
     for (int i = 0; i < domain.getPropertyList().size(); i++) {
       Property field = (Property)domain.getPropertyList().get(i);
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
       ManyToOne field = (ManyToOne)domain.getManyToOneList().get(i);
       sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
         field.getParameter() + "\"> \r\n");
       sb.append("\t<![CDATA[ \r\n");
       sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
         field.getParameter() + "#  \r\n");
       sb.append("\t]]> \r\n");
       sb.append("\t\t</isNotEmpty>  \r\n");
    }
     sb
       .append("\t\t<isNotEmpty prepend=\"and\" property=\"lastrownum\"> \r\n");
     sb.append("\t<![CDATA[ \r\n");
     sb.append("\t\t\t rownum  <= #lastrownum#  \r\n");
     sb.append("\t]]> \r\n");
     sb.append("\t\t</isNotEmpty>  \r\n");
     if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
       sb.append("\t</dynamic> \r\n");
    }
     sb.append("\t" + getOrder(domain) + "\r\n");
     return sb.toString();
  }
}
