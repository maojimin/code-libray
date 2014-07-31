package org.sevenstar.persistent.db.ibatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.persistent.db.MyDatabaseSouce;
import org.sevenstar.persistent.db.cfg.SdbConfigure;
import org.sevenstar.persistent.db.dialect.DialectFactory;
import org.sevenstar.persistent.db.exception.PersistentException;
import org.sevenstar.persistent.db.id.IDGeneratorFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author rtm 2005-9-4
 * @description
 * @spring.bean id="ibatisDao"
 */
public class IbatisDao {
	private static Log LOG = LogFactory.getLog(IbatisDao.class);

	private SqlMapClient sqlMap;

	private static ThreadLocal sqlThreadLocal = new ThreadLocal();

	private static long checkExecuteTime = -2;

	private static long resultSetSize = 0;

	private static Object LockObject = new Object();




	public static void startMonitor(long time) {
		if (time > -2) {
			synchronized (LockObject) {
				checkExecuteTime = time;
			}
		}
	}

	public static void setResultSetSize(long size) {
		if (size > 0) {
			synchronized (LockObject) {
				resultSetSize = size;
			}
		}
	}

	public static void stopMonitor() {
		synchronized (LockObject) {
			checkExecuteTime = -2;
		}
	}

	public static boolean isMonitor() {
		return checkExecuteTime > -2;
	}

	public static void main(String[] args) {
		insertSqlDelay("insert", 3, 4);
	}

