package com.xzcode.ggcloud.session.group.common.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.factory.DefaultChannelSessionFactory;
import xzcode.ggserver.core.common.session.impl.DefaultChannelSession;

public class SessionGroupSessionFactory extends DefaultChannelSessionFactory{

	public SessionGroupSessionFactory(GGConfig config) {
		super(config);
	}

	@Override
	public void channelActive(Channel channel) {
		super.channelActive(channel);
		DefaultChannelSession session = (DefaultChannelSession) channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).get();
		session.setReady(false);
	}

	
}
