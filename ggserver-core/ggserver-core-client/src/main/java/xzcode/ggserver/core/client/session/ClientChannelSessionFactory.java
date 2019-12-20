package xzcode.ggserver.core.client.session;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
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
		//初始化session
		ClientChannelSession session = new ClientChannelSession(channel, config.getSessionIdGenerator().generateSessionId(channel), config);
		InetSocketAddress remoteAddress = (InetSocketAddress)channel.remoteAddress();
		session.setHost(remoteAddress.getHostName());
		session.setPort(remoteAddress.getPort());
		channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).set(session);
				
		config.getSessionManager().addSessionIfAbsent(session);
	}

	

}
