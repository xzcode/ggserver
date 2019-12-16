package xzcode.ggserver.core.client.session;

import io.netty.channel.Channel;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;

/**
 * 默认通道会话工厂
 * 
 * 
 * @author zai
 * 2019-11-16 11:02:22
 */
public class ClientChannelSessionFactory extends DefaultChannelSessionFactory{
	
	protected GGClientConfig config; 

	public ClientChannelSessionFactory(GGClientConfig config) {
		super(config);
		this.config = config;
	}
	@Override
	public GGSession getSession(Channel channel, Pack pack) {
		if (!config.isChannelPoolEnabled()) {
			return super.getSession(channel, pack);
		}
		return null;
	}

	@Override
	public void channelActive(Channel channel) {
		if (!config.isChannelPoolEnabled()) {
			super.channelActive(channel);
		}
	}

	@Override
	public void channelInActive(Channel channel) {
		if (!config.isChannelPoolEnabled()) {
			super.channelInActive(channel);
		}
	}
	
	

}
