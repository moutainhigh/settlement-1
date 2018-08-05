package com.yuanheng100.settlement.payease.provider;

import java.util.HashMap;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by wangguangshuo on 2017/1/4.
 */
public class TRS001002SqlProvider
{

    public String getRecords002(Map<String,Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT * FROM payease_trs001002");
        buffer.append(" ORDER BY reqTime DESC LIMIT #{b_page},#{e_page} ");
        return buffer.toString();
    }


    public String getRecords002Count(Map<String,Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(*) FROM payease_trs001002");

        return buffer.toString();
    }

    public String selectInvestCountByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("payease_trs001002");
        WHERE("1 = 1");

        Object lender = searchConditions.get("lender");
        if (lender!=null&&!"".equals(lender)) {
            AND();
            WHERE("lender = #{lender,jdbcType=INTEGER}");
        }
        Object lenderId = searchConditions.get("lenderId");
        if (lenderId!=null&&!"".equals(lenderId)) {
            AND();
            WHERE("lenderId LIKE \"%\"#{lenderId,jdbcType=VARCHAR}\"%\"");
        }
        Object lenderName = searchConditions.get("lenderName");
        if (lenderName!=null&&!"".equals(lenderName)) {
            AND();
            WHERE("lenderName LIKE \"%\"#{lenderName,jdbcType=VARCHAR}\"%\"");
        }
        Object contractNum = searchConditions.get("contractNum");
        if (contractNum!=null&&!"".equals(contractNum)) {
            AND();
            WHERE("contractNum LIKE \"%\"#{contractNum,jdbcType=VARCHAR}\"%\"");
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
        return SQL();
    }

    public String selectInvestListByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("*");
        FROM("payease_trs001002");
        WHERE("1 = 1");

        Object lender = searchConditions.get("lender");
        if (lender!=null&&!"".equals(lender)) {
            AND();
            WHERE("lender = #{lender,jdbcType=INTEGER}");
        }
        Object lenderId = searchConditions.get("lenderId");
        if (lenderId!=null&&!"".equals(lenderId)) {
            AND();
            WHERE("lenderId LIKE \"%\"#{lenderId,jdbcType=VARCHAR}\"%\"");
        }
        Object lenderName = searchConditions.get("lenderName");
        if (lenderName!=null&&!"".equals(lenderName)) {
            AND();
            WHERE("lenderName LIKE \"%\"#{lenderName,jdbcType=VARCHAR}\"%\"");
        }
        Object contractNum = searchConditions.get("contractNum");
        if (contractNum!=null&&!"".equals(contractNum)) {
            AND();
            WHERE("contractNum LIKE \"%\"#{contractNum,jdbcType=VARCHAR}\"%\"");
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
        return SQL() + " ORDER BY reqTime DESC LIMIT #{fromIndex},#{endIndex}";
    }
}
