package com.yuanheng100.settlement.payease.mapper;

import com.yuanheng100.settlement.payease.model.recharge.RechargeBackResp;
import com.yuanheng100.settlement.payease.model.recharge.RechargeReq;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RechargeMapper
{
    @Insert({
            "INSERT INTO payease_recharge (v_oid, v_rcvname, ",
            "v_rcvaddr, v_rcvtel, ",
            "v_rcvpost, v_amount, ",
            "v_ymd, v_orderstatus, ",
            "v_ordername, v_moneytype, ",
            "v_pmode, v_merdata1, ",
            "v_merdata2, v_merdata3, ",
            "v_merdata4, v_merdata5, ",
            "v_merdata6, v_merdata7)",
            "VALUES (#{v_oid,jdbcType=VARCHAR}, #{v_rcvname,jdbcType=VARCHAR}, ",
            "#{v_rcvaddr,jdbcType=VARCHAR}, #{v_rcvtel,jdbcType=VARCHAR}, ",
            "#{v_rcvpost,jdbcType=VARCHAR}, #{v_amount,jdbcType=DECIMAL}, ",
            "#{v_ymd,jdbcType=VARCHAR}, #{v_orderstatus,jdbcType=CHAR}, ",
            "#{v_ordername,jdbcType=VARCHAR}, #{v_moneytype,jdbcType=INTEGER}, ",
            "#{v_pmode,jdbcType=INTEGER}, #{v_merdata1,jdbcType=VARCHAR}, ",
            "#{v_merdata2,jdbcType=VARCHAR}, #{v_merdata3,jdbcType=VARCHAR}, ",
            "#{v_merdata4,jdbcType=VARCHAR}, #{v_merdata5,jdbcType=VARCHAR}, ",
            "#{v_merdata6,jdbcType=VARCHAR}, #{v_merdata7,jdbcType=VARCHAR})"
    })
    int insert(RechargeReq record);

    @Update({
            "UPDATE payease_recharge",
            "SET v_pstatus = #{v_pstatus,jdbcType=SMALLINT},",
            "v_pstring = #{v_pstring,jdbcType=VARCHAR}",
            "WHERE v_oid = #{v_oid,jdbcType=VARCHAR}"
    })
    int update(RechargeBackResp record);

    @Select({
            "SELECT * FROM payease_recharge",
            "WHERE v_oid = #{v_oid,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column = "v_oid", property = "v_oid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "v_amount", property = "v_amount", jdbcType = JdbcType.DECIMAL),
            @Result(column = "v_moneytype", property = "v_moneytype", jdbcType = JdbcType.INTEGER),
            @Result(column = "v_pmode", property = "v_pmode", jdbcType = JdbcType.INTEGER),
            @Result(column = "v_pstatus", property = "v_pstatus", jdbcType = JdbcType.SMALLINT),
            @Result(column = "v_pstring", property = "v_pstring", jdbcType = JdbcType.VARCHAR)
    })
    RechargeBackResp selectResult(String v_oid);

    @SelectProvider(type = RechargeSqlProvider.class, method = "selectRechargeCountByCondition")
    Long selectRechargeCountByCondition(HashMap<String, Object> searchConditions);

    @SelectProvider(type = RechargeSqlProvider.class, method = "selectRechargeListByCondition")
    List<Map<String, Object>> selectRechargeListByCondition(HashMap<String, Object> searchConditions);

}