package com.yuanheng100.settlement.payease.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanheng100.settlement.payease.mapper.SequenceIdMapper;
import com.yuanheng100.settlement.payease.model.other.SequenceId;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;

public class SequenceIdServiceImpl implements ISequenceIdService
{

    @Autowired 
    SequenceIdMapper sequenceIdMapper;
    
    @Override
    public int getSequenceId()
    {
        SequenceId sequenceId = new SequenceId();
        int count = sequenceIdMapper.insert(sequenceId);
        return count != 0 ? sequenceId.getId() : 0;
    }

    
}
