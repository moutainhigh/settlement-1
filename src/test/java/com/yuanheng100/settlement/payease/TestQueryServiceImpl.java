package com.yuanheng100.settlement.payease;

import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.IQueryService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试：查询服务 TRS001010
 * @author Bai Song
 *
 */
public class TestQueryServiceImpl extends BaseTest
{
    private static Logger logger = Logger.getLogger(TestQueryServiceImpl.class);
    @Autowired
    private IQueryService payeaseQueryService;

    @Test
    public void testQueryInvestAccount(){
        TRS001010Req trs001010Req = new TRS001010Req();
        trs001010Req.setUser(BAISONG_ID);
        TRS001010Resp response = payeaseQueryService.queryInvestAccount(trs001010Req);
        logger.debug("账户额度查询【投资账户】测试代码收到: "+response);
    }

    @Test
    public void testQueryLiabilityAccount(){
        TRS001010Req trs001010Req = new TRS001010Req();
        trs001010Req.setUser(BAISONG_ID);
        TRS001010Resp response = payeaseQueryService.queryLiabilityAccount(trs001010Req);
        logger.debug("账户额度查询【负债账户】测试代码收到: "+response);
    }



}
