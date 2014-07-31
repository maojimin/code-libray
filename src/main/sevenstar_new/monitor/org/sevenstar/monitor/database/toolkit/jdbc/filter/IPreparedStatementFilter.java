package org.sevenstar.monitor.database.toolkit.jdbc.filter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IPreparedStatementFilter {
	public void setParamList(List list);
	public List getParamList();
	public String getSql();
	public void setSql(String sql);
	public boolean execute(PreparedStatement preparedStatement) throws SQLException;
	public ResultSet executeQuery(PreparedStatement preparedStatement) throws SQLException;
	public int executeUpdate(PreparedStatement preparedStatement) throws SQLException;
	public boolean execute(PreparedStatement preparedStatement,String sql) throws SQLException;
	public boolean execute(PreparedStatement preparedStatement,String sql, int autoGeneratedKeys) throws SQLException;
	public boolean execute(PreparedStatement preparedStatement,String sql, int[] columnIndexes) throws SQLException;
	public boolean execute(PreparedStatement preparedStatement,String sql, String[] columnNames) throws SQLException ;
	public int[] executeBatch(PreparedStatement preparedStatement) throws SQLException;
	public ResultSet executeQuery(PreparedStatement preparedStatement,String sql) throws SQLException;
	public int executeUpdate(PreparedStatement preparedStatement,String sql) throws SQLException;
	public int executeUpdate(PreparedStatement preparedStatement,String sql, int autoGeneratedKeys) throws SQLException;
	public int executeUpdate(PreparedStatement preparedStatement,String sql, int[] columnIndexes) throws SQLException;
	public int executeUpdate(PreparedStatement preparedStatement,String sql, String[] columnNames) throws SQLException;
}
