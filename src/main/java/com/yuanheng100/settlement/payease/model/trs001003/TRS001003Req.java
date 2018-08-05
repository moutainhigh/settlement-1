package com.yuanheng100.settlement.payease.model.trs001003;

import com.yuanheng100.settlement.payease.consts.Currency;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;

/**
 * 标的成功通知请求 TRS001003
 * @author Administrator
 *
 */
public class TRS001003Req extends PayeaseReq {

    private static final long serialVersionUID = -1166083358708813319L;
    /**
	 * 商户流水号
	 */
	private String serlNum;
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
	 * 借款总金额
	 */
	private String totalAmount;
	/**
	 * 借款总笔数
	 */
	private String totalNum;

	/**
	 * 授权号
	 */
	private String authId;
	
    public TRS001003Req()
    {
        super();
        this.setTransCode(TransCode.TRS001003.getCode());
        this.setCurr(Currency.RMB.getCode());
    }

	public String getSerlNum() {
		return serlNum;
	}
	public void setSerlNum(String serlNum) {
		this.serlNum = serlNum;
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

	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	
}
