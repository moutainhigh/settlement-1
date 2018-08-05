package com.yuanheng100.settlement.chanpay.util;

import com.yuanheng100.settlement.chanpay.consts.Cj;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLDecoder;

/**
 * 向畅捷支付前置发送报文的帮助类
 */
@Service
public class CjMsgSendHelper {

	public static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(CjMsgSendHelper.class);

	public static String sendAndGetString(String message) throws Exception {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getParams().setSoTimeout(10 * 60 * 1000);

		URL url = new URL(Cj.CJ_GATEWAY_URL);
		String urlstr = url.toString();
		LOG.info("提交地址" + urlstr);
		LOG.info("提交内容" + message);
		HttpPostBodyMethod post = new HttpPostBodyMethod(urlstr, message);

		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			String err = "访问失败！！HTTP_STATUS=" + statusCode;
			LOG.error(err);
			LOG.error("返回内容为：" + post.getResponseBodyAsString());
			throw new HttpException(err);
		}//if

		String respData = post.getResponseBodyAsString();
		LOG.info("收到响应报文：" + respData);
		return respData;
	}//method

	public static SendAndGetBytes_Response sendAndGetBytes(String message) throws Exception {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getParams().setSoTimeout(10 * 60 * 1000);

		URL url = new URL(Cj.CJ_GATEWAY_URL);
		String urlstr = url.toString();
		LOG.info("提交地址" + urlstr);
		HttpPostBodyMethod post = new HttpPostBodyMethod(urlstr, message);

		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			String err = "访问失败！！HTTP_STATUS=" + statusCode;
			LOG.error(err);
			LOG.error("返回内容为：" + post.getResponseBodyAsString());
			throw new HttpException(err);
		}//if

		SendAndGetBytes_Response ret = new SendAndGetBytes_Response();

		ret.contentType = findHeaderAttr(post.getResponseHeader("Content-Type"));
		LOG.info("收到响应类型contentType：" + ret.contentType);

		ret.data = post.getResponseBody();
		ret.contentDisposition = findHeaderAttr(post.getResponseHeader("Content-Disposition"));
		ret.retcode = findHeaderAttr(post.getResponseHeader(Cj.PROP_NAME_RET_CODE));
		ret.errmsg = findHeaderAttr(post.getResponseHeader(Cj.PROP_NAME_ERR_MSG));
		//TODO 需要字符集转义
		return ret;
	}//method

	public static class SendAndGetBytes_Response {
		public String retcode = "";
		public String errmsg = "";
		public byte[] data = {};
		public String contentType = "";
		public String contentDisposition = "";
	}//class

	private static String findHeaderAttr(Header head) throws Exception {
		if (head == null)
			return "";
		String val = head.getValue();
		if (StringUtils.isBlank(val)) {
			return "";
		}
		String msg = URLDecoder.decode(val, "UTF-8");
		LOG.info("收到HTTP-HEAD属性[" + head.getName() + "]=" + msg);
		return msg;
	}//method

}//class
