package xzcode.ggserver.core.common.channel.group.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.channel.group.IChannelGroupManager;
import xzcode.ggserver.core.common.config.GGConfig;

/**
 * 默认通道组管理器
 * 
 * 
 * @author zai
 * 2019-11-03 20:37:16
 */
public class GGChannelGroupManager implements IChannelGroupManager{
	
	private Map<String, IChannelGroup> groups = new ConcurrentHashMap<>();
	
	private GGConfig config;

	public GGChannelGroupManager(GGConfig config) {
		this.config = config;
	}

	@Override
	public boolean hasChannelGroup(String channelGroupId) {
		return groups.containsKey(channelGroupId);
	}
	

	@Override
	public IChannelGroup addChannelGroupIfAbsent(IChannelGroup channelGroup) {
		return groups.putIfAbsent(channelGroup.getChannelGroupId(), channelGroup);
	}
	
	@Override
	public void addToChannelGroup(String channelGroupId, Channel channel) {
		IChannelGroup channelGroup = groups.get(channelGroupId);
		if (channelGroup == null) {
			synchronized (this) {
				if (channelGroup == null) {
					channelGroup = new GGChannelGroup(channelGroupId);		
				}
			}
		}
		channelGroup.addChannel(channel);
	}
	
	@Override
	public void removeFromChannelGroup(String channelGroupId, Channel channel) {
		IChannelGroup channelGroup = groups.get(channelGroupId);
		if (channelGroup != null) {
			channelGroup.removeChannel(channel);
		}
	}

	@Override
	public IChannelGroup getChannelGroup(String channelGroupId) {
		return groups.get(channelGroupId);
	}

	@Override
	public IChannelGroup removeChannelGroup(String channelGroupId) {
		return groups.remove(channelGroupId);
	}

	@Override
	public void removeChannelGroup(Channel channel) {
		String channelGroupId = (String) channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.CHANNEL_GROUP_ID)).get();
		groups.remove(channelGroupId);
	}
	
	@Override
	public IChannelGroup getChannelGroup(Channel channel) {
		String channelGroupId = (String) channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.CHANNEL_GROUP_ID)).get();
		return groups.get(channelGroupId);
	}


}
