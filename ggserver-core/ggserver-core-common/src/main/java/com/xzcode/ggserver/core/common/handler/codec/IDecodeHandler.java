package com.xzcode.ggserver.core.common.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IDecodeHandler {
	
	
	void handle(ChannelHandlerContext ctx, ByteBuf in, String protocolType);
	
}
