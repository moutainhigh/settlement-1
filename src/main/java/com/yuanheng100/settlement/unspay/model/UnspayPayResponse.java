package com.yuanheng100.settlement.unspay.model;

import com.yuanheng100.channel.entity.MessageResponse;

/**
 * Created by qianjl on 2016-6-27.
 */
public class UnspayPayResponse extends MessageResponse {
    /**
     * 返回码 0000表示成功（发送代扣请求成功）
     */
    private String resultCode;
    /**
     * 错误信息
     */
    private String resultMessage;
    /**
     * 付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）
     */
    private String deductResult;
    /**
     * 交易结果描述信息（主动查询获取）
     */
    private String desc;

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

    public String getDeductResult() {
        return deductResult;
    }

    public void setDeductResult(String deductResult) {
        this.deductResult = deductResult;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
