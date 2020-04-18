package com.xzcode.ggserver.docs.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模型文档注解
 * 
 * @author zai
 * 2018-12-30 11:15:06
 */
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DocsModel {
	
	
	/**
	 * action id
	 * 
	 * @return
	 * @author zai
	 * 2018-12-30 11:17:07
	 */
	public String actionId() default "";
	
	/**
	 * 说明
	 * 
	 * @return
	 * @author zai
	 * 2018-12-30 11:17:01
	 */
	public String desc();
}
