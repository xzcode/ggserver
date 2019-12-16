package xzcode.ggserver.core.common.session.factory;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.EventTask;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.session.DefaultChannelSession;
import xzcode.ggserver.core.common.session.GGSession;
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
		session.updateExpire();
		return session;
	}
	
	@Override
	public GGSession getSession(Channel channel, Request<?> request) {
		return request.getSession();
	}

	@Override
	public void channelActive(Channel channel) {
		//初始化session
		DefaultChannelSession session = new DefaultChannelSession(channel, channel.id().asLongText(), config);
		
		channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).set(session);
		
		config.getSessionManager().addSessionIfAbsent(session);
		
		config.getTaskExecutor().submitTask(new EventTask(session, GGEvents.Connection.OPENED, null, config));
	}

	@Override
	public void channelInActive(Channel channel) {
		GGSession session = getSession(channel);
		if (GGLoggerUtil.getLogger().isDebugEnabled()) {
			GGLoggerUtil.getLogger().debug("Channel Close:{}",channel);
		}
		config.getTaskExecutor().submitTask(new EventTask(session, GGEvents.Connection.CLOSED, null, config));
		config.getSessionManager().remove(session.getSessonId());
	}
	
	

}
