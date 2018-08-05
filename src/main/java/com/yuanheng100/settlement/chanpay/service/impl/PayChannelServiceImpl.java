package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.consts.Charset;
import com.yuanheng100.settlement.chanpay.consts.Qpay;
import com.yuanheng100.settlement.chanpay.model.Q20007Bean;
import com.yuanheng100.settlement.chanpay.service.IPayChannelService;
import com.yuanheng100.settlement.chanpay.util.CjQpayHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by jlqian on 2016/9/24.
 */
@Service
public class PayChannelServiceImpl implements IPayChannelService
{
    private static final Logger LOG = Logger.getLogger(PayChannelServiceImpl.class);

    @Override
    public void getPayChannel(Q20007Bean q20007Bean)
    {
        q20007Bean.setService(Qpay.SERVICE_Q20007);
        q20007Bean.setProductCode(Qpay.QPAY_PRODUCT_CODE_RECHARGE);
        Map<String, String> origMap = CjQpayHelper.convertQBeanToMap(q20007Bean);
        try
        {
            String resp = CjQpayHelper.buildRequest(origMap, "RSA", Qpay.QPAY_MERCHANT_PRIVATE_KEY, Charset.UTF8, Qpay.QPAY_QPAY_URL);
            JSONObject jsonObject = JSONObject.parseObject(resp);
            Object is_success = jsonObject.get("is_success");
            System.out.println(is_success);
            if(Qpay.IS_SUCCESS_TRUE.equals(is_success)){
                //解析Q20007
                q20007Bean.setIsSuccess(Qpay.IS_SUCCESS_TRUE);
                q20007Bean.setPayInstList(JSON.parseArray(jsonObject.get("pay_inst_list").toString(),Q20007Bean.PayInst.class));
                return;
            }
            throw new IllegalPlatformAugumentException(resp);
        } catch (Exception e)
        {
            LOG.error(e);
        }
    }
}
