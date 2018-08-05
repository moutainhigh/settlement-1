package com.yuanheng100.settlement.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.yuanheng100.settlement.payease.util.EncDecUtil;
import com.yuanheng100.util.ConfigFile;
import com.yuanheng100.util.IPUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by qianjl on 2016-7-18.
 */
public class IPInterceptor extends HandlerInterceptorAdapter
{

    private static Logger logger = Logger.getLogger(IPInterceptor.class);

    //允许访问的IP地址数组
    private static String[][] allowIPs;

    static
    {
        String ips = ConfigFile.getProperty("system.allow.ip");
        if (ips != null)
        {
            String[] split = ips.split(",");
            allowIPs = new String[split.length][];
            for (int i = 0; i < allowIPs.length; i++)
            {
                allowIPs[i] = split[i].split("\\.");
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (allowIPs == null)
        {
            return true;
        }
        String ipAddr = IPUtil.getIpAddr(request);
        String[] ipAddrArray = ipAddr.split("\\.");

        boolean status[] = {false, false, false, false};

        for (int i = 0; i < allowIPs.length; i++)
        {

            String[] allowIp = allowIPs[i];

            for (int j = 0; j < allowIp.length; j++)
            {
                if ("*".equals(allowIp[j]) || allowIp[j].equals(ipAddrArray[j]))
                {
                    status[j] = true;
                } else
                {
                    status[j] = false;
                }
            }

            if (status[0] && status[1] && status[2] && status[3])
            {
                return true;
            }
        }
        logger.warn("异常IP访问，IP地址为：" + ipAddr + "，访问的路径为：" + request.getRequestURI() + "，查询字符串：" + request.getQueryString());
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String result = in.readLine();
        if (request.getRequestURI().contains("payease"))
        {
            result = EncDecUtil.dec(result);
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        logger.warn("异常IP访问，内容为: " + result + "，参数：" + JSON.toJSONString(parameterMap));
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return false;
    }
}
