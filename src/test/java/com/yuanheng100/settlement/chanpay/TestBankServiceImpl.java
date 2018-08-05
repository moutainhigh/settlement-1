package com.yuanheng100.settlement.chanpay;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.service.impl.BankServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by jlqian on 2016/9/23.
 */
public class TestBankServiceImpl extends BaseTest
{
    @Autowired
    private BankServiceImpl bankServiceImpl;

    @Test
    public void testObtainBank() throws IOException
    {
        bankServiceImpl.obtainBank();
    }
}
