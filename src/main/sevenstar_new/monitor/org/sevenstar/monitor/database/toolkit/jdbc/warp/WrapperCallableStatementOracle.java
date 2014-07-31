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
//import oracle.jdbc.driver.OracleCallableStatement;
import oracle.jdbc.OracleParameterMetaData;
import oracle.jdbc.OracleResultSetCache;
import oracle.jdbc.internal.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BINARY_DOUBLE;
import oracle.sql.BINARY_FLOAT;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.CustomDatum;
import oracle.sql.CustomDatumFactory;
import oracle.sql.DATE;
import oracle.sql.Datum;
import oracle.sql.INTERVALDS;
import oracle.sql.INTERVALYM;
import oracle.sql.NUMBER;
import oracle.sql.OPAQUE;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.RAW;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;
import oracle.sql.TIMESTAMPLTZ;
import oracle.sql.TIMESTAMPTZ;

public  class WrapperCallableStatementOracle      implements   OracleCallableStatement,IWarpperCallableStatement {
	private CallableStatement callableStatement;

	private WrapperConnection wrapperConnection;

	private String sql;

	private WrapperCallableStatementOracle() {
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

	public WrapperCallableStatementOracle(CallableStatement callableStatement) {
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
		return new WrapperResultSet(this.callableStatement.getGeneratedKeys());
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

	/*--------------- start oracle callable----------------*/

	public byte[] privateGetBytes(int arg0) throws SQLException {
 		return ((OracleCallableStatement)this.callableStatement).privateGetBytes(arg0);
	}

	public ARRAY getARRAY(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getARRAY(arg0);
	}

	public Object getAnyDataEmbeddedObject(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getAnyDataEmbeddedObject(arg0);
	}

	public InputStream getAsciiStream(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getAsciiStream(arg0);
	}

	public BFILE getBFILE(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getBFILE(arg0);
	}

	public BLOB getBLOB(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getBLOB(arg0);
	}

	public InputStream getBinaryStream(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getBinaryStream(arg0);
	}

	public CHAR getCHAR(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getCHAR(arg0);
	}

	public CLOB getCLOB(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getCLOB(arg0);
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getCharacterStream(arg0);
	}

	public ResultSet getCursor(int arg0) throws SQLException {
		return new WrapperResultSet(((OracleCallableStatement)this.callableStatement).getCursor(arg0));
	}

	public Object getCustomDatum(int arg0, CustomDatumFactory arg1)
			throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getCustomDatum(arg0, arg1);
	}

	public DATE getDATE(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getDATE(arg0);
	}

	public INTERVALDS getINTERVALDS(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getINTERVALDS(arg0);
	}

	public INTERVALYM getINTERVALYM(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getINTERVALYM(arg0);
	}

	public NUMBER getNUMBER(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getNUMBER(arg0);
	}

	public OPAQUE getOPAQUE(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getOPAQUE(arg0);
	}

	public Object getORAData(int arg0, ORADataFactory arg1) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getORAData(arg0, arg1);
	}

	public Datum getOracleObject(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getOracleObject(arg0);
	}

	public Datum[] getOraclePlsqlIndexTable(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getOraclePlsqlIndexTable(arg0);
	}

	public Object getPlsqlIndexTable(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getPlsqlIndexTable(arg0);
	}

