package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;
import java.util.List;

/**
 * 还款交易明细结果
 * @author Administrator
 *
 */
public class RepaymentDealResult  implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5755167103377901006L;
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
	 * P2P网站借款人注册名
	 */
	private String borrower;
	/**
	 * 借款人证件类型
	 */
	private String borrowerIdType;
	/**
	 * 借款人（还款人）证件号
	 */
	private String borrowerId;
	/**
	 * 借款人（还款人）姓名
	 */
	private String borrowerName;
	/**
	 * 借款标的号（合同号）
	 */
	private String contractNum;
	/**
	 * 币种
	 */
	private String curr;
	/**
	 * 还款总金额
	 */
	private String totalPayment;
	/**
	 * 还款总笔数
	 */
	private String totalNum;
	/**
	 * P2P总公司编号
	 */
	private String groupId;
	/**
	 * P2P子公司编号
	 */
	private String branchId;
	/**
	 * 被还款人明细
	 */
	private List<Item> itmes;
	
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
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getBorrowerIdType() {
		return borrowerIdType;
	}
	public void setBorrowerIdType(String borrowerIdType) {
		this.borrowerIdType = borrowerIdType;
	}
	public String getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
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
	public List<Item> getItmes() {
		return itmes;
	}
	public void setItmes(List<Item> itmes) {
		this.itmes = itmes;
	}
	

}
