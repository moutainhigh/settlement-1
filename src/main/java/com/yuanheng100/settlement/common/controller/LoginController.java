package com.yuanheng100.settlement.common.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanheng100.settlement.common.model.LoginStaff;
import com.yuanheng100.settlement.common.model.system.SysStaff;
import com.yuanheng100.settlement.common.service.ISysStaffService;
import com.yuanheng100.util.MD5;

/**
 * 用户登录
 * Created by qianjl on 2016-6-23.
 */
@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    public static final String ALLCHAR = "0123456789";

    @Autowired
    private LoginStaff loginStaff;
    @Autowired
    private ISysStaffService sysStaffService;
    /*@Autowired
    private IWeChatService weChatService;*/

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") Integer username,
                        @RequestParam("passwd") String passwd,
//                        @RequestParam("code") String code,
                        HttpServletRequest request,
                        Map<String, Object> map) {
        Object savedUsername = request.getSession().getAttribute("username");
//        Object savedCode = request.getSession().getAttribute("code");
        //2017-6-15 临时去掉对验证码的验证
        //if (savedUsername != null && savedCode != null && savedUsername.equals(username) && savedCode.equals(code)) {
        if (savedUsername != null && savedUsername.equals(username) ) {
//        	if (savedUsername != null && savedCode != null && savedUsername.equals(username) ) {
            try {
                passwd = MD5.encodeByMd5AndSalt(passwd);
                SysStaff sysStaff = sysStaffService.login(username, passwd);
                if (sysStaff != null) {
                    loginStaff.setSysStaff(sysStaff);
                    map.put("staffName", sysStaff.getStaffName());
                    logger.info("管理员" + sysStaff.getStaffName() + "登录成功！");
                    return "settlement/settlement";
                }
            } catch (NoSuchAlgorithmException e) {
                logger.error(e);
            }
        }
        return "redirect:/index.jsp";
    }

    @ResponseBody
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String code(@RequestParam("username") Integer username, @RequestParam("passwd") String passwd, HttpServletRequest request) {
        if ("".equals(username) || "".equals(passwd)) {
            return "0";
        }
        try {
            passwd = MD5.encodeByMd5AndSalt(passwd);
            SysStaff sysStaff = sysStaffService.login(username, passwd);
            if (sysStaff == null) {
                return "-1";
            }
            StringBuilder codeBuilder = new StringBuilder(4);
            for (int i = 0; i < 4; i++) {
                codeBuilder.append(ALLCHAR.charAt(new Random().nextInt(ALLCHAR.length())));
            }
            request.getSession().setAttribute("code", codeBuilder.toString());
            request.getSession().setAttribute("username", username);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("touser", sysStaff.getId().toString());
            hashMap.put("msgtype", "text");
            hashMap.put("content", "结算系统登录验证码为:" + codeBuilder.toString());
            hashMap.put("safe", 1);
            //weChatService.sendChat(hashMap);
            logger.info("管理员" + sysStaff.getStaffName() + "获取微信验证码成功，验证码为：" + codeBuilder.toString());
        } catch (NoSuchAlgorithmException e) {
            return "0";
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public SysStaff getStaffInfo() {
        return loginStaff.getSysStaff();
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editStaff(SysStaff sysStaff) {
        SysStaff primarySysStaff = loginStaff.getSysStaff();
        String newEmail = sysStaff.getEmail();
        if (newEmail != null && !"".equals(newEmail)) {
            primarySysStaff.setEmail(newEmail);
        }
        Long newMobile = sysStaff.getMobile();
        if (newMobile != null && !"".equals(newMobile)) {
            primarySysStaff.setMobile(newMobile);
        }
        String newPassword = sysStaff.getPassword();
        if (newPassword != null && !"".equals(newPassword)) {
            try {
                primarySysStaff.setPassword(MD5.encodeByMd5AndSalt(newPassword));
            } catch (NoSuchAlgorithmException e) {
                logger.error(e);
            }
        }
        sysStaffService.edit(primarySysStaff);
        return "1";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        loginStaff.setSysStaff(null);
        return "redirect:/index.jsp";
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage() {
        if (loginStaff.getSysStaff() != null) {
            return "settlement/welcome";
        }
        return "redirect:/index.jsp";
    }
}
