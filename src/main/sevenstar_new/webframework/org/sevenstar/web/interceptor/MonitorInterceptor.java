package org.sevenstar.web.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.web.context.WebContext;
import org.sevenstar.web.invocation.Invocation;
import org.sevenstar.persistent.db.ibatis.IbatisDao;
import org.sevenstar.persistent.db.ibatis.MyAppSqlConfig;

public class MonitorInterceptor implements Interceptor {

	private static Log LOG = LogFactory.getLog(MonitorInterceptor.class);

	private Map paramMap;

	public Map getParamMap() {
		if (this.paramMap == null) {
			this.paramMap = new HashMap();
		}
		return this.paramMap;
	}

	private long sqlExecuteTime = -2;

	private long actionExecuteTime = -2;

	private long resultSetSize = 0;

	private long sqlMapIds = 0;

	public Object intercept(Invocation invocation) {
		long starttime = System.currentTimeMillis();
		if (isMonitor()) {
			LOG.debug("start monitor interceptor");
		}
		try {
			return invocation.invoke();
		} finally {
			long count = System.currentTimeMillis() - starttime;
			if (isMonitor()
					&& (count > actionExecuteTime || (sqlMapIds > 0 && IbatisDao
							.getSqlmapSize() > sqlMapIds))) {
				log(WebContext.getUrl(),count,IbatisDao.getSqlmaps(),IbatisDao.getSqlmapSize());
			}
			if (IbatisDao.isMonitor()) {
				IbatisDao.clearSqlmap();
			}
		}
	}
	
	private void log(String url,long count,String sqlmaps,int sqlmapsize){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into s_monitor_action(id,url,EXECUTE_TIME,SQLMAPID,CREATE_DATE,SQLMAPID_NUM) values(seq_monitor.nextval,?,?,?,sysdate,?)";
		try {
			conn = MyAppSqlConfig.getSqlMapInstance().getDataSource().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, url);
			ps.setLong(2, count);
			ps.setString(3, sqlmaps);
			ps.setLong(4, sqlmapsize);
			ps.execute();
			if (!conn.getAutoCommit()) {
				conn.commit();
			}
		} catch (RuntimeException e) {
			LOG.error(e);
		} catch (Exception e) {
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

	public boolean isMonitor() {
		return actionExecuteTime > -2;
	}

	public void setParamMap(Map map) {
		this.paramMap = map;
		if (this.paramMap == null) {
			this.paramMap = new HashMap();
		}
		if (this.paramMap.get("resultSetSize") != null
				&& !"".equals(this.paramMap.get("resultSetSize"))) {
			try {
				resultSetSize = Long.parseLong((String) this.paramMap
						.get("resultSetSize"));
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		if (this.paramMap.get("sqlExecuteTime") != null
				&& !"".equals(this.paramMap.get("sqlExecuteTime"))) {
			try {
				sqlExecuteTime = Long.parseLong((String) this.paramMap
						.get("sqlExecuteTime"));
				if (!IbatisDao.isMonitor()) {
					IbatisDao.startMonitor(sqlExecuteTime);
					IbatisDao.setResultSetSize(resultSetSize);
				}
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		if (this.paramMap.get("actionExecuteTime") != null) {
			try {
				actionExecuteTime = Long.parseLong((String) this.paramMap
						.get("actionExecuteTime"));
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		if (this.paramMap.get("sqlMapIds") != null) {
			try {
				sqlMapIds = Long.parseLong((String) this.paramMap
						.get("sqlMapIds"));
			} catch (Exception e) {
				LOG.error(e);
			}
		}

	}

}
