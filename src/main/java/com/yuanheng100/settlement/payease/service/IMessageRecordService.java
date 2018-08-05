package com.yuanheng100.settlement.payease.service;

import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;

import java.util.List;
import java.util.Map;

/**
 * Created by wangguangshuo on 2017/1/4.
 */
public interface IMessageRecordService
{

    /**
     * 账户报文列表
     * @param param
     * @return
     */
    List<SYN001001Resp> getRecords001(Map<String, Object> param);

    /**
     * 账户报文总数
     * @param param
     * @return
     */
    int getRecords001Count(Map<String, Object> param);

    /**
     * 投标报文
     * @param param
     * @return
     */
    List<TRS001002Resp> getRecords002(Map<String, Object> param);

    /**
     * 投标报文总数
     * @param param
     * @return
     */
    int getRecords002Count(Map<String, Object> param);

    /**
     * 提现报文
     * @param param
     * @return
     */
    List<TRS001006Resp> getRecords006(Map<String, Object> param);

    /**
     * 提现报文总数
     * @param param
     * @return
     */
    int getRecords006Count(Map<String, Object> param);

    /**
     * 代扣报文
     * @param param
     * @return
     */
    List<TRS001008Resp> getRecords008(Map<String, Object> param);

    /**
     * 代扣总数
     * @param param
     * @return
     */
    int getRecords008Count(Map<String, Object> param);

    /**
     * 批量查询
     * @param param
     * @return
     */
    List<SYN001001Resp> getBatchQueryPage(Map<String, Object> param);

    int getBatchQueryPageCount(Map<String, Object> param);
}
