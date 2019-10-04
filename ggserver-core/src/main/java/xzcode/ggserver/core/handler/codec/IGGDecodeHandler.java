package xzcode.ggserver.core.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IGGDecodeHandler {
	
	
	void handle(ChannelHandlerContext ctx, ByteBuf in);
	
}
