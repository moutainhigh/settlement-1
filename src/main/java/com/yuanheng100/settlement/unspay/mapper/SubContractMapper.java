package com.yuanheng100.settlement.unspay.mapper;

import com.yuanheng100.settlement.unspay.model.UnspaySubContract;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SubContractMapper {

    //获取子协议上传记录数
    @SelectProvider(type = SubContractSqlProvider.class,method = "selectSubContractUploadCount")
    Long selectSubContractUploadCount(HashMap<String, Object> searchConditions);

    //获取子协议上传列表
    @SelectProvider(type = SubContractSqlProvider.class,method = "selectSubContractUploadList")
    public List<Map<String,Object>>selectSubContractUploadList(Map<String,Object> searchConditions);

    //根据上传文件名获取列表
    @Select({
            "SELECT",
            "id, filename, uploadDate, operator, loanApplyId, name, idCardNo, cardNo, bankCode, phoneNo, ",
            "startDate, endDate, cycle, triesLimit, sendDate,signStatus, signMsg, subContractId",
            "FROM unspay_sub_contract",
            "WHERE filename = #{filename,jdbcType=VARCHAR}",
            "ORDER BY id DESC"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
            @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
            @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
            @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="cardNo", property="cardNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="bankCode", property="bankCode", jdbcType=JdbcType.SMALLINT),
            @Result(column="phoneNo", property="phoneNo", jdbcType=JdbcType.INTEGER),
            @Result(column="startDate", property="startDate", jdbcType=JdbcType.DATE),
            @Result(column="endDate", property="endDate", jdbcType=JdbcType.DATE),
            @Result(column="cycle", property="cycle", jdbcType=JdbcType.SMALLINT),
            @Result(column="triesLimit", property="triesLimit", jdbcType=JdbcType.INTEGER),
            @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="signStatus", property="signStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="signMsg", property="signMsg", jdbcType=JdbcType.VARCHAR),
            @Result(column="subContractId", property="subContractId", jdbcType=JdbcType.VARCHAR)
    })
    List<UnspaySubContract> selectSubContractListByFileName(String filename);

    //保存子协议发送结果
    @Update({
            "UPDATE unspay_sub_contract",
            "SET sendDate = #{sendDate,jdbcType=TIMESTAMP},",
            "subContractId = #{subContractId,jdbcType=VARCHAR},",
            "signStatus = #{signStatus,jdbcType=VARCHAR},",
            "signMsg = #{signMsg,jdbcType=VARCHAR}",
            "WHERE id = #{id,jdbcType=INTEGER}"
    })
    public void savaSignSubContractResult(UnspaySubContract record);

    //通过进件编号获取子协议编号
    @Select({
        "SELECT",
        "subContractId",
        "FROM unspay_sub_contract",
        "WHERE loanApplyId = #{loanApplyId,jdbcType=BIGINT}"
    })
    String selectSubContractIdByLoanApplyId(Long loanApplyId);

    //通过进件编号获取SubCont
    @Select({
            "select",
            "id, filename, uploadDate, operator, loanApplyId, name, idCardNo, cardNo, bankCode, phoneNo, ",
            "startDate, endDate, cycle, triesLimit, sendDate,signStatus, signMsg, subContractId",
            "from unspay_sub_contract",
            "where loanApplyId = #{loanApplyId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
            @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
            @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
            @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="cardNo", property="cardNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="bankCode", property="bankCode", jdbcType=JdbcType.SMALLINT),
            @Result(column="phoneNo", property="phoneNo", jdbcType=JdbcType.INTEGER),
            @Result(column="startDate", property="startDate", jdbcType=JdbcType.DATE),
            @Result(column="endDate", property="endDate", jdbcType=JdbcType.DATE),
            @Result(column="cycle", property="cycle", jdbcType=JdbcType.SMALLINT),
            @Result(column="triesLimit", property="triesLimit", jdbcType=JdbcType.INTEGER),
            @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="signStatus", property="signStatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="signMsg", property="signMsg", jdbcType=JdbcType.VARCHAR),
            @Result(column="subContractId", property="subContractId", jdbcType=JdbcType.VARCHAR)
    })
    UnspaySubContract selectSubContractByLoanApplyId(Long loanApplyId);

    //子协议修改
    @UpdateProvider(type=SubContractSqlProvider.class, method="updateByLoanApplyIdSelective")
    int updateByLoanApplyIdSelective(UnspaySubContract record);

    //子协议延期
    @Update({
            "UPDATE unspay_sub_contract",
            "SET startDate = #{startDate,jdbcType=DATE},",
            "endDate = #{endDate,jdbcType=DATE}",
            "WHERE id = #{id,jdbcType=INTEGER}"
    })
    void extension(UnspaySubContract unspaySubContract);

    /**
     *判断进件编号是否存在
     * @param unspaySubContract
     * @return
     */
    @Select({
            "SELECT",
            "COUNT(*)",
            "FROM unspay_sub_contract",
            "WHERE loanApplyId = #{loanApplyId,jdbcType=BIGINT}"
    })
    int isExist(UnspaySubContract unspaySubContract);

    @Insert({
        "insert into unspay_sub_contract (id, filename, ",
        "uploadDate, operator, ",
        "loanApplyId, name, ",
        "idCardNo, cardNo, bankCode, ",
        "phoneNo, startDate, ",
        "endDate, cycle, triesLimit, ",
        "sendDate, subContractId)",
        "values (#{id,jdbcType=INTEGER}, #{filename,jdbcType=VARCHAR}, ",
        "#{uploadDate,jdbcType=DATE}, #{operator,jdbcType=INTEGER}, ",
        "#{loanApplyId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
        "#{idCardNo,jdbcType=VARCHAR}, #{cardNo,jdbcType=VARCHAR}, #{bankCode,jdbcType=SMALLINT}, ",
        "#{phoneNo,jdbcType=INTEGER}, #{startDate,jdbcType=DATE}, ",
        "#{endDate,jdbcType=DATE}, #{cycle,jdbcType=SMALLINT}, #{triesLimit,jdbcType=INTEGER}, ",
        "#{sendDate,jdbcType=TIMESTAMP}, #{subContractId,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(UnspaySubContract record);

    @InsertProvider(type=SubContractSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(UnspaySubContract record);

    @Select({
        "select",
        "id, filename, uploadDate, operator, loanApplyId, name, idCardNo, cardNo, bankCode, phoneNo, ",
        "startDate, endDate, cycle, triesLimit, sendDate, signStatus, signMsg, subContractId",
        "from unspay_sub_contract",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
        @Result(column="uploadDate", property="uploadDate", jdbcType=JdbcType.DATE),
        @Result(column="operator", property="operator", jdbcType=JdbcType.INTEGER),
        @Result(column="loanApplyId", property="loanApplyId", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="idCardNo", property="idCardNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="cardNo", property="cardNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="bankCode", property="bankCode", jdbcType=JdbcType.SMALLINT),
        @Result(column="phoneNo", property="phoneNo", jdbcType=JdbcType.INTEGER),
        @Result(column="startDate", property="startDate", jdbcType=JdbcType.DATE),
        @Result(column="endDate", property="endDate", jdbcType=JdbcType.DATE),
        @Result(column="cycle", property="cycle", jdbcType=JdbcType.SMALLINT),
        @Result(column="triesLimit", property="triesLimit", jdbcType=JdbcType.INTEGER),
        @Result(column="sendDate", property="sendDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="signStatus", property="signStatus", jdbcType=JdbcType.VARCHAR),
        @Result(column="signMsg", property="signMsg", jdbcType=JdbcType.VARCHAR),
        @Result(column="subContractId", property="subContractId", jdbcType=JdbcType.VARCHAR)
    })
    UnspaySubContract selectByPrimaryKey(Integer id);

    @UpdateProvider(type=SubContractSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UnspaySubContract record);

    @Update({
        "update unspay_sub_contract",
        "set filename = #{filename,jdbcType=VARCHAR},",
          "uploadDate = #{uploadDate,jdbcType=DATE},",
          "operator = #{operator,jdbcType=INTEGER},",
          "loanApplyId = #{loanApplyId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=VARCHAR},",
          "idCardNo = #{idCardNo,jdbcType=VARCHAR},",
          "cardNo = #{cardNo,jdbcType=VARCHAR},",
          "bankCode = #{bankCode,jdbcType=SMALLINT}",
          "phoneNo = #{phoneNo,jdbcType=INTEGER},",
          "startDate = #{startDate,jdbcType=DATE},",
          "endDate = #{endDate,jdbcType=DATE},",
          "cycle = #{cycle,jdbcType=SMALLINT},",
          "triesLimit = #{triesLimit,jdbcType=INTEGER},",
          "sendDate = #{sendDate,jdbcType=TIMESTAMP},",
          "subContractId = #{subContractId,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UnspaySubContract record);

    //列表页面总计数
    @SelectProvider(type = SubContractSqlProvider.class,method = "selectSubContractListCount")
    Long selectSubContractListCount(HashMap<String, Object> searchConditions);

    //列表页面数据
    @SelectProvider(type = SubContractSqlProvider.class,method = "selectSubContractList")
    List<UnspaySubContract> selectSubContractList(HashMap<String, Object> searchConditions);

}