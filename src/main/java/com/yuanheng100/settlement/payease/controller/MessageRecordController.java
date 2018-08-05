package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.settlement.common.conts.PageVo;
import com.yuanheng100.settlement.common.service.ISysBankService;
import com.yuanheng100.settlement.payease.consts.ReturnCode;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Resp;
import com.yuanheng100.settlement.payease.model.trs001002.TRS001002Resp;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Resp;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Resp;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.IMessageRecordService;
import com.yuanheng100.settlement.payease.service.IQueryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangguangshuo on 2017/1/4.
 */
@Controller
@RequestMapping("/record")
public class MessageRecordController
{

    private static Logger logger = Logger.getLogger(MessageRecordController.class);

    @Autowired
    private ISysBankService sysBankService;

    @Autowired
    private IMessageRecordService recordService;

    @Autowired
    private IQueryService payeaseQueryService;


    /**
     * 批量查询
     *
     * @param currentPage
     * @param bRegName
     * @param eRegName
     * @return
     */
    @ResponseBody
    @RequestMapping("/batchQueryPage")
    public PageVo<SYN001001Resp> batchQueryPage(Integer currentPage, String bRegName, String eRegName)
    {
        PageVo<SYN001001Resp> pageVo = new PageVo<SYN001001Resp>();
        if (pageVo.getCurrentPage() < 0) pageVo.setCurrentPage(1);
        Map<String, Object> param = new HashMap<String, Object>();
        if (bRegName != null && !"".equals(bRegName)) param.put("bRegName", bRegName);
        if (eRegName != null && !"".equals(eRegName)) param.put("eRegName", eRegName);
        pageVo.setRecordCount(recordService.getBatchQueryPageCount(param));
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

        List<SYN001001Resp> list = recordService.getBatchQueryPage(param);
        TRS001010Req req = null;
        excuteList(list, req);
        pageVo.setVoList(list);
        return pageVo;
    }

    private void excuteList(List<SYN001001Resp> list, TRS001010Req req)
    {
        for (SYN001001Resp resp : list)
        {
            resp.setAccBank(sysBankService.getBankByCode(resp.getAccBankCode()).getShortName());
            if (resp.getReturnCode() == null)
            {
                resp.setStatus("请求失败");
            } else if (ReturnCode.TRANS_SUCCESS.equals(resp.getReturnCode()))
            {
                resp.setStatus("开户");
            } else if (ReturnCode.BIND_CARD_SUCCESS.equals(resp.getReturnCode()))
            {
                resp.setStatus("开户+银行卡");
            } else if (ReturnCode.BIND_CARD_FAILURE.equals(resp.getReturnCode()))
            {
                resp.setStatus("开户[" + resp.getReturnMsg() + "]");
            } else
            {
                resp.setStatus("未开户[" + resp.getReturnMsg() + "]");
            }
            req = new TRS001010Req();
            req.setUser(resp.getUser());
            TRS001010Resp trs001010Resp = payeaseQueryService.queryInvestAccount(req);
            if (trs001010Resp != null) resp.setAccount1(trs001010Resp.getAvailableBalance());
            TRS001010Resp trs001010Resp1 = payeaseQueryService.queryLiabilityAccount(req);
            if (trs001010Resp1 != null) resp.setAccount2(trs001010Resp1.getAvailableBalance());
        }
    }


    /**
     * 批量查询
     *
     * @return
     */
    @RequestMapping("/batchQuery")
    public String batchQuery(PageVo<SYN001001Resp> pageVo)
    {
//        Map<String,Object> param = new HashMap<String, Object>();
//        if (pageVo.getCurrentPage() <= 0) pageVo.setCurrentPage(1);
//        if (pageVo.getPageSize() <= 0) pageVo.setPageSize(10);
//        int offset = pageVo.getCurrentPage() - 1;
//        param.put("b_page",offset*pageVo.getPageSize());
//        param.put("e_page",pageVo.getPageSize());
//        List<SYN001001Resp> list = recordService.getRecords001(param);
//        TRS001010Req req = null;
//        excuteList(list,req);
//        pageVo.setVoList(list);
//        if (pageVo.getRecordCount() <= 0 ) pageVo.setRecordCount(recordService.getRecords001Count(param));
        return "payease/batch_query";
    }


