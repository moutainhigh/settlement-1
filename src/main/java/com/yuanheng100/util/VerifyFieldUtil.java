package com.yuanheng100.util;

import java.util.IdentityHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 验证字段工具类：
 * 1.身份证号
 * 2.手机号
 * 3.中文名称
 * 4.邮箱
 */
public class VerifyFieldUtil
{

    public enum FieldType
    {

        IDCARD_NUMBER,
        MOBILE_PHONE,
        CHINESE_NAME,
        EMAIL_ADDRESS;

    }

    /**
     * 对HashMap中的字段进行验证
     *
     * @param fields
     * @return
     */
    public static boolean verify(IdentityHashMap<String, FieldType> fields)
    {
        boolean result = true;
        Set<Entry<String, FieldType>> entrySet = fields.entrySet();
        for (Entry<String, FieldType> entry : entrySet)
        {
            if (result)
            {
                FieldType value = entry.getValue();
                String key = entry.getKey();
                switch (value)
                {
                    case IDCARD_NUMBER:
                        result = verifyIdCard(key);
                        break;
                    case MOBILE_PHONE:
                        result = verifyMobilePhone(key);
                        break;
                    case CHINESE_NAME:
                        result = verifyChinese(key);
                        break;
                    case EMAIL_ADDRESS:
                        result = verifyEmail(key);
                        break;
                    default:
                        break;
                }
            } else
            {
                break;
            }
        }
        return result;
    }

    /**
     * 验证身份证号
     *
     * @param idCard
     * @return
     */
    public static boolean verifyIdCard(String idCard)
    {
        return IDCardUtil.validateCard(idCard);
    }

    /**
     * 验证手机号
     */
    private static final String MOBILE_REGEX = "^1[3|4|5|7|8]\\d{9}$";

    public static boolean verifyMobilePhone(String mobilePhone)
    {
        return Pattern.matches(MOBILE_REGEX, mobilePhone);
    }

    /**
     * 验证中文名称
     *
     * @param chinese
     * @return
     */
    private static final String CHINESE_REGEX = "^[\u2E80-\u9FFF]+$";

    public static boolean verifyChinese(String chinese)
    {
        return Pattern.matches(CHINESE_REGEX, chinese);
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    private static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

    public static boolean verifyEmail(String email)
    {
        return Pattern.matches(EMAIL_REGEX, email);
    }

}
