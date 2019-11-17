package xzcode.ggserver.core.common.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface IEncodeHandler {

	
	ByteBuf handle(ChannelHandlerContext ctx, Object msg, ChannelPromise promise);
	
}
