package xzcode.ggserver.core.common.session.factory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.channel.group.IChannelGroup;
import xzcode.ggserver.core.common.channel.group.impl.GGChannelGroup;
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
public class UserIdMetadataSessionFactory implements ISessionFactory{
	
	private GGConfig config; 
	
	private String channelGroupId = "default";
	private IChannelGroup channelGroup = new GGChannelGroup(channelGroupId);
	
	public UserIdMetadataSessionFactory(GGConfig config) {
		super();
		this.config = config;
		config.getChannelGroupManager().addChannelGroupIfAbsent(channelGroup);
	}

	@Override
	public GGSession getSession(Channel channel, Pack pack) {
		byte[] metadata = pack.getMetadata();
		ChannelGroupSession session = null;
		try {
			UserMetadata userMetadata = config.getSerializer().deserialize(metadata, UserMetadata.class);
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
			GGLoggerUtil.getLogger().error("UserIdMetadataSessionFactory.getSession Error!", e);
		}
		return session;
	}
	@Override
	public GGSession getSession(Channel channel) {
		return null;
	}

	@Override
	public GGSession channelActive(Channel channel) {
		return null;
	}

}
