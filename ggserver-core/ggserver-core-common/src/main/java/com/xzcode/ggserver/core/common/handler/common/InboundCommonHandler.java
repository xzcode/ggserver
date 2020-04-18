package com.xzcode.ggserver.core.common.handler.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.EventTask;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.session.GGSession;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

public class InboundCommonHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCommonHandler.class);
	
	protected GGConfig config;
	
	public InboundCommonHandler() {
		
	}
	
	public InboundCommonHandler(GGConfig config) {
		super();
		this.config = config;
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
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
		super.channelActive(ctx);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		config.getSessionFactory().channelInActive(ctx.channel());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("channel Inactive:{}", ctx.channel());
		}
		config.getTaskExecutor().submitTask(new EventTask((GGSession)ctx.channel().attr(AttributeKey.valueOf(DefaultChannelAttributeKeys.SESSION)).get(), GGEvents.Connection.CLOSED, null, config));
		super.channelInactive(ctx);
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Unregistered:{}", ctx.channel());
		}
		super.channelUnregistered(ctx);
	}
	
	
	
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("userEventTriggered:{}", evt);			
		}
		super.userEventTriggered(ctx, evt);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof java.io.IOException) {
			LOGGER.error("Inbound ERROR! {}", cause.getMessage());
			return;
		}
		LOGGER.error("Inbound ERROR! ", cause);
	}


	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}

}
