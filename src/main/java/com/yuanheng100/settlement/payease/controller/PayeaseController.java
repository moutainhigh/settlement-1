package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.conts.PageVo;
import com.yuanheng100.settlement.payease.model.other.IdcardVerify;
import com.yuanheng100.settlement.payease.model.recharge.RechargeBackResp;
import com.yuanheng100.settlement.payease.service.IIdCardVerifyService;
import com.yuanheng100.settlement.payease.service.IRechargeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实名认证
 * Created by qianjl on 2016-6-24.
 */
@Controller
@RequestMapping("/payease")
public class PayeaseController
{

    private static Logger logger = Logger.getLogger(PayeaseController.class);

    @Autowired
    private IIdCardVerifyService idCardVerifyService;

    /**
     * 添加实名认证
     *
     * @param name
     * @param idcardNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/verifyIdcard")
    public boolean verifyIdcard(@RequestParam String name, @RequestParam Long idcardNo)
    {
        return idCardVerifyService.verifyIdcard(name, String.valueOf(idcardNo));
    }


    /**
     * 实名认证页面
     *
     * @param pageVo
     * @return
     */
    @RequestMapping("/idCardVerify")
    public String idCardVerify(PageVo<IdcardVerify> pageVo)
    {
        Map<String, Object> param = new HashMap<String, Object>();
        if (pageVo.getCurrentPage() <= 0) pageVo.setCurrentPage(1);
        if (pageVo.getPageSize() <= 0) pageVo.setPageSize(10);
        int offset = pageVo.getCurrentPage() - 1;
        param.put("b_page", offset * pageVo.getPageSize());
        param.put("e_page", pageVo.getPageSize());
        List<IdcardVerify> list = idCardVerifyService.getIdCardVerifyList(param);
        pageVo.setVoList(list);
        if (pageVo.getRecordCount() <= 0) pageVo.setRecordCount(idCardVerifyService.getIdCardVerifyListCount(param));
        return "payease/idcard_verify";
    }

    /**
     * 实名认证列表查询，分页
     *
     * @param currentPage
     * @param name
     * @param idcardNo
     * @param date1
     * @param date2
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("/idCardVerifyPage")
    public PageVo<IdcardVerify> idCardVerifyPage(Integer currentPage, String name, Long idcardNo, String date1, String date2, Integer status)
    {
        PageVo<IdcardVerify> pageVo = new PageVo<IdcardVerify>();
        Map<String, Object> param = new HashMap<String, Object>();
        if (name != null) param.put("name", name);
        if (idcardNo != null) param.put("idcardNo", idcardNo);
        if (date1 != null) param.put("date1", date1);
        if (date2 != null) param.put("date2", date2);
        if (status != null) param.put("status", status);
        pageVo.setRecordCount(idCardVerifyService.getIdCardVerifyListCount(param));
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

        List<IdcardVerify> list = idCardVerifyService.getIdCardVerifyList(param);
        pageVo.setVoList(list);

        return pageVo;
    }

}
