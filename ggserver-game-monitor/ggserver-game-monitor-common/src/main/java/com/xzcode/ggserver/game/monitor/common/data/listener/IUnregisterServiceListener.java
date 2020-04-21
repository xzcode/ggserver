package com.xzcode.ggserver.game.monitor.common.data.listener;

import com.xzcode.ggserver.game.monitor.common.data.ServiceInfo;

/**
 * 取消注册服务监听器接口
 * 
 * @author zai
 * 2020-02-06 15:05:30
 */
public interface IUnregisterServiceListener {

	/**
	 * 取消注册服务时执行
	 * 
	 * @param service
	 * @author zai
	 * 2020-02-06 15:07:24
	 */
	void onUnregister(ServiceInfo service);
	
}
