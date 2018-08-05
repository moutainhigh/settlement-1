package com.yuanheng100.settlement.common.utils;

import com.yuanheng100.settlement.common.model.system.SysBank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2017/1/3.
 */
public class BankUtil
{
    private static List<SysBank> sysBanks = null;

    private static Map<Short, SysBank> codeBanks = new HashMap<Short, SysBank>();

    private static Map<String, SysBank> nameBanks = new HashMap<String, SysBank>();

    public static void init(List<SysBank> sysBanks)
    {
        BankUtil.sysBanks = sysBanks;
        for (SysBank sysBank : sysBanks)
        {
            codeBanks.put(sysBank.getCode(),sysBank);
            nameBanks.put(sysBank.getFullName(),sysBank);
        }
    }

    public static List<SysBank> getAllBanks()
    {
        return sysBanks;
    }

    public static SysBank getBankByCode(Short code)
    {
        return codeBanks.get(code);
    }

    public static SysBank getBankByFullName(String fullName)
    {
        return nameBanks.get(fullName);
    }
}
