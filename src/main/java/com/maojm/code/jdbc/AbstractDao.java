/**
 * yutian.com.cn Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 通用dao抽象类
 * 封装连接池，管理连接
 * 抽象sql执行模板
 * 支持多数据源
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2013-12-9-上午10:51:33
 * @version 1.0
 */
public abstract class AbstractDao {
	private static final Logger log = Logger.getLogger(AbstractDao.class);

	@Autowired
	protected DataSourcePool dataSourcePool;

	
	/**
	 * 根据数据源获取连接
	 * @param ds
	 * @return
	 * @throws SQLException
	 * @author maojimin
	 */
	protected Connection getConnection(DataSource ds) throws SQLException{
		if(ds==null){
			throw new SQLException("不存在 datasource");
		}
		Connection conn = ds.getConnection();
		if(conn!=null){
			conn.setAutoCommit(false);
		}
		return conn;
	}

	/**
	 * 获取默认连接池，可以由子类重写
	 * @return
	 * @author maojimin
	 */
	protected DataSource getDefaultDataSource(){
		return dataSourcePool.getMainDataSource();
	}
	
	/**
	 * 获取默认连接
	 * @return
	 * @throws SQLException
	 * @author maojimin
	 */
	protected Connection getConnection() throws SQLException{
		return getConnection(getDefaultDataSource());
	}
	
