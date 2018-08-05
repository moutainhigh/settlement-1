package com.yuanheng100.settlement.payease.mapper;

import com.yuanheng100.settlement.payease.model.other.IdcardVerify;
import com.yuanheng100.settlement.payease.provider.IdCardVerifySqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

public interface IdcardVerifyMapper {

    @Insert({
            "INSERT INTO payease_idcard_verify (name, idcardNo, ",
            "status, verifyTime)",
            "VALUES (#{name,jdbcType=VARCHAR}, #{idcardNo,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=SMALLINT}, #{verifyTime,jdbcType=TIMESTAMP})"
    })
    int insert(IdcardVerify record);

    @Select({
            "SELECT name, idcardNo, status, verifyTime",
            "FROM payease_idcard_verify",
            "WHERE name = #{name,jdbcType=VARCHAR}",
            "AND idcardNo = #{idcardNo,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="idcardNo", property="idcardNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
            @Result(column="verifyTime", property="verifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    IdcardVerify selectByNameAndIdcardNo(@Param("name") String name, @Param("idcardNo") String idcardNo);


    @SelectProvider(type = IdCardVerifySqlProvider.class,method = "getIdCardVerifyList")
    List<IdcardVerify> getIdCardVerifyList(Map<String, Object> param);

    @SelectProvider(type = IdCardVerifySqlProvider.class,method = "getIdCardVerifyListCount")
    int getIdCardVerifyListCount(Map<String, Object> param);
}