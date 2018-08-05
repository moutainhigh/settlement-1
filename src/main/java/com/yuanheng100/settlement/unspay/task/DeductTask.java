package com.yuanheng100.settlement.unspay.task;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import com.yuanheng100.settlement.unspay.service.IDeductService;
import com.yuanheng100.settlement.unspay.service.IPayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by qianjl on 2016-7-6.
 */
@Component
public class DeductTask {

    private static Logger logger = Logger.getLogger(DeductTask.class);

    @Autowired
    private IDeductService deductService;
    @Autowired
    private IPayService payService;

    private Boolean runDeduct;

    private Boolean runQuery;

    {
        runDeduct = true;
        runQuery = true;
    }

    public void setRunDeduct(Boolean runDeduct) {
        this.runDeduct = runDeduct;
    }

    public void setRunQuery(Boolean runQuery) {
        this.runQuery = runQuery;
    }

    public void doDeduct(){
        if (runDeduct){
            List<Integer> orderIds = deductService.queryDeductToSend();
            for (Integer orderId: orderIds) {
                deductService.collect(orderId);
            }
            List<Integer> payOrderIds = payService.queryPayToSend();
            for (Integer payOrderId: payOrderIds) {
                payService.pay(payOrderId);
            }
        }
    }

    public void doQuery(){
        if(runQuery){
            List<Integer> orderIds = deductService.queryQueryToSend(new Date(new Date().getTime()-24*60*60*1000),new Date(new Date().getTime()-5*60*1000));
            if(orderIds.size()>0){
                logger.info("目前24小时内，5分钟之前的扣款没有收到的订单号为:"+ JSON.toJSONString(orderIds));
                for (Integer orderId: orderIds) {
                    deductService.queryOrderStatusRemote(orderId);
                }
            }
        }
    }
}
