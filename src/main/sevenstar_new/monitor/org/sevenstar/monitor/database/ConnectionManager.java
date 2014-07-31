package org.sevenstar.monitor.database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jwebap.toolkit.bytecode.ClassEnhancer;
import org.jwebap.toolkit.bytecode.InjectException;
import org.sevenstar.monitor.database.filter.jdbc.CallableStatementFilterImpl;
import org.sevenstar.monitor.database.filter.jdbc.PreparedStatementFilterImpl;
import org.sevenstar.monitor.database.filter.jdbc.StatementFilterImpl;
import org.sevenstar.monitor.database.toolkit.JdbcDriverInjectHandler;
import org.sevenstar.monitor.database.toolkit.jdbc.warp.WrapperDataSource;




public class ConnectionManager {
	private static Log log = LogFactory.getLog(ConnectionManager.class);

	public static void bindDataSource(String jndiName) {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			Object dataSource = ctx.lookup(jndiName);
			if (dataSource != null) {
				ctx.unbind(jndiName);
				WrapperDataSource wrapperDataSource = new WrapperDataSource(
						(DataSource) dataSource);
				wrapperDataSource
						.setCallableStatementFilter(new CallableStatementFilterImpl());
				wrapperDataSource
						.setPreparedStatementFilter(new PreparedStatementFilterImpl());
				wrapperDataSource.setStatementFilter(new StatementFilterImpl());
				ctx.bind(jndiName, wrapperDataSource);
				log.debug("wrapper datasource [" + jndiName + "]");
			} else {
				log.error("not found datasource [" + jndiName + "]");
			}
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void enhancerOracleDriver() {
		ClassEnhancer enhancer = new ClassEnhancer();
		JdbcDriverInjectHandler handler = new JdbcDriverInjectHandler();
		handler.setCallableStatementFilter(new CallableStatementFilterImpl());
		handler.setPreparedStatementFilter(new PreparedStatementFilterImpl());
		handler.setStatementFilter(new StatementFilterImpl());
		try {
			enhancer.createClass("oracle.jdbc.driver.OracleDriver",
					handler);
		} catch (ClassNotFoundException e) {
			log.error("has not found oracle dirver");
			log.error(e);
		} catch (InjectException e) {
			log.error(e);
		}
	}

}
