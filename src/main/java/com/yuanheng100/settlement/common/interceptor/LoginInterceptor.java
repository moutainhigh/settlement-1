package com.yuanheng100.settlement.common.interceptor;

import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.ghbank.adapter.OGW00041Adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by qianjl on 2016-7-8.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter
{

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    
    @Autowired
    private LoginStaff loginStaff;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (loginStaff.getSysStaff() == null)
        {
            logger.debug("拦截请求"+request.getQueryString()+"  handler="+handler);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return false;
        }
        return true;
    }
}
