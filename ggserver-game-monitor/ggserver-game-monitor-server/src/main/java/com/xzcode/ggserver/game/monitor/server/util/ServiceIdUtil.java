package com.xzcode.ggserver.game.monitor.server.util;

import java.util.UUID;

public class ServiceIdUtil {
	
	public static String newServiceId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
}
