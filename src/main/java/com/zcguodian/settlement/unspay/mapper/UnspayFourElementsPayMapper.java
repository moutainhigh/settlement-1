package com.zcguodian.settlement.unspay.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;

public interface UnspayFourElementsPayMapper {
    /**
     *
     * @mbggenerated 2018-07-30
     */
    @Insert({
        "insert into unspay_zcgd_pay (filename, ",
        "uploadDate, operator, ",
        "auditor, loanApplyId, ",
        "name, cardNo, amount, ",
        "purpose, idCardNo, ",
        "summary, phoneNo, ",
        "sendDate, responseDate, ",
        "payResult)",
        "values (#{filename,jdbcType=VARCHAR}, ",
        "#{uploadDate,jdbcType=DATE}, #{operator,jdbcType=INTEGER}, ",
        "#{auditor,jdbcType=INTEGER}, #{loanApplyId,jdbcType=BIGINT}, ",
        "#{name,jdbcType=VARCHAR}, #{cardNo,jdbcType=VARCHAR}, #{amount,jdbcType=DECIMAL}, ",
        "#{purpose,jdbcType=VARCHAR}, #{idCardNo,jdbcType=VARCHAR}, ",
        "#{summary,jdbcType=VARCHAR}, #{phoneNo,jdbcType=BIGINT}, ",
        "#{sendDate,jdbcType=TIMESTAMP}, #{responseDate,jdbcType=TIMESTAMP}, ",
        "#{payResult,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=false, resultType=Integer.class)
    int insert(UnspayFourElementsPay record);

    /**
     *
     * @mbggenerated 2018-07-30
     */
    @InsertProvider(type=UnspayFourElementsPaySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="orderId", before=true, resultType=Integer.class)
    int insertSelective(UnspayFourElementsPay record);

    /**
     *
     * @mbggenerated 2018-07-30
     */
    @Select({
        "select",
        "orderId, filename, uploadDate, operator, auditor, loanApplyId, name, cardNo, ",
        "amount, purpose, idCardNo, summary, phoneNo, verifyStatus, sendDate, ",
        "responseDate, payResult, `desc`",
        "from unspay_zcgd_pay",
        "where orderId = #{orderId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="orderId", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
        @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
        @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
        @Result(column="auditor", property="auditor", jdbcType=JdbcType.INTEGER),
        @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="cardNo", property="cardNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="purpose", property="purpose", jdbcType=JdbcType.VARCHAR),
        @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="phoneNo", property="phoneNo", jdbcType=JdbcType.BIGINT),
        @Result(column="verifyStatus", property="verifyStatus", jdbcType=JdbcType.SMALLINT),
        @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="responseDate", property="responseDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="payResult", property="payResult", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR)
    })
    UnspayFourElementsPay selectByPrimaryKey(Integer orderId);

    /**
     *
     * @mbggenerated 2018-07-30
     */
    @UpdateProvider(type=UnspayFourElementsPaySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UnspayFourElementsPay record);

    /**
     *
     * @mbggenerated 2018-07-30
     */
    @Update({
        "update unspay_zcgd_pay",
        "set filename = #{filename,jdbcType=VARCHAR},",
          "uploadDate = #{uploadDate,jdbcType=DATE},",
          "operator = #{operator,jdbcType=INTEGER},",
          "auditor = #{auditor,jdbcType=INTEGER},",
          "loanApplyId = #{loanApplyId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "cardNo = #{cardNo,jdbcType=VARCHAR},",
          "amount = #{amount,jdbcType=DECIMAL},",
          "purpose = #{purpose,jdbcType=VARCHAR},",
          "idCardNo = #{idCardNo,jdbcType=VARCHAR},",
          "summary = #{summary,jdbcType=VARCHAR},",
          "phoneNo = #{phoneNo,jdbcType=BIGINT},",
          "verifyStatus = #{verifyStatus,jdbcType=SMALLINT},",
          "sendDate = #{sendDate,jdbcType=TIMESTAMP},",
          "responseDate = #{responseDate,jdbcType=TIMESTAMP},",
          "payResult = #{payResult,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR}",
        "where orderId = #{orderId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UnspayFourElementsPay record);
    
    @Update({
        "UPDATE unspay_zcgd_pay",
        "SET auditor = #{auditor,jdbcType=INTEGER},",
        "verifyStatus = #{verifyStatus,jdbcType=SMALLINT}",
        "WHERE orderId IN (${orderIds})"
    })
    void verify(@Param("orderIds") String orderIds, @Param("verifyStatus") Short verifyStatus, @Param("auditor") Integer auditor);
    
    @Update({
        "UPDATE unspay_zcgd_pay",
        "SET payResult = #{payResult,jdbcType=VARCHAR}",
        "`desc` = #{desc,jdbcType=VARCHAR}",
        "WHERE orderId = #{orderId,jdbcType=INTEGER}"
    })
    void changePayStatusAndDesc(@Param("orderId") Integer orderId, @Param("payResult") String payResult, @Param("desc") String desc);

    
    //获取实时代付上传记录数
    @SelectProvider(type = UnspayFourElementsPaySqlProvider.class, method = "selectFEPayUploadCount")
    Long selectFEPayUploadCount(HashMap<String, Object> searchConditions);
    
    //获取实时代付上传列表
    @SelectProvider(type = UnspayFourElementsPaySqlProvider.class, method = "selectFEPayUploadList")
    List<Map<String, Object>> selectFEPayUploadList(HashMap<String, Object> searchConditions);
    
    @Select({
        "select",
        "orderId, filename, uploadDate, operator, auditor, loanApplyId, name, cardNo, ",
        "amount, purpose, idCardNo, summary, phoneNo, verifyStatus, sendDate, ",
        "responseDate, payResult, `desc`",
        "from unspay_zcgd_pay",
        "where filename = #{filename,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="orderId", property="orderId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
        @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
        @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
        @Result(column="auditor", property="auditor", jdbcType=JdbcType.INTEGER),
        @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="cardNo", property="cardNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="amount", property="amount", jdbcType=JdbcType.DECIMAL),
        @Result(column="purpose", property="purpose", jdbcType=JdbcType.VARCHAR),
        @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="phoneNo", property="phoneNo", jdbcType=JdbcType.BIGINT),
        @Result(column="verifyStatus", property="verifyStatus", jdbcType=JdbcType.SMALLINT),
        @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="responseDate", property="responseDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="payResult", property="payResult", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR)
    })
    List<UnspayFourElementsPay> selectFEPayListByFileName(String filename);
    
    //根据上传文件名获取列表
    @Select({
        "select",
        "orderId, filename, uploadDate, operator, auditor, loanApplyId, name, cardNo, ",
        "amount, purpose, idCardNo, summary, phoneNo, verifyStatus, sendDate, ",
        "responseDate, payResult, `desc`",
        "from unspay_zcgd_pay",
        "where filename = #{filename,jdbcType=VARCHAR}",
        "ORDER BY orderId DESC"
    })
    List<Map<String, Object>> selectZCGDPayMapListByFileName(String filename);
    
    @SelectProvider(type = UnspayFourElementsPaySqlProvider.class, method = "selectFEPayListCount")
    Long selectFEPayListCount(HashMap<String, Object> searchConditions);

    @SelectProvider(type = UnspayFourElementsPaySqlProvider.class, method = "selectFEPayList")
    List<Map<String, Object>> selectFEPayList(HashMap<String, Object> searchConditions);
    
  
}