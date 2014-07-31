package com.maojm.code.vo;

import org.apache.commons.lang.StringUtils;


/**
 * 接口请求报文头信息
 * 
 * @author leon
 * @Create 2014-3-17
 */
public class RequestHeader
{

	/**
	 * 版本信息，如 Android_{channel}_1.0.0
	 */
	private String	clientVersion;
	private String	cua;
	
	/**
	 * 用户登录标识，由接口生成，登录的时候返回给客户端。
	 */
	private String	token;
	private String	imei;
	private String	mobNum;
	private String	userId;
	private String	transId;

	/**
	 * 客户端
	 */
	private String	client;

	/**
	 * 渠道信息
	 */
	private String	channel;

	/**
	 * 版本信息
	 */
	private String	version;

	/**
	 * 城市ID
	 */
	private String	cityId;

	/**
	 * 请求IP地址
	 */
	private String	ip;

	public String getCityId()
	{
		return cityId;
	}

	public void setCityId( String cityId )
	{
		this.cityId = cityId;
	}

	public String getClientVersion()
	{
		return clientVersion;
	}

	public void setClientVersion( String clientVersion )
	{
		this.clientVersion = clientVersion;
		if( !StringUtils.isEmpty( clientVersion ) )
		{
			String[] versions = clientVersion.split( "_" );
			if( versions.length == 3 )
			{
				this.setClient( versions[ 0 ].toLowerCase() );
				this.setChannel( versions[ 1 ].toLowerCase() );
				this.setVersion( versions[ 2 ] );
			}
		}
	}

	public String getCua()
	{
		return cua;
	}

	public void setCua( String cua )
	{
		this.cua = cua;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public String getImei()
	{
		return imei;
	}

	public void setImei( String imei )
	{
		this.imei = imei;
	}

	public String getMobNum()
	{
		return mobNum;
	}

	public void setMobNum( String mobNum )
	{
		this.mobNum = mobNum;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId( String userId )
	{
		this.userId = userId;
	}

	public String getTransId()
	{
		return transId;
	}

	public void setTransId( String transId )
	{
		this.transId = transId;
	}

	public String getClient()
	{
		return client;
	}

	public void setClient( String client )
	{
		this.client = client;
	}

	public String getChannel()
	{
		return channel;
	}

	public void setChannel( String channel )
	{
		this.channel = channel;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion( String version )
	{
		this.version = version;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

}
