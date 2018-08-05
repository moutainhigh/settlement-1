package com.yuanheng100.settlement.payease.mapper;

import org.apache.ibatis.annotations.*;

import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SYN001001Mapper
{


    @Insert({
            "INSERT INTO payease_syn001001 (transCode, ",
            "reqTime, operationCode, ",
            "user, idType, id, ",
            "userName, mobile, ",
            "accName, accProvince, ",
            "accCity, accBankCode, ",
            "accBranchName, accNum, ",
            "accType, accProp)",
            "VALUES (#{transCode,jdbcType=CHAR}, ",
            "#{reqTime,jdbcType=TIMESTAMP}, #{operationCode,jdbcType=SMALLINT}, ",
            "#{user,jdbcType=INTEGER}, #{idType,jdbcType=CHAR}, #{id,jdbcType=CHAR}, ",
            "#{userName,jdbcType=VARCHAR}, #{mobile,jdbcType=BIGINT}, ",
            "#{accName,jdbcType=VARCHAR}, #{accProvince,jdbcType=VARCHAR}, ",
            "#{accCity,jdbcType=VARCHAR}, #{accBankCode,jdbcType=SMALLINT}, ",
            "#{accBranchName,jdbcType=VARCHAR}, #{accNum,jdbcType=VARCHAR}, ",
            "#{accType,jdbcType=CHAR}, #{accProp,jdbcType=CHAR} )"
    })
    @Options(useGeneratedKeys = true, keyProperty = "msgid")
    int insert(SYN001001Req record);

    @Select({
            "SELECT * FROM payease_syn001001",
            "WHERE msgid = #{msgid,jdbcType=INTEGER}"
    })
    SYN001001Req selectByPrimaryKey(Integer msgid);


    @Update({
            "UPDATE payease_syn001001",
            "SET returnCode = #{returnCode,jdbcType=CHAR},",
            "returnMsg = #{returnMsg,jdbcType=VARCHAR},",
            "returnTime = #{returnTime,jdbcType=TIMESTAMP}",
            "where msgid = #{msgid,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(SYN001001Resp record);

    @SelectProvider(type = SYN001001SqlProvider.class, method = "selectAccountByCondition")
    Long selectAccountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type = SYN001001SqlProvider.class, method = "selectAccountListByCondition")
    List<Map<String, Object>> selectAccountListByCondition(HashMap<String, Object> searchConditions);

    @Select({
            "SELECT * FROM payease_syn001001",
            "WHERE user = #{user,jdbcType=INTEGER}",
            "ORDER BY msgid DESC"
    })
    List<SYN001001Resp> selectByUser(String user);

    @SelectProvider(type = SYN001001SqlProvider.class, method = "selectTradeCountByCondition")
    Long selectTradeCountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type = SYN001001SqlProvider.class, method = "selectTradeListByCondition")
    List<Map<String, Object>> selectTradeListByCondition(HashMap<String, Object> searchConditions);

    @Select({
            "SELECT * FROM `payease_syn001001` WHERE `user` = #{user} AND `returnCode` = #{returnCode} ORDER BY reqTime DESC LIMIT 0,1"
    })
    SYN001001Resp selectBindCardCustomer(@Param("user") String user, @Param("returnCode") String returnCode);

    @Select({
            "SELECT MAX(user) FROM payease_syn001001 WHERE user < #{before}"
    })
    Integer selectLastUserBefore(Integer before);

    /**
     * 账户报文
     * @param param
     * @return
     */
    @SelectProvider(type = SYN001001SqlProvider.class,method = "getRecords001")
    List<SYN001001Resp> getRecords001(Map<String, Object> param);

    /**
     * 账户报文
     * @param param
     * @return
     */
    @SelectProvider(type = SYN001001SqlProvider.class,method = "getRecords001Count")
    int getRecords001Count(Map<String, Object> param);


    @SelectProvider(type = SYN001001SqlProvider.class,method = "getBatchQueryPage")
    List<SYN001001Resp> getBatchQueryPage(Map<String, Object> param);

    @SelectProvider(type = SYN001001SqlProvider.class,method = "getBatchQueryPageCount")
    int getBatchQueryPageCount(Map<String, Object> param);
}