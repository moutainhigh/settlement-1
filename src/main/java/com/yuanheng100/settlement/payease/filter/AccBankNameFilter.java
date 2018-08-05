package com.yuanheng100.settlement.payease.filter;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * Created by jlqian on 2016/12/29.
 */
public class AccBankNameFilter implements NameFilter
{
    @Override
    public String process(Object object, String name, Object value)
    {
        if ("accBankCode".equals(name))
        {
            return "accBank";
        }
        return name;
    }
}
