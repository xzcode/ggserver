package com.xzcode.ggserver.core.common.handler.codec;

import com.xzcode.ggserver.core.common.message.Pack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface IEncodeHandler {

	
	ByteBuf handle(ChannelHandlerContext ctx, Pack pack);
	
}
