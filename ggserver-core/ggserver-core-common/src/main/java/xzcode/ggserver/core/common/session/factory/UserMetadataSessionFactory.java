package xzcode.ggserver.core.common.session.factory;

import java.util.concurrent.atomic.AtomicInteger;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.meta.UserMetadata;
import xzcode.ggserver.core.common.session.ChannelGroupSession;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.manager.ISessionManager;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 默认通道会话工厂
 * 
 * 
 * @author zai
 * 2019-11-16 11:02:22
 */
public class UserMetadataSessionFactory implements ISessionFactory{
	
	private GGConfig config; 
	private AtomicInteger counter = new AtomicInteger(0); 
	
	private String channelGroupIdPrefix = "default-";
	
	public UserMetadataSessionFactory(GGConfig config) {
		super();
		this.config = config;
	}

	@Override
	public GGSession getSession(Channel channel, Pack pack) {
		byte[] metadata = pack.getMetadata();
		ChannelGroupSession session = null;
		IChannelGroup channelGroup = config.getChannelGroupManager().getChannelGroup(channel);
		try {
			
			UserMetadata userMetadata = (UserMetadata) config.getMetadataResolver().resolve(metadata);
			if (userMetadata == null) {
				return null;
			}
			String userId = userMetadata.getUserId();
			ISessionManager sessionManager = config.getSessionManager();
			session = (ChannelGroupSession) sessionManager.getSession(userId);
			if (session == null) {
				session = new ChannelGroupSession(config, channelGroup);
				session = (ChannelGroupSession) sessionManager.addSessionIfAbsent(session);
			}
			session.updateExpire();
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("UserMetadataSessionFactory.getSession Error!", e);
		}
		return session;
	}
	@Override
	public GGSession getSession(Channel channel) {
		return null;
	}

	@Override
	public void channelActive(Channel channel) {
		
		//绑定通道组id到channel attrmap
		String channelGroupId = channelGroupIdPrefix + counter.incrementAndGet();
		config.getChannelGroupManager().addToChannelGroup(channelGroupId, channel);
	}

	@Override
	public void channelInActive(Channel channel) {
		config.getSessionManager().clearAllSession();
		config.getChannelGroupManager().removeChannelGroup(channel);
		
	}

}
