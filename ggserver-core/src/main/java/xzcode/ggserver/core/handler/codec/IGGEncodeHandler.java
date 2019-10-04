package xzcode.ggserver.core.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface IGGEncodeHandler {

	
	ByteBuf handle(ChannelHandlerContext ctx, Object msg, ChannelPromise promise);
	
}
