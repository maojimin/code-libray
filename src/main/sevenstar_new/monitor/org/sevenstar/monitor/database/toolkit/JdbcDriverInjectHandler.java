package org.sevenstar.monitor.database.toolkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;

import javax.sql.DataSource;

import org.jwebap.toolkit.bytecode.asm.MethodInjectHandler;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.ICallableStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IPreparedStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.warp.JdbcFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.warp.WrapperConnection;
import org.sevenstar.monitor.database.toolkit.jdbc.warp.WrapperDataSource;



public class JdbcDriverInjectHandler implements MethodInjectHandler,JdbcFilter{

	private ICallableStatementFilter callableStatementFilter;

	private IPreparedStatementFilter preparedStatementFilter;

	private IStatementFilter statementFilter;

	public JdbcDriverInjectHandler() {
	}

	public Object invoke(Object target, Method method, Method methodProxy,
			Object[] args) throws Throwable {
		Object o;
		try {
			o = methodProxy.invoke(target, args);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		} finally {

		}
		if (!Modifier.isPrivate(method.getModifiers())
				&& Connection.class.equals(method.getReturnType())
				&& o instanceof Connection
				&& !(o instanceof WrapperConnection)) {
			WrapperConnection conn = new WrapperConnection((Connection) o);
			conn.setCallableStatementFilter(this.callableStatementFilter);
			conn.setPreparedStatementFilter(this.preparedStatementFilter);
			conn.setStatementFilter(this.statementFilter);
			return conn;
		} else if (!Modifier.isPrivate(method.getModifiers())
				&& DataSource.class.equals(method.getReturnType())
				&& o instanceof DataSource
				&& !(o instanceof WrapperDataSource)) {
			WrapperDataSource datasource = new WrapperDataSource( (DataSource) o);
			datasource.setCallableStatementFilter(this.callableStatementFilter);
			datasource.setPreparedStatementFilter(this.preparedStatementFilter);
			datasource.setStatementFilter(this.statementFilter);
			return datasource;
		} else {
			return o;
		}

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
