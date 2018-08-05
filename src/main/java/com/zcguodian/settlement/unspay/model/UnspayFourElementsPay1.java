package com.zcguodian.settlement.unspay.model;

import java.math.BigDecimal;

public class UnspayFourElementsPay1
{
	/**
	 * 
	 */
	private Long orderId;
	/**
	 * 商户编号
	 */
	private Long accountId;
	/**
	 * 用户姓名
	 */
	private String name;
	
	private Long cardNo;
	
	private String purpose;
	
	private float amount;
	
	private Long idCardNo;
	
	private String summary;
	
	private Long phoneNo;
	
	private String responseUrl;
	
	private String mac;

	public Long getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}

	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getCardNo()
	{
		return cardNo;
	}

	public void setCardNo(Long cardNo)
	{
		this.cardNo = cardNo;
	}

	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose(String purpose)
	{
		this.purpose = purpose;
	}

	public float getAmount()
	{
		return amount;
	}

	public void setAmount(float amount)
	{
		this.amount = amount;
	}

	public Long getIdCardNo()
	{
		return idCardNo;
	}

	public void setIdCardNo(Long idCardNo)
	{
		this.idCardNo = idCardNo;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public Long getPhoneNo()
	{
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo)
	{
		this.phoneNo = phoneNo;
	}

	public String getResponseUrl()
	{
		return responseUrl;
	}

	public void setResponseUrl(String responseUrl)
	{
		this.responseUrl = responseUrl;
	}

	public String getMac()
	{
		return mac;
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	@Override
	public String toString()
	{
		return "UnspayFourElementsPay [orderId=" + orderId + ", accountId=" + accountId + ", name=" + name + ", cardNo="
				+ cardNo + ", purpose=" + purpose + ", amount=" + amount + ", idCardNo=" + idCardNo + ", summary="
				+ summary + ", phoneNo=" + phoneNo + ", responseUrl=" + responseUrl + ", mac=" + mac + "]";
	}
	
}
