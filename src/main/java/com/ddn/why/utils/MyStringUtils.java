package com.ddn.why.utils;

import org.junit.Test;

//字符串工具类
public class MyStringUtils {

	/**
	 * 判断字符串是否为null，为null则替换为""
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 字符串转换unicode,字母和数字不转换
	 */
	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {
			// 取出每一个字符
			char c = string.charAt(i);
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9')) {
				System.out.println(c + "---");
				unicode.append(c);
			} else {
				// 转换为unicode
				unicode.append("\\u" + Integer.toHexString(c));
			}
		}
		return unicode.toString();
	}

	@Test
	public void test(){
		Object str = "";
		System.out.println(str.getClass());
	}
	
	public static String cnToUnicode(String cn) {
		char[] chars = cn.toCharArray();
		String returnStr = "";
		for (int i = 0; i < chars.length; i++) {
			returnStr += "\\u" + Integer.toString(chars[i], 16);
		}
		return returnStr;
	}

}
