package org.sevenstar.persistent.db.jdbc.conn;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * jdbc操作包装类
 * @author rtm
 */
public class DatabaseConnection {

	private static Log log = LogFactory.getLog(DatabaseConnection.class);
	/**
	 * jdbc驱动类
	 */
	private String driver;

	private String url;

	/**
	 * 用户名
	 */
	private String user;

	/**
	 * 密码
	 */
	private String pass;

	private Connection conn;

	private Statement stmt;

	private PreparedStatement ps;

	private ResultSet rs;

	private String databaseType;

	private final static String configPropertiesFile = "conn.properties";

	/**
	 * 默认构造函数
	 */
	public DatabaseConnection() {
		this.databaseConnect();
	}

	public DatabaseConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				this.conn = conn;
			} else {
				throw new RuntimeException("conn is null or isClosed");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 连接数据库
	 *
	 * @return
	 */
	public Connection databaseConnect() {
		try {
			if (this.conn == null || this.conn.isClosed()) {
				this.loadEnvironment();
				Class.forName(this.getDriver());
				this.conn = DriverManager.getConnection(url, user, pass);
				setCommitFalse();
			}
			return this.conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("JDBC Driver :'" + this.getDriver()
					+ "' not find!!!");
		}
	}

	public Connection getConnection() {
		return databaseConnect();
	}

	/**
	 * 关闭数据库连接
	 *
	 * @return
	 */
	public void closeConnection() {
		try {
			if (this.conn != null && !this.conn.isClosed()) {
				this.conn.close();
				this.conn = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 得到本类连接的Statement
	 *
	 * @return
	 */
	public Statement getStatement() {
		if (this.conn == null) {
			throw new RuntimeException("conn is null or isClosed");
		}
		try {
			if (this.stmt == null) {
				this.stmt = this.conn.createStatement();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return this.stmt;
	}

	/**
	 * 关闭本类得到的Statement
	 *
	 * @return
	 */
	public void closeStatement() {
		if (this.stmt != null) {
			try {
				this.stmt.close();
				stmt = null;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 得到指定sql的PreparedStatement
	 *
	 * @param strSql
	 * @return
	 */
	public PreparedStatement getPreparedStatement(String sql) {
		if (this.conn != null) {
			try {
				log.debug("sql=" + sql);
				this.ps = this.conn.prepareStatement(sql);
				return this.ps;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("conn is null or isClosed");
		}
	}

	/**
	 * 关闭PreparedStatement
	 *
	 * @return
	 */
	public void closePreparedStatement() {
		if (this.ps != null) {
			try {
				this.ps.close();
				this.ps = null;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 执行查询得到ResultSet
	 *
	 * @param strSql
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		log.debug("sql=" + sql);
		try {
			return this.getStatement().executeQuery(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭本类得到的ResultSet
	 *
	 * @return
	 */
	public void closeResultset() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 执行指定sql的更新操作
	 *
	 * @param strSql
	 * @return
	 */
	public int executeUpdate(String sql) {
		log.debug("sql=" + sql);
		this.getStatement();
		try {
			int row = stmt.executeUpdate(sql);
			log.debug("row=" + row);
			return row;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStatement();
		}
	}

	public void executeUpdate(String sql, Object[] params) {
		PreparedStatement ps = this.getPreparedStatement(sql);
		if (params != null) {
			try {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, (String) params[i]);
				}
				ps.execute();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}finally{
				this.closePreparedStatement();
			}
		}
	}
	
	public Map getColumNamesAndType(String strTable) {
		String strSql = "select * from " + strTable;
		log.debug("strSql=" + strSql);
		ResultSet rs = this.executeQuery(strSql);
		ResultSetMetaData rsmd = null;
		Map map = new HashMap();
		try {
			rsmd = rs.getMetaData();
			if (rsmd != null) {
 				log.debug("rsmd.getColumnCount()=" + rsmd.getColumnCount());
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					map.put((rsmd.getColumnName(i + 1)).toLowerCase(), rsmd.getColumnClassName(i+1));
 				}
			}
			return map;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	/**
	 * 得到本用户名下的所有表
	 *
	 * @return
	 */
	public String[] getTableNames() {
		String[] strs = null;
		String[] types = { "TABLE" };
		try {
			DatabaseMetaData dbmd = this.getConnection().getMetaData();
			ResultSet rs = dbmd.getTables(null, this.user.toUpperCase(), null,
					types);
			List list = new ArrayList();
			while (rs.next()) {
				list.add(rs.getString("table_name"));
			}
			strs = new String[list.size()];
			for (int i = 0; i < strs.length; i++) {
				strs[i] = (String) list.get(i);
			}
			return strs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	/**
	 * 得到指定表的所有列
	 *
	 * @param strTable
	 * @return
	 */
	public String[] getColumNames(String strTable) {
		String strSql = "select * from " + strTable;
		log.debug("strSql=" + strSql);
		ResultSet rs = this.executeQuery(strSql);
		ResultSetMetaData rsmd = null;
		String[] sTmp = null;
		try {
			rsmd = rs.getMetaData();
			if (rsmd != null) {
				sTmp = new String[rsmd.getColumnCount()];
				log.debug("rsmd.getColumnCount()=" + rsmd.getColumnCount());
				for (int i = 0; i < sTmp.length; i++) {
					sTmp[i] = rsmd.getColumnName(i + 1);
					log.debug("sTmp[" + i + "]=" + sTmp[i]);
				}
			}
			return sTmp;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	/**
	 * 禁用自动提交
	 *
	 * @return
	 */
	public void setCommitFalse() {
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 起用自动提交
	 *
	 * @return
	 */
	public void setCommitTrue() {
		try {
			conn.setAutoCommit(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交
	 *
	 * @return
	 */
	public void commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 回滚
	 *
	 * @return
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 得到表的注释
	 *
	 * @param user
	 * @param tableName
	 * @return
	 */
	public String getTableComment(String tableName) {
		String comment = "";
		try {
			/**
			 * 得到表描述信息
			 */
			this.rs = this
					.executeQuery("select * from user_tab_comments where comments is not null and TABLE_NAME='"
							+ tableName.toUpperCase() + "'");
			while (rs.next()) {
				comment = rs.getString("COMMENTS");
				break;
			}
			return comment;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	public String getColumnComment(String tableName, String colum) {
		String comment = "";
		try {
			this.rs = this
					.executeQuery("select * from user_col_comments where comments is not null and TABLE_NAME='"
							+ tableName.toUpperCase()
							+ "' and COLUMN_NAME='"
							+ colum.toUpperCase() + "'");
			while (rs.next()) {
				comment = rs.getString("COMMENTS");
				break;
			}
			if (comment == null) {
				comment = "";
			}
			return comment;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	public String getColumnSize(String user, String tableName, String colum) {
		DatabaseMetaData dbmd = null;
		String size = "";
		try {
			dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getColumns(null, this.user.toUpperCase(),
					tableName.toUpperCase(), colum.toUpperCase());
			while (rs.next()) {
				size = rs.getString("COLUMN_SIZE").toLowerCase();
			}
			return size;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	/**
	 * 得到表某一列的描述信息，注意这里用户名，表名，列名都要大写
	 * 因为虽然sql是大小写无关的，但是存在oracle中的数据（用户名，表名，列名）是大写的
	 *
	 * @param user
	 * @param tableName
	 * @param colum
	 * @return
	 */
	public Map getColumnDes(String tableName, String colum) {
		Connection conn = this.getConnection();
		DatabaseMetaData dbmd = null;
		Map map = new HashMap();
		String comment = "";
		try {
			dbmd = conn.getMetaData();
			this.rs = dbmd.getColumns(null, this.user.toUpperCase(), tableName
					.toUpperCase(), colum.toUpperCase());
			while (rs.next()) {
				/**
				 * 等于数据库用户名
				 */
				map.put("TABLE_SCHEM".toLowerCase(), rs
						.getString("TABLE_SCHEM").toLowerCase());
				/**
				 * 列名
				 */
				map.put("COLUMN_NAME".toLowerCase(), rs
						.getString("COLUMN_NAME").toLowerCase());
				map.put("DATA_TYPE".toLowerCase(), rs.getString("DATA_TYPE")
						.toLowerCase());
				/**
				 * sql类型
				 */
				map.put("TYPE_NAME".toLowerCase(), rs.getString("TYPE_NAME")
						.toLowerCase());
				/**
				 * 字段长度
				 */
				map.put("COLUMN_SIZE".toLowerCase(), rs
						.getString("COLUMN_SIZE").toLowerCase());
				/**
				 * 默认值COLUMN_DEF
				 */
				String def = rs.getString("COLUMN_DEF");
				if (def == null) {
					def = "";
				}
				map.put("COLUMN_DEF".toLowerCase(), def.toLowerCase());
				/**
				 * 是否允许为空
				 */
				map.put("IS_NULLABLE".toLowerCase(), rs
						.getString("IS_NULLABLE").toLowerCase());
			}
			this.closeResultset();
			/**
			 * 得到列描述信息
			 */
			this.rs = this
					.executeQuery("select * from user_col_comments where comments is not null and TABLE_NAME='"
							+ tableName.toUpperCase()
							+ "' and COLUMN_NAME='"
							+ colum.toUpperCase() + "'");
			while (rs.next()) {
				comment = rs.getString("COMMENTS");
			}
			if (comment == null) {
				comment = "";
			}
			map.put("comment", comment);
			return map;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	/**
	 * 得到指定用户指定表的主键
	 *
	 * @param tableName
	 * @return
	 */
	public List getPrimaryKey(String tableName) {
		Connection conn = this.getConnection();
		DatabaseMetaData dbmd = null;
		ResultSet res = null;
		List pkList = new ArrayList();
		try {
			// 取指定数据库表中的列和列类型
			dbmd = conn.getMetaData();
			res = dbmd.getPrimaryKeys(null, this.user.toUpperCase(), tableName
					.toUpperCase());
			while (res.next()) {
				pkList.add(res.getString("COLUMN_NAME").toLowerCase());
			}
			return pkList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			this.closeStmtAndRs();
		}
	}

	public void closeStmtAndRs() {
		if (this.rs != null) {
			this.closeResultset();
		}
		if (this.stmt != null) {
			this.closeStatement();
		}
		if (this.ps != null) {
			this.closePreparedStatement();
		}
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		if (this.rs != null) {
			this.closeResultset();
		}
		if (this.stmt != null) {
			this.closeStatement();
		}
		if (this.ps != null) {
			this.closePreparedStatement();
		}
		if (this.conn != null) {
			this.closeConnection();
		}
	}

	/**
	 * 读取配置文件得到配置
	 *
	 * @return
	 */
	private void loadEnvironment() {
		if (this.user == null || this.pass == null || this.url == null
				|| this.driver == null) {
			try {
				String fileNameNoExt = this.configPropertiesFile;
				if(fileNameNoExt.indexOf(".") != -1){
					fileNameNoExt = fileNameNoExt.substring(0,fileNameNoExt.indexOf("."));
				}
				ResourceBundle rb = ResourceBundle
						.getBundle(fileNameNoExt);
				this.databaseType = emptyToNull(rb.getString("default.DB"));
				this.driver = emptyToNull(rb.getString(this.databaseType
						+ ".driver"));
				this.url = emptyToNull(rb.getString(this.databaseType + ".url"));
				this.user = emptyToNull(rb.getString(this.databaseType
						+ ".user"));
				this.pass = emptyToNull(rb.getString(this.databaseType
						+ ".pass"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if (this.user == null || this.pass == null || this.url == null
					|| this.driver == null) {
				throw new RuntimeException("数据库连接配置错误,jdbc连接url或用户名或密码没有配置");
			}
		}
	}

	private String emptyToNull(String str) {
		if (str != null && "".equals(str.trim())) {
			return null;
		}
		return str;
	}

	public String getUser() {
		return this.user;
	}
/*
	public Connection getConn() {
		return conn;
	} */

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getDatabaseType() {
		if (this.databaseType == null) {
			this.databaseType = "oracle";
		}
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getDriver() {
		if (this.driver == null) {
			this.driver = "oracle.jdbc.OracleDriver";
		}
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

}

//package org.sevenstar.persistent.db.jdbc.conn;
//
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//public class DatabaseConnection
//{
//  private static Log log = LogFactory.getLog(DatabaseConnection.class);
//  private String driver;
//  private String url;
//  private String user;
//  private String pass;
//  private Connection conn;
//  private Statement stmt;
//  private PreparedStatement ps;
//  private ResultSet rs;
//  private String databaseType;
//  private static final String configPropertiesFile = "conn.properties";
//
//  public DatabaseConnection()
//  {
//    databaseConnect();
//  }
//
//  public DatabaseConnection(Connection conn) {
//    try {
//      if ((conn != null) && (!conn.isClosed()))
//        this.conn = conn;
//      else
//        throw new RuntimeException("conn is null or isClosed");
//    }
//    catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public Connection databaseConnect()
//  {
//    try
//    {
//      if ((this.conn == null) || (this.conn.isClosed())) {
//        loadEnvironment();
//        Class.forName(getDriver());
//        this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
//        setCommitFalse();
//      }
//      return this.conn;
//    } catch (SQLException e) {
//      throw new RuntimeException(e); } catch (ClassNotFoundException e) {
//    }
//    throw new RuntimeException("JDBC Driver :'" + getDriver() + 
//      "' not find!!!");
//  }
//
//  public Connection getConnection()
//  {
//    return databaseConnect();
//  }
//
//  public void closeConnection()
//  {
//    try
//    {
//      if ((this.conn != null) && (!this.conn.isClosed())) {
//        this.conn.close();
//        this.conn = null;
//      }
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public Statement getStatement()
//  {
//    if (this.conn == null)
//      throw new RuntimeException("conn is null or isClosed");
//    try
//    {
//      if (this.stmt == null)
//        this.stmt = this.conn.createStatement();
//    }
//    catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//    return this.stmt;
//  }
//
//  public void closeStatement()
//  {
//    if (this.stmt != null)
//      try {
//        this.stmt.close();
//        this.stmt = null;
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      }
//  }
//
//  public PreparedStatement getPreparedStatement(String sql)
//  {
//    if (this.conn != null) {
//      try {
//        log.debug("sql=" + sql);
//        this.ps = this.conn.prepareStatement(sql);
//        return this.ps;
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      }
//    }
//    throw new RuntimeException("conn is null or isClosed");
//  }
//
//  public void closePreparedStatement()
//  {
//    if (this.ps != null)
//      try {
//        this.ps.close();
//        this.ps = null;
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      }
//  }
//
//  public ResultSet executeQuery(String sql){
//    log.debug("sql=" + sql);
//    try {
//      return getStatement().executeQuery(sql); } catch (SQLException e) {
//    }
//    throw new RuntimeException(e);
//  }
//
//  public void closeResultset()
//  {
//    try
//    {
//      if (this.rs != null) {
//        this.rs.close();
//        this.rs = null;
//      }
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public int executeUpdate(String sql)
//  {
//    log.debug("sql=" + sql);
//    getStatement();
//    try {
//      int row = this.stmt.executeUpdate(sql);
//      log.debug("row=" + row);
//      int i = row;
//      return i;
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStatement();
//    }throw localObject;
//  }
//
//  public void executeUpdate(String sql, Object[] params) {
//    PreparedStatement ps = getPreparedStatement(sql);
//    if (params != null)
//      try {
//        for (int i = 0; i < params.length; i++) {
//          ps.setObject(i + 1, (String)params[i]);
//        }
//        ps.execute();
//      } catch (SQLException e) {
//        throw new RuntimeException(e);
//      } finally {
//        closePreparedStatement();
//      }
//  }
//
//  public Map getColumNamesAndType(String strTable)
//  {
//    String strSql = "select * from " + strTable;
//    log.debug("strSql=" + strSql);
//    ResultSet rs = executeQuery(strSql);
//    ResultSetMetaData rsmd = null;
//    Map map = new HashMap();
//    try {
//      rsmd = rs.getMetaData();
//      if (rsmd != null) {
//        log.debug("rsmd.getColumnCount()=" + rsmd.getColumnCount());
//        for (int i = 0; i < rsmd.getColumnCount(); i++) {
//          map.put(rsmd.getColumnName(i + 1).toLowerCase(), rsmd.getColumnClassName(i + 1));
//        }
//      }
//      Map localMap1 = map;
//      return localMap1;
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public String[] getTableNames()
//  {
//    String[] strs = (String[])null;
//    String[] types = { "TABLE" };
//    try {
//      DatabaseMetaData dbmd = getConnection().getMetaData();
//      ResultSet rs = dbmd.getTables(null, this.user.toUpperCase(), null, 
//        types);
//      List list = new ArrayList();
//      while (rs.next()) {
//        list.add(rs.getString("table_name"));
//      }
//      strs = new String[list.size()];
//      for (int i = 0; i < strs.length; i++) {
//        strs[i] = ((String)list.get(i));
//      }
//      String[] arrayOfString1 = strs;
//      return arrayOfString1;
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public String[] getColumNames(String strTable)
//  {
//    String strSql = "select * from " + strTable;
//    log.debug("strSql=" + strSql);
//    ResultSet rs = executeQuery(strSql);
//    ResultSetMetaData rsmd = null;
//    String[] sTmp = (String[])null;
//    try {
//      rsmd = rs.getMetaData();
//      if (rsmd != null) {
//        sTmp = new String[rsmd.getColumnCount()];
//        log.debug("rsmd.getColumnCount()=" + rsmd.getColumnCount());
//        for (int i = 0; i < sTmp.length; i++) {
//          sTmp[i] = rsmd.getColumnName(i + 1);
//          log.debug("sTmp[" + i + "]=" + sTmp[i]);
//        }
//      }
//      String[] arrayOfString1 = sTmp;
//      return arrayOfString1;
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public void setCommitFalse()
//  {
//    try
//    {
//      this.conn.setAutoCommit(false);
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public void setCommitTrue()
//  {
//    try
//    {
//      this.conn.setAutoCommit(true);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public void commit()
//  {
//    try
//    {
//      this.conn.commit();
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public void rollback()
//  {
//    try
//    {
//      this.conn.rollback();
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
//
//  public String getTableComment(String tableName)
//  {
//    String comment = "";
//    try
//    {
//      this.rs = 
//        executeQuery("select * from user_tab_comments where comments is not null and TABLE_NAME='" + 
//        tableName.toUpperCase() + "'");
//      if (this.rs.next()) {
//        comment = this.rs.getString("COMMENTS");
//      }
//
//      String str1 = comment;
//      return str1;
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public String getColumnComment(String tableName, String colum) {
//    String comment = "";
//    try {
//      this.rs = 
//        executeQuery("select * from user_col_comments where comments is not null and TABLE_NAME='" + 
//        tableName.toUpperCase() + 
//        "' and COLUMN_NAME='" + 
//        colum.toUpperCase() + "'");
//      if (this.rs.next()) {
//        comment = this.rs.getString("COMMENTS");
//      }
//
//      if (comment == null) {
//        comment = "";
//      }
//      String str1 = comment;
//      return str1;
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public String getColumnSize(String user, String tableName, String colum) {
//    DatabaseMetaData dbmd = null;
//    String size = "";
//    try {
//      dbmd = this.conn.getMetaData();
//      ResultSet rs = dbmd.getColumns(null, this.user.toUpperCase(), 
//        tableName.toUpperCase(), colum.toUpperCase());
//      while (rs.next()) {
//        size = rs.getString("COLUMN_SIZE").toLowerCase();
//      }
//      String str1 = size;
//      return str1;
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public Map getColumnDes(String tableName, String colum)
//  {
//    Connection conn = getConnection();
//    DatabaseMetaData dbmd = null;
//    Map map = new HashMap();
//    String comment = "";
//    try {
//      dbmd = conn.getMetaData();
//      this.rs = dbmd
//        .getColumns(null, this.user.toUpperCase(), 
//        tableName.toUpperCase(), colum.toUpperCase());
//      while (this.rs.next())
//      {
//        map.put("TABLE_SCHEM".toLowerCase(), 
//          this.rs.getString("TABLE_SCHEM").toLowerCase());
//
//        map.put("COLUMN_NAME".toLowerCase(), 
//          this.rs.getString("COLUMN_NAME").toLowerCase());
//        map.put("DATA_TYPE".toLowerCase(), 
//          this.rs.getString("DATA_TYPE").toLowerCase());
//
//        map.put("TYPE_NAME".toLowerCase(), 
//          this.rs.getString("TYPE_NAME").toLowerCase());
//
//        map.put("COLUMN_SIZE".toLowerCase(), 
//          this.rs.getString("COLUMN_SIZE").toLowerCase());
//
//        String def = this.rs.getString("COLUMN_DEF");
//        if (def == null) {
//          def = "";
//        }
//        map.put("COLUMN_DEF".toLowerCase(), def.toLowerCase());
//
//        map.put("IS_NULLABLE".toLowerCase(), 
//          this.rs.getString("IS_NULLABLE").toLowerCase());
//      }
//      closeResultset();
//
//      this.rs = 
//        executeQuery("select * from user_col_comments where comments is not null and TABLE_NAME='" + 
//        tableName.toUpperCase() + 
//        "' and COLUMN_NAME='" + 
//        colum.toUpperCase() + "'");
//      while (this.rs.next()) {
//        comment = this.rs.getString("COMMENTS");
//      }
//      if (comment == null) {
//        comment = "";
//      }
//      map.put("comment", comment);
//      Map localMap1 = map;
//      return localMap1;
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public List getPrimaryKey(String tableName)
//  {
//    Connection conn = getConnection();
//    DatabaseMetaData dbmd = null;
//    ResultSet res = null;
//    List pkList = new ArrayList();
//    try
//    {
//      dbmd = conn.getMetaData();
//      res = dbmd.getPrimaryKeys(null, this.user.toUpperCase(), 
//        tableName.toUpperCase());
//      while (res.next()) {
//        pkList.add(res.getString("COLUMN_NAME").toLowerCase());
//      }
//      List localList1 = pkList;
//      return localList1;
//    } catch (SQLException e) {
//      throw new RuntimeException(e);
//    } finally {
//      closeStmtAndRs();
//    }throw localObject;
//  }
//
//  public void closeStmtAndRs() {
//    if (this.rs != null) {
//      closeResultset();
//    }
//    if (this.stmt != null) {
//      closeStatement();
//    }
//    if (this.ps != null)
//      closePreparedStatement();
//  }
//
//  public void close()
//  {
//    if (this.rs != null) {
//      closeResultset();
//    }
//    if (this.stmt != null) {
//      closeStatement();
//    }
//    if (this.ps != null) {
//      closePreparedStatement();
//    }
//    if (this.conn != null)
//      closeConnection();
//  }
//
//  private void loadEnvironment()
//  {
//    if ((this.user == null) || (this.pass == null) || (this.url == null) || 
//      (this.driver == null)) {
//      try {
//        String fileNameNoExt = "conn.properties";
//        if (fileNameNoExt.indexOf(".") != -1) {
//          fileNameNoExt = fileNameNoExt.substring(0, fileNameNoExt.indexOf("."));
//        }
//        ResourceBundle rb = 
//          ResourceBundle.getBundle(fileNameNoExt);
//        this.databaseType = emptyToNull(rb.getString("default.DB"));
//        this.driver = 
//          emptyToNull(rb
//          .getString(this.databaseType + 
//          ".driver"));
//        this.url = emptyToNull(rb.getString(this.databaseType + ".url"));
//        this.user = 
//          emptyToNull(rb
//          .getString(this.databaseType + 
//          ".user"));
//        this.pass = 
//          emptyToNull(rb
//          .getString(this.databaseType + 
//          ".pass"));
//      } catch (Exception e) {
//        throw new RuntimeException(e);
//      }
//      if ((this.user == null) || (this.pass == null) || (this.url == null) || 
//        (this.driver == null))
//        throw new RuntimeException("数据库连接配置错误,jdbc连接url或用户名或密码没有配置");
//    }
//  }
//
//  private String emptyToNull(String str)
//  {
//    if ((str != null) && ("".equals(str.trim()))) {
//      return null;
//    }
//    return str;
//  }
//
//  public String getUser() {
//    return this.user;
//  }
//
//  public void setConn(Connection conn)
//  {
//    this.conn = conn;
//  }
//
//  public String getDatabaseType() {
//    if (this.databaseType == null) {
//      this.databaseType = "oracle";
//    }
//    return this.databaseType;
//  }
//
//  public void setDatabaseType(String databaseType) {
//    this.databaseType = databaseType;
//  }
//
//  public String getDriver() {
//    if (this.driver == null) {
//      this.driver = "oracle.jdbc.OracleDriver";
//    }
//    return this.driver;
//  }
//
//  public void setDriver(String driver) {
//    this.driver = driver;
//  }
//
//  public String getPass() {
//    return this.pass;
//  }
//
//  public void setPass(String pass) {
//    this.pass = pass;
//  }
//
//  public PreparedStatement getPs() {
//    return this.ps;
//  }
//
//  public void setPs(PreparedStatement ps) {
//    this.ps = ps;
//  }
//
//  public ResultSet getRs() {
//    return this.rs;
//  }
//
//  public void setRs(ResultSet rs) {
//    this.rs = rs;
//  }
//
//  public Statement getStmt() {
//    return this.stmt;
//  }
//
//  public void setStmt(Statement stmt) {
//    this.stmt = stmt;
//  }
//
//  public String getUrl() {
//    return this.url;
//  }
//
//  public void setUrl(String url) {
//    this.url = url;
//  }
//
//  public void setUser(String user) {
//    this.user = user;
//  }
//}