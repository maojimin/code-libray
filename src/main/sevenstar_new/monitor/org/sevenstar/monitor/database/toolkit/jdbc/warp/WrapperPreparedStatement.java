package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.IPreparedStatementFilter;

public class WrapperPreparedStatement implements PreparedStatement,
		IWrapperPreparedStatement {

	private PreparedStatement preparedStatement;

	private WrapperConnection wrapperConnection;

    private List paramList = new ArrayList();

    private String sql;

	private WrapperPreparedStatement() {
		//
	}

	public void addParam(int x,Object paramValue){
		if(paramList.size() < x){
			while(paramList.size() < x){
				paramList.add(null);
			}
		}
		paramList.set(x-1, paramValue);
	}

	public WrapperPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public PreparedStatement getPlainPreparedStatement() {
		return this.preparedStatement;
	}

	public void addBatch() throws SQLException {
		this.preparedStatement.addBatch();
	}

	public void clearParameters() throws SQLException {
		this.preparedStatement.clearParameters();
	}

	public boolean execute() throws SQLException {
		IPreparedStatementFilter filter = this.getPreparedStatementFilter();
		filter.setSql(this.getSql());
		return filter.execute(
				this.getPlainPreparedStatement());
	}

	public ResultSet executeQuery() throws SQLException {
		IPreparedStatementFilter filter = this.getPreparedStatementFilter();
		filter.setSql(this.getSql());
		return new WrapperResultSet(filter
				.executeQuery(this.getPlainPreparedStatement()));
	}

	public int executeUpdate() throws SQLException {
		IPreparedStatementFilter filter = this.getPreparedStatementFilter();
		filter.setSql(this.getSql());
		return filter.executeUpdate(this.getPlainPreparedStatement());
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return this.preparedStatement.getMetaData();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		return this.preparedStatement.getParameterMetaData();
	}

	public void setArray(int i, Array x) throws SQLException {
		this.preparedStatement.setArray(i, x);
		addParam(i,x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		this.preparedStatement.setAsciiStream(parameterIndex, x, length);
		addParam(parameterIndex,x);
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x)
			throws SQLException {
		this.preparedStatement.setBigDecimal(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		this.preparedStatement.setBinaryStream(parameterIndex, x, length);
		addParam(parameterIndex,x);
	}

	public void setBlob(int i, Blob x) throws SQLException {
		this.preparedStatement.setBlob(i, x);
		addParam(i,x);
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		this.preparedStatement.setBoolean(parameterIndex, x);
		addParam(parameterIndex,new Boolean(x));
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		this.preparedStatement.setByte(parameterIndex, x);
		addParam(parameterIndex,new Byte(x));
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		this.preparedStatement.setBytes(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		this.preparedStatement.setCharacterStream(parameterIndex, reader,
				length);
		addParam(parameterIndex,reader);
	}

	public void setClob(int i, Clob x) throws SQLException {
		this.preparedStatement.setClob(i, x);
		addParam(i,x);
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		this.preparedStatement.setDate(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		this.preparedStatement.setDate(parameterIndex, x, cal);
		addParam(parameterIndex,x);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		this.preparedStatement.setDouble(parameterIndex, x);
		addParam(parameterIndex,new Double(x));
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		this.preparedStatement.setFloat(parameterIndex, x);
		addParam(parameterIndex,new Float(x));
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		this.preparedStatement.setInt(parameterIndex, x);
		addParam(parameterIndex,new Integer(x));
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		this.preparedStatement.setLong(parameterIndex, x);
		addParam(parameterIndex,new Long(x));
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		this.preparedStatement.setNull(parameterIndex, sqlType);
		//parameterList.set(parameterIndex,new Integer(sqlType));
	}

	public void setNull(int paramIndex, int sqlType, String typeName)
			throws SQLException {
		this.preparedStatement.setNull(paramIndex, sqlType, typeName);
 	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		this.preparedStatement.setObject(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		this.preparedStatement.setObject(parameterIndex, x, targetSqlType);
		addParam(parameterIndex,x);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scale) throws SQLException {
		this.preparedStatement.setObject(parameterIndex, x, targetSqlType,
				scale);
		addParam(parameterIndex,x);
	}

	public void setRef(int i, Ref x) throws SQLException {
		this.preparedStatement.setRef(i, x);
		addParam(i,x);
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		this.preparedStatement.setShort(parameterIndex, x);
		addParam(parameterIndex,new Short(x));
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		this.preparedStatement.setString(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		this.preparedStatement.setTime(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		this.preparedStatement.setTime(parameterIndex, x, cal);
		addParam(parameterIndex,x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		this.preparedStatement.setTimestamp(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		this.preparedStatement.setTimestamp(parameterIndex, x, cal);
		addParam(parameterIndex,x);
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		this.preparedStatement.setURL(parameterIndex, x);
		addParam(parameterIndex,x);
	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		this.preparedStatement.setUnicodeStream(parameterIndex, x, length);
		addParam(parameterIndex,x);
	}

	public void addBatch(String sql) throws SQLException {
		this.preparedStatement.addBatch(sql);
	}

	public void cancel() throws SQLException {
		this.preparedStatement.cancel();
	}

	public void clearBatch() throws SQLException {
		this.preparedStatement.clearBatch();
	}

	public void clearWarnings() throws SQLException {
		this.preparedStatement.clearWarnings();
	}

	public void close() throws SQLException {
		this.preparedStatement.close();
	}

	public boolean execute(String sql) throws SQLException {
		return this.getPreparedStatementFilter().execute(
				this.getPlainPreparedStatement(), sql);
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.getPreparedStatementFilter().execute(
				this.getPlainPreparedStatement(), sql, autoGeneratedKeys);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return this.getPreparedStatementFilter().execute(
				this.getPlainPreparedStatement(), sql, columnIndexes);
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return this.getPreparedStatementFilter().execute(
				this.getPlainPreparedStatement(), sql, columnNames);
	}

	public int[] executeBatch() throws SQLException {
		return this.getPreparedStatementFilter().executeBatch(
				this.getPlainPreparedStatement());
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return new WrapperResultSet(this.getPreparedStatementFilter()
				.executeQuery(this.getPlainPreparedStatement(), sql));
	}

	public int executeUpdate(String sql) throws SQLException {
		return this.getPreparedStatementFilter().executeUpdate(
				this.getPlainPreparedStatement(), sql);
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.getPreparedStatementFilter().executeUpdate(
				this.getPlainPreparedStatement(), sql, autoGeneratedKeys);
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return this.getPreparedStatementFilter().executeUpdate(
				this.getPlainPreparedStatement(), sql, columnIndexes);
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return this.getPreparedStatementFilter().executeUpdate(
				this.getPlainPreparedStatement(), sql, columnNames);
	}

	public Connection getConnection() throws SQLException {
		return this.preparedStatement.getConnection();
	}

	public int getFetchDirection() throws SQLException {
		return this.preparedStatement.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return this.preparedStatement.getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		ResultSet rs = this.preparedStatement.getGeneratedKeys();
		if(rs != null){
			return new WrapperResultSet(rs);
		}
		return null;
	}

	public int getMaxFieldSize() throws SQLException {
		return this.preparedStatement.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException {
		return this.preparedStatement.getMaxRows();
	}

	public boolean getMoreResults() throws SQLException {
		return this.preparedStatement.getMoreResults();
	}

	public boolean getMoreResults(int current) throws SQLException {
		return this.preparedStatement.getMoreResults(current);
	}

	public int getQueryTimeout() throws SQLException {
		return this.preparedStatement.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException {
		ResultSet rs = this.preparedStatement.getResultSet();
		  if(rs != null){
				return new WrapperResultSet(rs);
			}
			return null;
	}

	public int getResultSetConcurrency() throws SQLException {
		return this.preparedStatement.getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException {
		return this.preparedStatement.getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException {
		return this.preparedStatement.getResultSetType();
	}

	public int getUpdateCount() throws SQLException {
		return this.preparedStatement.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.preparedStatement.getWarnings();
	}

	public void setCursorName(String name) throws SQLException {
		this.preparedStatement.setCursorName(name);
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		this.preparedStatement.setEscapeProcessing(enable);
	}

	public void setFetchDirection(int direction) throws SQLException {
		this.preparedStatement.setFetchDirection(direction);
	}

	public void setFetchSize(int rows) throws SQLException {
		this.preparedStatement.setFetchSize(rows);
	}

	public void setMaxFieldSize(int max) throws SQLException {
		this.preparedStatement.setMaxFieldSize(max);
	}

	public void setMaxRows(int max) throws SQLException {
		this.preparedStatement.setMaxRows(max);
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		this.preparedStatement.setQueryTimeout(seconds);
	}

	public IPreparedStatementFilter getPreparedStatementFilter() {
		IPreparedStatementFilter filter = this.getWrapperConnection().getPreparedStatementFilter();
		filter.setParamList(this.paramList);
		return filter;
	}

	public WrapperConnection getWrapperConnection() {
		return this.wrapperConnection;
	}

	public void setWrapperConnection(WrapperConnection wrapperConnection) {
		this.wrapperConnection = wrapperConnection;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		 this.sql = sql;
	}

}
