package com.yuanheng100.settlement.chanpay.task;

import com.yuanheng100.settlement.chanpay.callback.IAsynAuthenticationCallbackListener;
import com.yuanheng100.settlement.chanpay.consts.CjDetailRetcode;
import com.yuanheng100.settlement.chanpay.model.G60001Bean;
import com.yuanheng100.settlement.chanpay.model.G60002Bean;
import com.yuanheng100.settlement.chanpay.service.IAsynAuthenticationQueryService;
import com.yuanheng100.settlement.chanpay.service.IAsynAuthenticationRequestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/11/28.
 */
@Component
public class AsynAuthenticationQueryTask
{
    private static Logger logger = Logger.getLogger(AsynAuthenticationQueryTask.class);

    @Autowired
    private IAsynAuthenticationRequestService asynAuthenticationRequestService;
    @Autowired
    private IAsynAuthenticationQueryService asynAuthenticationQueryService;

    private static Map<String, IAsynAuthenticationCallbackListener> listenerMap = null;

    static
    {
        listenerMap = new HashMap<String, IAsynAuthenticationCallbackListener>();
    }

    public static void addListener(String reqSn, IAsynAuthenticationCallbackListener listener)
    {
        listenerMap.put(reqSn, listener);
    }

    public void doQuery()
    {
        List<G60001Bean> g60001BeenList = asynAuthenticationRequestService.withoutQuery();
        for (G60001Bean g60001Bean : g60001BeenList)
        {
            //查询认证结果
            G60002Bean g60002Bean = asynAuthenticationQueryService.query(g60001Bean.getReqSn());
            IAsynAuthenticationCallbackListener listener = listenerMap.get(g60002Bean.getQryReqSn());
            if(listener!=null && (CjDetailRetcode.$0000_处理成功.getCode().equals(g60002Bean.getDtlRetCode()) || CjDetailRetcode.$2013_收款行未开通业务.getCode().equals(g60002Bean.getDtlRetCode()) || CjDetailRetcode.$3999_其他错误.getCode().equals(g60002Bean.getDtlRetCode()))){
                listener.authenticate(g60002Bean);
                listenerMap.remove(g60002Bean.getQryReqSn());
            }
        }

    }


}
