package com.xzcode.ggserver.docs.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模型属性说明注解
 * 
 * @author zai
 * 2018-12-30 11:09:24
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DocsModelProperty {
	
	/**
	 * 说明
	 * 
	 * @return
	 * @author zai
	 * 2018-12-30 11:38:35
	 */
	public String value();
	
	
}
