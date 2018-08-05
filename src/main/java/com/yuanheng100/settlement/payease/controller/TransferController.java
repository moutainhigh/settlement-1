package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.conts.PageVo;
import com.yuanheng100.settlement.payease.consts.ReturnCode;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.IQueryService;
import com.yuanheng100.settlement.payease.service.ISequenceIdService;
import com.yuanheng100.settlement.payease.service.ITransferService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转账处理
 * <p>
 * Created by wangguangshuo on 2017/1/6.
 */
@Controller
@RequestMapping("/payease")
public class TransferController
{
    private static Logger logger = Logger.getLogger(TransferController.class);

    @Autowired
    private ITransferService transferService;

    @Autowired
    private ISequenceIdService payeaseSequenceIdService;

    @Autowired
    private IQueryService queryService;


    /**
     * 转账
     *
     * @param transfer_outRegName 转出方注册名
     * @param transfer_inRegName  转入方注册名
     * @param transfer_amount     转账金额
     * @param type                1 投资转投资  2 投资转负债 3 负债转投资
     * @return
     */
    @ResponseBody
    @RequestMapping("/transferAmount")
    public boolean transferAmount(@RequestParam String transfer_outRegName, @RequestParam String transfer_inRegName, @RequestParam String transfer_amount,
                                  @RequestParam String type)
    {
        TRS001007Req trs001007Req = new TRS001007Req();
        trs001007Req.setSerlNum(String.valueOf(payeaseSequenceIdService.getSequenceId()));
        trs001007Req.setTransferOutUser(transfer_outRegName);
        trs001007Req.setTransferInUser(transfer_inRegName);
        trs001007Req.setTransferAmount(String.valueOf(new BigDecimal(transfer_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        trs001007Req.setTransferOutUserFee("0.00");
        trs001007Req.setTransferInUserFee("0.00");

        return transerResult(trs001007Req, type);
    }

    private boolean transerResult(TRS001007Req trs001007Req, String type)
    {
        TRS001007Resp trs001007Resp = null;
        try
        {
            if ("1".equals(type))
            {
                trs001007Resp = transferService.transferBetweenInvestAccount(trs001007Req);
            } else if ("2".equals(type))
            {
                trs001007Resp = transferService.transferInvestToLiability(trs001007Req);
            } else if ("3".equals(type))
            {
                trs001007Resp = transferService.transferLiabilityToInvest(trs001007Req);
            } else
            {
                logger.info("请求参数不合法（type：" + type + "）");
                return false;
            }
            if (trs001007Resp != null && ReturnCode.TRANS_SUCCESS.equals(trs001007Resp.getReturnCode()))
            {
                return true;
            }
            return false;
        } catch (IllegalPlatformAugumentException e)
        {
            logger.error("转账出错", e);
        }
        return false;
    }


    /**
     * 获取转账人姓名信息
     *
     * @param regName
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTransferName")
    public Map<String, Object> getTransferName(@RequestParam String regName, @RequestParam String type)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("name", transferService.getTransferName(regName));
        TRS001010Req req = new TRS001010Req();
        TRS001010Resp trs001010Resp = null;
        if ("1".equals(type) || "0".equals(type))
        {
            /*投资账户*/
            req.setUser(regName);
            trs001010Resp = queryService.queryInvestAccount(req);
            result.put("balance", trs001010Resp.getAvailableBalance());
        } else if ("2".equals(type) || "3".equals(type))
        {
            /*负债账户*/
            req.setUser(regName);
            trs001010Resp = queryService.queryLiabilityAccount(req);
        }
        if (trs001010Resp == null)
        {
            logger.error("查询账户余额返回null，或者type参数错误，当前type：" + type);
            return result;
        }
        result.put("balance", trs001010Resp.getAvailableBalance());
        return result;
    }


    /**
     * 转账列表、分页
     *
     * @param currentPage
     * @param outRegName
     * @param outRelName
     * @param inRegName
     * @param inRelName
     * @param date1
     * @param date2
     * @param returnCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/transferAccountPage")
    public PageVo transferAccountPage(Integer currentPage, String outRegName, String outRelName, String inRegName, String inRelName,
                                      String date1, String date2, String returnCode, @RequestParam(value = "plateform", required = false, defaultValue = "0") Integer plateform)
    {
        PageVo<Map<String, Object>> pageVo = new PageVo<Map<String, Object>>();
        Map<String, Object> param = new HashMap<String, Object>();
        if (outRegName != null) param.put("outRegName", outRegName);
        if (outRelName != null) param.put("outRelName", outRelName);
        if (date1 != null) param.put("date1", date1);
        if (date2 != null) param.put("date2", date2);
        if (inRegName != null) param.put("inRegName", inRegName);
        if (inRelName != null) param.put("inRelName", inRelName);
        if (returnCode != null) param.put("returnCode", returnCode);
        param.put("plateform", plateform);
        pageVo.setRecordCount(transferService.getTransferRecordsCount(param));
        if (currentPage != null)
        {
            pageVo.setCurrentPage(currentPage);
        } else
        {
            pageVo.setCurrentPage(1);
        }
        pageVo.setPageSize(10);
        int offset = pageVo.getCurrentPage() - 1;
        param.put("b_page", offset * pageVo.getPageSize());
        param.put("e_page", pageVo.getPageSize());

        List<Map<String, Object>> list = transferService.getTransferRecords(param);
        pageVo.setVoList(list);

        return pageVo;
    }


    /**
     * 转账页面
     *
     * @param pageVo
     * @return
     */
    @RequestMapping("/transfer")
    public String transfer(PageVo pageVo, @RequestParam(value = "plateform", required = false, defaultValue = "0") Integer plateform)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        if (pageVo.getCurrentPage() <= 0) pageVo.setCurrentPage(1);
        if (pageVo.getPageSize() <= 0) pageVo.setPageSize(10);
        int offset = pageVo.getCurrentPage() - 1;
        param.put("b_page", offset * pageVo.getPageSize());
        param.put("e_page", pageVo.getPageSize());
        param.put("plateform", plateform);
        List<Map<String, Object>> list = transferService.getTransferRecords(param);
        pageVo.setVoList(list);
        if (pageVo.getRecordCount() <= 0) pageVo.setRecordCount(transferService.getTransferRecordsCount(param));
        return "payease/transfer_account";
    }


}
