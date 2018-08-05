package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

/**
 * 提现交易
 * @author Administrator
 *
 */
public class TrsCashDeal  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9059359594676845403L;
	
	/**
	 * 系统序号
	 * */
	private String vSystemId;
	/**
	 * 交易码
	 * */
	private String vCCTransCode;
	/**
	 * 请求方流水号
	 * */
	private String vReqSeqNo;
	/**
	 * 收款方开户行城市
	 * */
	private String vCrCity;
	/**
	 * 商户号
	 * */
	private String groupId;
	/**
	 * 收款方账号
	 * */
	private String vCrAccNo;
	/**
	 * 收款方户名
	 * */
	private String vCrAccName;
	/**
	 * 收款方账户类型
	 * */
	private String vCrAccType;
	/**
	 * 货币类型
	 * */
	private String vCrCur;
	/**
	 * 收款方行别
	 * */
	private String vCrBankType;
	/**
	 * 收款方开户行号
	 * */
	private String vCrBankNo;
	/**
	 * 收款方开户行名
	 * */
	private String vCrBankName;
	/**
	 * 他行标志
	 * */
	private String vOthBankFlag;
	/**
	 * 公私类型
	 * */
	private String vPubPrivFlag;
	/**
	 * 同城异地
	 * */
	private String vIsSameCity;
	/**
	 * 金额
	 * */
	private String vAmt;
	/**
	 * 交易日期
	 * */
	private String vTxDt;
	/**
	 * 交易时间
	 * */
	private String vTxTm;
	/**
	 * 用途
	 * */
	private String vWhyUse;
	/**
	 * 数字签名
	 * */
	private String vSign;
	/**
	 * 加密标识
	 * */
	private String vSecFlag;
	/**
	 * 应答流水号
	 * */
	private String vRespSeqNo;
	/**
	 * 返回码
	 * */
	private String vRespCode;
	/**
	 * 返回信息
	 * */
	private String vRespInfo;
	/**
	 * 该流水状态信息
	 * */
	private String vPostscript;
	/**
	 * 通知URL
	 * */
	private String vNoticeUrl;
	/**
	 * 付款方行别(跨行)
	 * 跨行必须上送，目前只有农行可跨行代付，所以送103即可
	 * */
	private String vDrBankType;
	
	public String getvDrBankType() {
		return vDrBankType;
	}
	public void setvDrBankType(String vDrBankType) {
		this.vDrBankType = vDrBankType;
	}
	public String getvCrBankNo() {
		return vCrBankNo;
	}
	public void setvCrBankNo(String vCrBankNo) {
		this.vCrBankNo = vCrBankNo;
	}
	public String getvCrCity() {
		return vCrCity;
	}
	public void setvCrCity(String vCrCity) {
		this.vCrCity = vCrCity;
	}
	public String getvSecFlag() {
		return vSecFlag;
	}
	public void setvSecFlag(String vSecFlag) {
		this.vSecFlag = vSecFlag;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getvNoticeUrl() {
		return vNoticeUrl;
	}
	public void setvNoticeUrl(String vNoticeUrl) {
		this.vNoticeUrl = vNoticeUrl;
	}
	public String getvPostscript() {
		return vPostscript;
	}
	public void setvPostscript(String vPostscript) {
		this.vPostscript = vPostscript;
	}
	public String getvRespSeqNo() {
		return vRespSeqNo;
	}
	public void setvRespSeqNo(String vRespSeqNo) {
		this.vRespSeqNo = vRespSeqNo;
	}
	public String getvRespCode() {
		return vRespCode;
	}
	public void setvRespCode(String vRespCode) {
		this.vRespCode = vRespCode;
	}
	public String getvRespInfo() {
		return vRespInfo;
	}
	public void setvRespInfo(String vRespInfo) {
		this.vRespInfo = vRespInfo;
	}
	public String getvSystemId() {
		return vSystemId;
	}
	public void setvSystemId(String vSystemId) {
		this.vSystemId = vSystemId;
	}
	public String getvCCTransCode() {
		return vCCTransCode;
	}
	public void setvCCTransCode(String vCCTransCode) {
		this.vCCTransCode = vCCTransCode;
	}
	public String getvReqSeqNo() {
		return vReqSeqNo;
	}
	public void setvReqSeqNo(String vReqSeqNo) {
		this.vReqSeqNo = vReqSeqNo;
	}
	public String getvCrAccNo() {
		return vCrAccNo;
	}
	public void setvCrAccNo(String vCrAccNo) {
		this.vCrAccNo = vCrAccNo;
	}
	public String getvCrAccName() {
		return vCrAccName;
	}
	public void setvCrAccName(String vCrAccName) {
		this.vCrAccName = vCrAccName;
	}
	public String getvCrAccType() {
		return vCrAccType;
	}
	public void setvCrAccType(String vCrAccType) {
		this.vCrAccType = vCrAccType;
	}
	public String getvCrCur() {
		return vCrCur;
	}
	public void setvCrCur(String vCrCur) {
		this.vCrCur = vCrCur;
	}
	public String getvCrBankType() {
		return vCrBankType;
	}
	public void setvCrBankType(String vCrBankType) {
		this.vCrBankType = vCrBankType;
	}
	public String getvCrBankName() {
		return vCrBankName;
	}
	public void setvCrBankName(String vCrBankName) {
		this.vCrBankName = vCrBankName;
	}
	public String getvOthBankFlag() {
		return vOthBankFlag;
	}
	public void setvOthBankFlag(String vOthBankFlag) {
		this.vOthBankFlag = vOthBankFlag;
	}
	public String getvPubPrivFlag() {
		return vPubPrivFlag;
	}
	public void setvPubPrivFlag(String vPubPrivFlag) {
		this.vPubPrivFlag = vPubPrivFlag;
	}
	public String getvIsSameCity() {
		return vIsSameCity;
	}
	public void setvIsSameCity(String vIsSameCity) {
		this.vIsSameCity = vIsSameCity;
	}
	public String getvAmt() {
		return vAmt;
	}
	public void setvAmt(String vAmt) {
		this.vAmt = vAmt;
	}
	public String getvTxDt() {
		return vTxDt;
	}
	public void setvTxDt(String vTxDt) {
		this.vTxDt = vTxDt;
	}
	public String getvTxTm() {
		return vTxTm;
	}
	public void setvTxTm(String vTxTm) {
		this.vTxTm = vTxTm;
	}
	public String getvWhyUse() {
		return vWhyUse;
	}
	public void setvWhyUse(String vWhyUse) {
		this.vWhyUse = vWhyUse;
	}
	public String getvSign() {
		return vSign;
	}
	public void setvSign(String vSign) {
		this.vSign = vSign;
	}
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "TrsCashDeal [vSystemId=" + vSystemId + ", vCCTransCode=" + vCCTransCode + ", vReqSeqNo=" + vReqSeqNo
                + ", vCrCity=" + vCrCity + ", groupId=" + groupId + ", vCrAccNo=" + vCrAccNo + ", vCrAccName="
                + vCrAccName + ", vCrAccType=" + vCrAccType + ", vCrCur=" + vCrCur + ", vCrBankType=" + vCrBankType
                + ", vCrBankNo=" + vCrBankNo + ", vCrBankName=" + vCrBankName + ", vOthBankFlag=" + vOthBankFlag
                + ", vPubPrivFlag=" + vPubPrivFlag + ", vIsSameCity=" + vIsSameCity + ", vAmt=" + vAmt + ", vTxDt="
                + vTxDt + ", vTxTm=" + vTxTm + ", vWhyUse=" + vWhyUse + ", vSign=" + vSign + ", vSecFlag=" + vSecFlag
                + ", vRespSeqNo=" + vRespSeqNo + ", vRespCode=" + vRespCode + ", vRespInfo=" + vRespInfo
                + ", vPostscript=" + vPostscript + ", vNoticeUrl=" + vNoticeUrl + ", vDrBankType=" + vDrBankType + "]";
    }
	
	

}
