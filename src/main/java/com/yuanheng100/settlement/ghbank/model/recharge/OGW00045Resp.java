package com.yuanheng100.settlement.ghbank.model.recharge;

import com.yuanheng100.settlement.ghbank.model.GHBankResp;

/**
 * Created by jlqian on 2017/4/19.
 */
public class OGW00045Resp extends GHBankResp
{

    private static final long serialVersionUID = -1543116592340828965L;
    /**
     * 原交易流水号
     */
    private String oldReqSeqNo;
    /**
     * 订单处理状态
     */
    private String orderStatus;

    public String getOldReqSeqNo()
    {
        return oldReqSeqNo;
    }

    public void setOldReqSeqNo(String oldReqSeqNo)
    {
        this.oldReqSeqNo = oldReqSeqNo;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }


}
