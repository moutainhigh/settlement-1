package com.yuanheng100.settlement.unspay.model;

import com.yuanheng100.channel.entity.MessageRequest;

import java.util.Date;

/**
 * 子协议类
 *
 * @author qianjl
 * @version 1.0
 * @created 21-六月-2016 11:59:00
 */
public class UnspaySubContractRequest extends MessageRequest {
    /**
     * unspay_sub_contract每条记录的ID
     */
    private Integer tableId;
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

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
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