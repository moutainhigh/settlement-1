package com.yuanheng100.settlement.unspay.adaptor;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.adaptor.AbstractAdapter;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.unspay.consts.UnspayStatus;
import com.yuanheng100.settlement.unspay.mapper.DeductMapper;
import com.yuanheng100.settlement.unspay.model.UnspayDeduct;
import com.yuanheng100.settlement.unspay.model.UnspayDeductMessageType;
import com.yuanheng100.settlement.unspay.model.UnspayDeductRequest;
import com.yuanheng100.settlement.unspay.model.UnspayDeductResponse;
import com.yuanheng100.settlement.unspay.service.impl.DeductServiceImpl;
import com.yuanheng100.settlement.unspay.utils.UnspayUtil;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by qianjl on 2016-6-24.
 */
public class DeductAdaptor extends AbstractAdapter<UnspayDeductRequest> {

    private static Logger logger = Logger.getLogger(DeductAdaptor.class);

    @Override
    public UnspayDeductResponse send(UnspayDeductRequest unspayDeductRequest, AbstractMessageService<UnspayDeductRequest> abstractMessageService) {
        short messageType = unspayDeductRequest.getMessageType();
        Integer orderId = unspayDeductRequest.getOrderId();

        DeductServiceImpl deductServiceImpl = ((DeductServiceImpl) abstractMessageService);
        DeductMapper deductMapper = deductServiceImpl.getDeductMapper();
        UnspayDeduct unspayDeduct = deductMapper.selectByPrimaryKey(orderId);

        if(messageType== UnspayDeductMessageType.DEDUCT.getCode()){
            unspayDeduct.setSendDate(new Date());
        }

        logger.info("准备发送代扣【"+UnspayDeductMessageType.getUnspayDeductMessageTypeByCode(messageType).getDesc()+"】请求,请求代扣对象为："+ JSON.toJSONString(unspayDeduct));

        UnspayDeductResponse unspayDeductResponse = null;
        if(messageType== UnspayDeductMessageType.DEDUCT.getCode()){
            unspayDeductResponse = UnspayUtil.deduct(unspayDeduct);
        }else if(messageType== UnspayDeductMessageType.QUERYORDER.getCode()){
            unspayDeductResponse = UnspayUtil.queryDeductOrderStatus(unspayDeduct);
        }
        if(unspayDeductResponse !=null){
            unspayDeductRequest.setMessageChannelResult(200);
            String resultCode = unspayDeductResponse.getResultCode();
            String resultMessage = unspayDeductResponse.getResultMessage();
            unspayDeductRequest.setResultCode(resultCode);
            unspayDeductRequest.setResultMessage(resultMessage);
            if(!UnspayStatus.SUCCESS.getCode().equals(resultCode)){
                unspayDeduct.setDeductResult(resultCode);
                unspayDeduct.setDesc(resultMessage);
            }else{
                if(messageType== UnspayDeductMessageType.DEDUCT.getCode()){
                    unspayDeduct.setDeductResult(UnspayStatus.HANDDING.getCode());
                    unspayDeduct.setDesc("发送成功");
                }
                if(messageType== UnspayDeductMessageType.QUERYORDER.getCode()){
                    unspayDeduct.setDeductResult(unspayDeductResponse.getDeductResult());
                    unspayDeduct.setDesc(unspayDeductResponse.getDesc());
                }
            }
        }else{
            unspayDeductRequest.setMessageChannelResult(500);
        }
        logger.info("接收代扣【"+UnspayDeductMessageType.getUnspayDeductMessageTypeByCode(messageType).getDesc()+"】响应,并在数据库更新结果，接收响应的对象为："+ JSON.toJSONString(unspayDeduct));
        if(messageType== UnspayDeductMessageType.DEDUCT.getCode()){
            deductMapper.updateSendResult(unspayDeduct);
        }
        if(messageType== UnspayDeductMessageType.QUERYORDER.getCode()||(messageType== UnspayDeductMessageType.DEDUCT.getCode()&&!"0000".equals(unspayDeductResponse.getResultCode()))){
            unspayDeduct.setResponseDate(new Date());
            deductMapper.updateCallbackResult(unspayDeduct);
            deductServiceImpl.callback(unspayDeduct);//回调其它系统，通知代扣结果
        }
        return unspayDeductResponse;
    }
}
