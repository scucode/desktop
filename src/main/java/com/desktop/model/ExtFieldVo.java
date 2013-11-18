package com.desktop.model;

/**
 * 定义ExtJs模型字段的VO
 * 
 * @author wenyou
 *         <p>
 *         2013年9月28日 下午5:18:51
 */
public class ExtFieldVo {

	// 字段的名称
	private String name;
	// 字段的类型
	private String type;

	public ExtFieldVo(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
