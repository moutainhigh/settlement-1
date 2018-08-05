package com.yuanheng100.settlement.chanpay.controller;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper;
import com.yuanheng100.settlement.chanpay.util.CjMsgSendHelper.SendAndGetBytes_Response;
import com.yuanheng100.settlement.chanpay.util.CjSignHelper;
import com.yuanheng100.settlement.chanpay.util.U;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/G40003")
public class G40003Controller {
	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(G40003Controller.class);
	
	@RequestMapping(value = "/sendMessage")
	public void sendMessage(String mertid, String billType, String billMonth, String billDay, HttpServletResponse response) {
		try {
			SendAndGetBytes_Response ret = buildCjmsgAndSend(mertid,billType,billMonth,billDay);
			response.setContentType(ret.contentType);
			response.addHeader("Content-Disposition", ret.contentDisposition);
			response.addHeader(Cj.PROP_NAME_RET_CODE, ret.retcode);
			response.addHeader(Cj.PROP_NAME_ERR_MSG, ret.errmsg);
			response.setCharacterEncoding("utf-8");
			ServletOutputStream out = response.getOutputStream();
			out.write(ret.data);
			out.flush();
			out.close();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}//method
	
	/** 组织Cj报文，并发送 */
	private SendAndGetBytes_Response buildCjmsgAndSend(String mertid, String billType, String billMonth, String billDay) {
		SendAndGetBytes_Response ret = null;
		try {
			String cjReqmsg = buildCjmsg(mertid,billType,billMonth,billDay);

			// 签名
			CjSignHelper singHelper = new CjSignHelper();
			String signMsg = singHelper.signXml$Add(cjReqmsg);
			
			// 发送报文
			ret = CjMsgSendHelper.sendAndGetBytes(signMsg);

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return ret;
	}//method
	
	public String buildCjmsg(String mertid, String billType, String billMonth, String billDay) {
		Document doc = DocumentHelper.createDocument();
		Element msgEl = doc.addElement("MESSAGE");

		Element infoEl = msgEl.addElement("INFO");
		infoEl.addElement("TRX_CODE").setText(Cj.XMLMSG_TRANS_CODE_对账文件下载);
		infoEl.addElement("VERSION").setText(Cj.XMLMSG_VERSION_01);
		infoEl.addElement("MERCHANT_ID").setText(U.nvl(mertid));
		infoEl.addElement("REQ_SN").setText(U.nvl(U.createUUID()));
		infoEl.addElement("TIMESTAMP").setText(U.getCurrentTimestamp());
		infoEl.addElement("SIGNED_MSG").setText("");

		Element bodyEl = msgEl.addElement("BODY");
		bodyEl.addElement("BILL_TYPE").setText(U.nvl(billType));
		if("".equals(billMonth) || null==billMonth){
			bodyEl.addElement("BILL_MONTH").setText(U.nvl(""));
		}else{
			bodyEl.addElement("BILL_MONTH").setText(U.nvl(Cj.formatMonthDate_string(Cj.parseDate(billMonth))));
		}
		if("".equals(billDay) || null==billDay){
			bodyEl.addElement("BILL_DAY").setText(U.nvl(""));
		}else{
			bodyEl.addElement("BILL_DAY").setText(U.nvl(Cj.formatDate_string(Cj.parseDate(billDay))));
		}
		
		String xml = Cj.formatXml_UTF8(doc);
		LOG.info("产生G40003对账文件下载：" + U.substringByByte(xml, 512));
		return xml;
	}//method

}
