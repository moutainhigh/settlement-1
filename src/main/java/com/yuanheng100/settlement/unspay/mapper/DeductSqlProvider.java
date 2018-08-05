package com.yuanheng100.settlement.unspay.mapper;


import com.yuanheng100.settlement.unspay.model.UnspayDeduct;

import java.util.Map;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class DeductSqlProvider {

    public String selectDeductListCount(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("COUNT(*)");
        FROM("unspay_deduct ud");
        LEFT_OUTER_JOIN("unspay_sub_contract usc ON ud.loanApplyId = usc.loanApplyId");
        WHERE("1=1");

        Object name = searchConditions.get("name");
        if (name!=null&&!"".equals(name)) {
            AND();
            WHERE("usc.name LIKE \"%\"#{name,jdbcType=VARCHAR}\"%\"");
        }
        Object phoneNo = searchConditions.get("phoneNo");
        if (phoneNo!=null) {
            AND();
            WHERE("usc.phoneNo LIKE CONCAT('%',#{phoneNo,jdbcType=INTEGER},'%')");
        }
        Object idCardNo = searchConditions.get("idCardNo");
        if (idCardNo!=null&&!"".equals(idCardNo)) {
            AND();
            WHERE("usc.idCardNo LIKE \"%\"#{idCardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object cardNo = searchConditions.get("cardNo");
        if (cardNo!=null&&!"".equals(cardNo)) {
            AND();
            WHERE("usc.cardNo LIKE \"%\"#{cardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            AND();
            WHERE("ud.filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        Object deductResult = searchConditions.get("deductResult");
        if (deductResult!=null&&!"".equals(deductResult)) {
            AND();
            if(deductResult.equals("20")){
                WHERE("ud.deductResult != '0000'");
                AND();
                WHERE("ud.deductResult != '10'");
            }else{
                WHERE("ud.deductResult = #{deductResult,jdbcType=VARCHAR}");
            }
        }
        Object responseStartDate = searchConditions.get("responseStartDate");
        if (responseStartDate!=null) {
            AND();
            WHERE("ud.responseDate >= #{responseStartDate,jdbcType=TIMESTAMP}");
        }
        Object responseEndDate = searchConditions.get("responseEndDate");
        if (responseEndDate!=null) {
            AND();
            WHERE("ud.responseDate <= #{responseEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL();
    }

    public String selectDeductList(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("usc.name, usc.phoneNo, usc.idCardNo, usc.cardNo, usc.bankCode, ud.orderId, ud.filename,ud.loanApplyId, ud.repayPhaseId, ud.amount, ud.planDate, ud.sendDate, ud.responseDate, ud.purpose, ud.deductResult, ud.desc");
        FROM("unspay_deduct ud");
        LEFT_OUTER_JOIN("unspay_sub_contract usc ON ud.loanApplyId = usc.loanApplyId");
        WHERE("ud.verifyStatus = 1");

        Object name = searchConditions.get("name");
        if (name!=null&&!"".equals(name)) {
            AND();
            WHERE("usc.name LIKE \"%\"#{name,jdbcType=VARCHAR}\"%\"");
        }
        Object phoneNo = searchConditions.get("phoneNo");
        if (phoneNo!=null) {
            AND();
            WHERE("usc.phoneNo LIKE CONCAT('%',#{phoneNo,jdbcType=INTEGER},'%')");
        }
        Object idCardNo = searchConditions.get("idCardNo");
        if (idCardNo!=null&&!"".equals(idCardNo)) {
            AND();
            WHERE("usc.idCardNo LIKE \"%\"#{idCardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object cardNo = searchConditions.get("cardNo");
        if (cardNo!=null&&!"".equals(cardNo)) {
            AND();
            WHERE("usc.cardNo LIKE \"%\"#{cardNo,jdbcType=VARCHAR}\"%\"");
        }
        Object filename = searchConditions.get("filename");
        if (filename!=null&&!"".equals(filename)) {
            AND();
            WHERE("ud.filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        Object deductResult = searchConditions.get("deductResult");
        if (deductResult!=null&&!"".equals(deductResult)) {
            AND();
            if(deductResult.equals("20")){
                WHERE("ud.deductResult != '0000'");
                AND();
                WHERE("ud.deductResult != '10'");
            }else{
                WHERE("ud.deductResult = #{deductResult,jdbcType=VARCHAR}");
            }
        }
        Object responseStartDate = searchConditions.get("responseStartDate");
        if (responseStartDate!=null) {
            AND();
            WHERE("ud.responseDate >= #{responseStartDate,jdbcType=TIMESTAMP}");
        }
        Object responseEndDate = searchConditions.get("responseEndDate");
        if (responseEndDate!=null) {
            AND();
            WHERE("ud.responseDate <= #{responseEndDate,jdbcType=TIMESTAMP}");
        }
        return SQL() + " ORDER BY ud.orderId DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String selectDeductUploadCount(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("COUNT(DISTINCT filename)");
        FROM("unspay_deduct");
        if (searchConditions.get("uploadStartDate")!=null) {
            WHERE("uploadDate >= #{uploadStartDate,jdbcType=DATE}");
        }
        if (searchConditions.get("uploadEndDate")!=null) {
            WHERE("uploadDate <= #{uploadEndDate,jdbcType=DATE}");
        }
        if (searchConditions.get("filename")!=null) {
            WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        return SQL();
    }

    public String selectDeductUploadList(Map<String,Object> searchConditions){
        BEGIN();
        SELECT("DISTINCT filename, uploadDate, operator");
        FROM("unspay_deduct");
        if (searchConditions.get("uploadStartDate")!=null) {
            WHERE("uploadDate >= #{uploadStartDate,jdbcType=DATE}");
        }
        if (searchConditions.get("uploadEndDate")!=null) {
            WHERE("uploadDate <= #{uploadEndDate,jdbcType=DATE}");
        }
        if (searchConditions.get("filename")!=null) {
        	WHERE("filename LIKE \"%\"#{filename,jdbcType=VARCHAR}\"%\"");
        }
        return SQL() + " ORDER BY uploadDate DESC, filename DESC LIMIT #{fromIndex},#{endIndex}";
    }

    public String insertSelective(UnspayDeduct record) {
        BEGIN();
        INSERT_INTO("unspay_deduct");
        
        VALUES("orderId", "#{orderId,jdbcType=INTEGER}");
        
        if (record.getFilename() != null) {
            VALUES("filename", "#{filename,jdbcType=VARCHAR}");
        }
        
        if (record.getUploadDate() != null) {
            VALUES("uploadDate", "#{uploadDate,jdbcType=DATE}");
        }
        
        if (record.getOperator() != null) {
            VALUES("operator", "#{operator,jdbcType=INTEGER}");
        }
        
        if (record.getAuditor() != null) {
            VALUES("auditor", "#{auditor,jdbcType=INTEGER}");
        }
        
        if (record.getLoanApplyId() != null) {
            VALUES("loanApplyId", "#{loanApplyId,jdbcType=BIGINT}");
        }

        if (record.getRepayPhaseId() != null) {
            VALUES("repayPhaseId", "#{repayPhaseId,jdbcType=BIGINT}");
        }
        
        if (record.getSubContractId() != null) {
            VALUES("subContractId", "#{subContractId,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPurpose() != null) {
            VALUES("purpose", "#{purpose,jdbcType=VARCHAR}");
        }

        if (record.getVerifyStatus() != null) {
            VALUES("verifyStatus", "#{verifyStatus,jdbcType=SMALLINT}");
        }

        if (record.getPlanDate() != null) {
            VALUES("planDate", "#{planDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSendDate() != null) {
            VALUES("sendDate", "#{sendDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseDate() != null) {
            VALUES("responseDate", "#{responseDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeductResult() != null) {
            VALUES("deductResult", "#{deductResult,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            VALUES("desc", "#{desc,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(UnspayDeduct record) {
        BEGIN();
        UPDATE("unspay_deduct");
        
        if (record.getFilename() != null) {
            SET("filename = #{filename,jdbcType=VARCHAR}");
        }
        
        if (record.getUploadDate() != null) {
            SET("uploadDate = #{uploadDate,jdbcType=DATE}");
        }
        
        if (record.getOperator() != null) {
            SET("operator = #{operator,jdbcType=INTEGER}");
        }
        
        if (record.getAuditor() != null) {
            SET("auditor = #{auditor,jdbcType=INTEGER}");
        }
        
        if (record.getLoanApplyId() != null) {
            SET("loanApplyId = #{loanApplyId,jdbcType=BIGINT}");
        }

        if (record.getRepayPhaseId() != null) {
            SET("repayPhaseId = #{repayPhaseId,jdbcType=BIGINT}");
        }
        
        if (record.getSubContractId() != null) {
            SET("subContractId = #{subContractId,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            SET("amount = #{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getPurpose() != null) {
            SET("purpose = #{purpose,jdbcType=VARCHAR}");
        }

        if (record.getVerifyStatus() != null) {
            SET("verifyStatus = #{verifyStatus,jdbcType=SMALLINT}");
        }

        if (record.getPlanDate() != null) {
            SET("planDate = #{planDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getSendDate() != null) {
            SET("sendDate = #{sendDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getResponseDate() != null) {
            SET("responseDate = #{responseDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getDeductResult() != null) {
            SET("deductResult = #{deductResult,jdbcType=VARCHAR}");
        }
        
        if (record.getDesc() != null) {
            SET("desc = #{desc,jdbcType=VARCHAR}");
        }
        
        WHERE("orderId = #{orderId,jdbcType=INTEGER}");
        
        return SQL();
    }
}