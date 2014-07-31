package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.sql.Statement;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.IStatementFilter;

public interface IWrapperStatement {
	public Statement getPlainStatement();
	public WrapperConnection getWrapperConnection();
	public void setWrapperConnection(WrapperConnection wrapperConnection);
	public IStatementFilter getStatementFilter();
}
