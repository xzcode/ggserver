package com.xzcode.ggserver.core.common.session.listener;

import com.xzcode.ggserver.core.common.session.GGSession;

/**
 * 会话断开连接监听器
 * 
 * @author zai
 * 2019-12-19 17:36:05
 */
public interface ISessionDisconnectListener {
	
	/**
	 * 断开处理
	 * 
	 * @param session
	 * @author zai
	 * 2019-12-19 17:36:44
	 */
	void onDisconnect(GGSession session);
	
}
