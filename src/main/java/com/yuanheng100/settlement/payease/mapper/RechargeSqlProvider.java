package com.yuanheng100.settlement.payease.mapper;

import java.util.HashMap;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by jlqian on 2016/12/26.
 */
public class RechargeSqlProvider
{

    public String selectRechargeCountByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("payease_recharge");
        WHERE("1 = 1");

        Object v_merdata4 = searchConditions.get("v_merdata4");
        if (v_merdata4!=null&&!"".equals(v_merdata4)) {
            AND();
            WHERE("v_merdata4 LIKE \"%\"#{v_merdata4,jdbcType=VARCHAR}\"%\"");
        }
        Object v_merdata3 = searchConditions.get("v_merdata3");
        if (v_merdata3!=null&&!"".equals(v_merdata3)) {
            AND();
            WHERE("v_merdata3 LIKE \"%\"#{v_merdata3,jdbcType=VARCHAR}\"%\"");
        }
        Object v_merdata5 = searchConditions.get("v_merdata5");
        if (v_merdata5!=null&&!"".equals(v_merdata5)) {
            AND();
            WHERE("v_merdata5 LIKE \"%\"#{v_merdata5,jdbcType=VARCHAR}\"%\"");
        }
        Object v_pstatus = searchConditions.get("v_pstatus");
        if (v_pstatus!=null&&!"".equals(v_pstatus)) {
            AND();
            WHERE("v_pstatus = #{v_pstatus}");
        }
        Object v_ymd_start = searchConditions.get("v_ymd_start");
        if (v_ymd_start!=null) {
            AND();
            WHERE("v_ymd >= #{v_ymd_start,jdbcType=TIMESTAMP}");
        }
        Object v_ymd_end = searchConditions.get("v_ymd_end");
        if (v_ymd_end!=null) {
            AND();
            WHERE("v_ymd <= #{v_ymd_end,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectRechargeListByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("*");
        FROM("payease_recharge");
        WHERE("1 = 1");

        Object v_merdata4 = searchConditions.get("v_merdata4");
        if (v_merdata4!=null&&!"".equals(v_merdata4)) {
            AND();
            WHERE("v_merdata4 LIKE \"%\"#{v_merdata4,jdbcType=VARCHAR}\"%\"");
        }
        Object v_merdata3 = searchConditions.get("v_merdata3");
        if (v_merdata3!=null&&!"".equals(v_merdata3)) {
            AND();
            WHERE("v_merdata3 LIKE \"%\"#{v_merdata3,jdbcType=VARCHAR}\"%\"");
        }
        Object v_merdata5 = searchConditions.get("v_merdata5");
        if (v_merdata5!=null&&!"".equals(v_merdata5)) {
            AND();
            WHERE("v_merdata5 LIKE \"%\"#{v_merdata5,jdbcType=VARCHAR}\"%\"");
        }
        Object v_pstatus = searchConditions.get("v_pstatus");
        if (v_pstatus!=null&&!"".equals(v_pstatus)) {
            AND();
            WHERE("v_pstatus = #{v_pstatus}");
        }
        Object v_ymd_start = searchConditions.get("v_ymd_start");
        if (v_ymd_start!=null) {
            AND();
            WHERE("v_ymd >= #{v_ymd_start,jdbcType=TIMESTAMP}");
        }
        Object v_ymd_end = searchConditions.get("v_ymd_end");
        if (v_ymd_end!=null) {
            AND();
            WHERE("v_ymd <= #{v_ymd_end,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY v_ymd DESC LIMIT #{fromIndex},#{endIndex}";
    }
}
