package com.xzcode.ggserver.core.common.session.factory;

import java.net.InetSocketAddress;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.session.GGSession;
import com.xzcode.ggserver.core.common.session.impl.DefaultChannelSession;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 默认通道会话工厂
 * 
 * 
 * @author zai
 * 2019-11-16 11:02:22
 */
public class DefaultChannelSessionFactory implements ChannelSessionFactory{
	
	protected GGConfig config; 
	
	protected AttributeKey<GGSession> sessAttributeKey = AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION);
	
	public DefaultChannelSessionFactory(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public GGSession getSession(Channel channel) {
		GGSession session = channel.attr(sessAttributeKey).get();
		return session;
	}
	

	@Override
	public void channelActive(Channel channel) {
		//初始化session
		DefaultChannelSession session = new DefaultChannelSession(channel, config.getSessionIdGenerator().generateSessionId(channel), config);
		InetSocketAddress remoteAddress = (InetSocketAddress)channel.remoteAddress();
		session.setHost(remoteAddress.getHostString());
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
