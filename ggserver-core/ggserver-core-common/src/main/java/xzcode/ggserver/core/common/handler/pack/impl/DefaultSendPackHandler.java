package xzcode.ggserver.core.common.handler.pack.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.pack.IGGSendPackHandler;
import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.json.GGServerJsonUtil;

public class DefaultSendPackHandler implements IGGSendPackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSendPackHandler.class);

	private GGConfig config;
	
	
	public DefaultSendPackHandler(GGConfig config) {
		this.config = config;
	}
	
	@Override
	public void handle(PackModel packModel, GGSession session){
		Channel channel = session.getChannel();
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(packModel);			
		}else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(packModel));
			}
		}
	}



}
