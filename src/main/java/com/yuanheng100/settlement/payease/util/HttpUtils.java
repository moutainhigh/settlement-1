package com.yuanheng100.settlement.payease.util;

import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 发送HTTP请求
 * @author qianjl
 *
 */
public class HttpUtils {

	private static final Logger logger = Logger.getLogger(HttpUtils.class);

	private static TrustManager myX509TrustManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {

		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	/**
	 * 发送GET请求
	 * @param url
	 */
	public static String get(String url){
		return get(url, null);
	}

	public static String get(String url, Map<String,String> parameters) {
		StringBuffer getUrl = new StringBuffer(url);
		try {
			if(parameters!=null){
				getUrl.append("?");
				Set<Entry<String,String>> entrySet = parameters.entrySet();
				for (Entry<String, String> entry : entrySet) {
					String key = entry.getKey();
					String value = entry.getValue();
					getUrl.append(URLEncoder.encode(key,"UTF-8")).append("=").append(URLEncoder.encode(value,"UTF-8")).append("&");
				}
				//修正最后一个&符号
				getUrl.deleteCharAt(getUrl.length()-1);
			}
			return executeGet(getUrl.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String executeGet(String urlWithParameters) throws IOException{
		URL url = new URL(urlWithParameters);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置请求参数
		conn.setRequestMethod("GET");
		conn.setInstanceFollowRedirects(true);
		conn.setDoOutput(true);
		//设置请求头
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Connection", "keep-alive");
		logger.debug("------------------------------request------------------------------");
		logger.debug("url:"+urlWithParameters);
		logger.debug("headers:");
		Map<String, List<String>> requestProperties = conn.getRequestProperties();
		Set<Entry<String,List<String>>> entrySet = requestProperties.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			StringBuffer stringBuffer = new StringBuffer();
			for (String string : values) {
				stringBuffer.append(string).append(";");
			}
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
			logger.debug(key+":"+stringBuffer.toString());
		}
		logger.debug("------------------------------request End------------------------------");
		//开启连接
		conn.connect();
		String response = getResponse(conn);
		conn.disconnect();
		return response;
	}

	/**
	 * 发送post请求
	 * @param url
	 */
	public static String post(String url) throws IOException {
		return post(url, null);
	}

	public static String post(String url, Map<String,String> parameters) throws IOException {
		String result = null;
		try {
			result = executePost(url,parameters);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		} catch (KeyManagementException e) {
			logger.error(e);
		}
		return result;
	}
	
	private static String executePost(String uri,Map<String,String> parameters) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		URL url = new URL(uri);
		HttpURLConnection conn = null;
		if (uri.substring(0, 8).equalsIgnoreCase("https://")) {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[]{myX509TrustManager}, null);

			HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
			//HttpsURLConnection.setFollowRedirects(autoRedirect);
			conn = (HttpsURLConnection) url.openConnection();
		}
		else {
			//HttpURLConnection.setFollowRedirects(autoRedirect);
			conn = (HttpURLConnection) url.openConnection();
		}
		//设置请求参数
		conn.setRequestMethod("POST");
		conn.setInstanceFollowRedirects(true);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		//设置请求头
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		logger.debug("------------------------------request------------------------------");
		logger.debug("url:"+uri);
		logger.debug("headers:");
		Map<String, List<String>> requestProperties = conn.getRequestProperties();
		Set<Entry<String,List<String>>> entrySet = requestProperties.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			StringBuffer stringBuffer = new StringBuffer();
			for (String string : values) {
				stringBuffer.append(string).append(";");
			}
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
			logger.debug(key+":"+stringBuffer.toString());
		}

		//开启连接
		conn.connect();

		//发送数据
		OutputStream outputStream = null;
		try {
			outputStream = conn.getOutputStream();
			if (parameters!=null){
                Set<Entry<String, String>> entries = parameters.entrySet();
                String separator = "&";
                StringBuilder builder = new StringBuilder();
                for (Entry<String,String> entry: entries) {
                    String s = uploadData(outputStream, entry.getKey(), entry.getValue());
                    if(s!=null){
                        builder.append(s).append(separator);
                    }
                }
                builder.deleteCharAt(builder.length()-1);
                logger.debug("request:");
                logger.debug(builder.toString());
                outputStream.write(builder.toString().getBytes("UTF-8"));
                outputStream.flush();
            }
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {}
			}
		}

		logger.debug("------------------------------request End------------------------------");

		String response = getResponse(conn);
		conn.disconnect();
		return response;
	}
	
	private static String getResponse(HttpURLConnection conn) {

		URL url = conn.getURL();
		logger.debug("------------------------------response------------------------------");
		logger.debug("request:"+url);
		logger.debug("headers:");
		Map<String, List<String>> headerFields = conn.getHeaderFields();
		Set<Entry<String,List<String>>> entrySet = headerFields.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			StringBuffer stringBuffer = new StringBuffer();
			for (String string : values) {
				stringBuffer.append(string).append(";");
			}
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
			logger.debug(key+":"+stringBuffer.toString());
		}

		InputStream inputStream = null;
		String response = null;
		try {
			inputStream = conn.getInputStream();
			int available = inputStream.available();
			byte[] date = new byte[available];
			inputStream.read(date);
			response = new String(date);
			logger.debug("data:"+response);
			logger.debug("------------------------------response End------------------------------");
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {}
			}
		}
		return response;
	}

	/**
	 * uploadData
	 *
	 * @param outputStream
	 * @param sendName
	 * @param sendData
	 * @throws IOException
	 */
	private static String uploadData(OutputStream outputStream, String sendName, String sendData) throws IOException {
		if (sendData == null) {
			return null;
		}
		return URLEncoder.encode(sendName,"UTF-8") + "=" + URLEncoder.encode((String) sendData,"UTF-8");
	}
	
}
