package xzcode.ggserver.core.common.handler.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.GGEventTask;
import xzcode.ggserver.core.common.event.GGEvents;
import xzcode.ggserver.core.common.session.imp.DefaultGGSessionImpl;

public class InboundCommonHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCommonHandler.class);
	
	
	private GGConfig config;
	
	public InboundCommonHandler() {
	}
	
	
	
	public InboundCommonHandler(GGConfig config) {
		super();
		this.config = config;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof java.io.IOException) {
			LOGGER.error("Inbound ERROR! {}", cause.getMessage());
			return;
		}
		LOGGER.error("Inbound ERROR! ", cause);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Active:{}", ctx.channel());
		}
		//InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		Channel channel = ctx.channel();
		//初始化session
		DefaultGGSessionImpl session = new DefaultGGSessionImpl(ctx.channel());
		
		channel.attr(DefaultChannelAttributeKeys.SESSION).set(session);
		
		config.getSessionManager().put(session.getSessonId(), session);
		
		config.getTaskExecutor().submit(new GGEventTask(session, GGEvents.ConnectionState.ACTIVE, null, config));
		
		
		//注册channel关闭事件
		channel.closeFuture().addListener((ChannelFuture future) -> {
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel Close:{}", ctx.channel());
			}
			
			config.getTaskExecutor().submit(new GGEventTask(session, GGEvents.ConnectionState.CLOSE, null, config));
			config.getSessionManager().remove(session.getSessonId());
		});
		
		super.channelActive(ctx);
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
	
	



	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}

}
