package com.yuanheng100.settlement.payease.service.impl;

import com.capinfo.crypt.RSA_MD5;
import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.common.model.system.Page;
import com.yuanheng100.settlement.payease.callback.IRechargeCallbackListener;
import com.yuanheng100.settlement.payease.consts.IdType;
import com.yuanheng100.settlement.payease.consts.MoneyType;
import com.yuanheng100.settlement.payease.consts.RechargeStatus;
import com.yuanheng100.settlement.payease.mapper.RechargeMapper;
import com.yuanheng100.settlement.payease.mapper.SequenceIdMapper;
import com.yuanheng100.settlement.payease.model.other.SequenceId;
import com.yuanheng100.settlement.payease.model.recharge.RechargeBackResp;
import com.yuanheng100.settlement.payease.model.recharge.RechargeFrontResp;
import com.yuanheng100.settlement.payease.model.recharge.RechargeReq;
import com.yuanheng100.settlement.payease.service.IRechargeService;
import com.yuanheng100.settlement.payease.util.CallbackListenerContainer;
import com.yuanheng100.settlement.payease.util.Md5EncUtil;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2017/2/7.
 */
public class RechargeServiceImpl implements IRechargeService
{
    private static Logger logger = Logger.getLogger(RechargeServiceImpl.class);

    private static final String V_MID = ConfigFile.getProperty("payease.groupId");

    private static final String BRANCH_ID = ConfigFile.getProperty("payease.branchId");

    private static final String RECHARG_KEY = ConfigFile.getProperty("payease.recharg.key");

    private static final String PUBLIC_KEY_PATH = ConfigFile.getProperty("payease.public.key.path");

    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private SequenceIdMapper sequenceIdMapper;
    @Autowired
    private RechargeMapper rechargeMapper;

    @Override
    public RechargeReq getRechargeModel(BigDecimal v_amount, String v_ordername, Integer v_pmode, String v_url, String v_merdata1, String v_merdata3, String v_merdata4, String v_merdata5) throws IOException
    {
        v_merdata5 = "22001";
        //订单生成日期
        String v_ymd = yyyyMMdd.format(new Date());
        //流水号
        SequenceId sequenceId = new SequenceId();
        sequenceIdMapper.insert(sequenceId);
        //订单编号
        String v_oid = new StringBuilder().append(v_ymd).append("-").append(V_MID).append("-").append(sequenceId.getId()).toString();

        String v_amount_str = v_amount.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();

        StringBuilder md5EncStr = new StringBuilder();
        md5EncStr.append(MoneyType.CNY.getCode());
        md5EncStr.append(v_ymd);
        md5EncStr.append(v_amount_str);
        md5EncStr.append(V_MID);
        md5EncStr.append(v_oid);
        md5EncStr.append(V_MID);
        md5EncStr.append(v_url);

        RechargeReq rechargeReq = new RechargeReq();
        rechargeReq.setV_mid(V_MID);
        rechargeReq.setV_oid(v_oid);
        rechargeReq.setV_rcvname(V_MID);
        rechargeReq.setV_rcvaddr(V_MID);
        rechargeReq.setV_rcvtel(V_MID);
        rechargeReq.setV_rcvpost(V_MID);
        rechargeReq.setV_amount(v_amount);
        rechargeReq.setV_ymd(v_ymd);
        rechargeReq.setV_orderstatus(1);
        rechargeReq.setV_ordername(v_ordername);
        rechargeReq.setV_moneytype(0);
        rechargeReq.setV_pmode(v_pmode);
        rechargeReq.setV_url(v_url);
        rechargeReq.setV_md5info(Md5EncUtil.enco(md5EncStr.toString(), RECHARG_KEY));
        rechargeReq.setV_merdata1(v_merdata1);
        rechargeReq.setV_merdata2(IdType.IDENTITY_CARD.getCode());
        rechargeReq.setV_merdata3(v_merdata3);
        rechargeReq.setV_merdata4(v_merdata4);
        rechargeReq.setV_merdata5(v_merdata5);
        rechargeReq.setV_merdata6(V_MID);
        rechargeReq.setV_merdata7(BRANCH_ID);

        rechargeMapper.insert(rechargeReq);
        logger.info("【处理充值请求】        接收充值参数:[订单编号(v_oid):" + rechargeReq.getV_oid() + ",充值人姓名(v_ordername):" + rechargeReq.getV_ordername() + ",前台回调地址 (v_url):" + rechargeReq.getV_url() + "，支付方式(v_pmode)：" + rechargeReq.getV_pmode() +
                ",支付金额(v_amount):" + rechargeReq.getV_amount() + ",币种(v_moneytype):" + rechargeReq.getV_moneytype() + ",数字指纹(v_md5info):" + rechargeReq.getV_md5info() + ",会员ID(v_merdata1):" + rechargeReq.getV_merdata1() + ",真实姓名(v_merdata4):" + rechargeReq.getV_merdata4() + ",证件号码(v_merdata3):" + rechargeReq.getV_merdata3() + ",手机号码(v_merdata5):" + rechargeReq.getV_merdata5() + "]");

        return rechargeReq;
    }

