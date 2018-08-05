package com.yuanheng100.settlement.unspay.adaptor;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.adaptor.AbstractAdapter;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.unspay.mapper.SubContractMapper;
import com.yuanheng100.settlement.unspay.model.*;
import com.yuanheng100.settlement.unspay.service.impl.SubContractServiceImpl;
import com.yuanheng100.settlement.unspay.utils.UnspayUtil;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by qianjl on 2016-6-24.
 */
public class SubContractAdaptor extends AbstractAdapter<UnspaySubContractRequest> {

    private static Logger logger = Logger.getLogger(SubContractAdaptor.class);

    @Override
    public UnspaySubContractResponse send(UnspaySubContractRequest unspaySubContractRequest, AbstractMessageService<UnspaySubContractRequest> abstractMessageService) {
        short messageType = unspaySubContractRequest.getMessageType();
        Integer id = unspaySubContractRequest.getTableId();

        SubContractMapper subContractMapper = ((SubContractServiceImpl) abstractMessageService).getSubContractMapper();
        UnspaySubContract unspaySubContract = subContractMapper.selectByPrimaryKey(id);
        unspaySubContract.setSendDate(new Date());

        logger.info("准备发送子协议【"+ UnspaySubContractMessageType.getUnspaySubContractMessageTypeByCode(messageType).getDesc()+"】请求,请求代扣对象为："+ JSON.toJSONString(unspaySubContract));

        UnspaySubContractResponse unspaySubContractResponse = null;
        try {
            if(messageType== UnspaySubContractMessageType.SIGN.getCode()){
                unspaySubContractResponse = UnspayUtil.signSimpleSubContract(unspaySubContract);
                unspaySubContract.setSubContractId(unspaySubContractResponse.getSubContractId());
                unspaySubContract.setSignStatus(unspaySubContractResponse.getResultCode());
                unspaySubContract.setSignMsg(unspaySubContractResponse.getResultMessage());
            }else if(messageType== UnspaySubContractMessageType.QUERY.getCode()){
                unspaySubContractResponse = UnspayUtil.querySubContractId(unspaySubContract);
            }else if(messageType== UnspaySubContractMessageType.EXTENSION.getCode()){
                unspaySubContractResponse = UnspayUtil.subConstractExtension(unspaySubContract);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        logger.info("接收子协议【"+UnspaySubContractMessageType.getUnspaySubContractMessageTypeByCode(messageType).getDesc()+"】响应,并在数据库更新结果，接收响应的对象为："+ JSON.toJSONString(unspaySubContract));

        subContractMapper.savaSignSubContractResult(unspaySubContract);
        if(unspaySubContractResponse !=null){
            unspaySubContractRequest.setResultCode(unspaySubContractResponse.getResultCode());
            unspaySubContractRequest.setResultMessage(unspaySubContractResponse.getResultMessage());
            unspaySubContractRequest.setMessageChannelResult(200);
        }else{
            unspaySubContractRequest.setMessageChannelResult(500);
        }
        return unspaySubContractResponse;
    }
}
