package com.desktop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体注解
 * 
 * @author wenyou
 *         <p>
 *         2013年9月25日 下午5:16:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) 
public @interface FieldInfo {

	public String name() default ""; //字段名称
	public String type() default ""; //字段类型
	
}
