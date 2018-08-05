package com.yuanheng100.settlement.common.service.impl;

import com.yuanheng100.settlement.common.mapper.SysBankMapper;
import com.yuanheng100.settlement.common.model.system.SysBank;
import com.yuanheng100.settlement.common.service.ISysBankService;
import com.yuanheng100.settlement.common.utils.BankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2017/1/3.
 */
@Service("sysBankService")
public class SysBankServiceImpl implements ISysBankService
{
    @Autowired
    private SysBankMapper sysBankMapper;

    private List<SysBank> sysBanks = null;

    private Map<Short, SysBank> codeBanks = new HashMap<Short, SysBank>();

    private Map<String, SysBank> nameBanks = new HashMap<String, SysBank>();

    @PostConstruct
    public void init()
    {
        sysBanks = sysBankMapper.selectAll();
        for (SysBank sysBank : sysBanks)
        {
            codeBanks.put(sysBank.getCode(),sysBank);
            nameBanks.put(sysBank.getFullName(),sysBank);
        }
        //Bank构建静态资源
        BankUtil.init(sysBanks);
    }

    @Override
    public List<SysBank> getAllBanks()
    {
        return sysBanks;
    }

    @Override
    public SysBank getBankByCode(Short code)
    {
        return codeBanks.get(code);
    }

    @Override
    public SysBank getBankByFullName(String fullName)
    {
        return nameBanks.get(fullName);
    }
}
