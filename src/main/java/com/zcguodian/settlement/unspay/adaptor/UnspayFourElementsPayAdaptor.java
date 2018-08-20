package com.zcguodian.settlement.unspay.adaptor;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.adaptor.AbstractAdapter;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.zcguodian.settlement.unspay.consts.UnspayZCGDStatus;
import com.zcguodian.settlement.unspay.mapper.UnspayFourElementsPayMapper;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPay;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayMessageType;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayRequest;
import com.zcguodian.settlement.unspay.model.UnspayFourElementsPayResponse;
import com.zcguodian.settlement.unspay.service.impl.FourElementsPayServiceImpl;
import com.zcguodian.settlement.unspay.utils.UnspayZCGDUtil;

import org.apache.log4j.Logger;

import java.util.Date;

public class UnspayFourElementsPayAdaptor extends AbstractAdapter<UnspayFourElementsPayRequest>{

    private static Logger logger = Logger.getLogger(UnspayFourElementsPayAdaptor.class);

	@Override
	public MessageResponse send(UnspayFourElementsPayRequest unspayPayRequest, AbstractMessageService<UnspayFourElementsPayRequest> abstractMessageService) {
		short messageType = unspayPayRequest.getMessageType();
        Integer orderId = unspayPayRequest.getOrderId();

        UnspayFourElementsPayMapper payMapper = ((FourElementsPayServiceImpl) abstractMessageService).getUnspayFourElementsPayMapper();
        UnspayFourElementsPay unspayPay = payMapper.selectByPrimaryKey(orderId);

        unspayPay.setSendDate(new Date());

        logger.info("准备发送代付【"+ UnspayFourElementsPayMessageType.getUnspayPayMessageTypeByCode(messageType).getDesc()+"】请求,请求代扣对象为："+ JSON.toJSONString(unspayPay));

        UnspayFourElementsPayResponse unspayPayResponse = null;
        if(messageType== UnspayFourElementsPayMessageType.ZCGD_PAY.getCode()){
        	unspayPayResponse = UnspayZCGDUtil.pay(unspayPay);
        }else if(messageType== UnspayFourElementsPayMessageType.ZCGD_QUERYORDER.getCode()){
        	unspayPayResponse = UnspayZCGDUtil.queryPayOrderStatus(unspayPay);
        }
        if(unspayPayResponse !=null){
        	logger.info("接收代付【"+UnspayFourElementsPayMessageType.getUnspayPayMessageTypeByCode(messageType).getDesc()+"】响应，银生宝响应结果为：" + JSON.toJSONString(unspayPayResponse));
        	unspayPayRequest.setMessageChannelResult(200);
            String resultCode = unspayPayResponse.getResultCode();
            String resultMessage = unspayPayResponse.getResultMessage();
            unspayPayRequest.setResultCode(resultCode);
            unspayPayRequest.setResultMessage(resultMessage);
            if(!UnspayZCGDStatus.ZCGD_SUCCESS.getCode().equals(resultCode)){
                unspayPay.setPayResult(resultCode);
                unspayPay.setDesc(resultMessage);
            }else{
                if(messageType== UnspayFourElementsPayMessageType.ZCGD_PAY.getCode()){
                    unspayPay.setPayResult(UnspayZCGDStatus.ZCGD_HANDDING.getCode());
                    unspayPay.setDesc("发送成功！");
                }
                if(messageType== UnspayFourElementsPayMessageType.ZCGD_QUERYORDER.getCode()){
                    unspayPay.setPayResult(String.valueOf(unspayPayResponse.getStatus()));
                    unspayPay.setDesc(unspayPayResponse.getDesc());
                }

            }
        }else{
        	unspayPayRequest.setMessageChannelResult(500);
        }

        logger.info("接收代付【"+UnspayFourElementsPayMessageType.getUnspayPayMessageTypeByCode(messageType).getDesc()+"】响应,并在数据库更新结果，接收响应的对象为："+ JSON.toJSONString(unspayPay));

        payMapper.updateSendResult(unspayPay);
        return unspayPayResponse;
	}

}
