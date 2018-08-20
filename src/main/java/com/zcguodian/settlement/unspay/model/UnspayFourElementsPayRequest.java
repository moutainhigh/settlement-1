package com.zcguodian.settlement.unspay.model;

import com.yuanheng100.channel.entity.MessageRequest;

/**
 * 实时代付类
 *
 */
public class UnspayFourElementsPayRequest extends MessageRequest {
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 返回码 0000表示成功
     */
    private String resultCode;
    /**
     * 错误信息
     */
    private String resultMessage;
    /**
     * 消息类型（Adaptor中分辨消息使用）
     */
    private Short messageType;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Short getMessageType() {
        return messageType;
    }

    public void setMessageType(Short messageType) {
        this.messageType = messageType;
    }
}