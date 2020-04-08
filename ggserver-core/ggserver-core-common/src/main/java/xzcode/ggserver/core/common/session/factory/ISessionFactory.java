package xzcode.ggserver.core.common.session.factory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.message.MessageData;
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
	
	/**
	 * 获取会话
	 * 
	 * @param channel
	 * @param request
	 * @return
	 * @author zai
	 * 2019-12-16 16:47:20
	 */
	GGSession getSession(Channel channel, MessageData<?> request);
	
	/**
	 * 获取会话
	 * @param channel 通道
	 * @param pack 数据包
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 21:50:36
	 */
	GGSession getSession(Channel channel, Pack pack);
	
	/**
	 * 获取会话
	 * @param channel 通道
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 21:51:00
	 */
	GGSession getSession(Channel channel);
	
	/**
	 * 通道激活
	 * @param channel
	 * 
	 * @author zai
	 * 2019-11-24 21:51:12
	 */
	void channelActive(Channel channel);
	
	/**
	 * 通道失效
	 * @param channel
	 * 
	 * @author zai
	 * 2019-11-24 21:51:21
	 */
	void channelInActive(Channel channel);

	
}
