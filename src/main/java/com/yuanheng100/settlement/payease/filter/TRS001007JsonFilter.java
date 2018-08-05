package com.yuanheng100.settlement.payease.filter;

import com.alibaba.fastjson.serializer.NameFilter;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by jlqian on 2016/12/14.
 */
public class TRS001007JsonFilter implements NameFilter
{
    private static HashSet<String> properties =  new HashSet<String>();
    static {
        properties.add("transferOutUser");
        properties.add("transferInUser");
        properties.add("transferAmount");
        properties.add("transferOutUserFee");
        properties.add("transferInUserFee");
    }

    public String process(Object source, String name, Object value) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if(properties.contains(name)){
            char[] chars = name.toCharArray();
            chars[0]= Character.toUpperCase(chars[0]);

            String pascalName = new String(chars);
            return pascalName;
        }
        return name;
    }
}
