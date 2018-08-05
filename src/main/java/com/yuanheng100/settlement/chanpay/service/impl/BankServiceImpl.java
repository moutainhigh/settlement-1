package com.yuanheng100.settlement.chanpay.service.impl;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.mapper.ChanpaySequenceIdMapper;
import com.yuanheng100.settlement.chanpay.mapper.G40001BeanMapper;
import com.yuanheng100.settlement.chanpay.model.G40001Bean;
import com.yuanheng100.settlement.chanpay.model.SequenceId;
import com.yuanheng100.settlement.chanpay.service.GwMsgBaseService;
import com.yuanheng100.settlement.chanpay.service.IBankService;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by jlqian on 2016/9/23.
 */
public class BankServiceImpl extends GwMsgBaseService<G40001Bean> implements IBankService
{
    private static final Logger LOG = Logger.getLogger(BankServiceImpl.class);
    @Autowired
    private G40001BeanMapper g40001BeanMapper;
    @Autowired
    private ChanpaySequenceIdMapper chanpaySequenceIdMapper;

    @Override
    public void obtainBank()
    {
        G40001Bean bean = new G40001Bean();
        SequenceId sequenceId = new SequenceId();
        chanpaySequenceIdMapper.insert(sequenceId);
        bean.setReqSn(Cj.CJ_MERCHANT_ID+Cj.FMT_yyMMddHHmmss.format(new Date())+ String.format("%05d",sequenceId.getId()));
        bean.setTimestamp(new Date());
        buildCjmsgAndSend(bean);
    }

    @Override
    public List<G40001Bean> getBanksWithBankCodeAndBankName(String bankCode, String bankName)
    {
        return g40001BeanMapper.selectByBankCodeAndAccountBankName(bankCode, bankName);
    }

    @Override
    protected void buildCjmsgAndSend(G40001Bean bean)
    {

        try
        {
            String cjReqmsg = buildCjmsg(bean);
            // 签名
            CjSignHelper singHelper = new CjSignHelper();
            String signMsg = singHelper.signXml$Add(cjReqmsg);
            // 发送报文
            CjMsgSendHelper.SendAndGetBytes_Response cjRespmsg = CjMsgSendHelper.sendAndGetBytes(signMsg);

            //解析zip文件包
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cjRespmsg.data);
            ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
            ZipEntry nextEntry = null;
            while ((nextEntry = zipInputStream.getNextEntry()) != null)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipInputStream));
                String line = null;
                String[] split = null;
                G40001Bean g40001Bean = new G40001Bean();
                while ((line = bufferedReader.readLine()) != null)
                {
                    // 对获取到的数据进行处理，格式：
                    // 序号|银行代码|支付行号|支付行清算行号|开户行名称|地区码|是否支持二代业务
                    split = line.trim().split("\\|",7);
                    System.out.println(line);
                    System.out.println(split[0]+","+split[1]);
                    g40001Bean.setSn(Integer.valueOf(split[0]));
                    g40001Bean.setBankCode(split[1]);
                    g40001Bean.setAccountBankCode(split[2]);
                    g40001Bean.setClearingBankCode(split[3]);
                    g40001Bean.setAccountBankName(split[4]);
                    g40001Bean.setRegionCode(Integer.valueOf(split[5]));
                    g40001Bean.setSecondGeneration(split[6]);
                    g40001BeanMapper.insert(g40001Bean);
                }
            }
            zipInputStream.close();

        } catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String buildCjmsg(G40001Bean data)
    {
        Document doc = DocumentHelper.createDocument();
        Element msgEl = doc.addElement("MESSAGE");

        Element infoEl = msgEl.addElement("INFO");
        infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_行名行号下载);
        infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
        infoEl.addElement("MERCHANT_ID").setText(Cj.CJ_MERCHANT_ID);
        infoEl.addElement("REQ_SN").setText(U.nvl(data.getReqSn()));
        infoEl.addElement("TIMESTAMP").setText(Cj.formatDatetime_string(data.getTimestamp()));
        infoEl.addElement("SIGNED_MSG").setText("");

        Element bodyEl = msgEl.addElement("BODY");
        String xml = Cj.formatXml_UTF8(doc);
        return xml;
    }

}
