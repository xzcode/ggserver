package xzcode.ggserver.core.common.session.factory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 会话工厂接口
 * 
 * 
 * @author zai
 * 2019-11-16 11:01:05
 */
public interface ISessionFactory {
	
	GGSession getSession(Channel channel, Pack pack);
	
	GGSession getSession(Channel channel);
	
	GGSession channelActive(Channel channel);
	
}
