package com.yuanheng100.settlement.chanpay;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.callback.IAsynAuthenticationCallbackListener;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import com.yuanheng100.settlement.chanpay.model.G60002Bean;
import com.yuanheng100.settlement.chanpay.service.IAsynAuthenticationRequestService;
import com.yuanheng100.settlement.chanpay.task.AsynAuthenticationQueryTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jlqian on 2016/9/8.
 */
public class TestAsynAuthenticationRequestServiceImpl extends BaseTest {

    @Autowired
    private IAsynAuthenticationRequestService realNameAuthenticationService;
    @Autowired
    private AsynAuthenticationQueryTask task;

    @Test
    public void testAuthenticate(){
        G60001Bean g60001Bean = new G60001Bean();
        g60001Bean.setBankGeneralName("光大银行");
        g60001Bean.setBankName("中国光大银行股份有限公司北京建国门支行");
        g60001Bean.setBankCode("303");
        g60001Bean.setAccountType("00");
        g60001Bean.setAccountNo("6226630202347414");
        g60001Bean.setAccountName("钱家亮");
        g60001Bean.setIdType("0");
        try {
            boolean authenticate = realNameAuthenticationService.authenticate(g60001Bean, new IAsynAuthenticationCallbackListener()
            {
                @Override
                public void authenticate(G60002Bean g60002Bean)
                {
                    System.out.println("身份认证结果为："+g60002Bean.getDtlRetCode()+":"+g60002Bean.getDtlErrMsg());
                    System.out.println(JSON.toJSONString(g60002Bean));
                }
            });
            System.out.println("身份认证提交到第三方结果为："+authenticate);
        } catch (IllegalPlatformAugumentException e) {
            e.printStackTrace();
        }

        task.doQuery();
    }



}
