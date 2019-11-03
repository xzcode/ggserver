package xzcode.ggserver.core.common.channel.group.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.netty.channel.Channel;
import io.netty.util.internal.ThreadLocalRandom;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;

/**
 * 默认通道组
 * 
 * 
 * @author sai
 * 2019-11-03 20:54:08
 */
public class GGChannelGroup implements IChannelGroup {
	
	
	/**
	 * 通道集合
	 */
	private List<Channel> channels = new CopyOnWriteArrayList<>();
	
	
	/**
	 * 通道组id
	 */
	private String channelGroupId;
	
	/**
	 * 是否关闭
	 */
	private boolean shutdown;

	
	public GGChannelGroup(String channelGroupId) {
		super();
		this.channelGroupId = channelGroupId;
	}
	
	

	@Override
	public String getChannelGroupId() {
		return channelGroupId;
	}

	@Override
	public void addChannel(Channel channel) {
		synchronized (this) {
			if (shutdown) {
				channel.disconnect();
				return;
			}
			channels.add(channel);
		}
	}

	@Override
	public void removeChannel(Channel channel) {
		channels.remove(channel);
	}

	@Override
	public void shutdown() {
		for (Channel channel : channels) {
			channel.disconnect();
			channel.connect(channel.r);
		}
		channels.clear();
	}


	@Override
	public Channel getRandomChannel() {
		if (channels.size() == 0) {
			return null;
		}
		if (channels.size() == 1) {
			return channels.get(0);
		}
		return  channels.get(ThreadLocalRandom.current().nextInt(0, channels.size()));
	}

	@Override
	public boolean isActive() {
		if (channels.size() == 0) {
			return false;
		}
		if (shutdown) {
			return false;
		}
		return true;
	}

}
