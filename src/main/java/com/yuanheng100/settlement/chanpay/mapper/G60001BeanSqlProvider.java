package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.consts.CjInfoRetcode;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;

import java.util.HashMap;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class G60001BeanSqlProvider {

    public String selectAuthenticationListByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("g60001.reqSn, g60001.timestamp, g60001.bankGeneralName, g60001.bankName, g60001.bankCode, g60001.accountNo, g60001.accountName, g60001.id, g60001.tel, g60002.dtlRetCode retCode, g60002.dtlErrMsg errMsg");
        FROM("chanpay_g60001_bean g60001");
        LEFT_OUTER_JOIN("chanpay_g60002_bean g60002 ON g60002.qryReqSn = g60001.reqSn");

        Object reqSn = searchConditions.get("reqSn");
        if (reqSn!=null&&!"".equals(reqSn)) {
            AND();
            WHERE("g60001.reqSn LIKE \"%\"#{reqSn,jdbcType=VARCHAR}\"%\"");
        }
        Object bankGeneralName = searchConditions.get("bankGeneralName");
        if (bankGeneralName!=null&&!"".equals(bankGeneralName)) {
            AND();
            WHERE("g60001.bankGeneralName LIKE \"%\"#{bankGeneralName,jdbcType=VARCHAR}\"%\"");
        }
        Object bankCode = searchConditions.get("bankCode");
        if (bankCode!=null) {
            AND();
            WHERE("g60001.bankCode = #{orderTimeEndDate,jdbcType=INTEGER}");
        }
        Object accountNo = searchConditions.get("accountNo");
        if (accountNo!=null&&!"".equals(accountNo)) {
            AND();
            WHERE("g60001.accountNo LIKE \"%\"#{accountNo,jdbcType=VARCHAR}\"%\"");
        }
        Object accountName = searchConditions.get("accountName");
        if (accountName!=null&&!"".equals(accountName)) {
            AND();
            WHERE("g60001.accountName LIKE \"%\"#{accountName,jdbcType=VARCHAR}\"%\"");
        }
        Object retCode = searchConditions.get("retCode");
        if (retCode!=null&&!"".equals(retCode)) {
            AND();
            if(retCode.equals(CjDetailRetcode.$0000_处理成功.getCode())){
                WHERE("g60002.dtlRetCode = '0000'");
            }else {
                WHERE("g60002.retCode != '0000'");
            }
        }
        Object timestampStartDate = searchConditions.get("timestampStartDate");
        if (timestampStartDate!=null) {
            AND();
            WHERE("g60001.timestamp >= #{timestampStartDate,jdbcType=TIMESTAMP}");
        }
        Object timestampEndDate = searchConditions.get("timestampEndDate");
        if (timestampEndDate!=null) {
            AND();
            WHERE("g60001.timestamp <= #{timestampEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY g60001.timestamp DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String selectAuthenticationByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("chanpay_g60001_bean g60001");
        LEFT_OUTER_JOIN("chanpay_g60002_bean g60002 ON g60002.qryReqSn = g60001.reqSn");

        Object reqSn = searchConditions.get("reqSn");
        if (reqSn!=null&&!"".equals(reqSn)) {
            AND();
            WHERE("g60001.reqSn LIKE \"%\"#{reqSn,jdbcType=VARCHAR}\"%\"");
        }
        Object bankGeneralName = searchConditions.get("bankGeneralName");
        if (bankGeneralName!=null&&!"".equals(bankGeneralName)) {
            AND();
            WHERE("g60001.bankGeneralName LIKE \"%\"#{bankGeneralName,jdbcType=VARCHAR}\"%\"");
        }
        Object bankCode = searchConditions.get("bankCode");
        if (bankCode!=null) {
            AND();
            WHERE("g60001.bankCode = #{orderTimeEndDate,jdbcType=INTEGER}");
        }
        Object accountNo = searchConditions.get("accountNo");
        if (accountNo!=null&&!"".equals(accountNo)) {
            AND();
            WHERE("g60001.accountNo LIKE \"%\"#{accountNo,jdbcType=VARCHAR}\"%\"");
        }
        Object accountName = searchConditions.get("accountName");
        if (accountName!=null&&!"".equals(accountName)) {
            AND();
            WHERE("g60001.accountName LIKE \"%\"#{accountName,jdbcType=VARCHAR}\"%\"");
        }
        Object retCode = searchConditions.get("retCode");
        if (retCode!=null&&!"".equals(retCode)) {
            AND();
            if(retCode.equals(CjDetailRetcode.$0000_处理成功.getCode())){
                WHERE("g60002.dtlRetCode = '0000'");
            }else {
                WHERE("g60002.retCode != '0000'");
            }
        }
        Object timestampStartDate = searchConditions.get("timestampStartDate");
        if (timestampStartDate!=null) {
            AND();
            WHERE("g60001.timestamp >= #{timestampStartDate,jdbcType=TIMESTAMP}");
        }
        Object timestampEndDate = searchConditions.get("timestampEndDate");
        if (timestampEndDate!=null) {
            AND();
            WHERE("g60001.timestamp <= #{timestampEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }


    public String withoutQuery(){
        BEGIN();
        SELECT("g60001.reqSn, g60001.bankGeneralName, g60001.bankName, g60001.bankCode, g60001.accountType, g60001.accountNo, g60001.accountName, g60001.idType, g60001.id, g60001.tel, g60001.retCode, g60001.errMsg");
        FROM("chanpay_g60001_bean g60001");
        LEFT_OUTER_JOIN("chanpay_g60002_bean g60002 ON g60001.reqSn = g60002.qryReqSn");
        WHERE("g60001.retCode = '" + CjInfoRetcode.$0000_处理成功.getCode() +"'");
        AND();
        WHERE("g60002.dtlRetCode IS NULL OR g60002.dtlRetCode = '" + CjDetailRetcode.$0001_代理系统受理成功.getCode()+"' OR g60002.dtlRetCode = '"+ CjDetailRetcode.$0002_提交银行成功等待查询结果.getCode()+"'");
        return SQL();
    }

    public String insertSelective(G60001Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_g60001_bean");
        
        if (record.getReqSn() != null) {
            VALUES("reqSn", "#{reqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getTimestamp() != null) {
            VALUES("timestamp", "#{timestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getSn() != null) {
            VALUES("sn", "#{sn,jdbcType=VARCHAR}");
        }
        
        if (record.getBankGeneralName() != null) {
            VALUES("bankGeneralName", "#{bankGeneralName,jdbcType=VARCHAR}");
        }
        
        if (record.getBankName() != null) {
            VALUES("bankName", "#{bankName,jdbcType=VARCHAR}");
        }
        
        if (record.getBankCode() != null) {
            VALUES("bankCode", "#{bankCode,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountType() != null) {
            VALUES("accountType", "#{accountType,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountNo() != null) {
            VALUES("accountNo", "#{accountNo,jdbcType=VARCHAR}");
        }
        
        if (record.getAccountName() != null) {
            VALUES("accountName", "#{accountName,jdbcType=VARCHAR}");
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
        
        if (record.getRetCode() != null) {
            VALUES("retCode", "#{retCode,jdbcType=VARCHAR}");
        }
        
        if (record.getErrMsg() != null) {
            VALUES("errMsg", "#{errMsg,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }
}