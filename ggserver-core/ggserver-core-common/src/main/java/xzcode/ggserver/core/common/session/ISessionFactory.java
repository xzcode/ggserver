package xzcode.ggserver.core.common.session;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.GGConfig;

public interface ISessionFactory {
	
	GGSession createSession(GGConfig config, Channel channel);
	
}
