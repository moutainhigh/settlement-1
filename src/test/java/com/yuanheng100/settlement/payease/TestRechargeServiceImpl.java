package com.yuanheng100.settlement.payease;

import com.yuanheng100.settlement.payease.util.Md5EncUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by jlqian on 2017/2/7.
 */
public class TestRechargeServiceImpl
{
    @Test
    public void testMd5(){

        try
        {
            String test = Md5EncUtil.enco("0201702071000.00761320170207-7613-637613http://haoshidai.com/account/account_fund.html", "test");
            System.out.println(test);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
