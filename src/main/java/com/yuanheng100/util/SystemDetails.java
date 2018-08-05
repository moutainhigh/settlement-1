package com.yuanheng100.util;

import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SystemDetails {

    private static Logger logger = Logger.getLogger(SystemDetails.class);

    /**
     * 输出系统基本信息
     */
    public static void outputDetails() {
        logger.info("------------------结算系统启动------------------");
        logger.info("");
        timeZone();
        currentTime();
        os();
        jvm();
        conf();
        network();
    }

    /**
     * 输出系统时区
     */
    private static void timeZone() {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        logger.info("系统时区：\t\t" + timeZone.getID() + ", " + timeZone.getDisplayName());
    }

    /**
     * 输出系统时间
     */
    private static void currentTime() {
        Date myDate = new Date();
        logger.info("系统时间：\t\t" + DateUtil.formatToYYYYMMDDMMHHSS(myDate));
    }

    /**
     * 输出系统基本配置
     */
    private static void os() {
        String osName = System.getProperty("os.name"); // 操作系统名称
        logger.info("当前系统：\t\t" + osName);
        String osArch = System.getProperty("os.arch"); // 操作系统构架
        logger.info("当前系统架构：\t" + osArch);
        String osVersion = System.getProperty("os.version"); // 操作系统版本
        logger.info("当前系统版本：\t" + osVersion);
        String fileEncoding = System.getProperty("file.encoding");
        logger.info("当前系统字符集：\t" + fileEncoding);
        logger.info("");
    }

    /**
     * 输出jvm信息
     */
    private static void jvm() {
        logger.info("JVM名称：\t\t" + System.getProperty("java.vm.name"));
        logger.info("JVM版本：\t\t" + System.getProperty("java.runtime.version"));
        logger.info("JRE路径：\t\t" + System.getProperty("java.home"));
        logger.info("");

    }

    private static void conf() {
        logger.info("应用所在路径：\t" + System.getProperty("user.dir"));
        logger.info("系统数据根路径：\t" + System.getProperty("settlement.dir"));
        logger.info("");
    }

    /**
     * 取得本机ip地址 注意：Spring RmiServiceExporter取得本机ip的方法：InetAddress.getLocalHost()
     */
    private static void network() {
        try {
            logger.info("服务暴露的ip：\t" + java.net.InetAddress.getLocalHost().getHostAddress());
            logger.info("");
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
