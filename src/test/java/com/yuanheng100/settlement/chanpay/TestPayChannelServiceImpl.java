package com.yuanheng100.settlement.chanpay;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.model.Q20007Bean;
import com.yuanheng100.settlement.chanpay.service.IPayChannelService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by jlqian on 2016/9/24.
 */
public class TestPayChannelServiceImpl extends BaseTest
{
    @Autowired
    private IPayChannelService payChannelService;

    @Test
    public void testGetPayChannel() throws IOException
    {
        Q20007Bean q20007Bean = new Q20007Bean();
        payChannelService.getPayChannel(q20007Bean);
        System.out.println(JSON.toJSONString(q20007Bean));
        System.in.read();
    }

}
