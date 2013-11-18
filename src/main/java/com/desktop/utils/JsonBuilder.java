package com.desktop.utils;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.desktop.constant.ExtFieldType;
import com.desktop.constant.StringVeriable;
import com.desktop.model.ExtFieldVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON数据格式工具类
 * 
 * @author wenyou <br />
 *         2013年11月18日 下午3:23:26
 */
public class JsonBuilder {

	/**
	 * 得到JsonBuilder实例化对象
	 * 
	 * @return
	 */
	public static JsonBuilder getInstance() {
		return JsonHolder.JSON_BUILDER;
	}

	/**
	 * 静态内部类
	 * 
	 * @author wenyou
	 *         <p>
	 *         2013年9月25日 下午12:25:24
	 */
	private static class JsonHolder {
		private static final JsonBuilder JSON_BUILDER = new JsonBuilder();
		private static ObjectMapper mapper = new ObjectMapper();
	}

	/**
	 * 将一个数据实体解析成Json数据格式
	 * 
	 * @return
	 */
	public String toJson(Object obj) {
		try {
			return JsonHolder.mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将一个Json字符串封装为指定类型对象
	 * 
	 * @param obj
	 * @return
	 */
	public Object fromJson(String json, Class<?> c) {
		json = cleanJson(json);
		Object obj = null;
		try {
			obj = JsonHolder.mapper.readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 将一个JsonArray数据转换成一个List的键值对 [{name:'zsp',value:1},{name:'zsp',value:2}]
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> fromJsonArray(String json) {
		json = cleanJson(json);
		List<Map> dataList = (List<Map>) fromJson(json, ArrayList.class);

		return dataList;
	}

	/**
	 * 为操作成功返回Json
	 * 
	 * @param strData
	 * @return
	 */
	public String returnSuccessJson(String strData) {
		StringBuffer returnJson = new StringBuffer("{ success : true, obj : ");
		returnJson.append(strData);
		returnJson.append("}");
		return returnJson.toString();
	}

	/**
	 * 为操作失败返回Json
	 * 
	 * @param strData
	 * @return
	 */
	public String returnFailureJson(String strData) {
		StringBuffer returnJson = new StringBuffer("{ success : false, obj : ");
		returnJson.append(strData);
		returnJson.append("}");
		return returnJson.toString();
	}

	/**
	 * 为分页列表提供Json封装
	 * 
	 * @param count
	 * @param records
	 * @param listJson
	 * @return
	 */
	public String buildObjListToJson(Long count,
			Collection<? extends Object> records, boolean listJson) {
		try {
			StringBuffer pageJson = null;
			// 判断是否展示list的数据
			if (listJson) {
				pageJson = new StringBuffer("{totalCount:" + count + ","
						+ "rows" + ":");
			} else {
				pageJson = new StringBuffer("");
			}
			// 构建集合的json数据
			StringWriter w = new StringWriter();
			JsonHolder.mapper.writeValue(w, records);
			pageJson.append(w);
			w.close();

			if (listJson) {
				pageJson.append("}");
			} else {
				pageJson.append("");
			}
			return pageJson.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 移除掉Json字符串中的换行
	 * 
	 * @param json
	 * @return
	 */
	public String cleanJson(String json) {
		if (StringUtil.isNotEmpty(json)) {
			return json.replaceAll("\n", "");
		} else {
			return "";
		}
	}

	/**
	 * 获取json字符串中特定的sql语句
	 * 
	 * @param jsonSql
	 * @return
	 */
	public String[] jsonSqlToString(String jsonSql) {
		// 得到对象数据
		Object[] os = JSONArray.fromObject(jsonSql).toArray();
		String[] sqls = new String[os.length];
		for (int i = 0; i < os.length; i++) {
			// 使用JSONObject和sql键取出值
			JSONObject k = (JSONObject) os[i];
			String kk = (String) k.get("sql");
			sqls[i] = kk;
		}
		return sqls;
	}

	/**
	 * 构建类的ExtJs的fields字段数据
	 * 
	 * @param modelName
	 * @param fields
	 * @param excludes
	 * @return
	 */
	public String getModelFileds(String modelName, Field[] fields,
			String excludes) {
		List<ExtFieldVo> lists = new ArrayList<>();
		for (Field f : fields) {
			String[] excludesArray = excludes.split(StringVeriable.STR_SPLIT);
			Boolean flag = false;
			for (String exclude : excludesArray) {
				if (f.equals(exclude)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				continue;
			}
			String fieldType = f.getType().getSimpleName().toLowerCase();
			Boolean excludeFlag = false;
			if (fieldType.equals("double")) {
				fieldType = ExtFieldType.FLOAT;
			} else if (fieldType.equals("long")) {
				fieldType = ExtFieldType.INT;
			} else if (fieldType.equals("bigdecimal")) {
				fieldType = ExtFieldType.INT;
			} else if (fieldType.equals("timestamp")) {
				fieldType = ExtFieldType.STRING;
			} else if (fieldType.equals("date")) {
				fieldType = ExtFieldType.STRING;
			} else if (fieldType.equals("integer")) {
				fieldType = ExtFieldType.INT;
			} else if (fieldType.equals("string")) {
				fieldType = ExtFieldType.STRING;
			} else {
				excludeFlag = true;
			}
			ExtFieldVo vo = new ExtFieldVo(f.getName(), fieldType);
			if (!excludeFlag) {
				lists.add(vo);
			}
		}
		String strData = toJson(lists);
		ModelUtil.modelJson.put(modelName, strData);
		return strData;
	}

}
