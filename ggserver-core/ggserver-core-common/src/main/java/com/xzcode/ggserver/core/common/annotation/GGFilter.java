package com.xzcode.ggserver.core.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GGFilter {
	
	/**
	 * 序号，作用与 order字段一样
	 * @return
	 * 
	 * @author zai
	 * 2017-09-27
	 */
	 int value() default 0;
	 
	 /**
	  * 序号
	  * @return
	  * 
	  * @author zai
	  * 2017-09-27
	  */
	 int order() default 0;
	 
	 int type() default 0;
	
}
