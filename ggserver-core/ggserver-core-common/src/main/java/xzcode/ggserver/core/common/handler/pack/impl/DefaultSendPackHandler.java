package xzcode.ggserver.core.common.handler.pack.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.handler.pack.IGGSendPackHandler;
import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;
import xzcode.ggserver.core.common.utils.json.GGServerJsonUtil;

public class DefaultSendPackHandler implements IGGSendPackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSendPackHandler.class);

	private GGConfig config;
	
	
	public DefaultSendPackHandler(GGConfig config) {
		this.config = config;
	}
	
	@Override
	public GGFuture handle(GGSession session, PackModel packModel, long delay, TimeUnit timeUnit){
		if (session == null) {
			session = GGSessionUtil.getSession();
		}
		if (session != null) {
			Channel channel = session.getChannel();
			if (channel != null && channel.isActive()) {
				 GGFuture sendFuture = new GGFuture();
				if (delay <= 0) {
					sendFuture.setNettyFuture(channel.writeAndFlush(packModel));					
				}else {
					config.getTaskExecutor().schedule(() -> {
						sendFuture.setNettyFuture(channel.writeAndFlush(packModel));
					}, delay, timeUnit);
				}
				return sendFuture;
			}else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(packModel));
				}
			}
		}
		return null;
		
	}
	
	@Override
	public GGFuture handle(GGSession session, PackModel packModel){
		return handle(session, packModel, 0, TimeUnit.MILLISECONDS);
	}
	


}
