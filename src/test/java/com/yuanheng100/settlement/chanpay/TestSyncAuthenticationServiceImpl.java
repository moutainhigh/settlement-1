package com.yuanheng100.settlement.chanpay;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.model.G60009Bean;
import com.yuanheng100.settlement.chanpay.service.ISyncAuthenticationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jlqian on 2016/9/8.
 */
public class TestSyncAuthenticationServiceImpl extends BaseTest {

    @Autowired
    private ISyncAuthenticationService syncAuthenticationService;

    @Test
    public void testAuthenticate(){
        G60009Bean g60009Bean = new G60009Bean();
        g60009Bean.setBankGeneralName("光大银行");
        g60009Bean.setAccountType("00");
        g60009Bean.setAccountNo("6226630202347414");
        g60009Bean.setAccountName("钱家亮");
        g60009Bean.setIdType("0");
        g60009Bean.setBankCode("1000");
        try {
            boolean authenticate = syncAuthenticationService.authenticate(g60009Bean);
            System.out.println("身份认证结果为："+authenticate);
        } catch (IllegalPlatformAugumentException e) {
            e.printStackTrace();
        }
    }

}
