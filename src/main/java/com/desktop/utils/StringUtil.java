package com.desktop.utils;

/**
 * 字符串工具类
 * 
 * @author wenyou
 *         <p>
 *         2013年9月25日 下午12:15:50
 */
public class StringUtil {

	/**
	 * 判断字符串是否是整数
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isInteger(String number) {
		boolean isNumber = false;
		if (StringUtil.isNotEmpty(number)) {
			isNumber = number.matches("^([1-9]\\d*)|(0)$");
		}
		return isNumber;
	}

	/**
	 * 判断字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		boolean isNotEmpty = false;
		if (str != null && !str.trim().equals("")
				&& !str.trim().equalsIgnoreCase("NULL")) {
			isNotEmpty = true;
		}
		return isNotEmpty;
	}

	/**
	 * 判断字符串为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}

	/**
	 * 将数组转成SQL认识的字符串 123,432,2312 id in('123','432','2312')
	 * 
	 * @param ids
	 * @return
	 */
	public static String fromArrayToStr(String[] ids) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			str.append("'" + ids[i] + "',");
		}
		if (ids.length > 0) {
			str.deleteCharAt(str.length() - 1);
		}
		return str.toString();
	}

}