	protected void execute(Connection conn,String sql,Object[] parms) throws SQLException{
		log.debug(sql);		
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,parms);			
			ps.execute();
		}finally{
			close(null,ps,null);
		}
	}
	
	protected void execute(String sql,DataSource ds) throws SQLException{		
		execute(sql,null,ds);
	}
	
	protected void execute(String sql) throws SQLException{		
		execute(sql,null,getDefaultDataSource());
	}
	
	protected void query(String sql,DataSource ds,SqlCallBack callBack) throws Exception{
		query(sql,null,ds,callBack);
	}
	
	protected void query(String sql,SqlCallBack callBack) throws Exception{
		query(sql,null,getDefaultDataSource(),callBack);
	}
	
	protected void queryForSingle(String sql,SqlCallBack callBack) throws Exception{
		queryForSingle(sql,null,null,callBack);
	}
	protected void queryForSingle(String sql,Object[] params, SqlCallBack callBack) throws Exception{
		queryForSingle(sql,params,null,callBack);
	}
	
	protected void queryForSingle(String sql,Object[] params, DataSource ds, SqlCallBack callBack) throws Exception{
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,params);		
			rs = ps.executeQuery();	
			if(rs.next()){
				callBack.readerRows(rs);
			}
		}finally{			
			close(conn,ps,rs);
		}
	}
	
	protected float getFloatValue(String sql,Object[] params,DataSource ds) throws SQLException{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,params);				
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getFloat(1);
			}			
			
		}finally{			
			close(conn,ps,rs);
		}
		
		return 0;
	}
	
	protected float getFloatValue(String sql,Object[] params) throws SQLException{			
		return getFloatValue(sql,params,getDefaultDataSource());
	}
	
	protected long getLongValue(String sql,DataSource ds) throws SQLException{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);								
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getLong(1);
			}					
		}finally{			
			close(conn,ps,rs);
		}	
		return 0;
	}
	
	protected long getLongValue(String sql,Object[] params,DataSource ds) throws SQLException{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			setParmas(ps,params);
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getLong(1);
			}					
		}finally{			
			close(conn,ps,rs);
		}	
		return 0;
	}
	
	protected long getLongValue(String sql) throws SQLException{		
		return getLongValue(sql,getDefaultDataSource());
	}
	
	protected long getLongValue(String sql,Object[] params) throws SQLException{				
		return getLongValue(sql,params,getDefaultDataSource());
	}
	
	protected int getIntValue(String sql,Object[] params,DataSource ds) throws SQLException{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,params);			
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}				
		}finally{			
			close(conn,ps,rs);
		}		
		return 0;
	}
	
	protected int getIntValue(String sql,Object[] params) throws SQLException{	
		return getIntValue(sql,params,getDefaultDataSource());
	}
	
	protected Object getValue(String sql,int dataType) throws SQLException{
		return getValue(sql, dataType, null, getDefaultDataSource());
	}
	
	protected Object getValue(String sql,int dataType,Object[] params) throws SQLException{
		return getValue(sql, dataType, params, getDefaultDataSource());
	}
	
	protected Object getValue(String sql,int dataType,Object[] params,DataSource ds) throws SQLException{
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);								
			rs = ps.executeQuery();
			if(rs.next()){
				switch (dataType) {
				case Types.BIGINT:
					return rs.getLong(1);
				case Types.CHAR:
					return rs.getString(1);
				case Types.INTEGER:
					return rs.getInt(1);
				case Types.FLOAT:
					return rs.getFloat(1);
				case Types.DECIMAL:
					return rs.getDouble(1);
				case Types.TIMESTAMP:
					return rs.getTimestamp(1);
				case Types.TIME:
					return rs.getTime(1);
				case Types.DATE:
					return rs.getDate(1);
				default:
					return rs.getString(1);
				}
			}
		}finally{			
			close(conn,ps,rs);
		}	
		return null;
	} 
	
	
	protected Date getDateValue(String sql,DataSource ds) throws SQLException{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);								
			rs = ps.executeQuery();
			if(rs.next()){				
				return new Date(rs.getTimestamp(1).getTime());
			}
		}finally{			
			close(conn,ps,rs);
		}	
		return null;
	}
	
	protected Date getDateValue(String sql) throws SQLException{
		return getDateValue(sql,getDefaultDataSource());
	}
	
	protected Date getDateValue(String sql,Object[] params,DataSource ds) throws SQLException{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,params);						
			rs = ps.executeQuery();
			if(rs.next()){				
				return new Date(rs.getTimestamp(1).getTime());
			}
		}finally{			
			close(conn,ps,rs);
		}
		return null;
	}
	
	protected Date getDateValue(String sql,Object[] params) throws SQLException{
		return getDateValue(sql,params,getDefaultDataSource());
	}
	
	protected void query(String sql,Object[] params,DataSource ds,SqlCallBack callBack) throws Exception{
		log.debug(sql);
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,params);		
			rs = ps.executeQuery();	
			while(rs.next()){
				callBack.readerRows(rs);
			}
		}finally{			
			close(conn,ps,rs);
		}
	}
	
	protected void query(Connection conn,String sql,Object[] params,SqlCallBack callBack) throws Exception{
		log.debug(sql);
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,params);		
			rs = ps.executeQuery();	
			while(rs.next()){
				callBack.readerRows(rs);
			}
		}finally{			
			close(null,ps,rs);
		}
	}
	
	protected void query(String sql,Object[] params,SqlCallBack callBack) throws Exception{
		query(sql,params,getDefaultDataSource(),callBack);
	}
	
	/**
	 * 关闭数据库连接信息
	* @param conn
	* @param ps
	* @param rs
	* @throws SQLException
	* void
	* @author tom
	* @下午12:10:09 - 2011-2-21
	 */
	private void close(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException{
		if(rs!=null){
			try{
				rs.close();
			}catch(Exception e){
				log.warn("关闭 ResultSet 出错", e);
			}
		}
		if(ps!=null){
			try{
				ps.close();
			}catch(Exception e){
				log.warn("关闭preparestatement 出错", e);
			}
		}
		if(conn!=null){
			try{
				conn.commit();
			}catch(Exception e){
				log.warn("commit connection 出错", e);
			}
			try{
				conn.close();
			}catch(Exception e){
				log.warn("close connection 出错", e);
			}
		}
	}
	
	protected void commit(Connection conn){
		if(conn!=null){
			try {
				conn.commit();
			} catch (SQLException e) {
				log.warn("commit connection 出错", e);
			}
			try {
				conn.close();
			} catch (SQLException e) {
				log.warn("close connection 出错", e);
			}
		}
	}
	
	protected void execute(String sql,Object[] parms,DataSource ds) throws SQLException{		
		log.debug(sql);		
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,parms);			
			ps.execute();
		}finally{
			close(conn,ps,null);
		}
		
	}
	protected int executeUpdate(String sql,Object[] parms,DataSource ds) throws SQLException{		
		int ret = 0;
		log.debug(sql);		
		Connection conn = getConnection(ds);
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,parms);			
			ret = ps.executeUpdate();
		}finally{
			close(conn,ps,null);
		}
		return ret;
	}
	
	protected int executeUpdate(Connection conn,String sql,Object[] parms) throws SQLException{		
		int ret = 0;
		log.debug(sql);		
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement(sql);			
			setParmas(ps,parms);			
			ret = ps.executeUpdate();
		}finally{
			close(null,ps,null);
		}
		return ret;
	}
	
	protected void execute(String sql,Object[] parms) throws SQLException{		
		execute(sql,parms,getDefaultDataSource());		
	}
	
	protected int executeUpdate(String sql,Object[] parms) throws SQLException{		
		return executeUpdate(sql,parms,getDefaultDataSource());		
	}

	/**
	 * sql参数赋值
	* @param ps
	* @param parms
	* @throws SQLException
	* void
	* @author tom
	* @下午12:11:16 - 2011-2-21
	 */
	private void setParmas(PreparedStatement ps,Object[] parms ) throws SQLException{		
		if(parms!=null){
			int index = 1;
			for(Object obj:parms){
				if(obj instanceof String){
					ps.setString(index, (String)obj);
				}else if(obj instanceof Long){
					ps.setLong(index, (Long)obj);
				}else if(obj instanceof Integer){
					ps.setInt(index, (Integer)obj);
				}else if(obj instanceof Double){
					ps.setDouble(index, (Double)obj);
				}else if(obj instanceof Float){
					ps.setDouble(index, (Float)obj);
				}else if(obj instanceof Date){
					Date d = (Date)obj;
					java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
					ps.setTimestamp(index, sqlDate);
				}else{
					ps.setNull(index, Types.NULL);
				}
				index++;
			}			
		}
		
	}
	
	/**
	 * 获取查询数据接口.
	 * @author <a href="mailto:taomin.tw@taobao.com">taomin.tw</a>
	 * @date 2011-2-21-下午12:11:38
	 * @version 1.0
	 */
	protected interface SqlCallBack{		
		public void readerRows(ResultSet rs)throws Exception;
	}

	
}
