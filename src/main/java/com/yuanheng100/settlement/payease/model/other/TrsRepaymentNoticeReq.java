package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

/**
 * 还款通知请求
 * @author Administrator
 *
 */
public class TrsRepaymentNoticeReq  implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3727411013445679267L;
	/**
	 * 交易码
	 */
	private String transCode;
	/**
	 * 请求日期
	 */
	private String reqTime;
	/**
	 * 操作码
	 */
	private String operationCode;
	/**
	 * 流水号
	 */
	private String serNum;
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
	 * 总笔数
	 */
	private String totNum;
	/**
	 * 还款交易文件SFtp存放路径/文件名
	 */
	private String filePath;
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
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
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
	public String getTotNum() {
		return totNum;
	}
	public void setTotNum(String totNum) {
		this.totNum = totNum;
	}
	public String getSerNum() {
		return serNum;
	}
	public void setSerNum(String serNum) {
		this.serNum = serNum;
	}

}
