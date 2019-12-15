package xzcode.ggserver.core.client.pool;

import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;

/**
 * 默认通道池处理器
 * 
 * @author zai
 * 2019-12-12 19:36:15
 */
public class DefaultChannelPoolHandler implements ChannelPoolHandler {

	@Override
	public void channelReleased(Channel ch) throws Exception {
		System.out.println("channelReleased----->");
	}

	@Override
	public void channelAcquired(Channel ch) throws Exception {
		System.out.println("channelAcquired----->");
	}

	@Override
	public void channelCreated(Channel ch) throws Exception {
		System.out.println("channelCreated----->");
	}

}
