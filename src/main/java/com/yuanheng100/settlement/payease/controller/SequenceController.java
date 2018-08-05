package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *获取流水id
 *
 * Created by wangguangshuo on 2017/1/6.
 */
@Controller
@RequestMapping("/payease")
public class SequenceController
{
    @Autowired
    private ISequenceIdService sequenceIdService;


    @ResponseBody
    @RequestMapping("/nextSequenceId")
    public int nextSequenceId(){
        return sequenceIdService.getSequenceId();
    }
}
