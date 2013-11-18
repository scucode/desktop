package com.desktop.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 配置文件工具类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:37:50
 */
public class PropUtil {

	/**
	 * 根据key获取配置的值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		try {
			Properties prop = PropertiesLoaderUtils
					.loadAllProperties("sysconfig.properties");
			return prop.getProperty(key, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
