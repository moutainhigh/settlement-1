package com.zcguodian.util;

public class MACUtil
{
	public String MAC(String str)
	{
		StringBuffer stringBuffer = new StringBuffer();
		if(str != null)
		{
			stringBuffer.append(str);
		}
//		mac
		return stringBuffer.toString();
	}
}
