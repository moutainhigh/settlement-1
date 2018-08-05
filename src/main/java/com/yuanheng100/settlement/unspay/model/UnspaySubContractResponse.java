package com.yuanheng100.settlement.unspay.model;

import com.yuanheng100.channel.entity.MessageResponse;

import java.io.Serializable;

/**
 * Created by qianjl on 2016-6-27.
 */
public class UnspaySubContractResponse extends MessageResponse implements Serializable {

    /**
     * 返回码 0000表示成功
     */
    private String resultCode;
    /**
     * 错误信息
     */
    private String resultMessage;
    /**
     * 子协议号，在调用子协议录入接口后返回
     */
    private String subContractId;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSubContractId() {
        return subContractId;
    }

    public void setSubContractId(String subContractId) {
        this.subContractId = subContractId;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
