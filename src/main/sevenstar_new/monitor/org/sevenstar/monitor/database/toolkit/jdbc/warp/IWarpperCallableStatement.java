package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.sql.CallableStatement;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.ICallableStatementFilter;

public interface IWarpperCallableStatement {
	public CallableStatement getPlainCallableStatement();
	public WrapperConnection getWrapperConnection();
	public void setWrapperConnection(WrapperConnection wrapperConnection);
	public ICallableStatementFilter getCallableStatementFilter();
	public void setSql(String sql);
	public String getSql();
}
