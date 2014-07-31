package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.sql.PreparedStatement;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.IPreparedStatementFilter;

public interface IWrapperPreparedStatement {
	public PreparedStatement getPlainPreparedStatement();
	public WrapperConnection getWrapperConnection();
	public void setWrapperConnection(WrapperConnection wrapperConnection);
	public IPreparedStatementFilter getPreparedStatementFilter();
	public void setSql(String sql);
	public String getSql();
}
