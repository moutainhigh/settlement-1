package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

/**
 * 用户信息验证
 * @author Administrator
 *
 */
public class TrsInfoValResp  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9059359594676845403L;
	/**
	 * 交易码
	 * */
	private String transCode;
	/**
	 * 总公司编号
	 * */
	private String groupId;
	/**
	 * 证件号
	 * */
	private String cardId;
	/**
	 * 证件类型
	 * */
	private String cardKind;
	/**
	 * 持卡人姓名
	 * */
	private String cardName;
	/**
	 * 卡号
	 * */
	private String pan;
	/**
	 * 返回码
	 * */
	private String respCode;
	/**
	 * 返回码描述
	 * */
	private String respDesc;
	
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCardKind() {
		return cardKind;
	}
	public void setCardKind(String cardKind) {
		this.cardKind = cardKind;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	
}
