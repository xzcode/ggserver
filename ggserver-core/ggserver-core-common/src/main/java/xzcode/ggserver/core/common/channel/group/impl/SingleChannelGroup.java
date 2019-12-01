package xzcode.ggserver.core.common.channel.group.impl;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;

/**
 * 单通道通道组
 * 
 * 
 * @author zai
 * 2019-11-03 20:54:08
 */
public class SingleChannelGroup implements IChannelGroup {
	
	
	/**
	 * 通道组id
	 */
	private String channelGroupId;
	
	/**
	 * 通道
	 */
	private Channel channel;

	
	
	public SingleChannelGroup(String channelGroupId) {
		super();
		this.channelGroupId = channelGroupId;
	}
	
	
	




	public SingleChannelGroup(String channelGroupId, Channel channel) {
		super();
		this.channelGroupId = channelGroupId;
		this.channel = channel;
	}







	@Override
	public String getChannelGroupId() {
		return channelGroupId;
	}

	@Override
	public void addChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void removeChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void shutdown() {
		channel.disconnect();
	}


	@Override
	public Channel getRandomChannel() {
		return this.channel;
	}

	@Override
	public boolean isActive() {
		return this.channel.isActive();
	}

}
