package com.yuanheng100.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5
{
    private static final String SALT = "ULBsmBb2E5Be8WsolGYxDHgKgF6vs6VF";

    private static String EncoderByMd5(String buf) throws NoSuchAlgorithmException
    {
        MessageDigest digist = MessageDigest.getInstance("MD5");
        byte[] rs = digist.digest(buf.getBytes());
        StringBuffer digestHexStr = new StringBuffer();
        for (int i = 0; i < 16; i++)
        {
            digestHexStr.append(byteHEX(rs[i]));
        }
        return digestHexStr.toString();
    }

    public static String encodeByMd5AndSalt(String inbuf) throws NoSuchAlgorithmException
    {
        return EncoderByMd5(EncoderByMd5(inbuf) + SALT);
    }

    private static String byteHEX(byte ib)
    {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4 & 0xF)];
        ob[1] = Digit[(ib & 0xF)];
        String s = new String(ob);
        return s;
    }

    public static void main(String[] args)
    {
        try
        {
            System.out.println(EncoderByMd5("accountId=1120160627143946001&contractId=1120160627143946001&name=龚军&idCardNo=430402196808241512&cardNo=62220212306563132&phoneNo=13618448228&startDate=20160730&endDate=20171230&triesLimit=18"));
            System.out.println(encodeByMd5AndSalt("password"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}