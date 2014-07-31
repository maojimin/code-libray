package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sevenstar.monitor.database.filter.jdbc.CallableStatementFilterImpl;
import org.sevenstar.monitor.database.filter.jdbc.PreparedStatementFilterImpl;
import org.sevenstar.monitor.database.filter.jdbc.StatementFilterImpl;

public class WrapperDriver implements Driver {

	private static Driver oracleDriver = null;

	static {
		WrapperDriver wrapperDriver = new WrapperDriver();
		try {
			DriverManager.registerDriver(wrapperDriver);
			Class driverClass = Class
					.forName("oracle.jdbc.driver.OracleDriver");
			oracleDriver = (Driver) driverClass.newInstance();
 		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static Log LOG = LogFactory
			.getLog("org.sevenstar.monitor.connection.toolkit.jdbc.warp.WrapperDriver");

	private static String ORACLEDRIVER = "";

	public boolean acceptsURL(String url) throws SQLException {
		LOG.debug("acceptsURL(" + url + ")");
		return oracleDriver.acceptsURL(url);
	}

	public Connection connect(String url, Properties info) throws SQLException {
		LOG.debug("connect(" + url + ",properties["+info+"])");
		WrapperConnection wc = new WrapperConnection(oracleDriver.connect(url,
				info));
		wc.setCallableStatementFilter(new CallableStatementFilterImpl());
		wc.setPreparedStatementFilter(new PreparedStatementFilterImpl());
		wc.setStatementFilter(new StatementFilterImpl());
		return wc;
	}

	public int getMajorVersion() {
		return oracleDriver.getMajorVersion();
	}

	public int getMinorVersion() {
		return oracleDriver.getMinorVersion();
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		return oracleDriver.getPropertyInfo(url, info);
	}

	public boolean jdbcCompliant() {
		return oracleDriver.jdbcCompliant();
	}

}
