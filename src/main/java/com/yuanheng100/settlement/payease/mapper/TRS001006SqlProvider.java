package com.yuanheng100.settlement.payease.mapper;

import java.util.HashMap;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by jlqian on 2016/12/26.
 */
public class TRS001006SqlProvider
{


    public String getRecords006(Map<String,Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT * FROM payease_trs001006 ");
        buffer.append(" ORDER BY reqTime DESC LIMIT #{b_page},#{e_page}");
        return buffer.toString();
    }

    public String getRecords006Count(Map<String,Object> map)
    {
        StringBuffer buffer= new StringBuffer("SELECT COUNT(*) FROM payease_trs001006 ");
        return buffer.toString();
    }


    public String selectWithdrawCountByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("payease_trs001006");
        WHERE("1 = 1");

        Object accName = searchConditions.get("accName");
        if (accName!=null&&!"".equals(accName)) {
            AND();
            WHERE("accName LIKE \"%\"#{accName,jdbcType=VARCHAR}\"%\"");
        }
        Object accNum = searchConditions.get("accNum");
        if (accNum!=null&&!"".equals(accNum)) {
            AND();
            WHERE("accNum LIKE \"%\"#{accNum,jdbcType=VARCHAR}\"%\"");
        }
        Object user = searchConditions.get("user");
        if (user!=null&&!"".equals(user)) {
            AND();
            WHERE("user = #{user,jdbcType=INTEGER}");
        }
        Object returnCode = searchConditions.get("returnCode");
        if (returnCode!=null&&!"".equals(returnCode)) {
            AND();
            WHERE("returnCode = #{returnCode}");
        }
        Object returnStartTime = searchConditions.get("returnStartTime");
        if (returnStartTime!=null) {
            AND();
            WHERE("returnTime >= #{returnStartTime,jdbcType=TIMESTAMP}");
        }
        Object returnEndTime = searchConditions.get("returnEndTime");
        if (returnEndTime!=null) {
            AND();
            WHERE("returnTime <= #{returnEndTime,jdbcType=TIMESTAMP}");
        }
        Object plateform = searchConditions.get("plateform");
        if (1 == (Integer)plateform)
        {
            AND();
            WHERE("user >= #{online_plateform_user,jdbcType=INTEGER}");
        }else{
            AND();
            WHERE("user < #{online_plateform_user,jdbcType=INTEGER}");
        }
        return SQL();
    }

    public String selectWithdrawListByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("*");
        FROM("payease_trs001006");
        WHERE("1 = 1");

        Object accName = searchConditions.get("accName");
        if (accName!=null&&!"".equals(accName)) {
            AND();
            WHERE("accName LIKE \"%\"#{accName,jdbcType=VARCHAR}\"%\"");
        }
        Object accNum = searchConditions.get("accNum");
        if (accNum!=null&&!"".equals(accNum)) {
            AND();
            WHERE("accNum LIKE \"%\"#{accNum,jdbcType=VARCHAR}\"%\"");
        }
        Object user = searchConditions.get("user");
        if (user!=null&&!"".equals(user)) {
            AND();
            WHERE("user = #{user,jdbcType=INTEGER}");
        }
        Object returnCode = searchConditions.get("returnCode");
        if (returnCode!=null&&!"".equals(returnCode)) {
            AND();
            WHERE("returnCode = #{returnCode}");
        }
        Object returnStartTime = searchConditions.get("returnStartTime");
        if (returnStartTime!=null) {
            AND();
            WHERE("returnTime >= #{returnStartTime,jdbcType=TIMESTAMP}");
        }
        Object returnEndTime = searchConditions.get("returnEndTime");
        if (returnEndTime!=null) {
            AND();
            WHERE("returnTime <= #{returnEndTime,jdbcType=TIMESTAMP}");
        }
        Object plateform = searchConditions.get("plateform");
        if (1 == (Integer)plateform)
        {
            AND();
            WHERE("user >= #{online_plateform_user,jdbcType=INTEGER}");
        }else{
            AND();
            WHERE("user < #{online_plateform_user,jdbcType=INTEGER}");
        }
        return SQL() + " ORDER BY reqTime DESC LIMIT #{fromIndex},#{endIndex}";
    }
}