    /**
     * 提现报文列表
     *
     * @param currentPage
     * @return
     */
    @ResponseBody
    @RequestMapping("/records006Page")
    public PageVo<TRS001006Resp> records006Page(Integer currentPage)
    {
        PageVo<TRS001006Resp> pageVo = new PageVo<TRS001006Resp>();
        logger.info(pageVo.getCurrentPage());
        if (pageVo.getCurrentPage() < 0) pageVo.setCurrentPage(1);
        Map<String, Object> param = new HashMap<String, Object>();
        pageVo.setRecordCount(recordService.getRecords006Count(param));
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

        List<TRS001006Resp> list = recordService.getRecords006(param);
        for (TRS001006Resp resp : list)
        {
            resp.setAccBank(sysBankService.getBankByCode(resp.getAccBankCode()).getShortName());
        }
        pageVo.setVoList(list);
        return pageVo;
    }


    /**
     * 代扣报文
     *
     * @param currentPage
     * @return
     */
    @ResponseBody
    @RequestMapping("/records008Page")
    public PageVo<TRS001008Resp> records008Page(Integer currentPage)
    {
        PageVo<TRS001008Resp> pageVo = new PageVo<TRS001008Resp>();
        logger.info(pageVo.getCurrentPage());
        if (pageVo.getCurrentPage() < 0) pageVo.setCurrentPage(1);
        Map<String, Object> param = new HashMap<String, Object>();
        pageVo.setRecordCount(recordService.getRecords008Count(param));
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

        List<TRS001008Resp> list = recordService.getRecords008(param);
        for (TRS001008Resp resp : list)
        {
            resp.setAccBank(sysBankService.getBankByCode(resp.getAccBankCode()).getShortName());
        }
        pageVo.setVoList(list);
        return pageVo;
    }


    /**
     * 投标报文列表
     *
     * @param currentPage
     * @return
     */
    @ResponseBody
    @RequestMapping("/records002Page")
    public PageVo<TRS001002Resp> records002Page(Integer currentPage)
    {
        PageVo<TRS001002Resp> pageVo = new PageVo<TRS001002Resp>();
        logger.info(pageVo.getCurrentPage());
        if (pageVo.getCurrentPage() < 0) pageVo.setCurrentPage(1);
        Map<String, Object> param = new HashMap<String, Object>();
        pageVo.setRecordCount(recordService.getRecords002Count(param));
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

        List<TRS001002Resp> list = recordService.getRecords002(param);
        for (TRS001002Resp resp : list)
        {
            //            resp.setAccBank(sysBankService.getBankByCode(resp.getAccBankCode()).getShortName());
        }
        pageVo.setVoList(list);
        return pageVo;
    }


    /**
     * 账户报文列表
     *
     * @param currentPage
     * @return
     */
    @ResponseBody
    @RequestMapping("/records001Page")
    public PageVo<SYN001001Resp> records001Page(Integer currentPage)
    {
        PageVo<SYN001001Resp> pageVo = new PageVo<SYN001001Resp>();
        logger.info(pageVo.getCurrentPage());
        if (pageVo.getCurrentPage() < 0) pageVo.setCurrentPage(1);
        Map<String, Object> param = new HashMap<String, Object>();
        pageVo.setRecordCount(recordService.getRecords001Count(param));
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

        List<SYN001001Resp> list = recordService.getRecords001(param);
        for (SYN001001Resp resp : list)
        {
            resp.setAccBank(sysBankService.getBankByCode(resp.getAccBankCode()).getShortName());
        }
        pageVo.setVoList(list);
        return pageVo;
    }


    /**
     * 报文管理页面
     *
     * @param pageVo
     * @return
     */
    @RequestMapping("/messageRecord")
    public String messageRecord(PageVo<SYN001001Resp> pageVo)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        if (pageVo.getCurrentPage() <= 0) pageVo.setCurrentPage(1);
        if (pageVo.getPageSize() <= 0) pageVo.setPageSize(10);
        int offset = pageVo.getCurrentPage() - 1;
        param.put("b_page", offset * pageVo.getPageSize());
        param.put("e_page", pageVo.getPageSize());
        List<SYN001001Resp> list = recordService.getRecords001(param);
        for (SYN001001Resp resp : list)
        {
            if (resp.getAccBankCode() != null)
            {
                resp.setAccBank(sysBankService.getBankByCode(resp.getAccBankCode()).getShortName());
            }
        }
        pageVo.setVoList(list);
        if (pageVo.getRecordCount() <= 0) pageVo.setRecordCount(recordService.getRecords001Count(param));
        return "payease/message_record";
    }

}
