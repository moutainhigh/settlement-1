package com.yuanheng100.settlement.unspay.adaptor;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.adaptor.AbstractAdapter;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.unspay.consts.UnspayStatus;
import com.yuanheng100.settlement.unspay.mapper.PayMapper;
import com.yuanheng100.settlement.unspay.model.*;
import com.yuanheng100.settlement.unspay.service.impl.PayServiceImpl;
import com.yuanheng100.settlement.unspay.utils.UnspayUtil;
import org.apache.log4j.Logger;

import java.util.Date;

public class PayAdaptor extends AbstractAdapter<UnspayPayRequest>{

    private static Logger logger = Logger.getLogger(PayAdaptor.class);

	@Override
	public MessageResponse send(UnspayPayRequest unspayPayRequest, AbstractMessageService<UnspayPayRequest> abstractMessageService) {
		short messageType = unspayPayRequest.getMessageType();
        Integer orderId = unspayPayRequest.getOrderId();

        PayMapper payMapper = ((PayServiceImpl) abstractMessageService).getPayMapper();
        UnspayPay unspayPay = payMapper.selectByPrimaryKey(orderId);

        unspayPay.setSendDate(new Date());

        logger.info("准备发送代付【"+ UnspayPayMessageType.getUnspayPayMessageTypeByCode(messageType).getDesc()+"】请求,请求代扣对象为："+ JSON.toJSONString(unspayPay));

        UnspayPayResponse unspayPayResponse = null;
        if(messageType== UnspayPayMessageType.PAY.getCode()){
        	unspayPayResponse = UnspayUtil.pay(unspayPay);
        }else if(messageType== UnspayPayMessageType.QUERYORDER.getCode()){
        	unspayPayResponse = UnspayUtil.queryPayOrderStatus(unspayPay);
        }
        if(unspayPayResponse !=null){
        	unspayPayRequest.setMessageChannelResult(200);
            String resultCode = unspayPayResponse.getResultCode();
            String resultMessage = unspayPayResponse.getResultMessage();
            unspayPayRequest.setResultCode(resultCode);
            unspayPayRequest.setResultMessage(resultMessage);
            if(!UnspayStatus.SUCCESS.getCode().equals(resultCode)){
                unspayPay.setPayResult(resultCode);
                unspayPay.setDesc(resultMessage);
            }else{
                if(messageType== UnspayPayMessageType.PAY.getCode()){
                    unspayPay.setPayResult(UnspayStatus.HANDDING.getCode());
                    unspayPay.setDesc("发送成功！");
                }
                if(messageType== UnspayPayMessageType.QUERYORDER.getCode()){
                    unspayPay.setPayResult(unspayPayResponse.getDeductResult());
                    unspayPay.setDesc(unspayPayResponse.getDesc());
                }

            }
        }else{
        	unspayPayRequest.setMessageChannelResult(500);
        }

        logger.info("接收代付【"+UnspayPayMessageType.getUnspayPayMessageTypeByCode(messageType).getDesc()+"】响应,并在数据库更新结果，接收响应的对象为："+ JSON.toJSONString(unspayPay));

        payMapper.updateSendResult(unspayPay);
        return unspayPayResponse;
	}

}
