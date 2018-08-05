package com.yuanheng100.settlement.ghbank.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

/**
 * 发送HTTP请求
 *
 * @author qianjl
 */
public class HttpUtils
{

    private static final Logger logger = Logger.getLogger(HttpUtils.class);

    private static TrustManager myX509TrustManager = new X509TrustManager()
    {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
        {

        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
        {

        }

        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
    };

    /**
     * 发送post请求
     *
     * @param url
     */
    public static String post(String url) throws IOException
    {
        return post(url, null);
    }

    public static String post(String url, String postString)
    {
        String result = null;
        try
        {
            result = executePost(url, postString);
        }
        catch (NoSuchAlgorithmException e)
        {
            logger.error("发送请求时出现NoSuchAlgorithmException", e);
        }
        catch (KeyManagementException e)
        {
            logger.error("发送请求时出现KeyManagementException", e);
        }
        catch(IOException ioe)
        {
            logger.error("发送请求时出现IOException", ioe);
        }
        return result;
    }

    private static String executePost(String uri, String postString) throws IOException, NoSuchAlgorithmException, KeyManagementException
    {

        int contentLength = postString == null ? 0 : postString.getBytes("UTF-8").length;

        URL url = new URL(uri);
        HttpURLConnection conn = null;
        if (uri.substring(0, 8).equalsIgnoreCase("https://"))
        {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { myX509TrustManager }, null);

            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            // HttpsURLConnection.setFollowRedirects(autoRedirect);
            conn = (HttpsURLConnection) url.openConnection();
        }
        else
        {
            // HttpURLConnection.setFollowRedirects(autoRedirect);
            conn = (HttpURLConnection) url.openConnection();
        }
        // 设置请求参数
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(1000 * 15);
        conn.setReadTimeout(1000 * 60 * 2);
        conn.setUseCaches(false); // post请求不能使用缓存
        conn.setDoInput(true);
        conn.setDoOutput(true);
        // 设置请求头
        conn.setRequestProperty("Accept", "*/*");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Content-Length", String.valueOf(contentLength));
        conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
        logger.debug("------------------------------Request 开始------------------------------");
        logger.debug("Request URL:\t\t" + uri);
        logger.debug("Request Headers:\t" + conn.getRequestProperties());

        // 开启连接
        conn.connect();

        // 发送数据
        OutputStream outputStream = null;
        try
        {
            outputStream = conn.getOutputStream();
            if (postString != null)
            {
                logger.debug("Request 内容:\t\t" + postString);
                outputStream.write(postString.getBytes("UTF-8"));
                outputStream.flush();
            }
        }
        catch (IOException e)
        {
            logger.error("发送请求时遇到异常" + e);
        }
        finally
        {
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    logger.error("发送请求,final时遇到异常" + e);
                }
            }
        }

        logger.debug("------------------------------Request 结束------------------------------");

        String response = getResponse(conn);
        conn.disconnect();
        return response;
    }

    private static String getResponse(HttpURLConnection conn)
    {

        URL url = conn.getURL();
        logger.debug("------------------------------Response 开始------------------------------");
        logger.debug("Response URL:\t\t" + url);
        logger.debug("Response Headers:\t"+conn.getHeaderFields());

        InputStream inputStream = null;
        String response = null;
        try
        {
            inputStream = conn.getInputStream();
            int available = inputStream.available();
            byte[] date = new byte[available];
            inputStream.read(date);
            response = new String(date, "UTF-8");
            logger.debug("Response 内容:\t\t"+response);
            logger.debug("------------------------------Response 结束------------------------------");
        }
        catch (IOException e)
        {
            logger.error("接受Response时遇到异常", e);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    logger.error("接受Response,final时遇到异常",e);
                }
            }
        }
        return response;
    }

}