	public void execute(String sql) {
		if (sql == null || "".equals(sql)) {
			throw new RuntimeException("sql is null");
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = MyAppSqlConfig.getSqlMapInstance().getDataSource().getConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
		} finally {
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
	
	/**
	 * sqlserver中只能取到秒数
	 * @return
	 */
	public static long getCurrentDBTimeInMillisSqlserver(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = MyAppSqlConfig.getSqlMapInstance().getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select datediff(second,'1970-01-01 08:00:00',getdate())");
			long currentTimes = 0;
			while(rs.next()){
				currentTimes =  rs.getLong(1);
				break;
			}
			return currentTimes*1000;
		}catch (Exception e) {
 			throw new RuntimeException(e);
		}finally{
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
	
	public static long getCurrentDBTimeInMillis(){
	  return  DialectFactory.getDialect(SdbConfigure.getSdbModel().getDatabase()).getCurrentDBTimeInMillis();
	}

	private static void insertSqlDelay(String sqlmapId, int resultset_size,
			long execute_time) {
		if (!isMonitor()) {
			return;
		}
		if (execute_time < checkExecuteTime && resultset_size < resultSetSize) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement ste = (StackTraceElement) stack[i];
			String str = ste.getClassName() + "," + ste.getMethodName() + "["
					+ ste.getFileName() + ":" + ste.getLineNumber() + "]<br>";
			sb.append(str);
		}
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into s_monitor_sql(id,SQLMAPID,RESULTSET_SIZE,STACKTRACE,EXECUTE_TIME,CREATE_DATE) values(seq_monitor.nextval,?,?,?,?,sysdate)";
		try {
			conn = MyAppSqlConfig.getSqlMapInstance().getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, sqlmapId);
			ps.setLong(2, resultset_size);
			ps.setString(3, sb.toString());
			ps.setLong(4, execute_time);
			ps.execute();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			LOG.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
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

	private static void addSqlmap(String sqlmap) {
		if (isMonitor()) {
			List list = (List) sqlThreadLocal.get();
			if (list == null) {
				list = new ArrayList();
			}
			list.add(sqlmap);
			sqlThreadLocal.set(list);
		}
	}

	public static int getSqlmapSize() {
		if (isMonitor()) {
			List list = (List) sqlThreadLocal.get();
			if (list == null) {
				list = new ArrayList();
			}
			return list.size();
		}
		return 0;
	}

	public static void clearSqlmap() {
		sqlThreadLocal.set(null);
	}

	public static List getSqlmapList() {
		List list = (List) sqlThreadLocal.get();
		if (list == null) {
			list = new ArrayList();
		}
		return list;
	}

	public static String getSqlmaps() {
		List list = getSqlmapList();
		String str = null;
		for (int i = 0; i < list.size(); i++) {
			if (str == null) {
				str = (String) list.get(i);
			} else {
				str = str + "," + (String) list.get(i);
			}
		}
		if (str == null) {
			str = "";
		}
		return str;
	}

	public static IbatisDao getDao() {
		return new IbatisDao();
	}

	public static IbatisDao getDao(SqlMapClient client) {
		IbatisDao dao = new IbatisDao();
		dao.setSqlMap(client);
		return dao;
	}

	public String generateStringId() {
		return (String) IDGeneratorFactory.generate(null,null);
	}

	/**
	 * @return
	 */
	public SqlMapClient getSqlMap() {
		if (this.sqlMap == null) {
			this.sqlMap = MyAppSqlConfig.getSqlMapInstance();
		}
		return this.sqlMap;
	}

	public void setSqlMap(SqlMapClient client) {
		this.sqlMap = client;
	}

	/**
	 * 得到记录总数，一般是select count(*)
	 *
	 * @param sqlMapId
	 * @param param
	 * @return
	 */
	public int count(String sqlMapId, Object param) {
		List list = null;
		int num = 0;
		long starttime = System.currentTimeMillis();
		try {
			addSqlmap(sqlMapId);
			list = this.getSqlMap().queryForList(sqlMapId, param);
		} catch (SQLException e) {
			throw new PersistentException(e);
		} finally {
			long count = System.currentTimeMillis() - starttime;
			if (list != null) {
				num = list.size();
			}
			insertSqlDelay(sqlMapId, num, count);

		}
		if (list != null && list.size() > 0) {
			try {
				if (list.size() > 1) {
					/**
					 * 说明不是count语句，返回size
					 */
					return list.size();
				} else {
					num = Integer.parseInt(String.valueOf(list.get(0)));
				}
			} catch (RuntimeException e) {
				LOG.error(e);
				throw new PersistentException(e);
			}
		}
		return num;
	}

	/**
	 * 得到主键seq
	 *
	 * @param sqlMapId
	 * @return
	 */
	public Long getIdValue(String sqlMapId) {
		long starttime = System.currentTimeMillis();
		try {
			addSqlmap(sqlMapId);
			return (Long) this.getSqlMap().queryForObject(sqlMapId, null);
		} catch (SQLException e) {
			throw new PersistentException(e);
		} finally {
			long count = System.currentTimeMillis() - starttime;
			insertSqlDelay(sqlMapId, 1, count);

		}
	}

	/**
	 * 新增修改
	 *
	 * @param sqlMapId
	 * @param param
	 */
	public void update(String sqlMapId, Object param) {
		long starttime = System.currentTimeMillis();
		int num = 0;
		try {
			addSqlmap(sqlMapId);
			num = this.getSqlMap().update(sqlMapId, param);
		} catch (SQLException e) {
			throw new PersistentException(e.getMessage());
		} finally {
			long count = System.currentTimeMillis() - starttime;
			insertSqlDelay(sqlMapId, num, count);
		}
	}

	public String queryForString(String sqlMapId, Object param) {
		Object value = this.queryForObject(sqlMapId, param);
		if (value != null) {
			return String.valueOf(value);
		} else {
			return null;
		}
	}

	public Long queryForLong(String sqlMapId, Object param) {
		Object value = this.queryForObject(sqlMapId, param);
		if (value != null) {
			if (value instanceof Long) {
				return (Long) value;
			}
			if (value instanceof String) {
				return new Long(Long.parseLong((String) value));
			}
			throw new PersistentException("required Long,but find ["
					+ value.getClass().getName() + "]");
		} else {
			return null;
		}
	}

	/**
	 * 查询
	 *
	 * @param sqlMapId
	 * @param param
	 * @return
	 */
	public List queryForList(String sqlMapId, Object param) {
		addSqlmap(sqlMapId);
		List list = null;
		long starttime = System.currentTimeMillis();
		try {
			list = this.getSqlMap().queryForList(sqlMapId, param);
		} catch (SQLException e) {
			throw new PersistentException(e);
		} finally {
			long count = System.currentTimeMillis() - starttime;
			int num = 0;
			if (list != null) {
				num = list.size();
			}
			insertSqlDelay(sqlMapId, num, count);

		}
		if (list == null) {
			list = new ArrayList();
		}
		return list;
	}

	/**
	 * 查询得到一个实体
	 *
	 * @param sqlMapId
	 * @param param
	 * @return
	 */
	public Object queryForObject(String sqlMapId, Object param) {
		addSqlmap(sqlMapId);
		Object value = null;
		long starttime = System.currentTimeMillis();
		try {

			value = this.getSqlMap().queryForObject(sqlMapId, param);
		} catch (SQLException e) {
			throw new PersistentException(e);
		} finally {
			long count = System.currentTimeMillis() - starttime;

			insertSqlDelay(sqlMapId, 1, count);

		}
		return value;

	}

	/**
	 * 查询是否存在数据
	 *
	 * @param sqlMapId
	 * @param param
	 * @return
	 */
	public boolean queryIfExist(String sqlMapId, Object param) {
		addSqlmap(sqlMapId);
		List list = null;
		long starttime = System.currentTimeMillis();
		try {
			list = this.getSqlMap().queryForList(sqlMapId, param);
		} catch (SQLException e) {
			throw new PersistentException(e);
		} finally {
			long count = System.currentTimeMillis() - starttime;

			insertSqlDelay(sqlMapId, 1, count);

		}
		if (list == null || list.size() == 0) {
			return false;
		} else {
			return true;
		}

	}
}
