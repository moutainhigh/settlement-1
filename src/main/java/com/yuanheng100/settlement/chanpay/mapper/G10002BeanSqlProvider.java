package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;

import java.util.HashMap;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class G10002BeanSqlProvider {

    public String insertSelective(G10001Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_g10002_bean");
        
        if (record.getReqSn() != null) {
            VALUES("reqSn", "#{reqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getTimestamp() != null) {
            VALUES("timestamp", "#{timestamp,jdbcType=TIMESTAMP}");
        }
        
        if (record.getBankGeneralName() != null) {
            VALUES("bankGeneralName", "#{bankGeneralName,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountNo() != null) {
            VALUES("accountNo", "#{accountNo,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountName() != null) {
            VALUES("accountName", "#{accountName,jdbcType=VARCHAR}");
        }
        
        if (record.getBankName() != null) {
            VALUES("bankName", "#{bankName,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getIdType() != null) {
            VALUES("idType", "#{idType,jdbcType=VARCHAR}");
        }
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=VARCHAR}");
        }
        
        if (record.getTel() != null) {
            VALUES("tel", "#{tel,jdbcType=VARCHAR}");
        }
        
        if (record.getCorpFlowNo() != null) {
            VALUES("corpFlowNo", "#{corpFlowNo,jdbcType=VARCHAR}");
        }
        
        if (record.getSummary() != null) {
            VALUES("summary", "#{summary,jdbcType=VARCHAR}");
        }
        
        if (record.getPostscript() != null) {
            VALUES("postscript", "#{postscript,jdbcType=VARCHAR}");
        }
        
        if (record.getRetCode() != null) {
            VALUES("retCode", "#{retCode,jdbcType=VARCHAR}");
        }
        
        if (record.getErrMsg() != null) {
            VALUES("errMsg", "#{errMsg,jdbcType=VARCHAR}");
        }

        if (record.getTradeCode() != null) {
            VALUES("tradeCode", "#{tradeCode,jdbcType=VARCHAR}");
        }

        if (record.getTradeMsg() != null) {
            VALUES("tradeMsg", "#{tradeMsg,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectPayCountByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("chanpay_g10002_bean q10002");
        WHERE("1 = 1");

        Object accountName = searchConditions.get("accountName");
        if (accountName!=null&&!"".equals(accountName)) {
            AND();
            WHERE("q10002.accountName LIKE \"%\"#{accountName,jdbcType=VARCHAR}\"%\"");
        }
        Object reqSn = searchConditions.get("reqSn");
        if (reqSn!=null&&!"".equals(reqSn)) {
            AND();
            WHERE("q10002.reqSn LIKE \"%\"#{reqSn,jdbcType=VARCHAR}\"%\"");
        }
        Object accountNo = searchConditions.get("accountNo");
        if (accountNo!=null&&!"".equals(accountNo)) {
            AND();
            WHERE("q10002.accountNo LIKE \"%\"#{accountNo,jdbcType=VARCHAR}\"%\"");
        }
        Object tradeStatus = searchConditions.get("tradeStatus");
        if (tradeStatus!=null&&!"".equals(tradeStatus)) {
            if(tradeStatus.equals("TRADE_PROCESS")){
                AND();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$0001_代理系统受理成功.getCode()+"'");
                OR();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$0002_提交银行成功等待查询结果.getCode()+"'");
                OR();
                WHERE("q10002.tradeCode IS NULL");
            }else if(tradeStatus.equals("TRADE_SUCCESS")){
                AND();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$0000_处理成功.getCode()+"'");
            }else if(tradeStatus.equals("TRADE_FAILURE")){
                AND();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$2013_收款行未开通业务.getCode()+"'");
                OR();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$3999_其他错误.getCode()+"'");
            }
        }
        Object timestampStartDate = searchConditions.get("timestampStartDate");
        if (timestampStartDate!=null) {
            AND();
            WHERE("q10002.timestamp >= #{timestampStartDate,jdbcType=TIMESTAMP}");
        }
        Object timestampEndDate = searchConditions.get("timestampEndDate");
        if (timestampEndDate!=null) {
            AND();
            WHERE("q10002.timestamp <= #{timestampEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectPayListCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("q10002.reqSn, q10002.timestamp, q10002.accountName, q10002.bankGeneralName, q10002.bankName, q10002.accountNo, q10002.amount, q10002.summary, q10002.postscript, q10002.tradeCode, q10002.tradeMsg");
        FROM("chanpay_g10002_bean q10002");
        WHERE("1 = 1");

        Object accountName = searchConditions.get("accountName");
        if (accountName!=null&&!"".equals(accountName)) {
            AND();
            WHERE("q10002.accountName LIKE \"%\"#{accountName,jdbcType=VARCHAR}\"%\"");
        }
        Object reqSn = searchConditions.get("reqSn");
        if (reqSn!=null&&!"".equals(reqSn)) {
            AND();
            WHERE("q10002.reqSn LIKE \"%\"#{reqSn,jdbcType=VARCHAR}\"%\"");
        }
        Object accountNo = searchConditions.get("accountNo");
        if (accountNo!=null&&!"".equals(accountNo)) {
            AND();
            WHERE("q10002.accountNo LIKE \"%\"#{accountNo,jdbcType=VARCHAR}\"%\"");
        }
        Object tradeStatus = searchConditions.get("tradeStatus");
        if (tradeStatus!=null&&!"".equals(tradeStatus)) {
            if(tradeStatus.equals("TRADE_PROCESS")){
                AND();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$0001_代理系统受理成功.getCode()+"'");
                OR();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$0002_提交银行成功等待查询结果.getCode()+"'");
                OR();
                WHERE("q10002.tradeCode IS NULL");
            }else if(tradeStatus.equals("TRADE_SUCCESS")){
                AND();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$0000_处理成功.getCode()+"'");
            }else if(tradeStatus.equals("TRADE_FAILURE")){
                AND();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$2013_收款行未开通业务.getCode()+"'");
                OR();
                WHERE("q10002.tradeCode = '"+ CjDetailRetcode.$3999_其他错误.getCode()+"'");
            }
        }
        Object timestampStartDate = searchConditions.get("timestampStartDate");
        if (timestampStartDate!=null) {
            AND();
            WHERE("q10002.timestamp >= #{timestampStartDate,jdbcType=TIMESTAMP}");
        }
        Object timestampEndDate = searchConditions.get("timestampEndDate");
        if (timestampEndDate!=null) {
            AND();
            WHERE("q10002.timestamp <= #{timestampEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY q10002.timestamp DESC LIMIT #{fromIndex},#{endIndex}";
    }
}