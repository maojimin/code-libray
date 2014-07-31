package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
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
import java.util.Map;

import org.sevenstar.monitor.database.toolkit.jdbc.filter.ICallableStatementFilter;

public class WarpperCallableStatement implements CallableStatement,
		IWarpperCallableStatement {
     //OracleCallableStatement
	private CallableStatement callableStatement;

	private WrapperConnection wrapperConnection;

	private String sql;

	private WarpperCallableStatement() {
		//
	}

	private List paramList = new ArrayList();

	public void addParam(int x, Object paramValue) {
		if (paramList.size() < x) {
			while (paramList.size() < x) {
				paramList.add(null);
			}
		}
		paramList.set(x - 1, paramValue);
	}

	public CallableStatement getPlainCallableStatement() {
		return this.callableStatement;
	}

	public WarpperCallableStatement(CallableStatement callableStatement) {
		this.callableStatement = callableStatement;
	}

	public Array getArray(int i) throws SQLException {
		return this.callableStatement.getArray(i);
	}

	public Array getArray(String parameterName) throws SQLException {
		return this.callableStatement.getArray(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
		return this.callableStatement.getBigDecimal(parameterIndex);
	}

	public BigDecimal getBigDecimal(String parameterName) throws SQLException {
		return this.callableStatement.getBigDecimal(parameterName);
	}

	public BigDecimal getBigDecimal(int parameterIndex, int scale)
			throws SQLException {
		return this.callableStatement.getBigDecimal(parameterIndex, scale);
	}

	public Blob getBlob(int i) throws SQLException {
		return this.callableStatement.getBlob(i);
	}

	public Blob getBlob(String parameterName) throws SQLException {
		return this.callableStatement.getBlob(parameterName);
	}

	public boolean getBoolean(int parameterIndex) throws SQLException {
		return this.callableStatement.getBoolean(parameterIndex);
	}

	public boolean getBoolean(String parameterName) throws SQLException {
		return this.callableStatement.getBoolean(parameterName);
	}

	public byte getByte(int parameterIndex) throws SQLException {
		return this.callableStatement.getByte(parameterIndex);
	}

	public byte getByte(String parameterName) throws SQLException {
		return this.callableStatement.getByte(parameterName);
	}

	public byte[] getBytes(int parameterIndex) throws SQLException {
		return this.callableStatement.getBytes(parameterIndex);
	}

	public byte[] getBytes(String parameterName) throws SQLException {
		return this.callableStatement.getBytes(parameterName);
	}

	public Clob getClob(int i) throws SQLException {
		return this.callableStatement.getClob(i);
	}

	public Clob getClob(String parameterName) throws SQLException {
		return this.callableStatement.getClob(parameterName);
	}

	public Date getDate(int parameterIndex) throws SQLException {
		return this.callableStatement.getDate(parameterIndex);
	}

	public Date getDate(String parameterName) throws SQLException {
		return this.callableStatement.getDate(parameterName);
	}

	public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
		return this.callableStatement.getDate(parameterIndex, cal);
	}

	public Date getDate(String parameterName, Calendar cal) throws SQLException {
		return this.callableStatement.getDate(parameterName, cal);
	}

	public double getDouble(int parameterIndex) throws SQLException {
		return this.callableStatement.getDouble(parameterIndex);
	}

	public double getDouble(String parameterName) throws SQLException {
		return this.callableStatement.getDouble(parameterName);
	}

	public float getFloat(int parameterIndex) throws SQLException {
		return this.callableStatement.getFloat(parameterIndex);
	}

	public float getFloat(String parameterName) throws SQLException {
		return this.callableStatement.getFloat(parameterName);
	}

	public int getInt(int parameterIndex) throws SQLException {
		return this.callableStatement.getInt(parameterIndex);
	}

	public int getInt(String parameterName) throws SQLException {
		return this.callableStatement.getInt(parameterName);
	}

	public long getLong(int parameterIndex) throws SQLException {
		return this.callableStatement.getLong(parameterIndex);
	}

	public long getLong(String parameterName) throws SQLException {
		return this.callableStatement.getLong(parameterName);
	}

	public Object getObject(int parameterIndex) throws SQLException {
		return this.callableStatement.getObject(parameterIndex);
	}

	public Object getObject(String parameterName) throws SQLException {
		return this.callableStatement.getObject(parameterName);
	}

	public Ref getRef(int i) throws SQLException {
		return this.callableStatement.getRef(i);
	}

	public Ref getRef(String parameterName) throws SQLException {
		return this.callableStatement.getRef(parameterName);
	}

	public short getShort(int parameterIndex) throws SQLException {
		return this.callableStatement.getShort(parameterIndex);
	}

	public short getShort(String parameterName) throws SQLException {
		return this.callableStatement.getShort(parameterName);
	}

	public String getString(int parameterIndex) throws SQLException {
		return this.callableStatement.getString(parameterIndex);
	}

	public String getString(String parameterName) throws SQLException {
		return this.callableStatement.getString(parameterName);
	}

	public Time getTime(int parameterIndex) throws SQLException {
		return this.callableStatement.getTime(parameterIndex);
	}

	public Time getTime(String parameterName) throws SQLException {
		return this.callableStatement.getTime(parameterName);
	}

	public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
		return this.callableStatement.getTime(parameterIndex, cal);
	}

	public Time getTime(String parameterName, Calendar cal) throws SQLException {
		return this.callableStatement.getTime(parameterName, cal);
	}

	public Timestamp getTimestamp(int parameterIndex) throws SQLException {
		return this.callableStatement.getTimestamp(parameterIndex);
	}

	public Timestamp getTimestamp(String parameterName) throws SQLException {
		return this.callableStatement.getTimestamp(parameterName);
	}

	public Timestamp getTimestamp(int parameterIndex, Calendar cal)
			throws SQLException {
		return this.callableStatement.getTimestamp(parameterIndex, cal);
	}

	public Timestamp getTimestamp(String parameterName, Calendar cal)
			throws SQLException {
		return this.callableStatement.getTimestamp(parameterName, cal);
	}

	public URL getURL(int parameterIndex) throws SQLException {
		return this.callableStatement.getURL(parameterIndex);
	}

	public URL getURL(String parameterName) throws SQLException {
		return this.callableStatement.getURL(parameterName);
	}

	public void registerOutParameter(int parameterIndex, int sqlType)
			throws SQLException {
		this.callableStatement.registerOutParameter(parameterIndex, sqlType);
	}

	public void registerOutParameter(String parameterName, int sqlType)
			throws SQLException {
		this.callableStatement.registerOutParameter(parameterName, sqlType);
	}

	public void registerOutParameter(int parameterIndex, int sqlType, int scale)
			throws SQLException {
		this.callableStatement.registerOutParameter(parameterIndex, sqlType,
				scale);
	}

	public void registerOutParameter(int paramIndex, int sqlType,
			String typeName) throws SQLException {
		this.callableStatement.registerOutParameter(paramIndex, sqlType,
				typeName);
	}

	public void registerOutParameter(String parameterName, int sqlType,
			int scale) throws SQLException {
		this.callableStatement.registerOutParameter(parameterName, sqlType,
				scale);
	}

	public void registerOutParameter(String parameterName, int sqlType,
			String typeName) throws SQLException {
		this.callableStatement.registerOutParameter(parameterName, sqlType,
				typeName);
	}

	public void setAsciiStream(String parameterName, InputStream x, int length)
			throws SQLException {
		this.callableStatement.setAsciiStream(parameterName, x, length);
	}

	public void setBigDecimal(String parameterName, BigDecimal x)
			throws SQLException {
		this.callableStatement.setBigDecimal(parameterName, x);
	}

	public void setBinaryStream(String parameterName, InputStream x, int length)
			throws SQLException {
		this.callableStatement.setBinaryStream(parameterName, x, length);
	}

	public void setBoolean(String parameterName, boolean x) throws SQLException {
		this.callableStatement.setBoolean(parameterName, x);
	}

	public void setByte(String parameterName, byte x) throws SQLException {
		this.callableStatement.setByte(parameterName, x);
	}

	public void setBytes(String parameterName, byte[] x) throws SQLException {
		this.callableStatement.setBytes(parameterName, x);
	}

	public void setCharacterStream(String parameterName, Reader reader,
			int length) throws SQLException {
		this.callableStatement
				.setCharacterStream(parameterName, reader, length);
	}

	public void setDate(String parameterName, Date x) throws SQLException {
		this.callableStatement.setDate(parameterName, x);
	}

	public void setDate(String parameterName, Date x, Calendar cal)
			throws SQLException {
		this.callableStatement.setDate(parameterName, x);
	}

	public void setDouble(String parameterName, double x) throws SQLException {
		this.callableStatement.setDouble(parameterName, x);
	}

	public void setFloat(String parameterName, float x) throws SQLException {
		this.callableStatement.setFloat(parameterName, x);
	}

	public void setInt(String parameterName, int x) throws SQLException {
		this.callableStatement.setInt(parameterName, x);
	}

	public void setLong(String parameterName, long x) throws SQLException {
		this.callableStatement.setLong(parameterName, x);
	}

	public void setNull(String parameterName, int sqlType) throws SQLException {
		this.callableStatement.setNull(parameterName, sqlType);
	}

	public void setNull(String parameterName, int sqlType, String typeName)
			throws SQLException {
		this.callableStatement.setNull(parameterName, sqlType, typeName);
	}

	public void setObject(String parameterName, Object x) throws SQLException {
		this.callableStatement.setObject(parameterName, x);
	}

	public void setObject(String parameterName, Object x, int targetSqlType)
			throws SQLException {
		this.callableStatement.setObject(parameterName, x, targetSqlType);
	}

	public void setObject(String parameterName, Object x, int targetSqlType,
			int scale) throws SQLException {
		this.callableStatement
				.setObject(parameterName, x, targetSqlType, scale);
	}

	public void setShort(String parameterName, short x) throws SQLException {
		this.callableStatement.setShort(parameterName, x);
	}

	public void setString(String parameterName, String x) throws SQLException {
		this.callableStatement.setString(parameterName, x);
	}

	public void setTime(String parameterName, Time x) throws SQLException {
		this.callableStatement.setTime(parameterName, x);
	}

	public void setTime(String parameterName, Time x, Calendar cal)
			throws SQLException {
		this.callableStatement.setTime(parameterName, x, cal);
	}

	public void setTimestamp(String parameterName, Timestamp x)
			throws SQLException {
		this.callableStatement.setTimestamp(parameterName, x);
	}

	public void setTimestamp(String parameterName, Timestamp x, Calendar cal)
			throws SQLException {
		this.callableStatement.setTimestamp(parameterName, x, cal);
	}

	public void setURL(String parameterName, URL val) throws SQLException {
		this.callableStatement.setURL(parameterName, val);
	}

	public boolean wasNull() throws SQLException {
		return this.callableStatement.wasNull();
	}

	public void addBatch() throws SQLException {
		this.callableStatement.addBatch();
	}

	public void clearParameters() throws SQLException {
		this.callableStatement.clearParameters();
	}

	public boolean execute() throws SQLException {
		ICallableStatementFilter filter = this.getCallableStatementFilter();
		filter.setSql(this.getSql());
		return filter.execute(this.getPlainCallableStatement());
	}

	public ResultSet executeQuery() throws SQLException {
		ICallableStatementFilter filter = this.getCallableStatementFilter();
		filter.setSql(this.getSql());
 		return new WrapperResultSet(filter.executeQuery(this
				.getPlainCallableStatement()));
	}

	public int executeUpdate() throws SQLException {
		ICallableStatementFilter filter = this.getCallableStatementFilter();
		filter.setSql(this.getSql());
		return filter.executeUpdate(this.getPlainCallableStatement());
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return this.callableStatement.getMetaData();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		return this.callableStatement.getParameterMetaData();
	}

	public void setArray(int i, Array x) throws SQLException {
		this.callableStatement.setArray(i, x);
		addParam(i, x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		this.callableStatement.setAsciiStream(parameterIndex, x, length);
		addParam(parameterIndex, x);
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x)
			throws SQLException {
		this.callableStatement.setBigDecimal(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		this.callableStatement.setBinaryStream(parameterIndex, x, length);
		addParam(parameterIndex, x);
	}

	public void setBlob(int i, Blob x) throws SQLException {
		this.callableStatement.setBlob(i, x);
		addParam(i, x);
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		this.callableStatement.setBoolean(parameterIndex, x);
		addParam(parameterIndex, new Boolean(x));
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		this.callableStatement.setByte(parameterIndex, x);
		addParam(parameterIndex, new Byte(x));
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		this.callableStatement.setBytes(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		this.callableStatement.setCharacterStream(parameterIndex, reader,
				length);
		addParam(parameterIndex, reader);
	}

	public void setClob(int i, Clob x) throws SQLException {
		this.callableStatement.setClob(i, x);
		addParam(i, x);
	}

	public void setDate(int parameterIndex, Date x) throws SQLException {
		this.callableStatement.setDate(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		this.callableStatement.setDate(parameterIndex, x, cal);
		addParam(parameterIndex, x);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		this.callableStatement.setDouble(parameterIndex, x);
		addParam(parameterIndex, new Double(x));
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		this.callableStatement.setFloat(parameterIndex, x);
		addParam(parameterIndex, new Float(x));
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		this.callableStatement.setInt(parameterIndex, x);
		addParam(parameterIndex, new Integer(x));
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		this.callableStatement.setLong(parameterIndex, x);
		addParam(parameterIndex, new Long(x));
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		this.callableStatement.setNull(parameterIndex, sqlType);
		// addParam(parameterIndex,new Integer(sqlType));
		// addParam(parameterIndex,null);
	}

	public void setNull(int paramIndex, int sqlType, String typeName)
			throws SQLException {
		this.callableStatement.setNull(paramIndex, sqlType, typeName);
		// addParam(paramIndex,new Long(x));
		// addParam(paramIndex,null);
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		this.callableStatement.setObject(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		this.callableStatement.setObject(parameterIndex, x, targetSqlType);
		addParam(parameterIndex, x);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scale) throws SQLException {
		this.callableStatement.setObject(parameterIndex, x, targetSqlType,
				scale);
		addParam(parameterIndex, x);
	}

	public void setRef(int i, Ref x) throws SQLException {
		this.callableStatement.setRef(i, x);
		addParam(i, x);
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		this.callableStatement.setShort(parameterIndex, x);
		addParam(parameterIndex, new Short(x));
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		this.callableStatement.setString(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		this.callableStatement.setTime(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		this.callableStatement.setTime(parameterIndex, x, cal);
		addParam(parameterIndex, x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		this.callableStatement.setTimestamp(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		this.callableStatement.setTimestamp(parameterIndex, x, cal);
		addParam(parameterIndex, x);
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		this.callableStatement.setURL(parameterIndex, x);
		addParam(parameterIndex, x);
	}

	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		this.callableStatement.setUnicodeStream(parameterIndex, x, length);
		addParam(parameterIndex, x);
	}

	public void addBatch(String sql) throws SQLException {
		this.callableStatement.addBatch(sql);
	}

	public void cancel() throws SQLException {
		this.callableStatement.cancel();
	}

	public void clearBatch() throws SQLException {
		this.callableStatement.clearBatch();
	}

	public void clearWarnings() throws SQLException {
		this.callableStatement.clearWarnings();
	}

	public void close() throws SQLException {
		this.callableStatement.close();
	}

	public boolean execute(String sql) throws SQLException {
		return this.getCallableStatementFilter().execute(
				this.getPlainCallableStatement(), sql);
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.getCallableStatementFilter().execute(
				this.getPlainCallableStatement(), sql, autoGeneratedKeys);
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return this.getCallableStatementFilter().execute(
				this.getPlainCallableStatement(), sql, columnIndexes);
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return this.getCallableStatementFilter().execute(
				this.getPlainCallableStatement(), sql, columnNames);
	}

	public int[] executeBatch() throws SQLException {
		return this.getCallableStatementFilter().executeBatch(
				this.getPlainCallableStatement());
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return new WrapperResultSet(this.getCallableStatementFilter()
				.executeQuery(this.getPlainCallableStatement(), sql));
	}

	public int executeUpdate(String sql) throws SQLException {
		return this.getCallableStatementFilter().executeUpdate(
				this.getPlainCallableStatement(), sql);
	}

	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.getCallableStatementFilter().executeUpdate(
				this.getPlainCallableStatement(), sql, autoGeneratedKeys);
	}

	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return this.getCallableStatementFilter().executeUpdate(
				this.getPlainCallableStatement(), sql, columnIndexes);
	}

	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return this.getCallableStatementFilter().executeUpdate(
				this.getPlainCallableStatement(), sql, columnNames);
	}

	public Connection getConnection() throws SQLException {
		return this.callableStatement.getConnection();
	}

	public int getFetchDirection() throws SQLException {
		return this.callableStatement.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return this.callableStatement.getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		ResultSet rs = this.callableStatement.getGeneratedKeys();
		if(rs != null){
			return new WrapperResultSet(rs);
		}
		return null;
	//	return new WrapperResultSet(this.callableStatement.getGeneratedKeys());
	}

	public int getMaxFieldSize() throws SQLException {
		return this.callableStatement.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException {
		return this.callableStatement.getMaxRows();
	}

	public boolean getMoreResults() throws SQLException {
		return this.callableStatement.getMoreResults();
	}

	public boolean getMoreResults(int current) throws SQLException {
		return this.callableStatement.getMoreResults(current);
	}

	public int getQueryTimeout() throws SQLException {
		return this.callableStatement.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException {
		ResultSet rs = this.callableStatement.getResultSet();
		if(rs != null){
			return new WrapperResultSet(rs);
		}
		return null;
	}

	public int getResultSetConcurrency() throws SQLException {
		return this.callableStatement.getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException {
		return this.callableStatement.getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException {
		return this.callableStatement.getResultSetType();
	}

	public int getUpdateCount() throws SQLException {
		return this.callableStatement.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.callableStatement.getWarnings();
	}

	public void setCursorName(String name) throws SQLException {
		this.callableStatement.setCursorName(name);
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		this.callableStatement.setEscapeProcessing(enable);
	}

	public void setFetchDirection(int direction) throws SQLException {
		this.callableStatement.setFetchDirection(direction);
	}

	public void setFetchSize(int rows) throws SQLException {
		this.callableStatement.setFetchSize(rows);
	}

	public void setMaxFieldSize(int max) throws SQLException {
		this.callableStatement.setMaxFieldSize(max);
	}

	public void setMaxRows(int max) throws SQLException {
		this.callableStatement.setMaxRows(max);
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		this.callableStatement.setQueryTimeout(seconds);
	}

	public ICallableStatementFilter getCallableStatementFilter() {
		ICallableStatementFilter filter = this.getWrapperConnection()
				.getCallableStatementFilter();
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

	public Object getObject(int arg0, Map arg1) throws SQLException {
		return this.callableStatement.getObject(arg0, arg1);
	}

	public Object getObject(String arg0, Map arg1) throws SQLException {
		return this.callableStatement.getObject(arg0, arg1);
	}

}
