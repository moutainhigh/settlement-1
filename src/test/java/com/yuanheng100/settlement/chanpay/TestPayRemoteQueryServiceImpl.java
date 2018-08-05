package com.yuanheng100.settlement.chanpay;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.model.G20001Bean;
import com.yuanheng100.settlement.chanpay.service.IPayRemoteQueryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jlqian on 2016/9/12.
 */
public class TestPayRemoteQueryServiceImpl extends BaseTest
{
    @Autowired
    private IPayRemoteQueryService payRemoteQueryService;

    @Test
    public void testQuery(){
        G20001Bean g20001Bean = new G20001Bean();
        g20001Bean.setQryReqSn("cp201605189075716091410342000022");
        try
        {
            boolean query = payRemoteQueryService.query(g20001Bean);

        } catch (IllegalPlatformAugumentException e)
        {
            e.printStackTrace();
        }
    }
}
