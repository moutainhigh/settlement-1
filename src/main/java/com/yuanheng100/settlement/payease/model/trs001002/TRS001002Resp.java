package com.yuanheng100.settlement.payease.model.trs001002;

import com.yuanheng100.settlement.payease.model.PayeaseResp;


/**
 * 投标冻结解冻应签
 * @author Administrator
 *
 */
public class TRS001002Resp extends PayeaseResp {

	/**
	 * 商户流水号
	 */
	private String serlNum;
	/**
	 * 订单号
	 */
	private String authId;
	/**
	 * 借款标的号（合同号）
	 */
	private String contractNum;
	/**
	 * 出借借据编号（凭证）
	 */
	private String certNum;
	/**
	 * 币种
	 */
	private String curr;
	/**
	 * 出借金额
	 */
	private String loanAmount;

    
    private int moneyRecordId;

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
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
    /**
     * @return the moneyRecordId
     */
    public int getMoneyRecordId()
    {
        return moneyRecordId;
    }
    /**
     * @param moneyRecordId the moneyRecordId to set
     */
    public void setMoneyRecordId(int moneyRecordId)
    {
        this.moneyRecordId = moneyRecordId;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "TRS001002Resp [returnCode="+super.getReturnCode()+", returnMsg="+super.getReturnMsg()+", transCode=" + getTransCode() + ", reqTime=" + getReqTime() + ", operationCode=" + getOperationCode()
                + ", serlNum=" + serlNum + ", authId=" + authId + ", contractNum=" + contractNum + ", certNum="
                + certNum + ", curr=" + curr + ", loanAmount=" + loanAmount + "]";
    }

	
}
