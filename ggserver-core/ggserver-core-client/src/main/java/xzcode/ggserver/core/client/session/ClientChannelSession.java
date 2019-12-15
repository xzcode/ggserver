package xzcode.ggserver.core.client.session;

import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.DefaultChannelSession;

/**
 * 客户端会后
 * 
 * @author zai
 * 2019-12-15 18:10:39
 */
public class ClientChannelSession extends DefaultChannelSession{
	
	protected GGClientConfig config;
	
	public ClientChannelSession(Channel channel, String sessionId, GGClientConfig config) {
		super(channel, sessionId, config);
		this.config = config;
	}

	@Override
	public IGGFuture send(Pack pack, long delay, TimeUnit timeUnit) {
		
		if (!config.isChannelPoolEnabled()) {
			return super.send(pack, delay, timeUnit);
		}
		
		// 序列化后发送过滤器
		if (!getFilterManager().doAfterSerializeFilters(pack)) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		
		//以下通过通道池进行发送
		Future<Channel> acquireFuture = config.getChannelPool().acquire();
		GGNettyFacadeFuture future = new GGNettyFacadeFuture();
		acquireFuture.addListener((Future<Channel> f) -> {
			Channel ch = f.getNow();
			if (ch.isActive()) {
				if (delay <= 0) {
					ChannelFuture channelFuture = ch.writeAndFlush(pack);
					future.setFuture((Future<?>) channelFuture);

				} else {
					getTaskExecutor().schedule(delay, timeUnit, () -> {
						ChannelFuture channelFuture = ch.writeAndFlush(pack);
						future.setFuture((Future<?>) channelFuture);
					});
				}
			}
		});
		return future;
	}

}
