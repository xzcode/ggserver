package com.xzcode.socket.core.filter;

/**
 * 消息过滤器接口
 *
 * @author zai
 * 2018-12-20 10:16:13
 */
public interface SocketMessageFilter {
	
	public boolean doFilter(String actionId, Object message);

}
