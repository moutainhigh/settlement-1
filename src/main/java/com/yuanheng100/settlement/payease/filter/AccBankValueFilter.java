package com.yuanheng100.settlement.payease.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.yuanheng100.settlement.common.utils.BankUtil;

/**
 * Created by jlqian on 2016/12/29.
 */
public class AccBankValueFilter implements ValueFilter
{
    @Override
    public Object process(Object object, String name, Object value)
    {
        if ("accBank".equals(name))
        {
            if (value != null)
            {
                return BankUtil.getBankByCode((Short) value).getFullName();
            }
        }
        return value;
    }
}
