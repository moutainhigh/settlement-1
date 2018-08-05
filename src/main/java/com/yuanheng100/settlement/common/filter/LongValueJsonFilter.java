package com.yuanheng100.settlement.common.filter;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Created by jlqian on 2016/12/14.
 */
public class LongValueJsonFilter implements ValueFilter
{
    @Override
    public Object process(Object object, String name, Object value)
    {
        if(value instanceof Number){
            return value.toString();
        }
        return value;
    }
}
