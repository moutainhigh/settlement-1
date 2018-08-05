package com.yuanheng100.settlement.unspay.mapper;

import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeductMapper {

    //获取代扣上传记录数
    @SelectProvider(type = DeductSqlProvider.class,method = "selectDeductUploadCount")
    Long selectDeductUploadCount(HashMap<String, Object> searchConditions);

    //获取代扣上传列表
    @SelectProvider(type = DeductSqlProvider.class,method = "selectDeductUploadList")
    List<Map<String,Object>> selectDeductUploadList(HashMap<String, Object> searchConditions);

    //根据上传文件名获取列表
    @Select({
            "SELECT",
            "orderId, filename, uploadDate, operator, auditor, loanApplyId, repayPhaseId, subContractId, ",
            "amount, purpose, verifyStatus, planDate, sendDate, responseDate, deductResult, `desc`",
            "FROM unspay_deduct",
            "WHERE filename = #{filename,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="orderId", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
            @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
            @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
            @Result(column="auditor", property="auditor", jdbcType=JdbcType.INTEGER),
            @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
            @Result(column="repayPhaseId", property="repayPhaseId", jdbcType=JdbcType.BIGINT),
            @Result(column="subContractId", property="subContractId", jdbcType=JdbcType.VARCHAR),
            @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
            @Result(column="purpose", property="purpose", jdbcType=JdbcType.VARCHAR),
            @Result(column="verifyStatus", property="verifyStatus", jdbcType=JdbcType.SMALLINT),
            @Result(column="planDate", property="planDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="responseDate", property="responseDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="deductResult", property="deductResult", jdbcType=JdbcType.VARCHAR),
            @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR)
    })
    List<UnspayDeduct> selectDeductListByFileName(String filename);

    //根据上传文件名获取列表
    @Select({
            "SELECT",
            "usc.name, usc.phoneNo, usc.idCardNo, usc.cardNo, usc.bankCode, ",
            "ud.orderId, ud.filename, ud.loanApplyId, ud.auditor, ud.amount,ud.verifyStatus, ud.planDate, ud.sendDate, ud.responseDate, ud.purpose, ud.deductResult, ud.desc",
            "FROM unspay_deduct ud",
            "LEFT OUTER JOIN unspay_sub_contract usc ON ud.loanApplyId = usc.loanApplyId",
            "WHERE ud.filename = #{filename,jdbcType=VARCHAR}",
            "ORDER BY orderId DESC"
    })
    List<Map<String,Object>> selectDeductMapListByFileName(String filename);

    @Insert({
        "insert into unspay_deduct (filename, ",
        "uploadDate, operator, ",
        "auditor, loanApplyId, repayPhaseId, ",
        "subContractId, amount, ",
        "purpose, planDate, extra)",
        "values (#{filename,jdbcType=VARCHAR}, ",
        "#{uploadDate,jdbcType=DATE}, #{operator,jdbcType=INTEGER}, ",
        "#{auditor,jdbcType=INTEGER}, #{loanApplyId,jdbcType=BIGINT}, #{repayPhaseId,jdbcType=BIGINT}, ",
        "#{subContractId,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{purpose,jdbcType=VARCHAR}, #{planDate,jdbcType=TIMESTAMP}, #{extra,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=false, resultType=Integer.class)
    int insert(UnspayDeduct record);

    @Select({
            "SELECT",
            "orderId",
            "FROM unspay_deduct ",
            "WHERE sendDate IS NULL ",
            "AND (planDate <= #{data,jdbcType=TIMESTAMP} OR planDate IS NULL)",
            "AND verifyStatus = 1",
            "AND deductResult = '10'"
    })
    List<Integer> selectDeductToSend(Date date);

    @Select({
            "SELECT",
            "orderId",
            "FROM unspay_deduct ",
            "WHERE sendDate IS NOT NULL ",
            "AND sendDate >= #{from,jdbcType=TIMESTAMP}",
            "AND sendDate <= #{to,jdbcType=TIMESTAMP}",
            "AND verifyStatus = 1",
            "AND deductResult = '10'"
    })
    List<Integer> selectQueryToSend(@Param("from") Date from,@Param("to") Date to);

    @InsertProvider(type=DeductSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=false, resultType=Integer.class)
    int insertSelective(UnspayDeduct record);

    @Select({
        "SELECT",
        "orderId, filename, uploadDate, operator, auditor, loanApplyId, repayPhaseId, subContractId, ",
        "amount, purpose, verifyStatus, planDate, extra, sendDate, responseDate, deductResult, `desc`",
        "FROM unspay_deduct",
        "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="orderId", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
        @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
        @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
        @Result(column="auditor", property="auditor", jdbcType=JdbcType.INTEGER),
        @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
        @Result(column="repayPhaseId", property="repayPhaseId", jdbcType=JdbcType.BIGINT),
        @Result(column="subContractId", property="subContractId", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="purpose", property="purpose", jdbcType=JdbcType.VARCHAR),
        @Result(column="verifyStatus", property="verifyStatus", jdbcType=JdbcType.SMALLINT),
        @Result(column="planDate", property="planDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="extra", property="extra", jdbcType=JdbcType.VARCHAR),
        @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="responseDate", property="responseDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="deductResult", property="deductResult", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR)
    })
    UnspayDeduct selectByPrimaryKey(Integer orderId);

    @Update({
            "UPDATE unspay_deduct",
            "SET sendDate = #{sendDate,jdbcType=TIMESTAMP},",
            "deductResult = #{deductResult,jdbcType=VARCHAR},",
            "`desc` = #{desc,jdbcType=VARCHAR}",
            "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateSendResult(UnspayDeduct record);

    @Update({
            "UPDATE unspay_deduct",
            "SET responseDate = #{responseDate,jdbcType=TIMESTAMP},",
            "deductResult = #{deductResult,jdbcType=VARCHAR},",
            "`desc` = #{desc,jdbcType=VARCHAR}",
            "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateCallbackResult(UnspayDeduct record);


    @UpdateProvider(type=DeductSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UnspayDeduct record);

    @Update({
        "update unspay_deduct",
        "set filename = #{filename,jdbcType=VARCHAR},",
          "uploadDate = #{uploadDate,jdbcType=DATE},",
          "operator = #{operator,jdbcType=INTEGER},",
          "auditor = #{auditor,jdbcType=INTEGER},",
          "loanApplyId = #{loanApplyId,jdbcType=BIGINT},",
          "repayPhaseId = #{repayPhaseId,jdbcType=BIGINT},",
          "subContractId = #{subContractId,jdbcType=VARCHAR},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "purpose = #{purpose,jdbcType=VARCHAR},",
          "planDate = #{planDate,jdbcType=TIMESTAMP},",
          "sendDate = #{sendDate,jdbcType=TIMESTAMP},",
          "responseDate = #{responseDate,jdbcType=TIMESTAMP},",
          "deductResult = #{deductResult,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR}",
        "where orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UnspayDeduct record);

    @SelectProvider(type = DeductSqlProvider.class,method = "selectDeductListCount")
    Long selectDeductListCount(HashMap<String, Object> searchConditions);

    @SelectProvider(type = DeductSqlProvider.class,method = "selectDeductList")
    List<Map<String,Object>> selectDeductList(HashMap<String, Object> searchConditions);

    @Update({
            "UPDATE unspay_deduct",
            "SET auditor = #{auditor,jdbcType=INTEGER},",
            "verifyStatus = #{verifyStatus,jdbcType=SMALLINT}",
            "WHERE orderId IN (${orderIds})"
    })
    void varify(@Param("orderIds") String orderIds,@Param("verifyStatus") Short verifyStatus, @Param("auditor") Integer auditor);

    @Update({
            "UPDATE unspay_deduct",
            "SET deductResult = #{deductResult,jdbcType=VARCHAR}",
            "WHERE orderId IN (${orderIds})"
    })
    void changeDeductStatus(@Param("orderIds")String orderIds,@Param("deductResult") String deductResult);

    @Select({
            "SELECT",
            "orderId, filename, uploadDate, operator, auditor, loanApplyId, repayPhaseId, subContractId, ",
            "amount, purpose, verifyStatus, planDate, extra, sendDate, responseDate, deductResult, `desc`",
            "FROM unspay_deduct",
            "WHERE extra = #{extra,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="orderId", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
            @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
            @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
            @Result(column="auditor", property="auditor", jdbcType=JdbcType.INTEGER),
            @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
            @Result(column="repayPhaseId", property="repayPhaseId", jdbcType=JdbcType.BIGINT),
            @Result(column="subContractId", property="subContractId", jdbcType=JdbcType.VARCHAR),
            @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
            @Result(column="purpose", property="purpose", jdbcType=JdbcType.VARCHAR),
            @Result(column="verifyStatus", property="verifyStatus", jdbcType=JdbcType.SMALLINT),
            @Result(column="planDate", property="planDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="extra", property="extra", jdbcType=JdbcType.VARCHAR),
            @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="responseDate", property="responseDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="deductResult", property="deductResult", jdbcType=JdbcType.VARCHAR),
            @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR)
    })
    UnspayDeduct selectByExtra(String extra);
}