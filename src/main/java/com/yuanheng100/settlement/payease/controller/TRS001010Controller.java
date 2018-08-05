package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Req;
import com.yuanheng100.settlement.payease.model.trs001010.TRS001010Resp;
import com.yuanheng100.settlement.payease.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by jlqian on 2016/12/26.
 */
@RestController
@RequestMapping("/payease/trs001010")
public class TRS001010Controller
{
    @Autowired
    private IQueryService payeaseQueryService;

    @RequestMapping("/queryAccount")
    public TRS001010Resp[] queryAccount(@RequestParam("user") String user){
        TRS001010Resp[] trs001010Resp = new TRS001010Resp[2];
        trs001010Resp[0] = queryInvestAccount(user);
        trs001010Resp[1] = queryLiabilityAccount(user);
        return trs001010Resp;
    }


    @RequestMapping("/queryInvestAccount")
    public TRS001010Resp queryInvestAccount(@RequestParam("user") String user){
        TRS001010Req trs001010Req = new TRS001010Req();
        trs001010Req.setUser(user);
        return payeaseQueryService.queryInvestAccount(trs001010Req);
    }

    @RequestMapping("/queryLiabilityAccount")
    public TRS001010Resp queryLiabilityAccount(@RequestParam("user") String user){
        TRS001010Req trs001010Req = new TRS001010Req();
        trs001010Req.setUser(user);
        return payeaseQueryService.queryLiabilityAccount(trs001010Req);
    }


}
