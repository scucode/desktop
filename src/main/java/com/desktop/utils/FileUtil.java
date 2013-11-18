package com.desktop.utils;

import java.io.File;

/**
 * 文件工具类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:41:32
 */
public class FileUtil {

	/**
	 * 删除一个空目录
	 * 
	 * @param dir
	 */
	public static void doDeleteEmptyDir(String dir) {
		File f = new File(dir);
		f.delete();
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] childs = dir.list();
			for (int i = 0; i < childs.length; i++) {
				Boolean success = deleteDir(new File(dir, childs[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
