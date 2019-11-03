package xzcode.ggserver.core.common.session.imp;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.ISessionFactory;

public class DefaultSessionFactory implements ISessionFactory {

	@Override
	public GGSession createSession(GGConfig config, Channel channel) {
		return new DefaultSession(config, channel);
	}

}
