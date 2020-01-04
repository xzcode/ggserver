package xzcode.ggserver.core.common.message.response.support;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import xzcode.ggserver.core.common.executor.ITaskExecutor;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.future.GGFailedFuture;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.meta.provider.IMetadataProvider;
import xzcode.ggserver.core.common.message.model.IMessage;
import xzcode.ggserver.core.common.message.response.Response;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.json.GGServerJsonUtil;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai 2019-02-09 14:50:27
 */
public interface ISessionSendMessageSupport extends IMakePackSupport {
	
	/**
	 * 获取过滤器管理器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 16:35:06
	 */
	IFilterManager getFilterManager();

	/**
	 * 获取计划任务执行器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 16:35:12
	 */
	ITaskExecutor getTaskExecutor();

	/**
	 * 获取会话
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 16:35:24
	 */
	GGSession getSession();
	
	/**
	 * 获取元数据提供者
	 * 
	 * @return
	 * @author zai
	 * 2019-12-12 16:10:38
	 */
	IMetadataProvider<?> getMetadataProvider();
	
	/**
	 * 发送消息
	 * 
	 * @param action
	 * @return
	 * @author zai
	 * 2019-12-17 18:44:14
	 */
	default IGGFuture send(String action) {
		return send(new Response((GGSession) this, null, action, null), 0L, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 发送消息
	 * 
	 * @param action
	 * @param message
	 * @return
	 * @author zai
	 * 2019-12-17 18:44:20
	 */
	default IGGFuture send(String action, Object message) {
		return send(new Response((GGSession) this, null, action, message), 0L, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 * @author zai
	 * 2019-12-25 11:57:05
	 */
	default IGGFuture send(IMessage message) {
		return send(new Response((GGSession) this, null, message.getActionId(), message), 0L, TimeUnit.MILLISECONDS);
	}
	
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
	default IGGFuture send(GGSession session, String action, Object message) {
		return send(new Response(session, null, action, message), 0L, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param message
	 * @return
	 * @author zai
	 * 2019-12-25 11:57:44
	 */
	default IGGFuture send(GGSession session, IMessage message) {
		return send(new Response(session, null, message.getActionId(), message), 0L, TimeUnit.MILLISECONDS);
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
	default IGGFuture send(GGSession session, String action, Object message, long delay, TimeUnit timeUnit) {
		return send(new Response(session, null, action, message), delay, timeUnit);
	}
	
	/**
	 * message
	 * 
	 * @param session
	 * @param message
	 * @param delay
	 * @param timeUnit
	 * @return
	 * @author zai
	 * 2019-12-25 11:58:06
	 */
	default IGGFuture send(GGSession session, IMessage message, long delay, TimeUnit timeUnit) {
		return send(new Response(session, null, message.getActionId(), message), delay, timeUnit);
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
	default IGGFuture send(Response response, long delay, TimeUnit timeUnit) {
		if (response.getMetadata() == null) {
			IMetadataProvider<?> metadataProvider = getMetadataProvider();
			Object metadata = metadataProvider.provide(response.getSession());
			response.setMetadata(metadata);			
		}
		// 发送过滤器
		if (!getFilterManager().doResponseFilters(response)) {
			return null;
		}
		return send(makePack(response), delay, timeUnit);
	}
	
	/**
	 * 发送消息
	 * 
	 * @param response
	 * @return
	 * @author zai
	 * 2019-12-12 15:57:08
	 */
	default IGGFuture send(Response response) {
		return send(response, 0L, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 发送消息
	 * @param pack
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 16:45:12
	 */
	default IGGFuture send(Pack pack) {
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
	default IGGFuture send(Pack pack, long delay, TimeUnit timeUnit) {
		
		GGSession session = pack.getSession();
		
		Channel channel = null;
		if (session != null) {
			channel = session.getChannel();
		}
		if (channel == null) {
			channel = pack.getChannel();
		}
		
		if (channel == null || !channel.isActive()) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		
		
		
		// 序列化后发送过滤器
		if (!getFilterManager().doAfterSerializeFilters(pack)) {
			return GGFailedFuture.DEFAULT_FAILED_FUTURE;
		}
		
		if (channel.isActive()) {
			GGNettyFacadeFuture future = new GGNettyFacadeFuture();
			if (delay <= 0) {
				ChannelFuture channelFuture = channel.writeAndFlush(pack);
				future.setFuture((Future<?>) channelFuture);

			} else {
				Channel ch = channel;
				getTaskExecutor().schedule(delay, timeUnit, () -> {
					ChannelFuture channelFuture = ch.writeAndFlush(pack);
					future.setFuture((Future<?>) channelFuture);
				});
			}
			return future;
		}
		Logger logger = GGLoggerUtil.getLogger();
		if (logger.isDebugEnabled()) {
			logger.debug("Channel is inactived! Message will not be send, Pack:{}", GGServerJsonUtil.toJson(pack));
		}
		return GGFailedFuture.DEFAULT_FAILED_FUTURE;
	}


}
