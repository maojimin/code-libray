package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.ICallableStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IPreparedStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IStatementFilter;

public interface JdbcFilter {
	public void setCallableStatementFilter(ICallableStatementFilter callableStatementFilter);
	  public ICallableStatementFilter getCallableStatementFilter();
	  public void setPreparedStatementFilter(IPreparedStatementFilter preparedStatementFilter);
	  public IPreparedStatementFilter getPreparedStatementFilter();
	  public void setStatementFilter(IStatementFilter statementFilter);
	  public IStatementFilter getStatementFilter();
}
