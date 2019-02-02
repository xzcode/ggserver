package com.xzcode.socket.core.message;

/**
 * 动态请求消息绑定接口
 * 
 * @param <T>
 * @author zai
 * 2019-01-01 22:09:24
 */
public interface SocketOnMessage<T> {

	void onMessage(T t);
	
}
