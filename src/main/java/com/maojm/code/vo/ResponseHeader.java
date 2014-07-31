package com.maojm.code.vo;

public class ResponseHeader
{

	// ***（返回状态，0-正常，其它状态在各接口中说明）
	private int		espState		= 0;
	// **********（流水号，与客户端请求的一致）
	private String	transId;
	private String	contentType		= "text/html";
	// ****(报文大小 整型)
	private int		contentLength	= 0;

	public int getEspState()
	{
		return espState;
	}

	public void setEspState( int espState )
	{
		this.espState = espState;
	}

	public String getTransId()
	{
		return transId;
	}

	public void setTransId( String transId )
	{
		this.transId = transId;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType( String contentType )
	{
		this.contentType = contentType;
	}

	public int getContentLength()
	{
		return contentLength;
	}

	public void setContentLength( int contentLength )
	{
		this.contentLength = contentLength;
	}

}
