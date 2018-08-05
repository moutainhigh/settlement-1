package com.yuanheng100.settlement.unspay.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import com.yuanheng100.settlement.unspay.model.UnspayPay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface PayMapper {

    //插入一条代付记录
    @Insert({
            "insert into unspay_pay (filename, ",
            "uploadDate, operator, ",
            "auditor, loanApplyId, ",
            "name, cardNo, amount, ",
            "purpose, planDate)",
            "values (#{filename,jdbcType=VARCHAR}, ",
            "#{uploadDate,jdbcType=DATE}, #{operator,jdbcType=INTEGER}, ",
            "#{auditor,jdbcType=INTEGER}, #{loanApplyId,jdbcType=BIGINT}, ",
            "#{name,jdbcType=VARCHAR}, #{cardNo,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
            "#{purpose,jdbcType=VARCHAR}, #{planDate,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "orderId", before = false, resultType = Integer.class)
    int insert(UnspayPay record);

    //获取代付上传记录数
    @SelectProvider(type = PaySqlProvider.class, method = "selectPayUploadCount")
    Long selectPayUploadCount(HashMap<String, Object> searchConditions);

    //获取代付上传列表
    @SelectProvider(type = PaySqlProvider.class, method = "selectPayUploadList")
    List<Map<String, Object>> selectPayUploadList(HashMap<String, Object> searchConditions);

    //根据上传文件名获取列表
    @Select({
            "SELECT",
            "orderId, filename, uploadDate, operator, auditor, loanApplyId, name, cardNo, ",
            "amount, purpose, verifyStatus, planDate, sendDate, responseDate, payResult, `desc`",
            "FROM unspay_pay",
            "WHERE filename = #{filename,jdbcType=VARCHAR}",
            "ORDER BY orderId DESC"
    })
    @Results({
            @Result(column = "orderId", property = "orderId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "filename", property = "filename", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uploadDate", property = "uploadDate", jdbcType = JdbcType.DATE),
            @Result(column = "operator", property = "operator", jdbcType = JdbcType.INTEGER),
            @Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
            @Result(column = "loanApplyId", property = "loanApplyId", jdbcType = JdbcType.BIGINT),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cardNo", property = "cardNo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "amount", property = "amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "purpose", property = "purpose", jdbcType = JdbcType.VARCHAR),
            @Result(column = "verifyStatus", property = "verifyStatus", jdbcType = JdbcType.SMALLINT),
            @Result(column = "planDate", property = "planDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sendDate", property = "sendDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "responseDate", property = "responseDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "payResult", property = "payResult", jdbcType = JdbcType.VARCHAR),
            @Result(column = "desc", property = "desc", jdbcType = JdbcType.VARCHAR)
    })
    List<UnspayPay> selectPayListByFileName(String filename);

    //根据上传文件名获取列表
    @Select({
            "SELECT",
            "usc.name, usc.phoneNo, usc.idCardNo, usc.cardNo, usc.bankCode, ",
            "up.orderId, up.filename, up.loanApplyId, up.auditor, up.amount,up.verifyStatus, up.planDate, up.sendDate, up.responseDate, up.purpose, up.payResult, up.desc",
            "FROM unspay_pay up",
            "LEFT OUTER JOIN unspay_sub_contract usc ON up.loanApplyId = usc.loanApplyId",
            "WHERE up.filename = #{filename,jdbcType=VARCHAR}",
            "ORDER BY up.orderId DESC"
    })
    List<Map<String, Object>> selectPayMapListByFileName(String filename);

    @Select({
            "SELECT",
            "orderId",
            "FROM unspay_pay ",
            "WHERE sendDate IS NULL ",
            "AND (planDate <= #{data,jdbcType=TIMESTAMP} OR planDate IS NULL)",
            "AND verifyStatus = 1"
    })
    List<Integer> selectPayToSend(Date date);

    @InsertProvider(type = PaySqlProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "orderId", before = false, resultType = Integer.class)
    int insertSelective(UnspayPay record);

    @Select({
            "SELECT",
            "orderId, filename, uploadDate, operator, auditor, loanApplyId, name, cardNo, ",
            "amount, purpose, verifyStatus, planDate, sendDate, responseDate, payResult, `desc`",
            "FROM unspay_pay",
            "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "orderId", property = "orderId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "filename", property = "filename", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uploadDate", property = "uploadDate", jdbcType = JdbcType.DATE),
            @Result(column = "operator", property = "operator", jdbcType = JdbcType.INTEGER),
            @Result(column = "auditor", property = "auditor", jdbcType = JdbcType.INTEGER),
            @Result(column = "loanApplyId", property = "loanApplyId", jdbcType = JdbcType.BIGINT),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cardNo", property = "cardNo", jdbcType = JdbcType.VARCHAR),
            @Result(column = "amount", property = "amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "purpose", property = "purpose", jdbcType = JdbcType.VARCHAR),
            @Result(column = "verifyStatus", property = "verifyStatus", jdbcType = JdbcType.SMALLINT),
            @Result(column = "planDate", property = "planDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sendDate", property = "sendDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "responseDate", property = "responseDate", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "payResult", property = "payResult", jdbcType = JdbcType.VARCHAR),
            @Result(column = "desc", property = "desc", jdbcType = JdbcType.VARCHAR)
    })
    UnspayPay selectByPrimaryKey(Integer orderId);

    @Update({
            "UPDATE unspay_pay",
            "SET sendDate = #{sendDate,jdbcType=TIMESTAMP},",
            "payResult = #{payResult,jdbcType=VARCHAR},",
            "`desc` = #{desc,jdbcType=VARCHAR}",
            "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateSendResult(UnspayPay record);

    @Update({
            "UPDATE unspay_pay",
            "SET responseDate = #{responseDate,jdbcType=TIMESTAMP},",
            "payResult = #{payResult,jdbcType=VARCHAR},",
            "`desc` = #{desc,jdbcType=VARCHAR}",
            "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateCallbackResult(UnspayPay record);


    @UpdateProvider(type = PaySqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UnspayDeduct record);

    @Update({
            "update unspay_pay",
            "set filename = #{filename,jdbcType=VARCHAR},",
            "uploadDate = #{uploadDate,jdbcType=DATE},",
            "operator = #{operator,jdbcType=INTEGER},",
            "auditor = #{auditor,jdbcType=INTEGER},",
            "loanApplyId = #{loanApplyId,jdbcType=BIGINT},",
            "name = #{name,jdbcType=VARCHAR},",
            "cardNo = #{cardNo,jdbcType=VARCHAR},",
            "amount = #{amount,jdbcType=DECIMAL},",
            "purpose = #{purpose,jdbcType=VARCHAR},",
            "planDate = #{planDate,jdbcType=TIMESTAMP},",
            "sendDate = #{sendDate,jdbcType=TIMESTAMP},",
            "responseDate = #{responseDate,jdbcType=TIMESTAMP},",
            "payResult = #{payResult,jdbcType=VARCHAR},",
            "`desc` = #{desc,jdbcType=VARCHAR}",
            "where orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UnspayPay record);

    @SelectProvider(type = PaySqlProvider.class, method = "selectPayListCount")
    Long selectPayListCount(HashMap<String, Object> searchConditions);

    @SelectProvider(type = PaySqlProvider.class, method = "selectPayList")
    List<Map<String, Object>> selectPayList(HashMap<String, Object> searchConditions);

    @Update({
            "UPDATE unspay_pay",
            "SET auditor = #{auditor,jdbcType=INTEGER},",
            "verifyStatus = #{verifyStatus,jdbcType=SMALLINT}",
            "WHERE orderId IN (${orderIds})"
    })
    void varify(@Param("orderIds") String orderIds, @Param("verifyStatus") Short verifyStatus, @Param("auditor") Integer auditor);

    @Update({
            "UPDATE unspay_pay",
            "SET payResult = #{payResult,jdbcType=VARCHAR}",
            "WHERE orderId IN (${orderIds})"
    })
    void changePayStatus(@Param("orderIds") String orderIds, @Param("payResult") String payResult);
}