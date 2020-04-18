package com.xzcode.ggserver.core.common.channel.group;

import io.netty.channel.Channel;

/**
 * 通道组
 * 
 * 
 * @author zai
 * 2019-11-03 20:42:50
 */
public interface IChannelGroup{
	
	/**
	 * 获取通道组id
	 * @return
	 * 
	 * @author zai
	 * 2019-11-03 20:42:57
	 */
	String getChannelGroupId();
	
	/**
	 * 添加通道
	 * @param channel
	 * 
	 * @author zai
	 * 2019-11-03 20:43:06
	 */
	void addChannel(Channel channel);
	
	/**
	 * 移除通道
	 * @param channel
	 * 
	 * @author zai
	 * 2019-11-03 20:43:14
	 */
	void removeChannel(Channel channel);
	
	/**
	 * 随机获取一个通道
	 * @return
	 * 
	 * @author zai
	 * 2019-11-03 22:12:24
	 */
	Channel getRandomChannel();

}
