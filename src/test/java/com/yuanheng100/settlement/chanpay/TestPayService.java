package com.yuanheng100.settlement.chanpay;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.model.G10001Bean;
import com.yuanheng100.settlement.chanpay.service.IPayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by jlqian on 2016/9/10.
 */
public class TestPayService extends BaseTest {

    @Autowired
    private IPayService payService;

    @Test
    public void testPay(){
        G10001Bean g10001Bean = new G10001Bean();
        g10001Bean.setBankGeneralName("光大银行");
        g10001Bean.setAccountNo("6226630202347414");
        g10001Bean.setBankCode("303100000604");
        g10001Bean.setAccountName("钱家亮");
        g10001Bean.setBankName("光大银行光华路支行");
        g10001Bean.setAmount(new BigDecimal(0.01));
        try {
            payService.pay(g10001Bean);
            System.out.println("支付请求为:"+g10001Bean.getRetCode());
        } catch (IllegalPlatformAugumentException e) {
            System.out.println("支付请求失败:"+e.getMessage());
        }
    }

}
