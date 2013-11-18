package com.desktop.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.desktop.annotation.FieldInfo;

/**
 * 实体工具类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:50:12
 */
public class ModelUtil {

	public static Map<String, Field[]> modelFields = new HashMap<String, Field[]>();
	public static Map<String, String> modelJson = new HashMap<String, String>();

	/**
	 * 判断实体不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static Boolean isNotNull(Object obj) {
		if (obj != null) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 得到类的属性集合
	 * 
	 * @param c
	 * @param itself
	 *            是否是自身的字段
	 * @return
	 */
	public static Field[] getClassFields(Class<?> c, boolean itself) {
		if (itself) {
			if (modelFields.get(c.getName()) != null) {
				return modelFields.get(c.getName());
			} else {
				Field[] fields = c.getDeclaredFields();
				modelFields.put(c.getName(), fields);
				return fields;
			}
		} else {
			if (modelFields.get(c.getName()) != null) {
				return modelFields.get(c.getName());
			} else {
				List<Field> fields = new ArrayList<Field>();
				getAllDeclaredFields(c, fields);
				Field[] fies = new Field[fields.size()];
				fields.toArray(fies);
				modelFields.put(c.getName(), fies);
				return fies;
			}
		}
	}

	/**
	 * 从c类中取得全部字段,包括父类
	 * 
	 * @param c
	 * @param fields
	 */
	public static void getAllDeclaredFields(Class<?> c, List<Field> fields) {
		Field[] fies = c.getDeclaredFields();
		Collections.addAll(fields, fies);
		Class<?> parent = c.getSuperclass();
		if (parent != Object.class) {
			getAllDeclaredFields(parent, fields);
		} else {
			return;
		}
	}
	
	/**
	 * 获取实体主键
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassPkName(Class<?> clazz) {
		Field[] fields = getClassFields(clazz, true);
		String pkName = "";
		for (Field f : fields) {
			FieldInfo fieldInfo = f.getAnnotation(FieldInfo.class);
			if (fieldInfo != null && "ID".equals(fieldInfo.type())) {
				pkName = f.getName();
				break;
			}
		}
		return pkName;
	}
	
}
