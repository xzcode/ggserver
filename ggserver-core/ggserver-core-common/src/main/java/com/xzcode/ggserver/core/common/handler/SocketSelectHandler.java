package com.xzcode.ggserver.core.common.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.EventTask;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.session.GGSession;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

/**
 * socket类型选择处理器
 * 
 * @author zai
 * 2019-06-15 14:25:54
 */
public class SocketSelectHandler extends ByteToMessageDecoder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketSelectHandler.class);
	
	protected GGConfig config;
	
	
	public SocketSelectHandler(GGConfig config) {
		this.config = config;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readableBytes = in.readableBytes();
		in.markReaderIndex();
		if (readableBytes < 3) {
			return;
		}
		byte[] bytes = new byte[3];
		in.readBytes(bytes);
		String tag = new String(bytes, config.getCharset());
		
		ChannelPipeline pipeline = ctx.pipeline();
		
		if (tag.equalsIgnoreCase("GET")) {
			pipeline.addLast(new WebSocketChannelInitializer(this.config));
			
		}else {
			pipeline.addLast(new TcpChannelInitializer(this.config));
		}
		
		in.resetReaderIndex();
		pipeline.remove(SocketSelectHandler.class);
		super.channelActive(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof java.io.IOException) {
			LOGGER.error("Exception Caught! {}", cause.getMessage());
			return;
		}
		if (cause instanceof UnsupportedOperationException) {
			LOGGER.error("UnsupportedOperationException ! ", cause);
			ctx.close();
		}
		LOGGER.error("Exception Caught! ", cause);
	}
	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		config.getSessionFactory().channelActive(channel);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Active:{}", channel);
		}
		GGSession session = (GGSession)channel.attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).get();
		config.getTaskExecutor().submitTask(new EventTask(session, GGEvents.Connection.OPENED, null, config, channel));
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		config.getSessionFactory().channelInActive(ctx.channel());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("channel Inactive:{}", ctx.channel());
		}
		config.getTaskExecutor().submitTask(new EventTask((GGSession)ctx.channel().attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).get(), GGEvents.Connection.CLOSED, null, config));
	}

}
