package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.ICallableStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IPreparedStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IStatementFilter;

public class WrapperDataSource implements DataSource, JdbcFilter, Serializable {

	private DataSource datasource;

	private ICallableStatementFilter callableStatementFilter;

	private IPreparedStatementFilter preparedStatementFilter;

	private IStatementFilter statementFilter;

	private WrapperDataSource() {
		//
	}

	public WrapperDataSource(DataSource datasource) {
		this.datasource = datasource;
	}

	public Connection getConnection() throws SQLException {
		WrapperConnection conn = new WrapperConnection(datasource
				.getConnection());
		conn.setCallableStatementFilter(this.callableStatementFilter);
		conn.setPreparedStatementFilter(this.preparedStatementFilter);
		conn.setStatementFilter(this.statementFilter);
		// ConnectionFactory.register(conn);
		return conn;
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		WrapperConnection conn = new WrapperConnection(datasource
				.getConnection(username, password));
		conn.setCallableStatementFilter(this.callableStatementFilter);
		conn.setPreparedStatementFilter(this.preparedStatementFilter);
		conn.setStatementFilter(this.statementFilter);
		return conn;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return datasource.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return datasource.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		datasource.setLogWriter(out);

	}

	public void setLoginTimeout(int seconds) throws SQLException {
		datasource.setLoginTimeout(seconds);
	}

	public void setCallableStatementFilter(
			ICallableStatementFilter callableStatementFilter) {
		this.callableStatementFilter = callableStatementFilter;
	}

	public void setPreparedStatementFilter(
			IPreparedStatementFilter preparedStatementFilter) {
		this.preparedStatementFilter = preparedStatementFilter;
	}

	public void setStatementFilter(IStatementFilter statementFilter) {
		this.statementFilter = statementFilter;
	}

	public ICallableStatementFilter getCallableStatementFilter() {
		return this.callableStatementFilter;
	}

	public IPreparedStatementFilter getPreparedStatementFilter() {
		return this.preparedStatementFilter;
	}

	public IStatementFilter getStatementFilter() {
		return this.statementFilter;
	}
}
