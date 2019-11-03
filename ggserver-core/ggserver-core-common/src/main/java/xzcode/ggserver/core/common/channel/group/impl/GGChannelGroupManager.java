package xzcode.ggserver.core.common.channel.group.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.channel.group.IChannelGroupManager;

/**
 * 默认通道组管理器
 * 
 * 
 * @author sai
 * 2019-11-03 20:37:16
 */
public class GGChannelGroupManager implements IChannelGroupManager{
	
	private Map<String, IChannelGroup> groups = new ConcurrentHashMap<>();
	

	@Override
	public boolean hasChannelGroup(String channelGroupId) {
		return groups.containsKey(channelGroupId);
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

}
