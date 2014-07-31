package org.sevenstar.persistent.db.jdbc.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sevenstar.persistent.db.jdbc.conn.ConnectionFactory;
import org.sevenstar.persistent.db.jdbc.conn.DatabaseConnection;
import org.sevenstar.persistent.db.jdbc.page.AbstractPage;
import org.sevenstar.persistent.db.jdbc.page.OraclePageImpl;
import org.sevenstar.util.BeanHelper;

 
/**
 * 查询操作助手类
 * @author rtm
 *
 */
public class JdbcQuery {
	private DatabaseConnection dbconn;

	public static JdbcQuery getInstance() {
		DatabaseConnection dbconn = new DatabaseConnection(ConnectionFactory
				.getCurrentConnection());
		return new JdbcQuery(dbconn);
	}

	private JdbcQuery() {
		//this.dbconn = new DatabaseConnection();
	}

	private JdbcQuery(DatabaseConnection dbconn) {
		this.dbconn = dbconn;
	}

	public AbstractPage queryForPage(String sql,Class clazz,int page,int pagesize){
		AbstractPage dbpage = new OraclePageImpl();
		dbpage.getPageDateList(sql,clazz, page,pagesize);
		return dbpage;
	}

	public AbstractPage queryForPage(String sql,Class clazz,int page){
		AbstractPage dbpage = new OraclePageImpl();
		dbpage.getPageDateList(sql,clazz, page,15);
		return dbpage;
	}

	public List query(String sql) {
		List list = new ArrayList();
		try {
			ResultSet rs = dbconn.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			if (rsmd.getColumnCount() == 0) {
				return list;
			}
			while (rs.next()) {
				if (rsmd.getColumnCount() == 1) {
					list.add(rs.getString(1));
				} else {
					String[] ss = new String[rsmd.getColumnCount()];
					for (int i = 0; i < rsmd.getColumnCount(); i++) {
						ss[i] = rs.getString(i + 1);
					}
					list.add(ss);
				}
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dbconn.closeStmtAndRs();
		}
	}

	public Map queryForMap(String sql) {
		List list = queryForList(sql);
		if(list.size() > 1){
			throw new RuntimeException("返回多条数据");
		}
		if (list.size() == 1) {
			return (Map) list.get(0);
		} else {
			return new HashMap();
		}
	}

	public List queryForList(String sql) {
		List list = new ArrayList();
		try {
			ResultSet rs = dbconn.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				if (rsmd.getColumnCount() == 1) {
					list.add(rs.getString(1));
				} else {
					Map map = new HashMap();
					for (int i = 0; i < rsmd.getColumnCount(); i++) {
						map.put((rsmd.getColumnName(i + 1)).toLowerCase(), rs
								.getString(rsmd.getColumnName(i + 1)));
					}
					list.add(map);
				}
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			dbconn.closeStmtAndRs();
		}

	}

	public Long queryForLong(String sql) {
		Long value = null;
		try {
			ResultSet rs = dbconn.executeQuery(sql);
			while (rs.next()) {
				if(value != null){
					throw new RuntimeException("返回多条数据");
				}
				value = new Long(rs.getLong(1));
			}
			return value;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dbconn.closeStmtAndRs();
		}
	}

	public String queryForString(String sql) {
		String value = null;
		try {
			ResultSet rs = dbconn.executeQuery(sql);
			while (rs.next()) {
				if(value != null){
					throw new RuntimeException("返回多条数据");
				}
				value = rs.getString(1);
			}
			return value;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dbconn.closeStmtAndRs();
		}
	}

	public Object queryForObject(String sql, Class paramClass) {
		try {
			Object bean = null;
			ResultSet rs = dbconn.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				if(bean != null){
					throw new RuntimeException("返回多条数据");
				}
				bean = BeanHelper.newInstance(paramClass);
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i + 1).toLowerCase();
					if (BeanHelper.hasField(paramClass, columnName)) {
						Object value = rs.getObject(rsmd.getColumnName(i + 1));
						/**
						 * oracle number(10)返回数据类型为java.math.BigDecimal
						 * 但是一般使用Long,Double,Float等类型
						 */
						if (value instanceof BigDecimal) {
							if (!BeanHelper.getField(paramClass, columnName)
									.getType().equals(BigDecimal.class)) {
								BigDecimal bd = (BigDecimal) value;
								if (BeanHelper.getField(paramClass, columnName)
										.getType().equals(Long.class)) {
									BeanHelper.setPropertyValue(bean,
											columnName, Long.valueOf(bd
													.toString()));
								}
								if (BeanHelper.getField(paramClass, columnName)
										.getType().equals(Double.class)) {
									BeanHelper.setPropertyValue(bean,
											columnName, Double.valueOf(bd
													.toString()));
								}
								if (BeanHelper.getField(paramClass, columnName)
										.getType().equals(Float.class)) {
									BeanHelper.setPropertyValue(bean,
											columnName, Float.valueOf(bd
													.toString()));
								}
								continue;
							}
						}
						BeanHelper.setPropertyValue(bean, columnName, value);
					}
				}
			}
			return bean;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dbconn.closeStmtAndRs();
		}

	}

	public List queryForList(String sql, Class paramClass) {
		List list = new ArrayList();
		try {
			ResultSet rs = dbconn.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				Object bean = BeanHelper.newInstance(paramClass);
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i + 1).toLowerCase();
					if (BeanHelper.hasField(paramClass, columnName)) {
						Object value = rs.getObject(rsmd.getColumnName(i + 1));
						/**
						 * oracle number(10)返回数据类型为java.math.BigDecimal
						 * 但是一般使用Long,Double,Float等类型
						 */
						if (value instanceof BigDecimal) {
							if (!BeanHelper.getField(paramClass, columnName)
									.getType().equals(BigDecimal.class)) {
								BigDecimal bd = (BigDecimal) value;
								if (BeanHelper.getField(paramClass, columnName)
										.getType().equals(Long.class)) {
									BeanHelper.setPropertyValue(bean,
											columnName, Long.valueOf(bd
													.toString()));
								}
								if (BeanHelper.getField(paramClass, columnName)
										.getType().equals(Double.class)) {
									BeanHelper.setPropertyValue(bean,
											columnName, Double.valueOf(bd
													.toString()));
								}
								if (BeanHelper.getField(paramClass, columnName)
										.getType().equals(Float.class)) {
									BeanHelper.setPropertyValue(bean,
											columnName, Float.valueOf(bd
													.toString()));
								}
								continue;
							}
						}
						BeanHelper.setPropertyValue(bean, columnName, value);
					}
				}
				list.add(bean);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			dbconn.closeStmtAndRs();
		}
	}
/*
	public void close() {
		dbconn.close();
	}*/

}
