package com.yuanheng100.settlement.unspay.service.impl;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.common.mapper.SysStaffMapper;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.unspay.consts.UnspayStatus;
import com.yuanheng100.settlement.unspay.mapper.SubContractMapper;
import com.yuanheng100.settlement.unspay.model.UnspaySubContract;
import com.yuanheng100.settlement.unspay.model.UnspaySubContractMessageType;
import com.yuanheng100.settlement.unspay.model.UnspaySubContractRequest;
import com.yuanheng100.settlement.unspay.model.UnspaySubContractResponse;
import com.yuanheng100.settlement.unspay.service.ISubContractService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianjl on 2016-6-21.
 */
public class SubContractServiceImpl extends AbstractMessageService<UnspaySubContractRequest> implements ISubContractService {

    private static Logger logger = Logger.getLogger(SubContractServiceImpl.class);
    @Autowired
    private SubContractMapper subContractMapper;
    @Autowired
    private SysStaffMapper sysStaffMapper;

    public SubContractMapper getSubContractMapper() {
        return subContractMapper;
    }

    @Override
    public void getUploadListPage(HashMap<String, Object> searchConditions, Page<Map<String,Object>> page) {

        //查询总计数
        Long totalCount = subContractMapper.selectSubContractUploadCount(searchConditions);
        if(totalCount==null){
            totalCount = (long)0;
        }
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex",(pageNo-1)*pageSize);
        searchConditions.put("endIndex",pageNo*pageSize);

        List<Map<String,Object>> list = subContractMapper.selectSubContractUploadList(searchConditions);
        //查询其它数据
        for (Map<String,Object> subContract: list) {
            //操作人
            String staffName = sysStaffMapper.selectStaffNameById(Integer.parseInt(subContract.get("operator").toString()));
            subContract.put("staffName",staffName);
            
            List<UnspaySubContract> unspaySubContracts = subContractMapper.selectSubContractListByFileName(subContract.get("filename").toString());
            //状态
            //1.成功
            int success = 0;
            //2.失败
            int failur = 0;
            //3.处理中
            int process = 0;

            for (UnspaySubContract sub: unspaySubContracts) {
                String signStatus = sub.getSignStatus();
                if(signStatus==null){
                    process ++;
                }else if(signStatus.equals(UnspayStatus.SUCCESS.getCode())){
                    success ++;
                }else{
                    failur ++;
                }
            }
            subContract.put("count", unspaySubContracts.size());
            subContract.put("success",success);
            subContract.put("failur",failur);
            subContract.put("process",process);
        }
        page.setResult(list);
    }

    @Override
    public List<UnspaySubContract> uploadFileDetail(String filename) {
        return subContractMapper.selectSubContractListByFileName(filename);
    }

    @Override
    public boolean asyncSign(UnspaySubContract unspaySubContract) {
        boolean exist = subContractMapper.isExist(unspaySubContract)>=1;
        if(!exist){
            subContractMapper.insertSelective(unspaySubContract);
            UnspaySubContractRequest unspaySubContractRequest = new UnspaySubContractRequest();
            unspaySubContractRequest.setTableId(unspaySubContract.getId());
            unspaySubContractRequest.setMessageType(UnspaySubContractMessageType.SIGN.getCode());
            logger.info("新增子协议并加入异步发送："+ JSON.toJSONString(unspaySubContract));
            return enqueue(unspaySubContractRequest);
        }
        return false;
    }

    @Override
    public UnspaySubContractResponse syncSign(UnspaySubContract unspaySubContract) {
        UnspaySubContract existUnspaySubContract = subContractMapper.selectSubContractByLoanApplyId(unspaySubContract.getLoanApplyId());
        if(existUnspaySubContract==null){
            subContractMapper.insertSelective(unspaySubContract);
            UnspaySubContractRequest unspaySubContractRequest = new UnspaySubContractRequest();
            unspaySubContractRequest.setTableId(unspaySubContract.getId());
            unspaySubContractRequest.setMessageType(UnspaySubContractMessageType.SIGN.getCode());
            logger.info("新增子协议并加入同步发送："+ JSON.toJSONString(unspaySubContract));
            return (UnspaySubContractResponse)syncSend(unspaySubContractRequest);
        }else if(existUnspaySubContract.getSubContractId()==null){
            UnspaySubContractRequest unspaySubContractRequest = new UnspaySubContractRequest();
            unspaySubContractRequest.setTableId(existUnspaySubContract.getId());
            unspaySubContractRequest.setMessageType(UnspaySubContractMessageType.SIGN.getCode());
            logger.info("新增子协议并加入同步发送："+ JSON.toJSONString(unspaySubContract));
            return (UnspaySubContractResponse)syncSend(unspaySubContractRequest);
        }else{
            UnspaySubContractResponse unspaySubContractResponse = new UnspaySubContractResponse();
            unspaySubContractResponse.setResultCode(existUnspaySubContract.getSignStatus());
            unspaySubContractResponse.setResultMessage(existUnspaySubContract.getSignMsg());
            unspaySubContractResponse.setSubContractId(existUnspaySubContract.getSubContractId());
            return unspaySubContractResponse;
        }
    }

    public String queryRemote(UnspaySubContract unspaySubContract) {
        UnspaySubContractRequest unspaySubContractRequest = new UnspaySubContractRequest();
        unspaySubContractRequest.setTableId(unspaySubContract.getId());
        unspaySubContractRequest.setMessageType(UnspaySubContractMessageType.QUERY.getCode());
        logger.info("远程子协议编号查询同步发送："+ JSON.toJSONString(unspaySubContract));
        UnspaySubContractResponse messageResponse = (UnspaySubContractResponse) syncSend(unspaySubContractRequest);
        return messageResponse.getSubContractId();
    }

    @Override
    public String queryLocal(Long loanApplyId) {
        return subContractMapper.selectSubContractIdByLoanApplyId(loanApplyId);
    }

    @Override
    public UnspaySubContract querySubContractLocal(Long loanApplyId) {
        return subContractMapper.selectSubContractByLoanApplyId(loanApplyId);
    }

    public boolean edit(UnspaySubContract unspaySubContract) {
        //更新子协议
        subContractMapper.updateByLoanApplyIdSelective(unspaySubContract);
        UnspaySubContractRequest unspaySubContractRequest = new UnspaySubContractRequest();
        unspaySubContractRequest.setTableId(unspaySubContract.getId());
        unspaySubContractRequest.setMessageType(UnspaySubContractMessageType.SIGN.getCode());
        logger.info("子协议修改并加入异步发送："+ JSON.toJSONString(unspaySubContract));
        return enqueue(unspaySubContractRequest);
    }

    public boolean extension(UnspaySubContract unspaySubContract) {
        subContractMapper.extension(unspaySubContract);
        UnspaySubContractRequest unspaySubContractRequest = new UnspaySubContractRequest();
        unspaySubContractRequest.setTableId(unspaySubContract.getId());
        unspaySubContractRequest.setMessageType(UnspaySubContractMessageType.EXTENSION.getCode());
        logger.info("子协议延期并加入异步发送："+ JSON.toJSONString(unspaySubContract));
        return enqueue(unspaySubContractRequest);
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<UnspaySubContract> page) {
        Long totalCount = subContractMapper.selectSubContractListCount(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex",(pageNo-1)*pageSize);
        searchConditions.put("endIndex",pageNo*pageSize);

        List<UnspaySubContract> list = subContractMapper.selectSubContractList(searchConditions);
        page.setResult(list);
    }
}
