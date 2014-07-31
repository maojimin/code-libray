/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月30日 下午10:04:09
 */
public class SshUtil {
	private static final Log logger = LogFactory.getLog(SshUtil.class);
	private Connection conn;

	/**
	 * 密码登录
	 * @param host
	 * @param username
	 * @param password
	 */
	public SshUtil(String host,String username,String password){
		logger.debug("connecting to " + host + " with user " + username);
		conn = new Connection(host);
		boolean isAuthenticated = false;
		try {
			conn.connect(); // make sure the connection is opened
			isAuthenticated = conn.authenticateWithPassword(username, password);
		} catch (Exception e) {
			logger.error("Authentication failed.",e);
		}
		if (isAuthenticated == false)
			logger.error("Authentication failed.");
	}
	
	/**
	 * 公钥登录
	 * @param host
	 * @param rsaFile
	 * @param port
	 */
	public SshUtil(String host,String rsaFile,int port){
		conn = new Connection(host, port);
		boolean isAuthenticated = false;
		try {
			conn.connect();
			isAuthenticated = conn.authenticateWithPublicKey("dev",new File(rsaFile), null);
			if(!isAuthenticated){
				throw new RuntimeException("Authentication failed.");
			}
		} catch (IOException e) {
			throw new RuntimeException("Authentication failed.",e);
		} 
	}
	
	// 关闭连接
	public void closeConnection(){
		if(conn!=null){
			conn.close();
		}
	}
	
	/**
	 * 日期格式 Modify: 2012-09-13 17:30:27.000000000 +0800
	 * @param cmd
	 * @return
	 */
	public String getFileUpdateDate(String filePath){
		try{
			String cmd = "stat "+filePath+" |grep Modify";
			List<String> result  = runCmd(cmd);
			if(result==null || result.size()==0){
				return null;
			}
			String retval = result.get(0);
			String tag = "Modify: ";
			if(!retval.startsWith(tag)){
				return null;
			}
			return retval.substring(tag.length(),tag.length()+10);
		}catch(Exception e){
			return null;
		}
		
	}
	
	public List<String> runCmd(String cmd){
		List<String> resultList = new ArrayList<String>();
		Session sess = null;
		InputStream stdout = null;
		BufferedReader br = null;
		try {
			sess = conn.openSession();
			sess.execCommand(cmd);
			stdout = new StreamGobbler(sess.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout,"UTF-8"));
			String line = null;
			while((line = br.readLine()) != null){
				resultList.add(line);
			}
		} catch (Exception e) {
			logger.error("Error running SSH cmd [" + cmd + "]", e);
		}finally{
			if(stdout!=null){
				try {
					stdout.close();
				} catch (IOException e) {
					
				}
			}
			if(br != null){
				try{
					br.close();
				}catch (Exception e) {
				}
			}
			if(sess!=null){
				sess.close();
			}
		}
		return resultList;
	}
	
	public static void main(String[] args) {
		SshUtil cmd = new SshUtil("192.168.11.162", "weihu", "ydfSD89");
		List<String> list = cmd.runCmd("wc -l /home/weihu/.logstat/stat.log.2013-01-29");
		System.out.println(list.get(0));
		String str = list.get(0);
		String[] arr = str.split(" ");
		System.out.println(arr[0]);
		
	}
}
