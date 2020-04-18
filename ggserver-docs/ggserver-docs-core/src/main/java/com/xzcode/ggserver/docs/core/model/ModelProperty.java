package com.xzcode.ggserver.docs.core.model;

import java.lang.reflect.Field;

/**
 * 扫描到的注解的模型
 * 
 * @author zai
 * 2018-12-30 11:48:07
 */
public class ModelProperty {
	
	/**
	 * 属性对象
	 */
	private Field field;
	
	/**
	 * 参数名
	 */
	private String name;
	
	/**
	 * 说明
	 */
	private String desc;
	
	/**
	 * 数据类型
	 */
	private String dataType;
	
	/**
	 * 额外要求
	 */
	private String extra;
	
	/**
	 * 是否必须
	 */
	private boolean required;
	
	/**
	 * 最小长度
	 */
	private Integer minLength;
	
	/**
	 * 最大长度
	 */
	private Integer maxLength;
	
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public String getExtra() {
		return extra;
	}
	
}
