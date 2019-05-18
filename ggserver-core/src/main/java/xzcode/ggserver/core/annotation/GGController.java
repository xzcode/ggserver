package xzcode.ggserver.core.annotation;

import java.lang.annotation.*;

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
