package com.yuanheng100.settlement.payease.provider;

import java.util.Map;

/**
 * Created by wangguangshuo on 2016/12/26.
 */
public class IdCardVerifySqlProvider
{

    public String getIdCardVerifyList(Map<String,Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT name,idcardNo,status,verifyTime FROM payease_idcard_verify WHERE 1=1 ");
        if (map.get("name") != null) buffer.append(" AND name like '%"+map.get("name")+"%' ");
        if (map.get("idcardNo") != null) buffer.append(" AND idcardNo = #{idcardNo} ");
        if (map.get("status") != null) buffer.append(" AND status = #{status} ");
        if (map.get("date1") != null) buffer.append(" AND verifyTime >= #{date1} ");
        if (map.get("date2") != null) buffer.append(" AND verifyTime <= #{date2} ");
        buffer.append(" ORDER BY verifyTime DESC LIMIT #{b_page},#{e_page}");
        return buffer.toString();
    }


    public  String getIdCardVerifyListCount(Map<String,Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(name) FROM payease_idcard_verify WHERE 1=1 ");
        if (map.get("name") != null) buffer.append(" AND name like '%"+map.get("name")+"%' ");
        if (map.get("idcardNo") != null) buffer.append(" AND idcardNo = #{idcardNo} ");
        if (map.get("status") != null) buffer.append(" AND status = #{status} ");
        if (map.get("date1") != null) buffer.append(" AND verifyTime >= #{date1} ");
        if (map.get("date2") != null) buffer.append(" AND verifyTime <= #{date2} ");
        return buffer.toString();
    }
}
