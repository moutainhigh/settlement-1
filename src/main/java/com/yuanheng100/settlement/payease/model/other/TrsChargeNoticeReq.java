package com.yuanheng100.settlement.payease.model.other;

import java.io.Serializable;

/**
 * 充值通知请求
 * @author Administrator
 *
 */
public class TrsChargeNoticeReq  implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5473166368777845619L;
	/**
	 * 支付方式组
	 */
	private String vPmode;
	/**
	 * 数据签名
	 */
	private String vSign;
	/**
	 * 订单实际支付币种
	 */
	private String vMoneyType;
	/**
	 * 支付状态
	 */
	private String vPStatus;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 支付结果信息
	 */
	private String vPstring;
	/**
	 * 金额数据签名
	 */
	private String vMd5Money;
	/**
	 * 数字签名
	 */
	private String vMac;
	/**
	 * 订单实际支付金额
	 */
	private String vAmount;
	/**
	 * 订单个数
	 */
	private String vCount;
	/**
	 * 商户编号
	 */
	private String vMid;
	/**
	 * 请求报文
	 */
	private String reqTxt;
	
	public String getReqTxt() {
		return reqTxt;
	}
	public void setReqTxt(String reqTxt) {
		this.reqTxt = reqTxt;
	}
	public String getvPmode() {
		return vPmode;
	}
	public void setvPmode(String vPmode) {
		this.vPmode = vPmode;
	}
	public String getvSign() {
		return vSign;
	}
	public void setvSign(String vSign) {
		this.vSign = vSign;
	}
	public String getvMoneyType() {
		return vMoneyType;
	}
	public void setvMoneyType(String vMoneyType) {
		this.vMoneyType = vMoneyType;
	}
	public String getvPStatus() {
		return vPStatus;
	}
	public void setvPStatus(String vPStatus) {
		this.vPStatus = vPStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getvPstring() {
		return vPstring;
	}
	public void setvPstring(String vPstring) {
		this.vPstring = vPstring;
	}
	public String getvMd5Money() {
		return vMd5Money;
	}
	public void setvMd5Money(String vMd5Money) {
		this.vMd5Money = vMd5Money;
	}
	public String getvMac() {
		return vMac;
	}
	public void setvMac(String vMac) {
		this.vMac = vMac;
	}
	public String getvAmount() {
		return vAmount;
	}
	public void setvAmount(String vAmount) {
		this.vAmount = vAmount;
	}
	public String getvCount() {
		return vCount;
	}
	public void setvCount(String vCount) {
		this.vCount = vCount;
	}
	public String getvMid() {
		return vMid;
	}
	public void setvMid(String vMid) {
		this.vMid = vMid;
	}
}
