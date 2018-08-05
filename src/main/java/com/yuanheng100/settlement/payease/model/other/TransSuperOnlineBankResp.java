package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

public class TransSuperOnlineBankResp implements Serializable{

	private static final long serialVersionUID = -2253561558485629514L;
	
	/**
	 * 处理响应码
	 */
	private String respCode;
	
	/**
	 * 处理响应信息
	 */
	private String respMsg;
	
	/**
	 * 转账凭证号
	 */
	private String origThirdVoucher;
	
	/**
	 * 银行流水号
	 */
	private String frontLogNo;
	
	/**
	 * 货币类型
	 */
	private String ccyCode;
	
	/**
	 * 转出账户
	 */
	private String outAcctNo;
	
	/**
	 * 转入账户
	 */
	private String inAcctNo;
	
	/**
	 * 转入账户户名
	 */
	private String inAcctName;
	
	/**
	 * 交易金额
	 */
	private String tranAmount;
	
	/**
	 * 行内跨行标志
	 */
	private String unionFlag;
	
	/**
	 * 交易状态标志
	 */
	private String stt;
	
	/**
	 * 支付失败或退票原因描述
	 */
	private String backRem;
	
	/**
	 * 银行处理结果
	 */
	private String yhcljg;
	
	/**
	 * 转账加急标志
	 */
	private String sysFlag;
	
	/**
	 * 转账手续费
	 */
	private String fee;
	
	/**
	 * 转账代码类型
	 */
	private String transBsn;
	
	/**
	 * 签名信息
	 */
	private String signature;
	/**
	 * 响应报文基本状态；如果返回不是0代表该响应报文不正常应记入异常处理
	 * */
	private String status;
	/**
	 * 响应报文基本状态描述
	 * */
	private String statusDesc;
	/**
	 * 前置平台状态
	 * */
	private String qzstt;
	
	public String getQzstt() {
		return qzstt;
	}

	public void setQzstt(String qzstt) {
		this.qzstt = qzstt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getOrigThirdVoucher() {
		return origThirdVoucher;
	}

	public void setOrigThirdVoucher(String origThirdVoucher) {
		this.origThirdVoucher = origThirdVoucher;
	}

	public String getFrontLogNo() {
		return frontLogNo;
	}

	public void setFrontLogNo(String frontLogNo) {
		this.frontLogNo = frontLogNo;
	}

	public String getCcyCode() {
		return ccyCode;
	}

	public void setCcyCode(String ccyCode) {
		this.ccyCode = ccyCode;
	}

	public String getOutAcctNo() {
		return outAcctNo;
	}

	public void setOutAcctNo(String outAcctNo) {
		this.outAcctNo = outAcctNo;
	}

	public String getInAcctNo() {
		return inAcctNo;
	}

	public void setInAcctNo(String inAcctNo) {
		this.inAcctNo = inAcctNo;
	}

	public String getInAcctName() {
		return inAcctName;
	}

	public void setInAcctName(String inAcctName) {
		this.inAcctName = inAcctName;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getUnionFlag() {
		return unionFlag;
	}

	public void setUnionFlag(String unionFlag) {
		this.unionFlag = unionFlag;
	}

	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getBackRem() {
		return backRem;
	}

	public void setBackRem(String backRem) {
		this.backRem = backRem;
	}

	public String getYhcljg() {
		return yhcljg;
	}

	public void setYhcljg(String yhcljg) {
		this.yhcljg = yhcljg;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getTransBsn() {
		return transBsn;
	}

	public void setTransBsn(String transBsn) {
		this.transBsn = transBsn;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
