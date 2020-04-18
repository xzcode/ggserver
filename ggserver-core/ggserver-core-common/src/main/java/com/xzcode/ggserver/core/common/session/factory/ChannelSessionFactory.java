package com.xzcode.ggserver.core.common.session.factory;

import com.xzcode.ggserver.core.common.session.GGSession;

import io.netty.channel.Channel;

/**
 * 会话工厂接口
 * 
 * 
 * @author zai
 * 2019-11-16 11:01:05
 */
public interface ChannelSessionFactory {
	
	
	/**
	 * 获取会话
	 * @param channel 通道
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 21:51:00
	 */
	GGSession getSession(Channel channel);
	
	/**
	 * 通道激活
	 * @param channel
	 * 
	 * @author zai
	 * 2019-11-24 21:51:12
	 */
	void channelActive(Channel channel);
	
	/**
	 * 通道失效
	 * @param channel
	 * 
	 * @author zai
	 * 2019-11-24 21:51:21
	 */
	void channelInActive(Channel channel);

	
}
