package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

public class Item  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6595043840230007409L;
	/**
	 * 授权ID
	 */
	private String authId;
	/**
	 * 流水号
	 */
	private String serlNum;
	/**
	 * 还款转入金额
	 */
	private String payment;
	/**
	 * P2P网站出借人注册名
	 */
	private String lender;
	/**
	 * 出借人证件类型
	 */
	private String lenderIdtype;
	/**
	 * 出借人证件号
	 */
	private String lenderId;
	/**
	 * 出借人姓名
	 */
	private String lenderName;
	/**
	 * 结果返回码
	 */
	private String retrunCode;
	/**
	 * 结果描述
	 */
	private String returnMsg;
	/**
	 * 借据编号
	 */
	private String certNum;
	/**
	 * 备用字段1
	 */
	private String merdata1;
	/**
	 * 备用字段2
	 */
	private String merdata2;
	
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getSerlNum() {
		return serlNum;
	}
	public void setSerlNum(String serlNum) {
		this.serlNum = serlNum;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getLender() {
		return lender;
	}
	public void setLender(String lender) {
		this.lender = lender;
	}
	public String getLenderIdtype() {
		return lenderIdtype;
	}
	public void setLenderIdtype(String lenderIdtype) {
		this.lenderIdtype = lenderIdtype;
	}
	public String getLenderId() {
		return lenderId;
	}
	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getRetrunCode() {
		return retrunCode;
	}
	public void setRetrunCode(String retrunCode) {
		this.retrunCode = retrunCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getMerdata2() {
		return merdata2;
	}
	public void setMerdata2(String merdata2) {
		this.merdata2 = merdata2;
	}
	public String getMerdata1() {
		return merdata1;
	}
	public void setMerdata1(String merdata1) {
		this.merdata1 = merdata1;
	}
}
