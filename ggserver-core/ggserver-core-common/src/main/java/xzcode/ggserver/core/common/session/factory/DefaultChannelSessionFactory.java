package xzcode.ggserver.core.common.session.factory;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.EventTask;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.id.DefaultSessionIdGenerator;
import xzcode.ggserver.core.common.session.id.ISessionIdGenerator;
import xzcode.ggserver.core.common.session.impl.DefaultChannelSession;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 默认通道会话工厂
 * 
 * 
 * @author zai
 * 2019-11-16 11:02:22
 */
public class DefaultChannelSessionFactory implements ISessionFactory{
	
	protected GGConfig config; 
	
	protected AttributeKey<GGSession> sessAttributeKey = AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION);
	
	public DefaultChannelSessionFactory(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public GGSession getSession(Channel channel, Pack pack) {
		return getSession(channel);
	}
	@Override
	public GGSession getSession(Channel channel) {
		GGSession session = channel.attr(sessAttributeKey).get();
		if (session != null) {
			session.updateExpire();			
		}
		return session;
	}
	
	@Override
	public GGSession getSession(Channel channel, Request<?> request) {
		GGSession session = request.getSession();
		if (session != null) {
			session.updateExpire();
		}
		return session;
	}

	@Override
	public void channelActive(Channel channel) {
		//初始化session
		DefaultChannelSession session = new DefaultChannelSession(channel, config.getSessionIdGenerator().generateSessionId(channel), config);
		InetSocketAddress remoteAddress = (InetSocketAddress)channel.remoteAddress();
		session.setHost(remoteAddress.getHostName());
		session.setPort(remoteAddress.getPort());
		session.setReady(true);
		channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).set(session);
		
		config.getSessionManager().addSessionIfAbsent(session);
		
	}

	@Override
	public void channelInActive(Channel channel) {
		GGSession session = getSession(channel);
		if (session != null) {
			config.getSessionManager().remove(session.getSessonId());
		}
	}

}
