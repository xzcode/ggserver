package com.xzcode.ggserver.core.common.session.id;

import io.netty.channel.Channel;

/**
 * 会话id生成器
 * 
 * @author zai
 * 2019-12-20 13:31:05
 */
public interface ISessionIdGenerator {
	
	/**
	 * 生成sessionid
	 * 
	 * @return
	 * @author zai
	 * 2019-12-20 13:31:16
	 */
	String generateSessionId(Channel channel);
	
}