	public Object getPlsqlIndexTable(int arg0, Class arg1) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getPlsqlIndexTable(arg0, arg1);
	}

	public RAW getRAW(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getRAW(arg0);
	}

	public REF getREF(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getREF(arg0);
	}

	public ROWID getROWID(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getROWID(arg0);
	}

	public STRUCT getSTRUCT(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getSTRUCT(arg0);
	}

	public TIMESTAMP getTIMESTAMP(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getTIMESTAMP(arg0);
	}

	public TIMESTAMPLTZ getTIMESTAMPLTZ(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getTIMESTAMPLTZ(arg0);
	}

	public TIMESTAMPTZ getTIMESTAMPTZ(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getTIMESTAMPTZ(arg0);
	}

	public InputStream getUnicodeStream(int arg0) throws SQLException {
		return ((OracleCallableStatement)this.callableStatement).getUnicodeStream(arg0);
	}

	public void registerIndexTableOutParameter(int arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		  ((OracleCallableStatement)this.callableStatement).registerIndexTableOutParameter(arg0, arg1, arg2, arg3);
	}

	public void registerOutParameter(int arg0, int arg1, int arg2, int arg3)
			throws SQLException {
		  ((OracleCallableStatement)this.callableStatement).registerOutParameter(arg0, arg1, arg2, arg3);
	}

	public void registerOutParameter(String arg0, int arg1, int arg2, int arg3)
			throws SQLException {
		  ((OracleCallableStatement)this.callableStatement).registerOutParameter(arg0, arg1, arg2, arg3);
	}

	public void registerOutParameterBytes(int arg0, int arg1, int arg2, int arg3)
			throws SQLException {
		  ((OracleCallableStatement)this.callableStatement).registerOutParameterBytes(arg0, arg1, arg2, arg3);
	}

	public void registerOutParameterChars(int arg0, int arg1, int arg2, int arg3)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).registerOutParameterChars(arg0, arg1, arg2, arg3);
	}

	public int sendBatch() throws SQLException {
 		return ((OracleCallableStatement)this.callableStatement).sendBatch();
	}

	public void setBinaryDouble(String arg0, BINARY_DOUBLE arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryDouble(arg0, arg1);
	}

	public void setBinaryFloat(String arg0, BINARY_FLOAT arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryFloat(arg0, arg1);
	}

	public void setBytesForBlob(String arg0, byte[] arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBytesForBlob(arg0, arg1);
	}

	public void setExecuteBatch(int arg0) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setExecuteBatch(arg0);
	}

	public void setStringForClob(String arg0, String arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setStringForClob(arg0, arg1);
	}



	public OracleParameterMetaData OracleGetParameterMetaData()
			throws SQLException {
 		return ((OracleCallableStatement)this.callableStatement).OracleGetParameterMetaData();
	}

	public void defineParameterType(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineParameterType(arg0, arg1, arg2);
	}

	public void defineParameterTypeBytes(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineParameterTypeBytes(arg0, arg1, arg2);
	}

	public void defineParameterTypeChars(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineParameterTypeChars(arg0, arg1, arg2);
	}

	public int getExecuteBatch() {
 		return ((OracleCallableStatement)this.callableStatement).getExecuteBatch();
	}

	public ResultSet getReturnResultSet() throws SQLException {
 		return new WrapperResultSet(((OracleCallableStatement)this.callableStatement).getReturnResultSet());
	}

	public void registerReturnParameter(int arg0, int arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).registerReturnParameter(arg0, arg1);
	}

	public void registerReturnParameter(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).registerReturnParameter(arg0, arg1, arg2);
	}

	public void registerReturnParameter(int arg0, int arg1, String arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).registerReturnParameter(arg0, arg1, arg2);
	}

	public void setARRAY(int arg0, ARRAY arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setARRAY(arg0, arg1);
	}

	public void setARRAYAtName(String arg0, ARRAY arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setARRAYAtName(arg0, arg1);
	}

	public void setArrayAtName(String arg0, Array arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setArrayAtName(arg0, arg1);
	}

	public void setAsciiStreamAtName(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setAsciiStreamAtName(arg0, arg1, arg2);
	}

	public void setBFILE(int arg0, BFILE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBFILE(arg0, arg1);
	}

	public void setBFILEAtName(String arg0, BFILE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBFILEAtName(arg0, arg1);
	}

	public void setBLOB(int arg0, BLOB arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBLOB(arg0, arg1);
	}

	public void setBLOBAtName(String arg0, BLOB arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBLOBAtName(arg0, arg1);
	}

	public void setBfile(int arg0, BFILE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBfile(arg0, arg1);
	}

	public void setBfileAtName(String arg0, BFILE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBfileAtName(arg0, arg1);
	}

	public void setBigDecimalAtName(String arg0, BigDecimal arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBigDecimalAtName(arg0, arg1);
	}

	public void setBinaryDouble(int arg0, double arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryDouble(arg0, arg1);
	}

	public void setBinaryDouble(int arg0, BINARY_DOUBLE arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryDouble(arg0, arg1);
	}

	public void setBinaryDoubleAtName(String arg0, double arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryDoubleAtName(arg0, arg1);
	}

	public void setBinaryDoubleAtName(String arg0, BINARY_DOUBLE arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryDoubleAtName(arg0,arg1);
	}

	public void setBinaryFloat(int arg0, float arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryFloat( arg0,  arg1);
	}

	public void setBinaryFloat(int arg0, BINARY_FLOAT arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryFloat( arg0,  arg1);
	}

	public void setBinaryFloatAtName(String arg0, float arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryFloatAtName( arg0,  arg1);
	}

	public void setBinaryFloatAtName(String arg0, BINARY_FLOAT arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryFloatAtName( arg0,  arg1);
	}

	public void setBinaryStreamAtName(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBinaryStreamAtName( arg0,  arg1,  arg2);
	}

	public void setBlobAtName(String arg0, Blob arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBlobAtName( arg0,  arg1);
	}

	public void setBooleanAtName(String arg0, boolean arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBooleanAtName( arg0,  arg1);
	}

	public void setByteAtName(String arg0, byte arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setByteAtName( arg0,  arg1);
	}

	public void setBytesAtName(String arg0, byte[] arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBytesAtName( arg0,  arg1);
	}

	public void setBytesForBlob(int arg0, byte[] arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBytesForBlob( arg0, arg1);
	}

	public void setBytesForBlobAtName(String arg0, byte[] arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setBytesForBlobAtName( arg0,  arg1);
	}

	public void setCHAR(int arg0, CHAR arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCHAR( arg0,  arg1);

	}

	public void setCHARAtName(String arg0, CHAR arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCHARAtName( arg0,  arg1);
	}

	public void setCLOB(int arg0, CLOB arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCLOB( arg0,  arg1);
	}

	public void setCLOBAtName(String arg0, CLOB arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCLOBAtName( arg0,  arg1);
	}

	public void setCheckBindTypes(boolean arg0) {
		((OracleCallableStatement)this.callableStatement).setCheckBindTypes( arg0);
	}

	public void setClobAtName(String arg0, Clob arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setClobAtName( arg0,  arg1);
	}

	public void setCursor(int arg0, ResultSet arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCursor( arg0,  arg1);
	}

	public void setCursorAtName(String arg0, ResultSet arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCursorAtName( arg0,  arg1);
	}

	public void setCustomDatum(int arg0, CustomDatum arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCustomDatum( arg0,  arg1);
	}

	public void setCustomDatumAtName(String arg0, CustomDatum arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setCustomDatumAtName( arg0,  arg1);
	}

	public void setDATE(int arg0, DATE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setDATE( arg0,  arg1);
	}

	public void setDATEAtName(String arg0, DATE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setDATEAtName( arg0,  arg1);
	}

	public void setDateAtName(String arg0, Date arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setDateAtName( arg0,  arg1);
	}

	public void setDisableStmtCaching(boolean arg0) {
		((OracleCallableStatement)this.callableStatement).setDisableStmtCaching( arg0);
	}

	public void setDoubleAtName(String arg0, double arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setDoubleAtName( arg0,  arg1);
	}

	public void setFixedCHAR(int arg0, String arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setFixedCHAR( arg0,  arg1);
	}

	public void setFixedCHARAtName(String arg0, String arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setFixedCHARAtName( arg0,  arg1);
	}

	public void setFloatAtName(String arg0, float arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setFloatAtName( arg0,  arg1);
	}

	public void setFormOfUse(int arg0, short arg1) {
		((OracleCallableStatement)this.callableStatement).setFormOfUse( arg0,  arg1);
	}

	public void setINTERVALDS(int arg0, INTERVALDS arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setINTERVALDS( arg0,  arg1);
	}

	public void setINTERVALDSAtName(String arg0, INTERVALDS arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setINTERVALDSAtName( arg0,  arg1);
	}

	public void setINTERVALYM(int arg0, INTERVALYM arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setINTERVALYM( arg0,  arg1);
	}

	public void setINTERVALYMAtName(String arg0, INTERVALYM arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setINTERVALYMAtName( arg0,  arg1);
	}

	public void setIntAtName(String arg0, int arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setIntAtName( arg0,  arg1);
	}

	public void setLongAtName(String arg0, long arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setLongAtName( arg0,  arg1);
	}

	public void setNUMBER(int arg0, NUMBER arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setNUMBER( arg0,  arg1);
	}

	public void setNUMBERAtName(String arg0, NUMBER arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setNUMBERAtName( arg0,  arg1);
	}

	public void setNullAtName(String arg0, int arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setNullAtName( arg0,  arg1);
	}

	public void setNullAtName(String arg0, int arg1, String arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setNullAtName( arg0,  arg1,  arg2);
	}

	public void setOPAQUE(int arg0, OPAQUE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setOPAQUE( arg0,  arg1);
	}

	public void setOPAQUEAtName(String arg0, OPAQUE arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setOPAQUEAtName( arg0,  arg1);
	}

	public void setORAData(int arg0, ORAData arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setORAData( arg0,  arg1);
	}

	public void setORADataAtName(String arg0, ORAData arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setORADataAtName( arg0,  arg1);
	}

	public void setObjectAtName(String arg0, Object arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setObjectAtName( arg0,  arg1);
	}

	public void setObjectAtName(String arg0, Object arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setObjectAtName( arg0,  arg1,  arg2);
	}

	public void setObjectAtName(String arg0, Object arg1, int arg2, int arg3)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setObjectAtName( arg0,  arg1,  arg2,  arg3);
	}

	public void setOracleObject(int arg0, Datum arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setOracleObject( arg0,  arg1);
	}

	public void setOracleObjectAtName(String arg0, Datum arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setOracleObjectAtName( arg0,  arg1);
	}

	public void setPlsqlIndexTable(int arg0, Object arg1, int arg2, int arg3,
			int arg4, int arg5) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setPlsqlIndexTable( arg0,  arg1,  arg2,  arg3,
				 arg4,  arg5);
	}

	public void setRAW(int arg0, RAW arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setRAW( arg0,  arg1);
	}

	public void setRAWAtName(String arg0, RAW arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setRAWAtName( arg0,  arg1);
	}

	public void setREF(int arg0, REF arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setREF( arg0,  arg1);
	}

	public void setREFAtName(String arg0, REF arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setREFAtName( arg0,  arg1);
	}

	public void setROWID(int arg0, ROWID arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setROWID( arg0,  arg1);
	}

	public void setROWIDAtName(String arg0, ROWID arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setROWIDAtName( arg0,  arg1);
	}

	public void setRefAtName(String arg0, Ref arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setRefAtName( arg0,  arg1);
	}

	public void setRefType(int arg0, REF arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setRefType( arg0,  arg1);
	}

	public void setRefTypeAtName(String arg0, REF arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setRefTypeAtName( arg0,  arg1);
	}

	public void setSTRUCT(int arg0, STRUCT arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setSTRUCT( arg0,  arg1);
	}

	public void setSTRUCTAtName(String arg0, STRUCT arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setSTRUCTAtName( arg0,  arg1);
	}

	public void setShortAtName(String arg0, short arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setShortAtName( arg0,  arg1);
	}

	public void setStringAtName(String arg0, String arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setStringAtName( arg0,  arg1);
	}

	public void setStringForClob(int arg0, String arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setStringForClob( arg0,  arg1);
	}

	public void setStringForClobAtName(String arg0, String arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setStringForClobAtName( arg0,  arg1);
	}

	public void setStructDescriptor(int arg0, StructDescriptor arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setStructDescriptor( arg0,  arg1);
	}

	public void setStructDescriptorAtName(String arg0, StructDescriptor arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setStructDescriptorAtName( arg0,  arg1);
	}

	public void setTIMESTAMP(int arg0, TIMESTAMP arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTIMESTAMP( arg0,  arg1);
	}

	public void setTIMESTAMPAtName(String arg0, TIMESTAMP arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTIMESTAMPAtName( arg0,  arg1);
	}

	public void setTIMESTAMPLTZ(int arg0, TIMESTAMPLTZ arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTIMESTAMPLTZ( arg0,  arg1);
	}

	public void setTIMESTAMPLTZAtName(String arg0, TIMESTAMPLTZ arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTIMESTAMPLTZAtName( arg0,  arg1);
	}

	public void setTIMESTAMPTZ(int arg0, TIMESTAMPTZ arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTIMESTAMPTZ( arg0,  arg1);
	}

	public void setTIMESTAMPTZAtName(String arg0, TIMESTAMPTZ arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTIMESTAMPTZAtName( arg0,  arg1);
	}

	public void setTimeAtName(String arg0, Time arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTimeAtName( arg0,  arg1);
	}

	public void setTimestampAtName(String arg0, Timestamp arg1)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setTimestampAtName( arg0,  arg1);
	}

	public void setURLAtName(String arg0, URL arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setURLAtName( arg0,  arg1);
	}

	public void setUnicodeStreamAtName(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setUnicodeStreamAtName( arg0,  arg1,  arg2);
	}

	public void clearDefines() throws SQLException {
		((OracleCallableStatement)this.callableStatement).clearDefines();
	}

	public void closeWithKey(String arg0) throws SQLException {
		((OracleCallableStatement)this.callableStatement).closeWithKey(arg0);
	}

	public int creationState() {
 		return ((OracleCallableStatement)this.callableStatement).creationState();
	}

	public void defineColumnType(int arg0, int arg1) throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineColumnType( arg0,  arg1);
	}

	public void defineColumnType(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineColumnType( arg0,  arg1,  arg2);
	}

	public void defineColumnType(int arg0, int arg1, String arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineColumnType( arg0,  arg1,  arg2);
	}

	public void defineColumnType(int arg0, int arg1, int arg2, short arg3)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineColumnType( arg0,  arg1,  arg2,  arg3);
	}

	public void defineColumnTypeBytes(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineColumnTypeBytes( arg0,  arg1,  arg2);
	}

	public void defineColumnTypeChars(int arg0, int arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).defineColumnTypeChars(arg0,arg1, arg2);
	}

	public int getRowPrefetch() {
 		return ((OracleCallableStatement)this.callableStatement).getRowPrefetch();
	}

	public boolean isNCHAR(int arg0) throws SQLException {
 		return ((OracleCallableStatement)this.callableStatement).isNCHAR(arg0);
	}

	public void setResultSetCache(OracleResultSetCache arg0)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setResultSetCache(arg0);
	}

	public void setRowPrefetch(int arg0) throws SQLException {
		((OracleCallableStatement)this.callableStatement).setRowPrefetch(arg0);
	}

	public void enterExplicitCache() throws SQLException {
		((OracleCallableStatement)this.callableStatement).enterExplicitCache();
	}

	public void enterImplicitCache() throws SQLException {
		((OracleCallableStatement)this.callableStatement).enterImplicitCache();
	}

	public void exitExplicitCacheToActive() throws SQLException {
		((OracleCallableStatement)this.callableStatement).exitExplicitCacheToActive();
	}

	public void exitExplicitCacheToClose() throws SQLException {
		((OracleCallableStatement)this.callableStatement).exitExplicitCacheToClose();
	}

	public void exitImplicitCacheToActive() throws SQLException {
		((OracleCallableStatement)this.callableStatement).exitImplicitCacheToActive();
	}

	public void exitImplicitCacheToClose() throws SQLException {
		((OracleCallableStatement)this.callableStatement).exitImplicitCacheToClose();
	}

	public void setInternalBytes(int arg0, byte[] arg1, int arg2)
			throws SQLException {
		((OracleCallableStatement)this.callableStatement).setInternalBytes(arg0,arg1,arg2);
	}

	public boolean getFixedString() {
 		return ((OracleCallableStatement)this.callableStatement).getFixedString();
	}

	public int getcacheState() {
 		return ((OracleCallableStatement)this.callableStatement).getcacheState();
	}

	public boolean getserverCursor() {
 		return ((OracleCallableStatement)this.callableStatement).getserverCursor();
	}

	public int getstatementType() {
 		return ((OracleCallableStatement)this.callableStatement).getstatementType();
	}

	public void setFixedString(boolean arg0) {
		((OracleCallableStatement)this.callableStatement).setFixedString(arg0);
	}




}
