package xzcode.ggserver.core.common.message.send.support;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.future.GGNettyFacadeFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.send.Response;
import xzcode.ggserver.core.common.session.support.IGGSessionSupport;
import xzcode.ggserver.core.common.utils.json.GGServerJsonUtil;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISessionSendMessageSupport extends IGGConfigSupport, IGGSessionSupport{
	
	/**
	 * 生成字节码数据包
	 * @param response
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 17:28:57
	 */
	default Pack makePack(Response response) {
		try {
			GGConfig config = getConfig();
			ISerializer serializer = config.getSerializer();
			byte[] metadataBytes = response.getMetadata() == null ? null : serializer.serialize(response.getMetadata());
			byte[] actionIdData = response.getAction().getBytes(config.getCharset());
			byte[] messageData = response.getMessage() == null ? null : serializer.serialize(response.getMessage());
			return new Pack(metadataBytes, actionIdData, messageData);
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("Make pack Error!", e);
		}
		return null;
	}
	
	/**
	 * 发送消息
	 * @param response 响应消息
	 * @param delay 延迟时间
	 * @param timeUnit 时间单位
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 17:29:24
	 */
	default IGGFuture<?> send(Response response, long delay, TimeUnit timeUnit) {
		return send(makePack(response), delay, timeUnit);
	}

	/**
	 * 发送消息
	 * @param pack
	 * @param delay
	 * @param timeUnit
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 23:08:36
	 */
	default IGGFuture<?> send(Pack pack, long delay, TimeUnit timeUnit) {
		Channel channel = getSession().getChannel();

		//序列化后发送过滤器
		if (!getConfig().getFilterManager().doAfterSerializeFilters(getSession(), pack)) {
			return null;
		}
		if (channel.isActive()) {
			GGNettyFacadeFuture<?> future = new GGNettyFacadeFuture<>();	
			if (delay <= 0) {
				ChannelFuture channelFuture = channel.writeAndFlush(pack);
				future.setFuture((Future<?>) channelFuture);
							
			}else {
				getConfig().getTaskExecutor().schedule(() -> {
					ChannelFuture channelFuture = channel.writeAndFlush(pack);
					future.setFuture((Future<?>) channelFuture);
				}, delay, timeUnit);
			}
			return future;	
		}
		Logger logger = LoggerFactory.getLogger(ISessionSendMessageSupport.class);
		if (logger.isDebugEnabled()) {
			logger.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(pack));
		}
		return null;
	}


}
