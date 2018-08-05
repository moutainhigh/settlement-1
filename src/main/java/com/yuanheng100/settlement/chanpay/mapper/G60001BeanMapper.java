package com.yuanheng100.settlement.chanpay.mapper;

import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface G60001BeanMapper {

    @Insert({
        "INSERT INTO chanpay_g60001_bean (reqSn, timestamp, ",
        "sn, bankGeneralName, ",
        "bankName, bankCode, ",
        "accountType, accountNo, ",
        "accountName, idType, ",
        "id, tel, retCode, ",
        "errMsg)",
        "VALUES (#{reqSn,jdbcType=VARCHAR}, #{timestamp,jdbcType=VARCHAR}, ",
        "#{sn,jdbcType=VARCHAR}, #{bankGeneralName,jdbcType=VARCHAR}, ",
        "#{bankName,jdbcType=VARCHAR}, #{bankCode,jdbcType=VARCHAR}, ",
        "#{accountType,jdbcType=VARCHAR}, #{accountNo,jdbcType=VARCHAR}, ",
        "#{accountName,jdbcType=VARCHAR}, #{idType,jdbcType=VARCHAR}, ",
        "#{id,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR}, #{retCode,jdbcType=VARCHAR}, ",
        "#{errMsg,jdbcType=VARCHAR})"
    })
    int insert(G60001Bean record);

    @InsertProvider(type=G60001BeanSqlProvider.class, method="insertSelective")
    int insertSelective(G60001Bean record);

    /**
     * 四要素获取G60001对象
     * @return
     */
    @Select({
            "SELECT * FROM chanpay_g60001_bean",
            "WHERE accountName = #{accountName,jdbcType=VARCHAR}",
            "AND accountNo = #{accountNo,jdbcType=VARCHAR}",
            "AND id = #{id,jdbcType=VARCHAR}",
            "AND tel = #{tel,jdbcType=VARCHAR}"
    })
    G60001Bean selectByFourElement(@Param("accountName") String accountName,
                                   @Param("accountNo") String accountNo,
                                   @Param("id") String id,
                                   @Param("tel") String tel);

    /**
     * 发送异步实名认证，但是木有查询的G60001bean
     * @return
     */
    @SelectProvider(type=G60001BeanSqlProvider.class, method="withoutQuery")
    List<G60001Bean> withoutQuery();

    /**
     * 查询实名认证的数量
     * @param searchConditions
     * @return
     */
    @SelectProvider(type=G60001BeanSqlProvider.class, method="selectAuthenticationByCondition")
    Long selectAuthenticationByCondition(HashMap<String, Object> searchConditions);

    /**
     * 查询实名认证的列表
     * @param searchConditions
     * @return
     */
    @SelectProvider(type=G60001BeanSqlProvider.class, method="selectAuthenticationListByCondition")
    List<Map<String,Object>> selectAuthenticationListByCondition(HashMap<String, Object> searchConditions);
}