package xzcode.ggserver.core.common.session.factory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.EventTask;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.message.Pack;
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
	
	private GGConfig config; 
	
	private AttributeKey<GGSession> sessAttributeKey = AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION);
	
	
	public DefaultChannelSessionFactory(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public GGSession getSession(Channel channel, Pack pack) {
		GGSession session = channel.attr(sessAttributeKey).get();
		session.updateExpire();
		return session;
	}
	@Override
	public GGSession getSession(Channel channel) {
		return getSession(channel, null);
	}

	@Override
	public GGSession channelActive(Channel channel) {
		//初始化session
		DefaultChannelSession session = new DefaultChannelSession(config, channel);
		
		channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).set(session);
		
		config.getSessionManager().addSessionIfAbsent(session);
		
		config.getTaskExecutor().submit(new EventTask(session, GGEvents.Connection.OPEN, null, config));
		
		//注册channel关闭事件
		channel.closeFuture().addListener((ChannelFuture future) -> {
			
			if (GGLoggerUtil.getLogger().isDebugEnabled()) {
				GGLoggerUtil.getLogger().debug("Channel Close:{}",channel);
			}
			
			config.getTaskExecutor().submit(new EventTask(session, GGEvents.Connection.CLOSE, null, config));
			config.getSessionManager().remove(session.getSessonId());
		});
		
		return null;
	}

}
