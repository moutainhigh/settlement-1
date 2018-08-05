package com.yuanheng100.settlement.startup;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.unspay.service.IDeductService;
import com.yuanheng100.settlement.unspay.task.DeductTask;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * tomcat启动类
 *
 * @author 田伟
 *
 */
public class ProviderMain
{
    private static final Logger log = Logger.getLogger(ProviderMain.class);

    private static ClassPathXmlApplicationContext applicationContext;

    public static void main(String[] args)
    {
        new ProviderMain().start();
    }

    public void start()
    {
        log.info("开始把结算服务注册到zookeeper上...");
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-provider.xml");
        applicationContext.start();
        log.info("");
        log.info("结算服务已经在zookeeper上注册完毕。");
    }

    public void stop()
    {
        log.info("Settlement Provider stopping.");
        //取消代扣发送轮询
        DeductTask deductTask = applicationContext.getBean(DeductTask.class);
        deductTask.setRunDeduct(false);
        deductTask.setRunQuery(false);
        //代扣查看是否没有返回的
        IDeductService deductService = applicationContext.getBean(IDeductService.class);
        String waitTimeStr = ConfigFile.getProperty("system.shutdown.wait.max");
        Date stopDate = new Date();
        int waitTime = 2;
        try {
            waitTime = Integer.parseInt(waitTimeStr);
        } catch (NumberFormatException e) {
            waitTime = 2;
        }
        while(true){
            List<Integer> orderIds = deductService.queryQueryToSend(new Date(stopDate.getTime() - waitTime * 60 * 1000), stopDate);
            if(orderIds.size()>0){
                log.info(waitTime+"分钟之内（停机开始）没有收到的代扣回调为"+ JSON.toJSONString(orderIds));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }else{
                break;
            }
        }
        List<Integer> orderIds = deductService.queryQueryToSend(new Date(stopDate.getTime() - 10 * 60 * 1000), stopDate);
        if(orderIds.size()>0){
            log.info(waitTime+"分钟之前（停机开始）没有收到的代扣回调为"+ JSON.toJSONString(orderIds)+"采用主动查询");
            for (Integer orderId: orderIds) {
                deductService.queryOrderStatusRemote(orderId);
            }
        }
        applicationContext.stop();
        applicationContext.destroy();
        log.info("Settlement Provider stoped.");
    }

}
