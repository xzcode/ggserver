package xzcode.ggserver.core.client.pool;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.pool.ChannelPool;
import io.netty.util.concurrent.Future;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

public interface IChannelPoolSendMessageSupport {
	
	/**
	 * 获取过滤器管理器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 16:34:01
	 */
	GGClientConfig getConfig();
	
	
	default IGGFuture poolSend(Pack pack, long delay, TimeUnit timeUnit) {

		// 序列化后发送过滤器
		if (!getConfig().getFilterManager().doAfterSerializeFilters(pack)) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		
		//以下通过通道池进行发送
		ChannelPool channelPool = getConfig().getChannelPool();
		Future<Channel> acquireFuture = channelPool.acquire();
		GGNettyFacadeFuture future = new GGNettyFacadeFuture();
		acquireFuture.addListener((Future<Channel> f) -> {
			if (!f.isDone()) {
				Throwable cause = f.cause();
				if (cause instanceof ConnectException) {
					GGLoggerUtil.getLogger().error(cause.getMessage());
				}else {
					GGLoggerUtil.getLogger().error("Cannot acquire channel!");					
				}
				return;
			}
			
			Channel ch = f.getNow();
			//TODO 归还channel
			if (ch.isActive()) {
				if (delay <= 0) {
					ChannelFuture channelFuture = ch.writeAndFlush(pack);
					future.setFuture((Future<?>) channelFuture);
				} else {
					getConfig().getTaskExecutor().schedule(delay, timeUnit, () -> {
						ChannelFuture channelFuture = ch.writeAndFlush(pack);
						future.setFuture((Future<?>) channelFuture);
					});
				}
			}
		});
		return future;
	}
	
}
