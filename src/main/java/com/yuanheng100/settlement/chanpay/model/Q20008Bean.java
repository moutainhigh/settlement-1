package com.yuanheng100.settlement.chanpay.model;

import com.yuanheng100.settlement.chanpay.common.QpayMsgBase;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jlqian on 2016/9/7.
 */
public class Q20008Bean extends QpayMsgBase {
    /**
     * 通知ID 	通知的唯一标识
     */
    private String notifyId;
    /**
     * 通知类型 交易通知此字段为：trade_status_sync
     */
    private String notifyType;
    /**
     * 通知时间 通知的发送时间，格式：yyyyMMddHHmmss
     */
    private Date notifyTime;
    /**
     * 商户网站唯一订单号 订单支付中的一笔订单号
     */
    private String outerTradeNo;
    /**
     * 支付平台交易订单号 交易订单号
     */
    private String innerTradeNo;
    /**
     * 交易状态 参见通知交易状态
     */
    private String tradeStatus;
    /**
     * 交易金额 交易金额，单位为RMB-Yuan，精确到小数点后两位
     */
    private BigDecimal tradeAmount;
    /**
     * 交易创建时间 交易创建时间，格式：yyyyMMddHHmmss
     */
    private Date gmtCreate;
    /**
     * 交易支付时间 交易支付时间，格式：yyyyMMddHHmmss
     */
    private Date gmtPayment;
    /**
     * 交易关闭时间 交易关闭时间，格式：yyyyMMddHHmmss
     */
    private Date gmtClose;
    /**
     * 扩展参数 格式：{}
     */
    private String extension;

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getOuterTradeNo() {
        return outerTradeNo;
    }

    public void setOuterTradeNo(String outerTradeNo) {
        this.outerTradeNo = outerTradeNo;
    }

    public String getInnerTradeNo() {
        return innerTradeNo;
    }

    public void setInnerTradeNo(String innerTradeNo) {
        this.innerTradeNo = innerTradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtPayment() {
        return gmtPayment;
    }

    public void setGmtPayment(Date gmtPayment) {
        this.gmtPayment = gmtPayment;
    }

    public Date getGmtClose() {
        return gmtClose;
    }

    public void setGmtClose(Date gmtClose) {
        this.gmtClose = gmtClose;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
