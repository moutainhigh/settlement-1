package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

/**
 * 提现响应
 * @author Administrator
 *
 */
public class TrsCashResp  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4657597341844968463L;
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
	 * 商户流水号
	 */
	private String serlNum;
	/**
	 * 授权号
	 */
	private String authId;
	/**
	 * P2P网站用户注册名
	 */
	private String user;
	/**
	 * 注册用户手机号
	 */
	private String mobile;
	/**
	 * 开户证件类型
	 */
	private String idType;
	/**
	 * 开户证件号
	 */
	private String id;
	/**
	 * 开户名
	 */
	private String accName;
	/**
	 * 开户省
	 */
	private String accProvince;
	/**
	 * 开户市
	 */
	private String accCity;
	/**
	 * 开户银行名称
	 */
	private String accBank;
	/**
	 * 开户分行支行名称
	 */
	private String accBranchName;
	/**
	 * 银行账号
	 */
	private String accNum;
	/**
	 * 账号类型
	 */
	private String accType;
	/**
	 * 账号类别
	 */
	private String accProp;
	/**
	 * 提现金额
	 */
	private String amount;
	/**
	 * P2P商户编号
	 */
	private String groupId;
	/**
	 * P2P子公司编号
	 */
	private String branchId;
	/**
	 * 结果返回码
	 */
	private String returnCode;
	/**
	 * 结果描述
	 */
	private String returnMsg;
	/**
	 * 预留字段1
	 */
	private String merData1;
	/**
	 * 预留字段2
	 */
	private String merData2;
	
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getAccProvince() {
		return accProvince;
	}
	public void setAccProvince(String accProvince) {
		this.accProvince = accProvince;
	}
	public String getAccCity() {
		return accCity;
	}
	public void setAccCity(String accCity) {
		this.accCity = accCity;
	}
	public String getAccBank() {
		return accBank;
	}
	public void setAccBank(String accBank) {
		this.accBank = accBank;
	}
	public String getAccBranchName() {
		return accBranchName;
	}
	public void setAccBranchName(String accBranchName) {
		this.accBranchName = accBranchName;
	}
	public String getAccNum() {
		return accNum;
	}
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getAccProp() {
		return accProp;
	}
	public void setAccProp(String accProp) {
		this.accProp = accProp;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
	public String getMerData1() {
		return merData1;
	}
	public void setMerData1(String merData1) {
		this.merData1 = merData1;
	}
	public String getMerData2() {
		return merData2;
	}
	public void setMerData2(String merData2) {
		this.merData2 = merData2;
	}
}
