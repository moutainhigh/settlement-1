package com.yuanheng100.settlement.payease.service.impl;

import com.yuanheng100.settlement.payease.mapper.SYN001001Mapper;
import com.yuanheng100.settlement.payease.mapper.TRS001002Mapper;
import com.yuanheng100.settlement.payease.mapper.TRS001006Mapper;
import com.yuanheng100.settlement.payease.mapper.TRS001008Mapper;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import com.yuanheng100.settlement.payease.service.IMessageRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by wangguangshuo on 2017/1/4.
 */
public class MessageRecordServiceImpl implements IMessageRecordService
{

    @Autowired
    private SYN001001Mapper syn001001Mapper;

    @Autowired
    private TRS001002Mapper trs001002Mapper;

    @Autowired
    private TRS001006Mapper trs001006Mapper;

    @Autowired
    private TRS001008Mapper trs001008Mapper;


    @Override
    public List<SYN001001Resp> getRecords001(Map<String, Object> param)
    {
        return syn001001Mapper.getRecords001(param);
    }

    @Override
    public int getRecords001Count(Map<String, Object> param)
    {
        return syn001001Mapper.getRecords001Count(param);
    }

    @Override
    public List<TRS001002Resp> getRecords002(Map<String, Object> param)
    {
        return trs001002Mapper.getRecords002(param);
    }

    @Override
    public int getRecords002Count(Map<String, Object> param)
    {
        return trs001002Mapper.getRecords002Count(param);
    }

    @Override
    public List<TRS001006Resp> getRecords006(Map<String, Object> param)
    {
        return trs001006Mapper.getRecords006(param);
    }

    @Override
    public int getRecords006Count(Map<String, Object> param)
    {
        return trs001006Mapper.getRecords006Count(param);
    }

    @Override
    public List<TRS001008Resp> getRecords008(Map<String, Object> param)
    {
        return trs001008Mapper.getRecords008(param);
    }

    @Override
    public int getRecords008Count(Map<String, Object> param)
    {
        return trs001008Mapper.getRecords008Count(param);
    }

    @Override
    public List<SYN001001Resp> getBatchQueryPage(Map<String, Object> param)
    {
        return syn001001Mapper.getBatchQueryPage(param);
    }

    @Override
    public int getBatchQueryPageCount(Map<String, Object> param)
    {
        return syn001001Mapper.getBatchQueryPageCount(param);
    }
}
