package com.yuanheng100.settlement.chanpay;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.BaseTest;
import com.yuanheng100.settlement.chanpay.model.Q20003Bean;
import com.yuanheng100.settlement.chanpay.service.IRechargeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by jlqian on 2016/9/7.
 */
public class TestRechargeServiceImpl extends BaseTest {

    @Autowired
    private IRechargeService rechargeService;

    @Test
    public void test_getPayUrl(){
        Q20003Bean q20003Bean = new Q20003Bean();
        q20003Bean.setPayMethod((short)1);
        q20003Bean.setTradeAmount(new BigDecimal(1024.00));
        q20003Bean.setOutTradeNo((UUID.randomUUID().toString()).replace("-", ""));
        q20003Bean.setBankCode("ICBC");
        q20003Bean.setReturnUrl("http://117.114.139.34:2084/settlement/");
        q20003Bean.setNotifyUrl("http://117.114.139.34:2084/settlement/chanpay/q20008/notifyTrade");
        try {
            String payUrl = rechargeService.getRechargeUrl(q20003Bean);
            System.out.println("获取支付地址为："+payUrl);
        } catch (IllegalPlatformAugumentException e) {
            e.printStackTrace();
        }
    }

}
