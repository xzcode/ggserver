package com.xzcode.ggserver.game.monitor.common.util;

import java.util.UUID;

/**
 * 注册中心服务id工具
 * 
 * @author zai
 * 2020-02-03 11:20:02
 */
public class DiscoveryServiceIdUtil {
	
	/**
	 * 新服务id
	 * 
	 * @return
	 * @author zai
	 * 2020-02-03 11:21:07
	 */
	public static String newServiceId() {
		return UUID.randomUUID().toString();
	}

}
