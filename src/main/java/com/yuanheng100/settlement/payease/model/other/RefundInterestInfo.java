package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

public class RefundInterestInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7437430368338903683L;
	/**
	 * 流水号
	 */
	private String serlNum;
	/**
	 * 授权号
	 */
	private String authId;
	/**
	 * 子公司账户退息凭证号
	 */
	private String cert_Num;
	/**
	 * 转入方P2P网站用户注册名
	 */
	private String transferOutUser;
	/**
	 * 证件类型
	 */
	private String transferOutIdType;
	/**
	 * 证件号
	 */
	private String transferOutId;
	/**
	 * 注册用户手机号
	 */
	private String transferOutMobile;
	/**
	 * 币种
	 */
	private String curr;
	/**
	 * 转账金额
	 */
	private String transferAmount;
	/**
	 * P2P子公司编号
	 */
	private String branchId;
	/**
	 * 预留字段1
	 */
	private String merdata1;
	/**
	 * 预留字段2
	 */
	private String merdata2;
	/**
	 * 结果返回码
	 */
	private String retrunCode;
	/**
	 * 结果描述
	 */
	private String returnMsg;
	public String getSerlNum() {
		return serlNum;
	}
	public void setSerlNum(String serlNum) {
		this.serlNum = serlNum;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getCert_Num() {
		return cert_Num;
	}
	public void setCert_Num(String cert_Num) {
		this.cert_Num = cert_Num;
	}
	public String getTransferOutUser() {
		return transferOutUser;
	}
	public void setTransferOutUser(String transferOutUser) {
		this.transferOutUser = transferOutUser;
	}
	public String getTransferOutIdType() {
		return transferOutIdType;
	}
	public void setTransferOutIdType(String transferOutIdType) {
		this.transferOutIdType = transferOutIdType;
	}
	public String getTransferOutId() {
		return transferOutId;
	}
	public void setTransferOutId(String transferOutId) {
		this.transferOutId = transferOutId;
	}
	public String getTransferOutMobile() {
		return transferOutMobile;
	}
	public void setTransferOutMobile(String transferOutMobile) {
		this.transferOutMobile = transferOutMobile;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getMerdata1() {
		return merdata1;
	}
	public void setMerdata1(String merdata1) {
		this.merdata1 = merdata1;
	}
	public String getMerdata2() {
		return merdata2;
	}
	public void setMerdata2(String merdata2) {
		this.merdata2 = merdata2;
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

	
	
}
