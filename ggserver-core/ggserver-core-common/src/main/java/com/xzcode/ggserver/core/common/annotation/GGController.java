package com.xzcode.ggserver.core.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@GGComponent
public @interface GGController{
	
	/**
	 * actionid前缀
	 * 
	 * @return
	 * @author zai
	 * 2019-05-18 21:25:32
	 */
	String value() default "";
	
	/**
	 * actionid前缀，等同value值，优先选择此值
	 * 
	 * @return
	 * @author zai
	 * 2019-05-18 21:24:34
	 */
	String actionIdPrefix() default "";
}
