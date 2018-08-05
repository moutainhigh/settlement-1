package com.yuanheng100.settlement.payease.provider;

import java.util.Map;

/**
 * Created by wangguangshuo on 2016/12/28.
 */
public class TransferAccountSqlProvider
{

    public String getTransferRecords(Map<String, Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT a.serlNum,a.transferOutUser,b.userName outName,a.transferInUser,c.userName inName,a.transferAmount,a.reqTime,a.returnCode FROM payease_trs001007 a  ");
        buffer.append(" LEFT JOIN payease_syn001001 b ON a.transferOutUser = b.user ");
        buffer.append(" LEFT JOIN payease_syn001001 c ON a.transferInUser = c.user  WHERE 1=1 ");
        if (map.get("outRegName") != null)
        {
            buffer.append(" AND a.transferOutUser like '%" + map.get("outRegName") + "%' ");
        }
        if (map.get("outRelName") != null)
        {
            buffer.append(" AND b.userName like '%" + map.get("outRelName") + "%' ");
        }
        if (map.get("inRegName") != null)
        {
            buffer.append(" AND a.transferInUser like '%" + map.get("inRegName") + "%' ");
        }
        if (map.get("inRelName") != null)
        {
            buffer.append(" AND c.userName like '%" + map.get("inRelName") + "%' ");
        }
        if (map.get("date1") != null)
        {
            buffer.append(" AND a.reqTime >= #{date1} ");
        }
        if (map.get("date2") != null)
        {
            buffer.append(" AND a.reqTime <= #{date2} ");
        }
        if (map.get("returnCode") != null)
        {
//            if (AcknowledgeCallBackCode.TRANS_EXCUTE.equals(String.valueOf(map.get("returnCode"))))
//            {
//                buffer.append(" AND a.returnCode is NULL ");
//            }
//            else
//            {
            buffer.append(" AND a.returnCode = #{returnCode} ");
//            }
        }
        if (1 == (Integer)map.get("plateform"))
        {
            buffer.append(" AND a.transferOutUser >= #{user} OR a.transferInUser >= #{user}");
        }else{
            buffer.append(" AND a.transferOutUser < #{user} OR a.transferInUser < #{user}");
        }
        buffer.append(" GROUP BY a.serlNum ORDER BY a.reqTime DESC LIMIT #{b_page},#{e_page}");
        return buffer.toString();
    }

    public String getTransferRecordsCount(Map<String, Object> map)
    {
        StringBuffer buffer = new StringBuffer("SELECT COUNT(c.serlNum) FROM (SELECT a.serlNum FROM payease_trs001007 a  ");
        buffer.append(" LEFT JOIN payease_syn001001 b ON a.transferOutUser = b.user ");
        buffer.append(" LEFT JOIN payease_syn001001 c ON a.transferInUser = c.user WHERE 1=1  ");
        if (map.get("outRegName") != null)
        {
            buffer.append(" AND a.transferOutUser like '%" + map.get("outRegName") + "%' ");
        }
        if (map.get("outRelName") != null)
        {
            buffer.append(" AND b.userName like '%" + map.get("outRelName") + "%' ");
        }
        if (map.get("inRegName") != null)
        {
            buffer.append(" AND a.transferInUser like '%" + map.get("inRegName") + "%' ");
        }
        if (map.get("inRelName") != null)
        {
            buffer.append(" AND c.userName like '%" + map.get("inRelName") + "%' ");
        }
        if (map.get("date1") != null)
        {
            buffer.append(" AND a.reqTime >= #{date1} ");
        }
        if (map.get("date2") != null)
        {
            buffer.append(" AND a.reqTime <= #{date2} ");
        }
        if (map.get("returnCode") != null)
        {
//            if (AcknowledgeCallBackCode.TRANS_EXCUTE.equals(String.valueOf(map.get("returnCode"))))
//            {
//                buffer.append(" AND a.returnCode is NULL ");
//            }
//            else
//            {
            buffer.append(" AND a.returnCode = #{returnCode} ");
//            }
        }
        if (1 == (Integer)map.get("plateform"))
        {
            buffer.append(" AND a.transferOutUser >= #{user} OR a.transferInUser >= #{user}");
        }else{
            buffer.append(" AND a.transferOutUser < #{user} OR a.transferInUser < #{user}");
        }
        buffer.append(" GROUP BY a.serlNum) c ");
        return buffer.toString();
    }
}
