package com.yuanheng100.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {

	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		
		//兼容多次代理
		if(ip!=null&&ip.indexOf(",")!=-1){
			ip = ip.substring(0,ip.indexOf(","));
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.indexOf(":") >= 0)
		{ // 判断是否为IPV6地址
			ip = "127.0.0.1";
		}
		return ip;
	}
}
