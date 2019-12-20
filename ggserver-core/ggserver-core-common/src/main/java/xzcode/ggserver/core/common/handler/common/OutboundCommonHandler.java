package xzcode.ggserver.core.common.handler.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import xzcode.ggserver.core.common.config.GGConfig;

public class OutboundCommonHandler extends ChannelOutboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OutboundCommonHandler.class);
	
	private GGConfig config;
	
	public OutboundCommonHandler(GGConfig config) {
		this.config = config;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof java.io.IOException) {
			LOGGER.error("Inbound ERROR! {}", cause.getMessage());
			return;
		}
		LOGGER.error("Inbound ERROR! ", cause);
		super.exceptionCaught(ctx, cause);
	}

}
