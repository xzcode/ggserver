package com.xzcode.ggserver.core.common.channel.group.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.xzcode.ggserver.core.common.channel.group.IChannelGroup;
import com.xzcode.ggserver.core.common.channel.group.IChannelGroupManager;

import io.netty.channel.Channel;

/**
 * 默认通道组管理器
 * 
 * 
 * @author zai
 * 2019-11-03 20:37:16
 */
public class DefaultChannelGroupManager implements IChannelGroupManager{
	
	private ConcurrentMap<String, IChannelGroup> channelGroups = new ConcurrentHashMap<>(100);

	@Override
	public boolean hasChannelGroup(String channelGroupId) {
		return channelGroups.containsKey(channelGroupId);
	}

	@Override
	public IChannelGroup getChannelGroup(String channelGroupId) {
		return channelGroups.get(channelGroupId);
	}

	
	@Override
	public IChannelGroup removeChannelGroup(String channelGroupId) {
		return channelGroups.remove(channelGroupId);
	}

	
	@Override
	public void addToChannelGroup(String channelGroupId, Channel channel) {
		IChannelGroup putChannelGroup;
		if (!channelGroups.containsKey(channelGroupId)) {
			DefaultChannelGroup defaultChannelGroup = new DefaultChannelGroup(channelGroupId);
			putChannelGroup = channelGroups.putIfAbsent(channelGroupId, defaultChannelGroup);
			if (putChannelGroup == null) {
				putChannelGroup = defaultChannelGroup;
			}
		}else {
			putChannelGroup = channelGroups.get(channelGroupId);
		}
		putChannelGroup.addChannel(channel);
	}

	@Override
	public void removeFromChannelGroup(String channelGroupId, Channel channel) {
		IChannelGroup channelGroup = channelGroups.get(channelGroupId);
		if (channelGroup != null) {
			channelGroup.removeChannel(channel);
		}
	}

	@Override
	public IChannelGroup addChannelGroupIfAbsent(IChannelGroup channelGroup) {
		String channelGroupId = channelGroup.getChannelGroupId();
		IChannelGroup putChannelGroup;
		if (!channelGroups.containsKey(channelGroupId)) {
			DefaultChannelGroup defaultChannelGroup = new DefaultChannelGroup(channelGroupId);
			putChannelGroup = channelGroups.putIfAbsent(channelGroupId, defaultChannelGroup);
		}else {
			putChannelGroup = channelGroups.get(channelGroupId);
		}
		return putChannelGroup;
	}

	@Override
	public void removeChannelGroup(IChannelGroup channelGroup) {
		channelGroups.remove(channelGroup.getChannelGroupId());
	}

	@Override
	public Channel getRandomChannel(String channelGroupId) {
		IChannelGroup channelGroup = getChannelGroup(channelGroupId);
		if (channelGroup != null) {
			return channelGroup.getRandomChannel();
		}
		return null;
	}



}
