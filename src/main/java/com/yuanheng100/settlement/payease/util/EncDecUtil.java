package com.yuanheng100.settlement.payease.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.yuanheng100.util.ConfigFile;

/**
 * 加密解密工具
 * 
 * @author Administrator
 * 
 */
public class EncDecUtil
{

    private static Logger logger = Logger.getLogger(EncDecUtil.class);

    private static final String key = ConfigFile.getProperty("payease.key");

    /**
     * 解密
     * 
     * @param encMsg
     * @return
     */
    public static String dec(String encMsg)
    {

        String decMsg = "";
        try
        {
            byte[] srcBytes = Des3.decryptMode(key, Base64.decodeBase64(encMsg));
            // 正式
            decMsg = new String(srcBytes, "UTF-8");
            // 测试
            // decMsg =new String(srcBytes);
        }
        catch (Exception e)
        {
            decMsg = "";
            logger.error(e);
        }
        // logger.debug("decMsg:\n"+decMsg);
        return decMsg;
    }

    /**
     * 加密
     * 
     * @param msg
     * @return
     */
    public static String enc(String msg)
    {
        String encMsg = "";
        try
        {
            // 正式
            byte[] encoded = Des3.encryptMode(key, msg.getBytes("UTF-8"));
            // 测试
            // byte[] encoded = Des3.encryptMode(key, msg.getBytes());
            encMsg = new String(encoded);
            // logger.debug("encMsg:\n"+encMsg);
        }
        catch (Exception e)
        {
            logger.error(e);
            encMsg = "";
        }
        return encMsg;
    }

}
