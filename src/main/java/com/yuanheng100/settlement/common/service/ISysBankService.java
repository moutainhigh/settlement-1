package com.yuanheng100.settlement.common.service;

import com.yuanheng100.settlement.common.model.system.SysBank;

import java.util.List;

/**
 * Created by jlqian on 2017/1/3.
 */
public interface ISysBankService
{
    /**
     * 获取存储的所有Bank
     * @return
     */
    List<SysBank> getAllBanks();

    /**
     * 通过银行代码获取银行对象
     * @param code
     * @return
     */
    SysBank getBankByCode(Short code);

    /**
     * 通过银行全称获取银行对象
     * @param fullName
     * @return
     */
    SysBank getBankByFullName(String fullName);


}
