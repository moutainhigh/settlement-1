package com.yuanheng100.settlement.chanpay.service;

import com.yuanheng100.settlement.chanpay.model.G40001Bean;

import java.util.List;

/**
 * Created by jlqian on 2016/9/23.
 */
public interface IBankService
{
    /**
     * 从畅捷通获取银行数据，并存储道数据库
     */
    void obtainBank();

    List<G40001Bean> getBanksWithBankCodeAndBankName(String bankCode, String bankName);


}
