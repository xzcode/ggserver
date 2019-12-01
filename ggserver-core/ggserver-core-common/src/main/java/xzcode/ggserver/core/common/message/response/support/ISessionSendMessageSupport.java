package xzcode.ggserver.core.common.message.response.support;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.response.Response;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.json.GGServerJsonUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai 2019-02-09 14:50:27
 */
public interface ISessionSendMessageSupport extends IMakePackSupport, IGGConfigSupport {

	
	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param action
	 * @param message
	 * @return
	 * @author zai
	 * 2019-11-29 15:26:11
	 */
	default IGGFuture<?> send(GGSession session, String action, Object message) {
		return send(new Response(session, null, action, message), 0L, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param action
	 * @param message
	 * @param delay
	 * @param timeUnit
	 * @return
	 * @author zai
	 * 2019-11-29 15:26:18
	 */
	default IGGFuture<?> send(GGSession session, String action, Object message, long delay, TimeUnit timeUnit) {
		return send(new Response(session, null, action, message), delay, timeUnit);
	}
	
	/**
	 * 发送消息
	 * 
	 * @param response 响应消息
	 * @param delay    延迟时间
	 * @param timeUnit 时间单位
	 * @return
	 * 
	 * @author zai 2019-11-24 17:29:24
	 */
	default IGGFuture<?> send(Response response, long delay, TimeUnit timeUnit) {
		return send(makePack(response), delay, timeUnit);
	}
	
	/**
	 * 发送消息
	 * @param pack
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 16:45:12
	 */
	default IGGFuture<?> send(Pack pack) {
		return send(pack, 0, TimeUnit.MILLISECONDS);
	}

	/**
	 * 发送消息
	 * 
	 * @param pack
	 * @param delay
	 * @param timeUnit
	 * @return
	 * 
	 * @author zai 2019-11-24 23:08:36
	 */
	default IGGFuture<?> send(Pack pack, long delay, TimeUnit timeUnit) {
		GGSession session = pack.getSession();
		if (!session.isActive()) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		Channel channel = getSession().getChannel();

		// 序列化后发送过滤器
		if (!getConfig().getFilterManager().doAfterSerializeFilters(pack)) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		if (channel.isActive()) {
			GGNettyFacadeFuture<?> future = new GGNettyFacadeFuture<>();
			if (delay <= 0) {
				ChannelFuture channelFuture = channel.writeAndFlush(pack);
				future.setFuture((Future<?>) channelFuture);

			} else {
				getConfig().getTaskExecutor().schedule(delay, timeUnit, () -> {
					ChannelFuture channelFuture = channel.writeAndFlush(pack);
					future.setFuture((Future<?>) channelFuture);
				});
			}
			return future;
		}
		Logger logger = LoggerFactory.getLogger(ISessionSendMessageSupport.class);
		if (logger.isDebugEnabled()) {
			logger.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(pack));
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}

	GGSession getSession();

}