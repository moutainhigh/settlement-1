package com.yuanheng100.settlement.payease.model.trs001002;

import com.yuanheng100.settlement.payease.consts.Currency;
import com.yuanheng100.settlement.payease.consts.TransCode;
import com.yuanheng100.settlement.payease.model.PayeaseReq;

/**
 * 投标冻结解冻请求
 * 
 * @author Administrator
 * 
 */
public class TRS001002Req extends PayeaseReq
{


    private static final long serialVersionUID = -7480465732328650859L;
    /**
	 * 商户流水号
	 */
	private String serlNum;
	/**
	 * 授权号
	 */
	private String authId;
	// /**
	// * 订单号
	// */
	// private String ordrId;
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
	/**
	 * P2P网站出借人（转出方）注册名
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
	
	
	private int moneyRecordId;
	

	public TRS001002Req()
    {
        super();
        this.setTransCode(TransCode.TRS001002.getCode());
        this.setCurr(Currency.RMB.getCode());
    }

    public String getSerlNum()
	{
		return serlNum;
	}

	public void setSerlNum(String serlNum)
	{
		this.serlNum = serlNum;
	}

	// public String getOrdrId() {
	// return ordrId;
	// }
	// public void setOrdrId(String ordrId) {
	// this.ordrId = ordrId;
	// }
	public String getBorrower()
	{
		return borrower;
	}

	public void setBorrower(String borrower)
	{
		this.borrower = borrower;
	}

	public String getBorrowerIdType()
	{
		return borrowerIdType;
	}

	public void setBorrowerIdType(String borrowerIdType)
	{
		this.borrowerIdType = borrowerIdType;
	}

	public String getBorrowerId()
	{
		return borrowerId;
	}

	public void setBorrowerId(String borrowerId)
	{
		this.borrowerId = borrowerId;
	}

	public String getBorrowerName()
	{
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName)
	{
		this.borrowerName = borrowerName;
	}

	public String getContractNum()
	{
		return contractNum;
	}

	public void setContractNum(String contractNum)
	{
		this.contractNum = contractNum;
	}

	public String getCertNum()
	{
		return certNum;
	}

	public void setCertNum(String certNum)
	{
		this.certNum = certNum;
	}

	public String getCurr()
	{
		return curr;
	}

	public void setCurr(String curr)
	{
		this.curr = curr;
	}

	public String getLoanAmount()
	{
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public String getLender()
	{
		return lender;
	}

	public void setLender(String lender)
	{
		this.lender = lender;
	}

	public String getLenderIdtype()
	{
		return lenderIdtype;
	}

	public void setLenderIdtype(String lenderIdtype)
	{
		this.lenderIdtype = lenderIdtype;
	}

	public String getLenderId()
	{
		return lenderId;
	}

	public void setLenderId(String lenderId)
	{
		this.lenderId = lenderId;
	}

	public String getLenderName()
	{
		return lenderName;
	}

	public void setLenderName(String lenderName)
	{
		this.lenderName = lenderName;
	}

	public String getAuthId()
	{
		return authId;
	}

	public void setAuthId(String authId)
	{
		this.authId = authId;
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
		return "TRS001002Req [serlNum=" + serlNum + ", authId=" + authId + ", borrower=" + borrower + ", borrowerIdType="
				+ borrowerIdType + ", borrowerId=" + borrowerId + ", borrowerName=" + borrowerName + ", contractNum="
				+ contractNum + ", certNum=" + certNum + ", curr=" + curr + ", loanAmount=" + loanAmount + ", lender="
				+ lender + ", lenderIdtype=" + lenderIdtype + ", lenderId=" + lenderId + ", lenderName=" + lenderName
				 + "]";
	}
	
}
