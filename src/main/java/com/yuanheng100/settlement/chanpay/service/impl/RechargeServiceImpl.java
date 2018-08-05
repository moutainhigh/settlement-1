package com.yuanheng100.settlement.chanpay.service.impl;

import com.yuanheng100.exception.IllegalPlatformAugumentException;
import com.yuanheng100.settlement.chanpay.callback.IRechargeCallbackListener;
import com.yuanheng100.settlement.chanpay.consts.Qpay;
import com.yuanheng100.settlement.chanpay.mapper.Q20003BeanMapper;
import com.yuanheng100.settlement.chanpay.model.Q20003Bean;
import com.yuanheng100.settlement.chanpay.service.IRechargeService;
import com.yuanheng100.settlement.chanpay.util.CallbackListenerContainer;
import com.yuanheng100.settlement.chanpay.util.CjQpayHelper;
import com.yuanheng100.settlement.common.model.system.Page;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jlqian on 2016/9/7.
 */
public class RechargeServiceImpl implements IRechargeService {

    private static final Logger LOG = Logger.getLogger(RechargeServiceImpl.class);

    @Autowired
    private Q20003BeanMapper q20003BeanMapper;

    @Override
    public String getRechargeUrl(Q20003Bean q20003Bean) throws IllegalPlatformAugumentException {
        //验证参数：确保 trade_amount 参数不能为空
        BigDecimal tradeAmount = q20003Bean.getTradeAmount();
        if(tradeAmount==null||tradeAmount.doubleValue()<=0){
            throw new IllegalPlatformAugumentException("交易金额参数不正确！");
        }
        //查询订单编号是否重复
        String outTradeNo = q20003Bean.getOutTradeNo();
        if(outTradeNo==null||outTradeNo.equals("")){
            throw new IllegalPlatformAugumentException("订单编号参数不正确！");
        }
        Q20003Bean existQ20003Bean = q20003BeanMapper.selectByOutTradeNo(outTradeNo);
        if(existQ20003Bean!=null){
            throw new IllegalPlatformAugumentException("订单编号已存在！");
        }
        //添加额外字段
        q20003Bean.setService(Qpay.SERVICE_Q20003);
        q20003Bean.setOrderTime(new Date());
        q20003Bean.setIsAnonymous("Y");// 是否匿名 必须写Y，然后buyer,seller相关的参数不要填写，因为写N的话，要求买家（商户那边，来付款的普通用户）也是畅捷的用户
        q20003Bean.setNotifyUrl(Qpay.QPAY_NOTIFY_URL);
        //根据是否有bankCode
        if(!StringUtils.isEmpty(q20003Bean.getBankCode())){
            //设置直连参数
            q20003Bean.setPayMethod(Qpay.PAY_METHOD_直连);
            q20003Bean.setPayType(Qpay.PAY_TYPE_对私_借记);
        }else{
            q20003Bean.setPayMethod(Qpay.PAY_METHOD_收银台);
        }
        //保存Q20003Bean
        q20003BeanMapper.insertSelective(q20003Bean);
        return CjQpayHelper.cjt_create_instant_trade(q20003Bean);
    }

    @Override
    public String getRechargeUrl(Q20003Bean q20003Bean, IRechargeCallbackListener rechargeCallbackListener) throws IllegalPlatformAugumentException
    {
        String rechargeUrl = getRechargeUrl(q20003Bean);
        CallbackListenerContainer.putRechargeCallbackListener(q20003Bean.getOutTradeNo(),rechargeCallbackListener);
        return rechargeUrl;
    }

    @Override
    public void getListPage(HashMap<String, Object> searchConditions, Page<Map<String,Object>> page) {
        Long totalCount = q20003BeanMapper.selectRechargeCountByCondition(searchConditions);
        page.setTotalCount(totalCount);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();

        searchConditions.put("fromIndex",(pageNo-1)*pageSize);
        searchConditions.put("endIndex",pageSize);

        List<Map<String,Object>> list = q20003BeanMapper.selectRechargeListCondition(searchConditions);
        page.setResult(list);
    }
}
