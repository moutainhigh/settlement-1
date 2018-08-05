package com.yuanheng100.settlement.ghbank.model.register;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00044Resp extends GHBankResp
{

    private static final long serialVersionUID = 8414961678783693566L;
    /**
     * 原开户交易流水号
     */
    private String oldReqSeqNo;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }
}
