package org.sevenstar.monitor.database.toolkit.jdbc.warp;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class WrapperResultSet implements ResultSet{
	private ResultSet resultSet;

	private WrapperResultSet(){
		//
	}

	public ResultSet getPlainResultSet(){
		return this.resultSet;
	}

	public WrapperResultSet(ResultSet resultSet){
       this.resultSet = resultSet;
	}

	public boolean absolute(int row) throws SQLException {
 		return this.resultSet.absolute(row);
	}

	public void afterLast() throws SQLException {
		this.resultSet.afterLast();
	}

	public void beforeFirst() throws SQLException {
		this.resultSet.beforeFirst();
	}

	public void cancelRowUpdates() throws SQLException {
		this.resultSet.cancelRowUpdates();
	}

	public void clearWarnings() throws SQLException {
		this.resultSet.clearWarnings();
	}

	public void close() throws SQLException {
		this.resultSet.close();
	}

	public void deleteRow() throws SQLException {
		this.resultSet.deleteRow();
	}

	public int findColumn(String columnName) throws SQLException {
 		return this.resultSet.findColumn(columnName);
	}

	public boolean first() throws SQLException {
 		return this.resultSet.first();
	}

	public Array getArray(int i) throws SQLException {
 		return this.resultSet.getArray(i);
	}

	public Array getArray(String colName) throws SQLException {
 		return this.resultSet.getArray(colName);
	}

	public InputStream getAsciiStream(int columnIndex) throws SQLException {
 		return this.resultSet.getAsciiStream(columnIndex);
	}

	public InputStream getAsciiStream(String columnName) throws SQLException {
 		return this.resultSet.getAsciiStream(columnName);
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
 		return this.resultSet.getBigDecimal(columnIndex);
	}

	public BigDecimal getBigDecimal(String columnName) throws SQLException {
 		return this.resultSet.getBigDecimal(columnName);
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
 		return this.resultSet.getBigDecimal(columnIndex, scale);
	}

	public BigDecimal getBigDecimal(String columnName, int scale)
			throws SQLException {
 		return this.resultSet.getBigDecimal(columnName, scale);
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
 		return this.resultSet.getBinaryStream(columnIndex);
	}

	public InputStream getBinaryStream(String columnName) throws SQLException {
		return this.resultSet.getBinaryStream(columnName);
	}

	public Blob getBlob(int i) throws SQLException {
 		return this.resultSet.getBlob(i);
	}

	public Blob getBlob(String colName) throws SQLException {
 		return this.resultSet.getBlob(colName);
	}

	public boolean getBoolean(int columnIndex) throws SQLException {
		return this.resultSet.getBoolean(columnIndex);
	}

	public boolean getBoolean(String columnName) throws SQLException {
		return this.resultSet.getBoolean(columnName);
	}

	public byte getByte(int columnIndex) throws SQLException {
		return this.resultSet.getByte(columnIndex);
	}

	public byte getByte(String columnName) throws SQLException {
 		return this.resultSet.getByte(columnName);
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		return this.resultSet.getBytes(columnIndex);
	}

	public byte[] getBytes(String columnName) throws SQLException {
		return this.resultSet.getBytes(columnName);
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		return this.resultSet.getCharacterStream(columnIndex);
	}

	public Reader getCharacterStream(String columnName) throws SQLException {
		return this.resultSet.getCharacterStream(columnName);
	}

	public Clob getClob(int i) throws SQLException {
		return this.resultSet.getClob(i);
	}

	public Clob getClob(String colName) throws SQLException {
		return this.resultSet.getClob(colName);
	}

	public int getConcurrency() throws SQLException {
		return this.resultSet.getConcurrency();
	}

	public String getCursorName() throws SQLException {
		return this.resultSet.getCursorName();
	}

	public Date getDate(int columnIndex) throws SQLException {
		return this.resultSet.getDate(columnIndex);
	}

	public Date getDate(String columnName) throws SQLException {
		return this.resultSet.getDate(columnName);
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return this.resultSet.getDate(columnIndex, cal);
	}

	public Date getDate(String columnName, Calendar cal) throws SQLException {
		return this.resultSet.getDate(columnName, cal);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return this.resultSet.getDouble(columnIndex);
	}

	public double getDouble(String columnName) throws SQLException {
		return this.resultSet.getDouble(columnName);
	}

	public int getFetchDirection() throws SQLException {
		return this.resultSet.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return this.resultSet.getFetchSize();
	}

	public float getFloat(int columnIndex) throws SQLException {
		return this.resultSet.getFloat(columnIndex);
	}

	public float getFloat(String columnName) throws SQLException {
		return this.resultSet.getFloat(columnName);
	}

	public int getInt(int columnIndex) throws SQLException {
		return this.resultSet.getInt(columnIndex);
	}

	public int getInt(String columnName) throws SQLException {
		return this.resultSet.getInt(columnName);
	}

	public long getLong(int columnIndex) throws SQLException {
		return this.resultSet.getLong(columnIndex);
	}

	public long getLong(String columnName) throws SQLException {
		return this.resultSet.getLong(columnName);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return this.resultSet.getMetaData();
	}

	public Object getObject(int columnIndex) throws SQLException {
		return this.resultSet.getObject(columnIndex);
	}

	public Object getObject(String columnName) throws SQLException {
		return this.resultSet.getObject(columnName);
	}

	public Object getObject(int i, Map  map)
			throws SQLException {
		return this.resultSet.getObject(i, map);
	}

	public Object getObject(String colName, Map  map)
			throws SQLException {
		return this.resultSet.getObject(colName, map);
	}

	public Ref getRef(int i) throws SQLException {
		return this.resultSet.getRef(i);
	}

	public Ref getRef(String colName) throws SQLException {
		return this.resultSet.getRef(colName);
	}

	public int getRow() throws SQLException {
		return this.resultSet.getRow();
	}

	public short getShort(int columnIndex) throws SQLException {
		return this.resultSet.getShort(columnIndex);
	}

	public short getShort(String columnName) throws SQLException {
		return this.resultSet.getShort(columnName);
	}

	public Statement getStatement() throws SQLException {
		return this.resultSet.getStatement();
	}

	public String getString(int columnIndex) throws SQLException {
		return this.resultSet.getString(columnIndex);
	}

	public String getString(String columnName) throws SQLException {
		return this.resultSet.getString(columnName);
	}

	public Time getTime(int columnIndex) throws SQLException {
		return this.resultSet.getTime(columnIndex);
	}

	public Time getTime(String columnName) throws SQLException {
		return this.resultSet.getTime(columnName);
	}

	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		return this.resultSet.getTime(columnIndex, cal);
	}

	public Time getTime(String columnName, Calendar cal) throws SQLException {
		return this.resultSet.getTime(columnName, cal);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return this.resultSet.getTimestamp(columnIndex);
	}

	public Timestamp getTimestamp(String columnName) throws SQLException {
		return this.resultSet.getTimestamp(columnName);
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		return this.resultSet.getTimestamp(columnIndex, cal);
	}

	public Timestamp getTimestamp(String columnName, Calendar cal)
			throws SQLException {
		return this.resultSet.getTimestamp(columnName, cal);
	}

	public int getType() throws SQLException {
		return this.resultSet.getType();
	}

	public URL getURL(int columnIndex) throws SQLException {
		return this.resultSet.getURL(columnIndex);
	}

	public URL getURL(String columnName) throws SQLException {
		return this.resultSet.getURL(columnName);
	}

	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		return this.resultSet.getUnicodeStream(columnIndex);
	}

	public InputStream getUnicodeStream(String columnName) throws SQLException {
		return this.resultSet.getUnicodeStream(columnName);
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.resultSet.getWarnings();
	}

	public void insertRow() throws SQLException {
		this.resultSet.insertRow();
	}

	public boolean isAfterLast() throws SQLException {
 		return this.resultSet.isAfterLast();
	}

	public boolean isBeforeFirst() throws SQLException {
		return this.resultSet.isBeforeFirst();
	}

	public boolean isFirst() throws SQLException {
		return this.resultSet.isFirst();
	}

	public boolean isLast() throws SQLException {
		return this.resultSet.isLast();
	}

	public boolean last() throws SQLException {
		return this.resultSet.last();
	}

	public void moveToCurrentRow() throws SQLException {
		this.resultSet.moveToCurrentRow();
	}

	public void moveToInsertRow() throws SQLException {
		this.resultSet.moveToInsertRow();
	}

	public boolean next() throws SQLException {
 		return this.resultSet.next();
	}

	public boolean previous() throws SQLException {
		return this.resultSet.previous();
	}

	public void refreshRow() throws SQLException {
		this.resultSet.refreshRow();
	}

	public boolean relative(int rows) throws SQLException {
		return this.resultSet.relative(rows);
	}

	public boolean rowDeleted() throws SQLException {
		return this.resultSet.rowDeleted();
	}

	public boolean rowInserted() throws SQLException {
		return this.resultSet.rowInserted();
	}

	public boolean rowUpdated() throws SQLException {
		return this.resultSet.rowUpdated();
	}

	public void setFetchDirection(int direction) throws SQLException {
		this.resultSet.setFetchDirection(direction);
	}

	public void setFetchSize(int rows) throws SQLException {
		this.resultSet.setFetchSize(rows);
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		this.resultSet.updateArray(columnIndex, x);
	}

	public void updateArray(String columnName, Array x) throws SQLException {
		this.resultSet.updateArray(columnName, x);
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		this.resultSet.updateAsciiStream(columnIndex, x, length);
	}

	public void updateAsciiStream(String columnName, InputStream x, int length)
			throws SQLException {
		this.resultSet.updateAsciiStream(columnName, x, length);
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		this.resultSet.updateBigDecimal(columnIndex, x);
	}

	public void updateBigDecimal(String columnName, BigDecimal x)
			throws SQLException {
		this.resultSet.updateBigDecimal(columnName, x);
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		this.resultSet.updateBinaryStream(columnIndex, x, length);
	}

	public void updateBinaryStream(String columnName, InputStream x, int length)
			throws SQLException {
		this.resultSet.updateBinaryStream(columnName, x, length);
	}

	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		this.resultSet.updateBlob(columnIndex, x);
	}

	public void updateBlob(String columnName, Blob x) throws SQLException {
		this.resultSet.updateBlob(columnName, x);
	}

	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		this.resultSet.updateBoolean(columnIndex, x);
	}

	public void updateBoolean(String columnName, boolean x) throws SQLException {
		this.resultSet.updateBoolean(columnName, x);
	}

	public void updateByte(int columnIndex, byte x) throws SQLException {
		this.resultSet.updateByte(columnIndex, x);
	}

	public void updateByte(String columnName, byte x) throws SQLException {
		this.resultSet.updateByte(columnName, x);
	}

	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		this.resultSet.updateBytes(columnIndex, x);
	}

	public void updateBytes(String columnName, byte[] x) throws SQLException {
		this.resultSet.updateBytes(columnName, x);
	}

	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		this.resultSet.updateCharacterStream(columnIndex, x, length);
	}

	public void updateCharacterStream(String columnName, Reader reader,
			int length) throws SQLException {
		this.resultSet.updateCharacterStream(columnName, reader, length);
	}

	public void updateClob(int columnIndex, Clob x) throws SQLException {
		this.resultSet.updateClob(columnIndex, x);
	}

	public void updateClob(String columnName, Clob x) throws SQLException {
		this.resultSet.updateClob(columnName, x);
	}

	public void updateDate(int columnIndex, Date x) throws SQLException {
		this.resultSet.updateDate(columnIndex, x);
	}

	public void updateDate(String columnName, Date x) throws SQLException {
		this.resultSet.updateDate(columnName, x);
	}

	public void updateDouble(int columnIndex, double x) throws SQLException {
		this.resultSet.updateDouble(columnIndex, x);
	}

	public void updateDouble(String columnName, double x) throws SQLException {
		this.resultSet.updateDouble(columnName, x);
	}

	public void updateFloat(int columnIndex, float x) throws SQLException {
		this.resultSet.updateFloat(columnIndex, x);
	}

	public void updateFloat(String columnName, float x) throws SQLException {
		this.resultSet.updateFloat(columnName, x);
	}

	public void updateInt(int columnIndex, int x) throws SQLException {
		this.resultSet.updateInt(columnIndex, x);
	}

	public void updateInt(String columnName, int x) throws SQLException {
		this.resultSet.updateInt(columnName, x);
	}

	public void updateLong(int columnIndex, long x) throws SQLException {
		this.resultSet.updateLong(columnIndex, x);
	}

	public void updateLong(String columnName, long x) throws SQLException {
		this.resultSet.updateLong(columnName, x);
	}

	public void updateNull(int columnIndex) throws SQLException {
		this.resultSet.updateNull(columnIndex);
	}

	public void updateNull(String columnName) throws SQLException {
		this.resultSet.updateNull(columnName);
	}

	public void updateObject(int columnIndex, Object x) throws SQLException {
		this.resultSet.updateObject(columnIndex, x);
	}

	public void updateObject(String columnName, Object x) throws SQLException {
		this.resultSet.updateObject(columnName, x);
	}

	public void updateObject(int columnIndex, Object x, int scale)
			throws SQLException {
		this.resultSet.updateObject(columnIndex, x, scale);
	}

	public void updateObject(String columnName, Object x, int scale)
			throws SQLException {
		 this.resultSet.updateObject(columnName, x, scale);
	}

	public void updateRef(int columnIndex, Ref x) throws SQLException {
		this.resultSet.updateRef(columnIndex, x);
	}

	public void updateRef(String columnName, Ref x) throws SQLException {
		this.resultSet.updateRef(columnName, x);
	}

	public void updateRow() throws SQLException {
		this.resultSet.updateRow();
	}

	public void updateShort(int columnIndex, short x) throws SQLException {
		this.resultSet.updateShort(columnIndex, x);
	}

	public void updateShort(String columnName, short x) throws SQLException {
		this.resultSet.updateShort(columnName, x);
	}

	public void updateString(int columnIndex, String x) throws SQLException {
		this.resultSet.updateString(columnIndex, x);
	}

	public void updateString(String columnName, String x) throws SQLException {
		this.resultSet.updateString(columnName, x);
	}

	public void updateTime(int columnIndex, Time x) throws SQLException {
		this.resultSet.updateTime(columnIndex, x);
	}

	public void updateTime(String columnName, Time x) throws SQLException {
		this.resultSet.updateTime(columnName, x);
	}

	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		this.resultSet.updateTimestamp(columnIndex, x);
	}

	public void updateTimestamp(String columnName, Timestamp x)
			throws SQLException {
		this.resultSet.updateTimestamp(columnName, x);
	}

	public boolean wasNull() throws SQLException {
		return this.resultSet.wasNull();
	}

}
