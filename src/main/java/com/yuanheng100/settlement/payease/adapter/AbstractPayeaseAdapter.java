package com.yuanheng100.settlement.payease.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.yuanheng100.channel.adaptor.AbstractAdapter;
import com.yuanheng100.channel.entity.MessageResponse;
import com.yuanheng100.channel.service.AbstractMessageService;
import com.yuanheng100.settlement.payease.filter.AccBankNameFilter;
import com.yuanheng100.settlement.payease.filter.AccBankValueFilter;
import com.yuanheng100.settlement.payease.filter.TRS001007JsonFilter;
import com.yuanheng100.settlement.payease.model.PayeaseReq;
import com.yuanheng100.settlement.payease.model.syn001001.SYN001001Req;
import com.yuanheng100.settlement.payease.model.trs001006.TRS001006Req;
import com.yuanheng100.settlement.payease.model.trs001007.TRS001007Req;
import com.yuanheng100.settlement.payease.model.trs001008.TRS001008Req;
import com.yuanheng100.settlement.payease.util.EncDecUtil;
import com.yuanheng100.util.ConfigFile;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 向首信易发送
 *
 * @author Bai Song
 */
public abstract class AbstractPayeaseAdapter<M extends PayeaseReq> extends AbstractAdapter<PayeaseReq>
{
    private static Logger logger = Logger.getLogger(AbstractPayeaseAdapter.class);

    private static final SerializeFilter trs001007JsonFilter = new TRS001007JsonFilter();

    private static final SerializeFilter accBankNameFilter = new AccBankNameFilter();

    private static final SerializeFilter accBankValueFilter = new AccBankValueFilter();

    private static final SerializeFilter[] accBankFilter = new SerializeFilter[2];

    static
    {
        accBankFilter[0] = accBankNameFilter;
        accBankFilter[1] = accBankValueFilter;
    }

    private static final String GROUP_ID = ConfigFile.getProperty("payease.groupId");

    private static final String BRANCH_ID = ConfigFile.getProperty("payease.branchId");

    private static URL url;

    private HttpURLConnection urlConn;

    protected AbstractPayeaseAdapter()
    {
        try
        {
            url = new URL(ConfigFile.getProperty("payease.base_url") + getModuleSubUrl());
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setRequestMethod("POST");
            urlConn.setUseCaches(false);
        } catch (IOException ioe)
        {
            logger.error("生成首信易HttpURLConnection对象时出现错误", ioe);
        }
    }

    @Override
    abstract public MessageResponse send(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1);

    /**
     * 抽象父Adapter发送请求，接受response（字符串格式），其继承的子Adapter解析字符串为具体的response model
     *
     * @param payeaseReq
     * @param arg1
     * @return
     */
    public String postAndReceive(PayeaseReq payeaseReq, AbstractMessageService<PayeaseReq> arg1)
    {
        payeaseReq.setGroupId(GROUP_ID);
        payeaseReq.setBranchId(BRANCH_ID);
        String postString = null;
        if (payeaseReq instanceof SYN001001Req || payeaseReq instanceof TRS001008Req || payeaseReq instanceof TRS001006Req)
        {
            postString = JSON.toJSONString(payeaseReq, accBankFilter);
        } else if (payeaseReq instanceof TRS001007Req)
        {
            postString = JSON.toJSONString(payeaseReq, trs001007JsonFilter);
        } else
        {
            postString = JSON.toJSONString(payeaseReq);
        }
        logger.debug("向首信易发送：" + postString);
        postString = EncDecUtil.enc(postString);
        String inStr = null;
        StringBuffer response = null;
        try
        {
            urlConn.setRequestProperty("Content-Length", String.valueOf(postString.getBytes().length));
            urlConn.getOutputStream().write(postString.getBytes("utf-8"));
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            response = new StringBuffer();
            while ((inStr = in.readLine()) != null)
            {
                response.append(EncDecUtil.dec(inStr));
                logger.debug("接收首信易：" + response);
            }
        } catch (IOException ioe)
        {
            logger.error("异常", ioe);
        }

        urlConn.disconnect();

        return response.toString();

    }

    /**
     * 取子业务对应的suburl
     *
     * @return
     */
    public abstract String getModuleSubUrl();

}
