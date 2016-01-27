package com.kingdee.lightapp.utils;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * version、appid、timestamp、nonce和所申请的appSecret升序排列后，各项指标的值按字符串方式拼凑在一起进行md5(
 * 其中的shaHex算法)签名后进行UTF-8 URL编码所得的值 Desc:
 * 
 * @author kingdee
 */
public class SHAUtils
{
	/**
	 */
	public static String sha(String... data)
	{
		//按字母顺序排序数组
		Arrays.sort(data);
		//把数组连接成字符串（无分隔符），并sha1哈希
        return DigestUtils.shaHex(StringUtils.join(data));

	}
	
	/**
	 */
	public static String byteToStr(byte[] byteArray)
	{
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++)
		{
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 
	 * @return
	 */
	public static String byteToHexStr(byte mByte)
	{
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
