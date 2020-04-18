package com.xzcode.ggserver.core.common.utils.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GGLoggerUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(GGLoggerUtil.class);
	
	
	public static Logger getLogger() {
		return LOGGER;
		 
	}
	public static Logger getLogger(Object object) {
		return LoggerFactory.getLogger(object.getClass());
		
	}
	
	
}
