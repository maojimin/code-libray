/*
 * yutian.com Inc.
 * Copyright (c) 2010-2013 All Rights Reserved.
 */
package com.maojm.code.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import org.apache.log4j.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:maojimin@yutian.com.cn">毛积敏</a>
 * 2014年4月29日 下午1:10:28
 */
public class FtpClientUtil extends FTPClient{
	private static Logger logger=Logger.getLogger(FtpClientUtil.class);
	//设置本地编码
	private String localEncoding="UTF-8";

	/**
	 * FTP登陆
	 * @param host FTP主机地址
	 * @param port FTP端口
	 * @param userName FTP用户名
	 * @param password FTP密码
	 * @return boolean  true 登陆成功 false 登陆失败
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public boolean ftpLogin(String host, int port, String userName,String password) throws SocketException, IOException {
		boolean result=false;
		connect(host, port);
		result=login(userName, password);
		return result;
	}

	
	public boolean isConnSuccess(){
		boolean flag = true;
		try {
			int reply = getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				disconnect();
				flag = false;
			}
		} catch (IOException e) {
			logger.error("检测连接是否成功:"+e.getMessage(),e);
		}
		return flag;
	}

	/**
	 * 通过FTP目录路径获取文件列表
	 * @param pathName FTP目录路径
	 * @return FTPFile[]
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public FTPFile[] getListFileByPath(String remotePath) throws UnsupportedEncodingException, IOException{
		FTPFile[] remoteFiles = null;
		remoteFiles=listFiles(new String(remotePath.getBytes(),getRemoteEncoding()));
		return remoteFiles;
	}

	/**
	 * 获取服务器端编码
	 * @return String 编码
	 */
	public String getRemoteEncoding(){
		return getControlEncoding();
	}

	/**
	 * 获取本地编码
	 * @return String 编码
	 */
	public String getLocalEncoding(){
		return localEncoding;
	}

	/**
	 * 获取重试
	 */
	public String getReplyString() {
		String result=super.getReplyString();
		logger.info(result);
		return result;
	}

