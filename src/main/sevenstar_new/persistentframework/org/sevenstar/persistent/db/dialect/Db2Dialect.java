package org.sevenstar.persistent.db.dialect;

import com.ibatis.sqlmap.client.SqlMapClient;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.exception.ConfigureException;
import org.sevenstar.persistent.db.ibatis.MyAppSqlConfig;
import org.sevenstar.persistent.db.model.Domain;
import org.sevenstar.persistent.db.model.Id;
import org.sevenstar.persistent.db.model.ManyToOne;
import org.sevenstar.persistent.db.model.Property;
import org.sevenstar.persistent.db.model.db.Column;
import org.sevenstar.persistent.db.model.db.Table;

public class Db2Dialect extends AbstractDialect
{
  private static Log LOG = LogFactory.getLog(Db2Dialect.class);

  public static void main(String[] args) {
    Db2Dialect dialect = new Db2Dialect();
    System.out.println(dialect.getCurrentDBTimeInMillis());
    System.out.println(System.currentTimeMillis());
  }

	public long getCurrentDBTimeInMillis() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = MyAppSqlConfig.getSqlMapInstance().getDataSource()
					.getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("SELECT (DAYS(current   timestamp) - DAYS(timestamp('1970-01-01 08:00:00'))) * 86400 + (MIDNIGHT_SECONDS(current   timestamp) - MIDNIGHT_SECONDS(timestamp('1970-01-01 08:00:00')))  from sysibm.sysdummy1");
			long currentTimes = 0;
			while (rs.next()) {
				currentTimes = rs.getLong(1);
				break;
			}
			return currentTimes * 1000;
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
				if (conn != null && !conn.isClosed()) {
					conn.close();
					conn = null;
				}
			} catch (Exception e1) {
				LOG.error(e1);
			}
		}
	}

  public String getDatabase() {
    return "db2";
  }

  public String getJdbcType(String javatype) {
    if ("java.lang.String".equals(javatype)) {
      return "VARCHAR";
    }
    if (("java.lang.Long".equals(javatype)) || 
      ("java.lang.Integer".equals(javatype)) || 
      ("java.lang.Double".equals(javatype))) {
      return "BIGINT";
    }
    if ("java.lang.Float".equals(javatype)) {
      return "DECIMAL";
    }
    if ("java.util.Date".equals(javatype)) {
      return "TIMESTAMP";
    }
    throw new ConfigureException("no default jdbctype for javatype [" + 
      javatype + "]");
  }

  public String getPageAfterSql(Domain domain) {
    return " <![CDATA[  ) as temp_ where  rownum <= #lastrownum# and rownum > #firstrownum#  ]]> ";
  }

  public String getPageBeforeSql(Domain domain)
  {
    return "select * from ( ";
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
    if ("DECIMAL".equalsIgnoreCase(jdbcType)) {
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

  public String getSequenceSql(Domain domain) {
    return "select nextval for " + domain.getId().getSeq() + 
      "  from sysibm.sysdummy1";
  }

  public String getPageSql(Domain domain)
  {
    StringBuffer sb = new StringBuffer();
    if ((domain.getCondition() == null) || 
      ("".equals(domain.getCondition()))) {
      sb.append("\t\tselect rownumber() over( " + super.getOrder(domain) + ") as rownum,  " + getSelectHeader(domain) + " from " + 
        domain.getTable().getName() + " \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    }
    else {
      sb.append("\t\tselect  rownumber() over( " + super.getOrder(domain) + ") as rownum, " + getSelectHeader(domain) + " from " + 
        domain.getTable().getName());
      sb.append("\r\n\t<![CDATA[ \r\n");
      sb.append(" where " + 
        domain.getCondition() + " \r\n");
      sb.append("\t]]> \r\n");
    }
    addWhere(domain, sb);
    sb.append(" " + super.getOrder(domain));
    return sb.toString();
  }

  public String getSelectEqualMaxSizeSql(Domain domain)
  {
    StringBuffer sb = new StringBuffer();

    if ((domain.getCondition() == null) || 
      ("".equals(domain.getCondition()))) {
      sb.append("\t\tselect * from (select rownumber() over( " + super.getOrder(domain) + ") as rownum,  " + getSelectHeader(domain) + " from " + 
        domain.getTable().getName() + "   \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
      sb.append("\t\tselect * from ( select  rownumber() over( " + super.getOrder(domain) + ") as rownum, " + getSelectHeader(domain) + " from " + 
        domain.getTable().getName() + "  ");
      sb.append("\r\n\t<![CDATA[ \r\n");
      sb.append(" where " + 
        domain.getCondition() + " \r\n");
      sb.append("\t]]> \r\n");
    }

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
      sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"" + 
        field.getSelect_property() + "\"> \r\n");
      sb.append("\t<![CDATA[ \r\n");
      sb.append("\t\t\t" + field.getColumn().getName() + "  = #" + 
        field.getSelect_property() + "#  \r\n");
      sb.append("\t]]> \r\n");
      sb.append("\t\t</isNotEmpty>  \r\n");
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
    sb.append("\t" + super.getOrder(domain) + "\r\n");
    if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
      sb.append("\t</dynamic> \r\n");
    }
    sb.append(" ) ");
    sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"lastrownum\"> \r\n");
    sb.append("\t<![CDATA[ \r\n");
    sb.append("\t\t\t rownum  <= #lastrownum#  \r\n");
    sb.append("\t]]> \r\n");
    sb.append("\t\t</isNotEmpty>  \r\n");
    sb.append("\t</dynamic> \r\n");
    return sb.toString();
  }

  public String getSelectMaxSizeSql(Domain domain)
  {
    StringBuffer sb = new StringBuffer();

    if ((domain.getCondition() == null) || 
      ("".equals(domain.getCondition()))) {
      sb.append("\t\tselect * from (select rownumber() over( " + super.getOrder(domain) + ") as rownum,  " + getSelectHeader(domain) + " from " + 
        domain.getTable().getName() + "  \r\n");
      sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    } else {
      sb.append("\t\tselect * from ( select  rownumber() over( " + super.getOrder(domain) + ") as rownum, " + getSelectHeader(domain) + " from " + 
        domain.getTable().getName() + " ");
      sb.append("\r\n\t<![CDATA[ \r\n");
      sb.append(" where " + 
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
    sb.append("\t" + super.getOrder(domain) + "\r\n");
    if ((domain.getCondition() == null) || ("".equals(domain.getCondition()))) {
      sb.append("\t</dynamic> \r\n");
    }
    sb.append(" ) ");
    sb.append("\t<dynamic prepend=\"WHERE\"> \r\n");
    sb.append("\t\t<isNotEmpty prepend=\"and\" property=\"lastrownum\"> \r\n");
    sb.append("\t<![CDATA[ \r\n");
    sb.append("\t\t\t rownum  <= #lastrownum#  \r\n");
    sb.append("\t]]> \r\n");
    sb.append("\t\t</isNotEmpty>  \r\n");
    sb.append("\t</dynamic> \r\n");

    return sb.toString();
  }
}