package com.yuanheng100.settlement.payease.util;

import com.capinfo.crypt.Md5;

import java.io.IOException;


/**
 * MD5加密Util
 * @author hexiaofei
 *
 */
public class Md5EncUtil
{

	/**
	 * 
	 * @param source 明文字符串
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static String enco(String source,String key) throws IOException{
		Md5 md5 = new Md5("");
		md5.hmac_Md5(source, key);
		byte b[]= md5.getDigest();
		return md5.stringify(b) ;
	}
	
}
