package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.consts.CjInfoRetcode;
import com.yuanheng100.settlement.chanpay.model.G60009Bean;

import java.util.HashMap;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class G60009BeanSqlProvider {

    public String insertSelective(G60009Bean record) {
        BEGIN();
        INSERT_INTO("chanpay_g60009_bean");
        
        if (record.getReqSn() != null) {
            VALUES("reqSn", "#{reqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getTimestamp() != null) {
            VALUES("timestamp", "#{timestamp,jdbcType=VARCHAR}");
        }
        
        if (record.getSmsCode() != null) {
            VALUES("smsCode", "#{smsCode,jdbcType=VARCHAR}");
        }
        
        if (record.getOrgReqSn() != null) {
            VALUES("orgReqSn", "#{orgReqSn,jdbcType=VARCHAR}");
        }
        
        if (record.getSubMerchantId() != null) {
            VALUES("subMerchantId", "#{subMerchantId,jdbcType=VARCHAR}");
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
            VALUES("bankCode", "#{bankCode,jdbcType=INTEGER}");
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
            VALUES("tel", "#{tel,jdbcType=BIGINT}");
        }
        
        if (record.getRetCode() != null) {
            VALUES("retCode", "#{retCode,jdbcType=VARCHAR}");
        }
        
        if (record.getErrMsg() != null) {
            VALUES("errMsg", "#{errMsg,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String selectAuthenticationByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("chanpay_g60009_bean");
        WHERE("1 = 1");

        Object reqSn = searchConditions.get("reqSn");
        if (reqSn!=null&&!"".equals(reqSn)) {
            AND();
            WHERE("reqSn LIKE \"%\"#{reqSn,jdbcType=VARCHAR}\"%\"");
        }
        Object bankGeneralName = searchConditions.get("bankGeneralName");
        if (bankGeneralName!=null&&!"".equals(bankGeneralName)) {
            AND();
            WHERE("bankGeneralName LIKE \"%\"#{bankGeneralName,jdbcType=VARCHAR}\"%\"");
        }
        Object bankCode = searchConditions.get("bankCode");
        if (bankCode!=null) {
            AND();
            WHERE("bankCode = #{orderTimeEndDate,jdbcType=INTEGER}");
        }
        Object accountNo = searchConditions.get("accountNo");
        if (accountNo!=null&&!"".equals(accountNo)) {
            AND();
            WHERE("accountNo LIKE \"%\"#{accountNo,jdbcType=VARCHAR}\"%\"");
        }
        Object accountName = searchConditions.get("accountName");
        if (accountName!=null&&!"".equals(accountName)) {
            AND();
            WHERE("accountName LIKE \"%\"#{accountName,jdbcType=VARCHAR}\"%\"");
        }
        Object retCode = searchConditions.get("retCode");
        if (retCode!=null&&!"".equals(retCode)) {
            AND();
            if(retCode.equals(CjInfoRetcode.$0000_处理成功.getCode())){
                WHERE("retCode = '0000'");
            }else {
                WHERE("retCode != '0000'");
            }
        }
        Object timestampStartDate = searchConditions.get("timestampStartDate");
        if (timestampStartDate!=null) {
            AND();
            WHERE("timestamp >= #{timestampStartDate,jdbcType=TIMESTAMP}");
        }
        Object timestampEndDate = searchConditions.get("timestampEndDate");
        if (timestampEndDate!=null) {
            AND();
            WHERE("timestamp <= #{timestampEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectAuthenticationListByCondition(HashMap<String, Object> searchConditions){
        BEGIN();
        SELECT("*");
        FROM("chanpay_g60009_bean");
        WHERE("1 = 1");

        Object reqSn = searchConditions.get("reqSn");
        if (reqSn!=null&&!"".equals(reqSn)) {
            AND();
            WHERE("reqSn LIKE \"%\"#{reqSn,jdbcType=VARCHAR}\"%\"");
        }
        Object bankGeneralName = searchConditions.get("bankGeneralName");
        if (bankGeneralName!=null&&!"".equals(bankGeneralName)) {
            AND();
            WHERE("bankGeneralName LIKE \"%\"#{bankGeneralName,jdbcType=VARCHAR}\"%\"");
        }
        Object bankCode = searchConditions.get("bankCode");
        if (bankCode!=null) {
            AND();
            WHERE("bankCode = #{orderTimeEndDate,jdbcType=INTEGER}");
        }
        Object accountNo = searchConditions.get("accountNo");
        if (accountNo!=null&&!"".equals(accountNo)) {
            AND();
            WHERE("accountNo LIKE \"%\"#{accountNo,jdbcType=VARCHAR}\"%\"");
        }
        Object accountName = searchConditions.get("accountName");
        if (accountName!=null&&!"".equals(accountName)) {
            AND();
            WHERE("accountName LIKE \"%\"#{accountName,jdbcType=VARCHAR}\"%\"");
        }
        Object retCode = searchConditions.get("retCode");
        if (retCode!=null&&!"".equals(retCode)) {
            AND();
            if(retCode.equals(CjInfoRetcode.$0000_处理成功.getCode())){
                WHERE("retCode = '0000'");
            }else {
                WHERE("retCode != '0000'");
            }
        }
        Object timestampStartDate = searchConditions.get("timestampStartDate");
        if (timestampStartDate!=null) {
            AND();
            WHERE("timestamp >= #{timestampStartDate,jdbcType=TIMESTAMP}");
        }
        Object timestampEndDate = searchConditions.get("timestampEndDate");
        if (timestampEndDate!=null) {
            AND();
            WHERE("timestamp <= #{timestampEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY timestamp DESC LIMIT #{fromIndex},#{endIndex}";
    }
}