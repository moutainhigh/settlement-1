package com.yuanheng100.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * 读配置文件
 */
public class ConfigFile
{

    private static Logger logger = Logger.getLogger(ConfigFile.class);

    private static Map<String, String> systemPropertyMap = null;

    /**
     * 是否为测试环境
     */
    private static boolean isProduction = false;

    /**
     * 测试环境后缀
     */
    private static final String TEST_SUFIX = ".test";

    /**
     * 公共配置后缀
     */
    private static final String PUBLIC_SUFIX = ".public";


    static
    {
        systemPropertyMap = new HashMap<String, String>();

        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("settlementConfig.properties");
        Properties properties = new Properties();
        try
        {
            properties.load(new InputStreamReader(resourceAsStream, "utf-8"));
            Set<Entry<Object, Object>> entrySet = properties.entrySet();
            for (Entry<Object, Object> entry : entrySet)
            {
                Object key = entry.getKey();
                Object value = entry.getValue();
                systemPropertyMap.put((String) key, (String) value);
            }
            //获取环境信息【测试、正式】
            if ("TRUE".equals(systemPropertyMap.get("system.environment.production")))
            {
                isProduction = true;
            }
        } catch (IOException e)
        {
            logger.error("系统资源读取失败！", e);
        }
    }

    public static String getProperty(String key)
    {
        if(key.startsWith("system")){
            return systemPropertyMap.get(key);
        }
        String value = null;
        if (isProduction)
        {
            value = systemPropertyMap.get(key);
        } else
        {
            value = systemPropertyMap.get(key + TEST_SUFIX);
        }
        if (value == null)
        {
            value = systemPropertyMap.get(key + PUBLIC_SUFIX);
        }
        return value;
    }
}
