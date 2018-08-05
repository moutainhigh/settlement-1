package com.yuanheng100.settlement.ghbank;

import com.yuanheng100.settlement.ghbank.utils.DES3EncryptAndDecrypt;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by jlqian on 2017/4/17.
 * 测试报文加密解密
 *
 */
public class TestDES3
{
    String source = "<BATCH_ID>580116</BATCH_ID><PROJECT_CODE>PRJ201603160236168741</PROJECT_CODE><PROJECT_ID>b904af2c-1834-434b-a0cb-1b91a8c19caa</PROJECT_ID>";

    String target = "NVnwXP2rp6vOBT0MUM7ksYWuVU9JH/yK6Emtf8BLHTgMyyBjG2uy0nEzEJ40ch0dk0Q0bjvGw8/EFp7ccofRz07f5/gYJZq4Dwj7d+X8vyg2hnjyLfTJDCoDsBZ26ypULjYyPO5EjOLEUPTVbwX3/4L49nXtIStjYk6RgCDoV6LUjr3lJgOt+EkETdLRiYb0";

    @Test
    public void testEncrypt()
    {
        try
        {
            String s = DES3EncryptAndDecrypt.DES3EncryptMode(source);
            Assert.assertEquals(s, target);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testDecrypt()
    {
        try
        {
            String s = DES3EncryptAndDecrypt.DES3DecryptMode(target);
            Assert.assertEquals(s, source);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
