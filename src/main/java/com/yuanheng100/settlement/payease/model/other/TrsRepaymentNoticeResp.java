package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

/**
 * 还款通知应答
 * @author Administrator
 *
 */
public class TrsRepaymentNoticeResp  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2408955155988566509L;
	/**
	 * 交易码
	 */
	private String transCode;
	/**
	 * 请求日期
	 */
	private String reqTime;
	/**
	 * 批次号
	 */
	private String batchNum;
	/**
	 * 币种
	 */
	private String curr;
	/**
	 * 还款总金额
	 */
	private String totPayment;
	/**
	 * 还款交易文件SFtp存放路径/文件名
	 */
	private String filePath;
	/**
	 * 结果返回码
	 */
	private String returnCode;
	/**
	 * 结果描述
	 */
	private String returnMsg;
	/**
	 * P2P总公司编号
	 */
	private String groupId;
	/**
	 * P2P子公司编号
	 */
	private String branchId;
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getTotPayment() {
		return totPayment;
	}
	public void setTotPayment(String totPayment) {
		this.totPayment = totPayment;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	}