    @Override
    public RechargeReq getRechargeModel(BigDecimal v_amount, String v_ordername, Integer v_pmode, String v_url, String v_merdata1, String v_merdata3, String v_merdata4, String v_merdata5, IRechargeCallbackListener rechargeCallbackListener) throws IOException
    {
        RechargeReq rechargeModel = getRechargeModel(v_amount, v_ordername, v_pmode, v_url, v_merdata1, v_merdata3, v_merdata4, v_merdata5);
        CallbackListenerContainer.putRechargeCallbackListener(rechargeModel.getV_oid(), rechargeCallbackListener);
        return rechargeModel;
    }

    @Override
    public boolean verifyRechargeFrontResp(RechargeFrontResp rechargeFrontResp)
    {
        logger.info("【前台同步处理充值结果】        接收充值参数:[订单编号(v_oid):" + rechargeFrontResp.getV_oid() + ",支付状态(v_pstatus):" + rechargeFrontResp.getV_pstatus() + ",支付结果 (v_pstring):" + rechargeFrontResp.getV_pstring() + "，支付方式(v_pmode)：" + rechargeFrontResp.getV_pmode() +
                ",支付金额(v_amount):" + rechargeFrontResp.getV_amount() + ",币种(v_moneytype):" + rechargeFrontResp.getV_moneytype() + ",数字指纹(v_md5info):" + rechargeFrontResp.getV_md5info() + ",数字指纹(v_md5money):" + rechargeFrontResp.getV_md5money() + ",商城数据签名(v_sign):" + rechargeFrontResp.getV_sign() + "]");

        String v_amount = rechargeFrontResp.getV_amount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();

        String source_v_md5info = rechargeFrontResp.getV_oid() + rechargeFrontResp.getV_pstatus() + rechargeFrontResp.getV_pstring() + rechargeFrontResp.getV_pmode();
        String source_v_md5money = v_amount + rechargeFrontResp.getV_moneytype();
        String source_v_sign = rechargeFrontResp.getV_oid() + rechargeFrontResp.getV_pstatus() + v_amount + rechargeFrontResp.getV_moneytype();

        logger.debug("【前台同步处理充值结果】        接收充值数字指纹v_md5info明文：" + source_v_md5info);
        logger.debug("【前台同步处理充值结果】        接收充值数字指纹v_md5money明文：" + source_v_md5money);
        logger.debug("【前台同步处理充值结果】        接收充值商城数据v_sign明文：" + source_v_sign);

        //校验
        try
        {
            if (verifyMd5(source_v_md5info, rechargeFrontResp.getV_md5info()) && verifyMd5(source_v_md5money, rechargeFrontResp.getV_md5money()) && verifySign(source_v_sign, rechargeFrontResp.getV_sign()))
            {
                return true;
            }
        } catch (IOException e)
        {
            logger.error(e.getMessage(), e);
        }

        return false;
    }

    public boolean verifyMd5(String source_v_md5, String v_md5) throws IOException
    {
        if (Md5EncUtil.enco(source_v_md5, RECHARG_KEY).equals(v_md5))
            return true;
        return false;
    }

    public boolean verifySign(String source_v_sign, String v_sign) throws IOException
    {
        RSA_MD5 myRSA = new RSA_MD5();
        return myRSA.PublicVerifyMD5(PUBLIC_KEY_PATH, v_sign, source_v_sign) == 0;
    }

    @Override
    public void saveRechargeResult(RechargeBackResp[] rechargeBackResps) throws IllegalPlatformAugumentException
    {
        for (RechargeBackResp rechargeBackResp : rechargeBackResps)
        {
            if (RechargeStatus.Back.SUCCESS.getCode().equals(rechargeBackResp.getV_pstatus()))
            {
                //  1：支付完成
                logger.info("【后台异步处理充值结果】        支付成功" + rechargeBackResp.getV_pstatus() + ":" + rechargeBackResp.getV_pstring() + "[订单号：" + rechargeBackResp.getV_oid() + "币种：" + rechargeBackResp.getV_moneytype() + "充值方式：" + rechargeBackResp.getV_pmode() + "充值金额：" + rechargeBackResp.getV_amount() + "]");
            } else
            {
                //  0：待处理  3：支付被拒绝
                logger.info("【后台异步处理充值结果】        支付失败" + rechargeBackResp.getV_pstatus() + ":" + rechargeBackResp.getV_pstring() + "[订单号：" + rechargeBackResp.getV_oid() + "币种：" + rechargeBackResp.getV_moneytype() + "充值方式：" + rechargeBackResp.getV_pmode() + "充值金额：" + rechargeBackResp.getV_amount() + "]");
            }
            //回调P2P
            IRechargeCallbackListener iRechargeCallbackListener = CallbackListenerContainer.removeRechargeCallbackListener(rechargeBackResp.getV_oid());
            if (iRechargeCallbackListener != null)
            {
                iRechargeCallbackListener.setRechargeResult(rechargeBackResp);
            }
            rechargeMapper.update(rechargeBackResp);
        }
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String, Object>> page)
    {
        Long totalCount = rechargeMapper.selectRechargeCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex", (pageNo - 1) * pageSize);
        searchConditions.put("endIndex", pageSize);

        List<Map<String, Object>> list = rechargeMapper.selectRechargeListByCondition(searchConditions);

        page.setResult(list);
    }
}
