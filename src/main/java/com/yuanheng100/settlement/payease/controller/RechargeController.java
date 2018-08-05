package com.yuanheng100.settlement.payease.controller;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.model.recharge.RechargeBackResp;
import com.yuanheng100.settlement.payease.service.IRechargeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jlqian on 2017/2/14.
 */
@Controller
@RequestMapping("/payease/recharge")
public class RechargeController
{

    private static Logger logger = Logger.getLogger(RechargeController.class);

    @Autowired
    private IRechargeService rechargeService;

    @ResponseBody
    @RequestMapping("/rechargeCallBack")
    public String rechargeResult(HttpServletRequest request)
    {
        String result = "error";
        String queryStr = request.getQueryString();
        logger.info("【异步处理充值结果】        参数信息：" + queryStr);
        String[] querys = queryStr.split("&");
        Map<String, String> queryMap = new HashMap<String, String>();
        try
        {
            for (String query : querys)
            {
                String[] para = query.split("=");
                queryMap.put(para[0], URLDecoder.decode(para[1], "GBK"));
            }
            //  订单个数
            final String v_count = queryMap.get("v_count");
            if (Integer.valueOf(v_count) < 0)
            {
                logger.info("【异步处理充值结果】        数据异常：订单笔数小于等于0[v_count=" + v_count + "]");
                throw new IllegalPlatformAugumentException("充值订单笔数小于等于0");
            }
            //  订单编号
            final String v_oid = queryMap.get("v_oid");
            if (v_oid == null)
            {
                logger.info("【异步处理充值结果】        数据异常：订单为空！");
                throw new IllegalPlatformAugumentException("订单为空");
            }
            //  支付方式
            final String v_pmode = queryMap.get("v_pmode");
            if (v_pmode == null)
            {
                logger.info("【异步处理充值结果】        数据异常：支付方式为空！");
                throw new IllegalPlatformAugumentException("订单为空");
            }
            //  支付状态码
            final String v_pstatus = queryMap.get("v_pstatus");
            //  支付结果信息
            final String v_pstring = queryMap.get("v_pstring");
            //  支付金额
            final String v_amount = queryMap.get("v_amount");
            //  币种
            final String v_moneytype = queryMap.get("v_moneytype");
            //  数字指纹(v_mac)
            final String v_mac = queryMap.get("v_mac");
            //  数字指纹(v_md5money)
            final String v_md5money = queryMap.get("v_md5money");
            // 商城数据签名(v_sign)
            final String v_sign = queryMap.get("v_sign");

            logger.info("【异步处理充值结果】        接收充值参数:[ 订单笔数(v_count)：" + v_count + ",订单编号(v_oid)：" + v_oid + "，支付方式(v_pmode)：" + v_pmode +
                    ",支付状态(v_pstatus):" + v_pstatus + ",支付结果 (v_pstring):" + v_pstring + ",支付金额(v_amount):" + v_amount + ",币种(v_moneytype):" + v_moneytype +
                    ",数字指纹(v_mac):" + v_mac + ",数字指纹(v_md5money):" + v_md5money + ",商城数据签名(v_sign):" + v_sign + "]");

            String source_v_mac = v_oid + v_pmode + v_pstatus + v_pstring + v_count;
            String source_v_md5money = v_amount + v_moneytype;
            String source_v_sign = v_oid + v_pstatus + v_amount + v_moneytype + v_count;
            logger.info("【异步处理充值结果】        接收充值数字指纹v_mac明文：" + source_v_mac);
            logger.info("【异步处理充值结果】        接收充值数字指纹v_md5money明文：" + source_v_md5money);
            logger.info("【异步处理充值结果】        接收充值商城数据v_sign明文：" + source_v_sign);

            if (rechargeService.verifyMd5(source_v_mac, v_mac) && rechargeService.verifyMd5(source_v_md5money, v_md5money) && rechargeService.verifySign(source_v_sign, v_sign))
            {
                logger.info("【异步处理充值结果】        接收充值数字指纹验证认证成功 [订单号=" + v_oid + "]");
                String[] v_oids = v_oid.split("\\|\\_\\|");
                String[] v_pstatuss = v_pstatus.split("\\|\\_\\|");
                String[] v_pmodes = v_pmode.split("\\|\\_\\|");
                String[] v_pstrings = v_pstring.split("\\|\\_\\|");
                String[] v_amounts = v_amount.split("\\|\\_\\|");
                String[] v_moneytypes = v_moneytype.split("\\|\\_\\|");

                RechargeBackResp[] rechargeBackResps = new RechargeBackResp[Integer.valueOf(v_count)];
                for (int i = 0; i < Integer.valueOf(v_count); i++)
                {
                    //构建充值结果对象
                    RechargeBackResp rechargeBackResp = new RechargeBackResp();
                    rechargeBackResp.setV_oid(v_oids[i]);
                    rechargeBackResp.setV_pstatus(Short.parseShort(v_pstatuss[i]));
                    rechargeBackResp.setV_pstring(v_pstrings[i]);
                    rechargeBackResp.setV_pmode(v_pmodes[i]);
                    rechargeBackResp.setV_amount(new BigDecimal(v_amounts[i]));
                    rechargeBackResp.setV_moneytype(Integer.parseInt(v_moneytypes[i]));

                    rechargeBackResps[i] = rechargeBackResp;
                }
                rechargeService.saveRechargeResult(rechargeBackResps);
                result = "sent";
            } else
            {
                logger.info("【异步处理充值结果】        接收充值数字指纹验证失败!!![v_oid=" + v_oid + "]");
            }
        } catch (IOException e)
        {
            logger.error("【异步处理充值结果】        接收充值数字指纹验证异常[v_oid=" + queryMap.get("v_oid") + "]", e);
        } catch (IllegalPlatformAugumentException e)
        {
            logger.error("【异步处理充值结果】        回调P2P参数异常[v_oid=" + queryMap.get("v_oid") + "]", e);
        }

        logger.info("<--【异步处理充值结果】        充值结果" + result + "[v_oid=" + queryMap.get("v_oid") + "]");
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String authenticateList(@RequestParam(value = "v_merdata4", required = false) String v_merdata4,
                                   @RequestParam(value = "v_merdata3", required = false) String v_merdata3,
                                   @RequestParam(value = "v_merdata5", required = false) String v_merdata5,
                                   @RequestParam(value = "v_ymd_start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date v_ymd_start,
                                   @RequestParam(value = "v_ymd_end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date v_ymd_end,
                                   @RequestParam(value = "v_pstatus", required = false) String v_pstatus,
                                   @RequestParam(value = "page_no", required = false, defaultValue = "1") Integer page_no,
                                   Map<String, Object> map)
    {

        Page<Map<String, Object>> page = new Page<Map<String, Object>>(10);
        page.setPageNo(page_no);
        HashMap<String, Object> searchConditions = new HashMap<String, Object>();
        searchConditions.put("v_merdata4", v_merdata4);
        searchConditions.put("v_merdata3", v_merdata3);
        searchConditions.put("v_merdata5", v_merdata5);
        searchConditions.put("v_ymd_start", v_ymd_start);
        searchConditions.put("v_ymd_end", v_ymd_end);
        searchConditions.put("v_pstatus", v_pstatus);

        rechargeService.getListPage(searchConditions, page);

        map.put("page", page);
        map.putAll(searchConditions);

        return "payease/recharge";
    }

}
