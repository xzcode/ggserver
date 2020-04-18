package com.xzcode.ggserver.core.common.channel.group.impl;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.xzcode.ggserver.core.common.channel.group.IChannelGroup;

import io.netty.channel.Channel;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * 默认通道组
 * 
 * 
 * @author zai
 * 2019-11-03 20:54:08
 */
public class DefaultChannelGroup implements IChannelGroup {
	
	/**
	 * 通道集合
	 */
	private Set<Channel> channels = new CopyOnWriteArraySet<>();
	
	/**
	 * 通道组id
	 */
	private String channelGroupId;
	
	
	public DefaultChannelGroup(String channelGroupId) {
		this.channelGroupId = channelGroupId;
	}

	@Override
	public String getChannelGroupId() {
		return channelGroupId;
	}

	@Override
	public void addChannel(Channel channel) {
		channels.add(channel);
		channel.closeFuture().addListener((f) -> {
			removeChannel(channel);
		});
	}

	@Override
	public void removeChannel(Channel channel) {
		channels.remove(channel);
	}


	@Override
	public Channel getRandomChannel() {
		if (channels.size() == 0) {
			return null;
		}
		Object[] array = channels.toArray();
		if (channels.size() == 1) {
			return (Channel) array[0];
		}
		return  (Channel) array[ThreadLocalRandom.current().nextInt(0, channels.size())];
	}


}
