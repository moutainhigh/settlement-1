package com.zcguodian.settlement.unspay.model;


public class UnspayFourElementsPayResponse
{
	/**
	 * 返回码  0000表示处理成功，改成功只代表受理代付请求成功，不代表代付交易结果是成功
	 */
	private String resultCode;
	
	/**
	 * 返回信息
	 */
	private String resultMessage;
	
	/**
	 * 交易状态：00，成功；10，处理中；20，失败；
	 */
	private Short Status;
	
	/**
	 * 交易结果描述信息
	 */
	private String desc;
	
	public String getResultCode()
	{
		return resultCode;
	}

	public void setResultCode(String resultCode)
	{
		this.resultCode = resultCode;
	}

	public String getResultMessage()
	{
		return resultMessage;
	}

	public void setResultMessage(String resultMessage)
	{
		this.resultMessage = resultMessage;
	}

	public Short getStatus()
	{
		return Status;
	}

	public void setStatus(Short status)
	{
		Status = status;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}
}
