package xzcode.ggserver.core.common.handler.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelOutboundHandlerAdapter;
import xzcode.ggserver.core.common.config.GGConfig;

public class OutboundCommonHandler extends ChannelOutboundHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(OutboundCommonHandler.class);
	
	private GGConfig config;
	
	public OutboundCommonHandler(GGConfig config) {
		this.config = config;
	}

}