	/**
	 * 返回当前工作目录
	 * @return String 目录
	 */
	public String workingDirectory(){
		String result="";
		try {
			result = printWorkingDirectory();
		} catch (Exception e) {
			logger.error("返回当前工作目录:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 进入工作目录
	 * @return String 目录
	 */
	public boolean changeWorkingDirectory(String remotePathName){
		boolean result=false;
		try{
			result=super.changeWorkingDirectory(new String(remotePathName.getBytes(),getRemoteEncoding()));
		}catch(Exception e){
			logger.error("进入目录失败:"+e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 获取客户端FTP缓存的大小
	 * @return int 返回缓存大小
	 */
	public int getBufferSize(){
		return 100000;
	}

	/**
	 * 根据文件名删除远程文件
	 * @param remotePathName
	 * @return boolean 删除成功ture 失败false
	 */
	public boolean deleteRemoteFile(String remotePathName){
		boolean result=false;
		try{
			//设置路径为FTP服务器编码
			result=deleteFile(new String(remotePathName.getBytes(),getRemoteEncoding()));
		}catch(Exception e){
			logger.error("删除FTP服务器文件:"+remotePathName+"失败!",e);
		}
		return result;
	}

	/**
	 * 根据路径创建目录
	 * @param remotePathName
	 * @return boolean 创建成功true 失败false
	 */
	public boolean createDirectory(String remotePathName){
		boolean result=false;
		try{
			result=makeDirectory(new String(remotePathName.getBytes(),getRemoteEncoding()));
		}catch(Exception e){
			logger.error("服务器创建目录失败:"+e.getMessage(),e);
		}
		getReplyString();
		return result;
	}

	/**
	 * 删除目录
	 * @param remotePathName 路径名
	 * @return boolean 成功true 失败false
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public boolean deleteDirectory(String remotePathName) throws UnsupportedEncodingException, IOException{
		boolean result=false;
		result=removeDirectory(new String(remotePathName.getBytes(),getRemoteEncoding()));
		getReplyString();
		return result;
	}

	/**
	 * 验证是否是目录
	 * @param remotePathName
	 * @return boolean 创建成功true 失败false
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public boolean existDirectory(String remotePathName) throws UnsupportedEncodingException, IOException{
		boolean result=false;
		FTPFile[] remoteFiles=listFiles(new String(remotePathName.getBytes(),getRemoteEncoding()));
		for (FTPFile ftpFile : remoteFiles) {
			if (ftpFile.isDirectory()&& ftpFile.getName().equalsIgnoreCase(remotePathName)) {
				result = true;
                break;
            }
		}
		return result;
	}

	/**
	 * 上传文件到服务器
	 * @param String localPathName 本地保存路径
	 * @param String remotePathName 远程服务器文件地址
	 * @return boolean 返回true表示下载完成,返回false表示下载失败
	 * @throws IOException 
	 */
	public boolean uploadFile(String localPathName, String remotePathName) throws IOException{
		boolean result=false;
		InputStream inStream=null;
		try{
			File file=new File(localPathName);
			inStream=new FileInputStream(file);
			//上传
			result=storeFile(remotePathName,inStream);
		}finally{
			try{
				if(inStream!=null){
					inStream.close();
				}
			}catch(Exception ex){
				logger.error(ex.getMessage(), ex);
			}
		}
		return result;
	}
	/**
	 * 从服务器下载文件
	 * @param String localPathName 本地保存路径
	 * @param String remotePathName 远程服务器文件地址
	 * @return boolean 返回true表示下载完成,返回false表示下载失败
	 * @throws IOException 
	 */
	public boolean downloadFile(String localPathName, String remotePathName) throws IOException{
		boolean rename = false;
		String localTempFile = null;
		try {
			remotePathName = new String(remotePathName.getBytes(),getRemoteEncoding());
			localPathName = new String(localPathName.getBytes(),getLocalEncoding());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		

		localTempFile = localPathName + ".tem";//临时文件
		long pointLength = 0;
		File localFile = new File(localTempFile);// 获得临时文件大小
		pointLength = localFile.length();//当前指针,支持续传

		// 下载文件
		long beginTime = System.currentTimeMillis();
		InputStream remoteIn;
		
		pasv();
		getReplyString();
		
		// 断点传输
		setBufferSize(getBufferSize()); 
		pasv();
		getReplyString();
		rest(String.valueOf(pointLength));//设置继点下载
		getReplyString();
		remoteIn = retrieveFileStream(remotePathName);
		getReplyString();
		if(remoteIn!=null){
			RandomAccessFile raf = new RandomAccessFile(localTempFile, "rw");//随机读取
			raf.seek(pointLength);
			int bytesRead;
			logger.info("从：" + pointLength + " 开始读取");
			byte[] buf = new byte[getBufferSize()];
			while ((bytesRead = remoteIn.read(buf, 0, buf.length)) != -1) {// 从断点处写入
				raf.write(buf, 0, bytesRead);
			}
			remoteIn.close();
			raf.close();
			completePendingCommand();
			long endTime = System.currentTimeMillis() - beginTime;
			logger.info("Download Complete >>Time：" + (endTime / 1000) + "s");//下载时间

			rename = localFile.renameTo(new File(localPathName));//重名命
			logger.info("rename:" + rename);
			if (rename){// 从命名成功，删除临时文件
				localFile.deleteOnExit();
			}
		}
		
		return rename;
		
	}


	/**
	 * 退出并关闭FTP连接
	 */
	public void close() {
		if (null != this && this.isConnected()) {
	        try {
	            boolean reuslt = logout();// 退出FTP服务器
	            logger.info("FTP Quit：" + reuslt);
	        } catch (IOException e) {
	        	logger.error("退出FTP服务器异常。",e);
	        } catch(Exception ex){
	        	logger.error("退出FTP服务器异常。",ex);
	        }
	        finally {
	            try {
	            	this.disconnect();// 关闭FTP服务器的连接
	            } catch (Exception e) {
	                logger.error("关闭FTP服务器的连接异常。",e);
	            }
	        }
	    }
	}
	
}
