package com.yuanheng100.settlement.common.model.customer;

import com.yuanheng100.settlement.common.model.Settlement;

/**
 * 所有与客户相关操作的类，开户，绑卡等操作
 * @author tianwei
 *
 */
public class Customer extends Settlement {
    /**
     * p2p网站用户注册id，在网站上唯一
     */
    private String customerId;
    
    /**
     * 用户在结算合作方的唯一标示，由结算合作方提供
     */
    private String usrCustId;
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsrCustId() {
        return usrCustId;
    }

    public void setUsrCustId(String usrCustId) {
        this.usrCustId = usrCustId;
    }
    
}
