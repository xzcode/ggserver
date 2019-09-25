package xzcode.ggserver.core.handler.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.core.session.GGSessionUtil;
import xzcode.ggserver.core.session.imp.SocketSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutboundCommonHandler extends ChannelOutboundHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(OutboundCommonHandler.class);
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(" Outbound ERROR! ", cause);
		if (cause instanceof UnsupportedOperationException) {
			logger.error("UnsupportedOperationException, channel close ! ", cause);
			ctx.channel().close();
		}
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		logger.error(" Outbound disconnect! ");
		super.disconnect(ctx, promise);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		super.write(ctx, msg, promise);
	}
	
	
	

}
