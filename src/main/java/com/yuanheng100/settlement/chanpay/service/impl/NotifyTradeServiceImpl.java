package com.yuanheng100.settlement.chanpay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.chanpay.callback.IRechargeCallbackListener;
import com.yuanheng100.settlement.chanpay.consts.Qpay;
import com.yuanheng100.settlement.chanpay.mapper.Q20008BeanMapper;
import com.yuanheng100.settlement.chanpay.model.Q20008Bean;
import com.yuanheng100.settlement.chanpay.service.INotifyTradeService;
import com.yuanheng100.settlement.chanpay.util.CallbackListenerContainer;
import com.yuanheng100.settlement.chanpay.util.CjQpayHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by jlqian on 2016/9/8.
 */
public class NotifyTradeServiceImpl implements INotifyTradeService
{

    private static final Logger LOG = Logger.getLogger(NotifyTradeServiceImpl.class);

    @Autowired
    private Q20008BeanMapper q20008BeanMapper;

    public boolean notifyTrade(HashMap<String, String> stringStringHashMap)
    {
        //验证是否为畅捷通回调
        boolean verify = CjQpayHelper.notifyVerify(stringStringHashMap);
        LOG.info("畅捷通交易状态通知进行验证" + JSON.toJSONString(stringStringHashMap));
        if (verify)
        {
            try
            {
                Q20008Bean bean = (Q20008Bean) CjQpayHelper.convertMapToQBean(stringStringHashMap, Q20008Bean.class);
                //防止同一订单插入多条重复状态记录
                Q20008Bean existBean = q20008BeanMapper.selectByOuterTradeNoAndTradeStatus(bean.getOuterTradeNo(), bean.getTradeStatus());
                if (existBean != null)
                {
                    LOG.info("畅捷通交易状态通知成功，数据库存在记录，不再执行数据库操作" + JSON.toJSONString(bean));
                } else
                {
                    IRechargeCallbackListener rechargeCallbackListener = CallbackListenerContainer.getRechargeCallbackListener(bean.getOuterTradeNo());
                    LOG.info("畅捷通交易状态通知成功，执行数据库操作" + JSON.toJSONString(bean));
                    q20008BeanMapper.insertSelective(bean);
                    if (rechargeCallbackListener != null && (Qpay.NotifyTradeStatus.TRADE_SUCCESS.name().equals(bean.getTradeStatus()) || Qpay.NotifyTradeStatus.TRADE_CLOSED.name().equals(bean.getTradeStatus())))
                    {
                        LOG.info("畅捷通交易状态通知成功，对P2P系统进行回调");
                        rechargeCallbackListener.setRechargeResult(bean);
                        CallbackListenerContainer.removeRechargeCallbackListener(bean.getOuterTradeNo());
                    }
                }
                return true;
            } catch (Exception e)
            {
                LOG.error("程序发生异常", e);
            }
        }
        return false;
    }
}
