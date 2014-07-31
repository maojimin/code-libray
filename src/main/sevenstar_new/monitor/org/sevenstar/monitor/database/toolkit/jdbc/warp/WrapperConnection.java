package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;
import java.util.Random;

import org.sevenstar.monitor.database.toolkit.helper.UUIDHexGenerator;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.ICallableStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IPreparedStatementFilter;
import org.sevenstar.monitor.database.toolkit.jdbc.filter.IStatementFilter;

public class WrapperConnection implements Connection, JdbcFilter {

	private Connection conn;
	private String id;

	private ICallableStatementFilter callableStatementFilter;

	private IPreparedStatementFilter preparedStatementFilter;

	private IStatementFilter statementFilter;

	private StackTraceElement stackTraceElement[];

	public WrapperConnection(Connection conn) {
		this.conn = conn;
 		UUIDHexGenerator gen = new UUIDHexGenerator();
		id = gen.generate() + getRandomString(30);
		stackTraceElement = (new Throwable()).getStackTrace();
	}

	public StackTraceElement[] getStackTraceElement() {
		return stackTraceElement;
	}

	/*
	 * public void printStackTrace(){ if(stackTraceElement != null){
	 * System.out.println("----trace start-------"); for(int i=0;i<stackTraceElement.length;i++){
	 * StackTraceElement element = stackTraceElement[i];
	 * System.out.println("class["+element.getClassName()+"],method["+element.getMethodName()+"]");
	 * System.out.println("file["+element.getFileName()+"],line["+element.getLineNumber()+"]"); }
	 * System.out.println("----trace end-------"); } }
	 */

	public String getId() {
		return id;
	}

	private String getRandomString(int length) {
		long seed = 1;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < length; i++) {
			seed *= 10;
		}
		Random random = new Random(seed);
		long randomNum = random.nextLong();
		String strRandomNum = String.valueOf(randomNum);
		int len = strRandomNum.length();
		// 位数不够补0[在前面补0]
		for (int i = len; i < length; i++) {
			result.append("0");
		}
		result.append(strRandomNum);
		return result.toString();
	}

	public void clearWarnings() throws SQLException {
		conn.clearWarnings();
	}

	public void close() throws SQLException {
		conn.close();
		// ConnectionFactory.remove(this);
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public Statement createStatement() throws SQLException {
		WrapperStatement stmt = new WrapperStatement(conn.createStatement());
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		WrapperStatement stmt = new WrapperStatement(conn.createStatement(resultSetType,
				resultSetConcurrency));
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		WrapperStatement stmt =  new WrapperStatement(conn.createStatement(resultSetType,
				resultSetConcurrency, resultSetHoldability));
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public boolean getAutoCommit() throws SQLException {
		return conn.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return conn.getCatalog();
	}

	public int getHoldability() throws SQLException {
		return conn.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return conn.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return conn.getTransactionIsolation();
	}

	public Map getTypeMap() throws SQLException {
		return conn.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return conn.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return conn.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return conn.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException {
		return conn.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		WarpperCallableStatement stmt =  new WarpperCallableStatement(conn.prepareCall(sql));
		stmt.setWrapperConnection(this);
		stmt.setSql(sql);
		return stmt;
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		WarpperCallableStatement stmt =   new WarpperCallableStatement(conn.prepareCall(sql,
				resultSetType, resultSetConcurrency));
		stmt.setWrapperConnection(this);
		stmt.setSql(sql);
		return stmt;
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		WarpperCallableStatement stmt =  new WarpperCallableStatement(conn.prepareCall(sql,
				resultSetType, resultSetConcurrency, resultSetHoldability));
		stmt.setWrapperConnection(this);
		stmt.setSql(sql);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		WrapperPreparedStatement stmt = new WrapperPreparedStatement(conn.prepareStatement(sql));
		stmt.setSql(sql);
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		WrapperPreparedStatement stmt =  new WrapperPreparedStatement(conn.prepareStatement(sql,
				autoGeneratedKeys));
		stmt.setSql(sql);
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		WrapperPreparedStatement stmt =   new WrapperPreparedStatement(conn.prepareStatement(sql,
				columnIndexes));
		stmt.setSql(sql);
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		WrapperPreparedStatement stmt =   new WrapperPreparedStatement(conn.prepareStatement(sql,
				columnNames));
		stmt.setSql(sql);
		stmt.setWrapperConnection(this);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		WrapperPreparedStatement stmt = new WrapperPreparedStatement(conn.prepareStatement(sql,
				resultSetType, resultSetConcurrency));
		stmt.setWrapperConnection(this);
		stmt.setSql(sql);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		WrapperPreparedStatement stmt = new WrapperPreparedStatement(conn.prepareStatement(sql,
				resultSetType, resultSetConcurrency, resultSetHoldability));
		stmt.setWrapperConnection(this);
		stmt.setSql(sql);
		return stmt;
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		conn.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException {
		conn.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		conn.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException {
		conn.setCatalog(catalog);
	}

	public void setHoldability(int holdability) throws SQLException {
		conn.setHoldability(holdability);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		conn.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException {
		return conn.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return conn.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		conn.setTransactionIsolation(level);
	}

	public void setTypeMap(Map  map) throws SQLException {
		conn.setTypeMap(map);
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
