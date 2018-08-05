package com.yuanheng100.settlement.payease.mapper;

import com.yuanheng100.settlement.payease.consts.ReturnCode;

import java.util.HashMap;
import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

/**
 * Created by jlqian on 2016/12/23.
 */
public class SYN001001SqlProvider
{

    public String getBatchQueryPage(Map<String, Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT a.* FROM (SELECT * FROM payease_syn001001  WHERE 1=1 ");
        if (map.get("bRegName") != null)
        {
            buffer.append(" AND user >= #{bRegName} ");
        }
        if (map.get("eRegName") != null)
        {
            buffer.append(" AND user <= #{eRegName} ");
        }
        buffer.append(" ORDER BY reqTime DESC ) a  GROUP BY a.user LIMIT #{b_page},#{e_page}");
        return buffer.toString();
    }

    public String getBatchQueryPageCount(Map<String, Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(b.user) FROM (SELECT a.user FROM (SELECT user FROM payease_syn001001  WHERE 1=1 ");
        if (map.get("bRegName") != null)
        {
            buffer.append(" AND user >= #{bRegName} ");
        }
        if (map.get("eRegName") != null)
        {
            buffer.append(" AND user <= #{eRegName} ");
        }
        buffer.append(" ORDER BY reqTime DESC ) a  GROUP BY a.user)b ");
        return buffer.toString();
    }

    public String getRecords001(Map<String, Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT * FROM payease_syn001001 ");
        buffer.append(" ORDER BY reqTime DESC LIMIT #{b_page},#{e_page} ");
        return buffer.toString();
    }

    public String getRecords001Count(Map<String, Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(*) FROM payease_syn001001");
        return buffer.toString();
    }

    public String selectAccountByCondition(HashMap<String, Object> searchConditions)
    {
        BEGIN();
        SELECT("COUNT(*)");
        FROM("(SELECT * FROM (SELECT * FROM `payease_syn001001` origin ORDER BY `msgid` DESC) origin2 GROUP BY user) syn1");
        WHERE("1 = 1");

        Object userName = searchConditions.get("userName");
        if (userName != null && !"".equals(userName))
        {
            AND();
            WHERE("userName LIKE \"%\"#{userName,jdbcType=VARCHAR}\"%\"");
        }
        Object accNum = searchConditions.get("accNum");
        if (accNum != null && !"".equals(accNum))
        {
            AND();
            WHERE("accNum LIKE \"%\"#{accNum,jdbcType=VARCHAR}\"%\"");
        }
        Object returnCode = searchConditions.get("returnCode");
        if (returnCode != null && !"".equals(returnCode))
        {
            AND();
            if (ReturnCode.BIND_CARD_FAILURE.equals(returnCode))
            {
                WHERE("returnCode = '" + ReturnCode.BIND_CARD_FAILURE + "' OR returnCode = '" + ReturnCode.TRANS_SUCCESS + "'");
            } else
            {
                WHERE("returnCode = #{returnCode}");
            }
        }
        Object reqTimeStartDate = searchConditions.get("reqTimeStartDate");
        if (reqTimeStartDate != null)
        {
            AND();
            WHERE("reqTime >= #{reqTimeStartDate,jdbcType=TIMESTAMP}");
        }
        Object reqTimeEndDate = searchConditions.get("reqTimeEndDate");
        if (reqTimeEndDate != null)
        {
            AND();
            WHERE("reqTime <= #{reqTimeEndDate,jdbcType=TIMESTAMP}");
        }
        Object plateform = searchConditions.get("plateform");
        if (1 == (Integer)plateform)
        {
            AND();
            WHERE("user >= #{user,jdbcType=INTEGER}");
        }else{
            AND();
            WHERE("user < #{user,jdbcType=INTEGER}");
        }
        return SQL();
    }

    public String selectAccountListByCondition(HashMap<String, Object> searchConditions)
    {
        BEGIN();
        SELECT("*");
        FROM("(SELECT * FROM (SELECT * FROM `payease_syn001001` origin ORDER BY `msgid` DESC ) origin2 GROUP BY user ) syn1");
        WHERE("1 = 1");

        Object userName = searchConditions.get("userName");
        if (userName != null && !"".equals(userName))
        {
            AND();
            WHERE("userName LIKE \"%\"#{userName,jdbcType=VARCHAR}\"%\"");
        }
        Object accNum = searchConditions.get("accNum");
        if (accNum != null && !"".equals(accNum))
        {
            AND();
            WHERE("accNum LIKE \"%\"#{accNum,jdbcType=VARCHAR}\"%\"");
        }
        Object returnCode = searchConditions.get("returnCode");
        if (returnCode != null && !"".equals(returnCode))
        {
            AND();
            if (ReturnCode.BIND_CARD_FAILURE.equals(returnCode))
            {
                WHERE("returnCode = '" + ReturnCode.BIND_CARD_FAILURE + "' OR returnCode = '" + ReturnCode.TRANS_SUCCESS + "'");
            } else
            {
                WHERE("returnCode = #{returnCode}");
            }
        }
        Object reqTimeStartDate = searchConditions.get("reqTimeStartDate");
        if (reqTimeStartDate != null)
        {
            AND();
            WHERE("reqTime >= #{reqTimeStartDate,jdbcType=TIMESTAMP}");
        }
        Object reqTimeEndDate = searchConditions.get("reqTimeEndDate");
        if (reqTimeEndDate != null)
        {
            AND();
            WHERE("reqTime <= #{reqTimeEndDate,jdbcType=TIMESTAMP}");
        }
        Object plateform = searchConditions.get("plateform");
        if (1 == (Integer)plateform)
        {
            AND();
            WHERE("user >= #{user,jdbcType=INTEGER}");
        }else{
            AND();
            WHERE("user < #{user,jdbcType=INTEGER}");
        }

        return SQL() + " ORDER BY user DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String selectTradeCountByCondition(HashMap<String, Object> searchConditions)
    {
        BEGIN();
        SELECT("COUNT(*)");
        FROM("(SELECT serlNum, user, returnCode, returnTime FROM payease_trs001006 trs6 UNION SELECT serlNum, transferOutUser user, returnCode, returnTime  FROM payease_trs001007 trs7 UNION SELECT serlNum, user, returnCode, returnTime  FROM payease_trs001008 trs8) AS trs");
        WHERE("1 = 1");

        Object user = searchConditions.get("user");
        if (user != null && !"".equals(user))
        {
            AND();
            WHERE("user = #{user,jdbcType=INTEGER}");
        }
        Object returnCode = searchConditions.get("returnCode");
        if (returnCode != null && !"".equals(returnCode))
        {
            AND();
            WHERE("returnCode = #{returnCode}");
        }
        Object returnStartTime = searchConditions.get("returnStartTime");
        if (returnStartTime != null)
        {
            AND();
            WHERE("returnTime >= #{returnStartTime,jdbcType=TIMESTAMP}");
        }
        Object returnEndTime = searchConditions.get("returnEndTime");
        if (returnEndTime != null)
        {
            AND();
            WHERE("returnTime <= #{returnEndTime,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectTradeListByCondition(HashMap<String, Object> searchConditions)
    {
        BEGIN();
        SELECT("*");
        FROM("(SELECT reqTime, operationCode, serlNum, user, accNum, accBankCode, amount, returnCode, returnMsg, returnTime FROM payease_trs001006 trs6 UNION SELECT reqTime, operationCode, serlNum, transferOutUser user, '', '', transferAmount amount, returnCode, returnMsg, returnTime FROM payease_trs001007 trs7 UNION SELECT reqTime, operationCode, serlNum, user, accNum, accBankCode, amount, returnCode, returnMsg, returnTime FROM payease_trs001008 trs8) AS trs");
        WHERE("1 = 1");

        Object user = searchConditions.get("user");
        if (user != null && !"".equals(user))
        {
            AND();
            WHERE("user = #{user,jdbcType=INTEGER}");
        }
        Object returnCode = searchConditions.get("returnCode");
        if (returnCode != null && !"".equals(returnCode))
        {
            AND();
            WHERE("returnCode = #{returnCode}");
        }
        Object returnStartTime = searchConditions.get("returnStartTime");
        if (returnStartTime != null)
        {
            AND();
            WHERE("returnTime >= #{returnStartTime,jdbcType=TIMESTAMP}");
        }
        Object returnEndTime = searchConditions.get("returnEndTime");
        if (returnEndTime != null)
        {
            AND();
            WHERE("returnTime <= #{returnEndTime,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY reqTime DESC LIMIT #{fromIndex},#{endIndex}";
    }

}
