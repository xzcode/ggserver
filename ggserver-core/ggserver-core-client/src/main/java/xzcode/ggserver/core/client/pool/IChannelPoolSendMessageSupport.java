package xzcode.ggserver.core.client.pool;

import java.net.ConnectException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.pool.ChannelPool;
import io.netty.util.concurrent.Future;
import xzcode.ggserver.core.client.config.GGClientConfig;
import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.GGNettyFuture;
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
	
	/**
	 * 通过连接池发送
	 * 
	 * @param pack
	 * @param delay
	 * @param timeUnit
	 * @return
	 * @author zai
	 * 2019-12-16 12:35:38
	 */
	default IGGFuture poolSend(Pack pack) {

		// 序列化后发送过滤器
		if (!getConfig().getFilterManager().doAfterSerializeFilters(pack)) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		
		//以下通过通道池进行发送
		ChannelPool channelPool = getConfig().getChannelPool();
		GGNettyFuture future = new GGNettyFuture();
		handlePoolSend(channelPool, pack, future);
		return future;
	}
	
	/**
	 * 处理详细发送过程
	 * 
	 * @param channelPool
	 * @param pack
	 * @param returningFuture
	 * @return
	 * @author zai
	 * 2019-12-16 12:35:47
	 */
	default IGGFuture handlePoolSend(final ChannelPool channelPool, Pack pack, GGNettyFuture returningFuture) {
		Future<Channel> acquireFuture = channelPool.acquire();
		acquireFuture.addListener((Future<Channel> f) -> {
				Channel ch = f.getNow();
				if (!f.isDone() || ch == null) {
					Throwable cause = f.cause();
					if (cause instanceof ConnectException) {
						GGLoggerUtil.getLogger(this).error(cause.getMessage());
					}else {
						GGLoggerUtil.getLogger(this).error("Cannot acquire channel from channel pool!", cause);					
					}
					return;
				}
				ChannelFuture channelFuture = ch.writeAndFlush(pack);
				returningFuture.setFuture(channelFuture);
				channelPool.release(ch);
				
		});
		return returningFuture;
	}
	
}
