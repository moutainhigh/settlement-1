package com.zcguodian.settlement.unspay.model;

import java.math.BigDecimal;

public class UnspayFourElementsPayResponse
{
	private String resultCode;
	
	private String resultMessage;
	
	private Short Status;
	
	private String desc;
	
	private BigDecimal amount;
	
	private Long orderId;
	
	private String mac;
	
	private BigDecimal balance;

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

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public Long getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}

	public String getMac()
	{
		return mac;
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}
	
}
