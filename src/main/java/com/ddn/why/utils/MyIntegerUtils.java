package com.ddn.why.utils;

//Integer工具类
public class MyIntegerUtils {

	/**
	 * 判断Integer是否为null，为null则改为0
	 * @param Int
	 * @return
	 */
	public static Integer isNull(Integer Int) {
		return Int == null ? 0 : Int;
	}

}
