package xzcode.ggserver.core.common.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import xzcode.ggserver.core.common.message.Pack;

public interface IEncodeHandler {

	
	ByteBuf handle(ChannelHandlerContext ctx, Pack pack);
	
}
