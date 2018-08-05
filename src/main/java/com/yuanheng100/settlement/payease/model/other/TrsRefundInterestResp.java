package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;
import java.util.List;

public class TrsRefundInterestResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2707576200940633765L;
	/**
	 * 交易码
	 */
	private String transCode;
	/**
	 * 操作码
	 */
	private String operationCode;
	/**
	 * 请求日期
	 */
	private String reqTime;
	/**
	 * 流水号
	 */
	private String serlNum;
	/**
	 * 批次号
	 */
	private String batchNum;

	/**
	 * 转入方P2P网站用户注册名
	 */
	private String transferInUser;
	/**
	 * 证件类型
	 */
	private String transferInIdType;
	/**
	 * 证件号
	 */
	private String transferInId;
	/**
	 * 注册用户手机号
	 */
	private String transferInMobile;
	/**
	 * 批次交易总金额
	 */
	private String totalAmount;
	/**
	 * 
	 */
	private String totalNum;
	/**
	 * 
	 */
	private String pin;
	/**
	 * P2P总公司编号
	 */
	private String groupId;
	/**
	 * 预留字段1
	 */
	private String merdata1;
	/**
	 * 预留字段2
	 */
	private String merdata2;
	/**
	 * 单笔退款明细
	 */
	private List<RefundInterestInfo> infos;
	/**
	 * 结果返回码
	 */
	private String returnCode;
	/**
	 * 结果描述
	 */
	private String returnMsg;
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getSerlNum() {
		return serlNum;
	}
	public void setSerlNum(String serlNum) {
		this.serlNum = serlNum;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getTransferInUser() {
		return transferInUser;
	}
	public void setTransferInUser(String transferInUser) {
		this.transferInUser = transferInUser;
	}
	public String getTransferInIdType() {
		return transferInIdType;
	}
	public void setTransferInIdType(String transferInIdType) {
		this.transferInIdType = transferInIdType;
	}
	public String getTransferInId() {
		return transferInId;
	}
	public void setTransferInId(String transferInId) {
		this.transferInId = transferInId;
	}
	public String getTransferInMobile() {
		return transferInMobile;
	}
	public void setTransferInMobile(String transferInMobile) {
		this.transferInMobile = transferInMobile;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public List<RefundInterestInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<RefundInterestInfo> infos) {
		this.infos = infos;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	
}
