package com.xzcode.ggserver.core.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GGOnEvent {
	
	String value();

}
