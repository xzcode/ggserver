package com.xzcode.socket.core.handler.life;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.channel.DefaultAttributeKeys;
import com.xzcode.socket.core.channel.SocketChannelGroups;
import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.event.SocketEventTask;
import com.xzcode.socket.core.event.SocketEvents;
import com.xzcode.socket.core.session.imp.SocketSession;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InboundLifeCycleHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundLifeCycleHandler.class);
	
	
	private SocketServerConfig config;
	
	public InboundLifeCycleHandler() {
	}
	
	
	
	public InboundLifeCycleHandler(SocketServerConfig config) {
		super();
		this.config = config;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}



	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOGGER.error("Inbound ERROR! ", cause);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Channel Active:{}", ctx.channel());
		}
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		
		//初始化session
		SocketSession session = new SocketSession(
				ctx.channel(), 
				socketAddress.getHostString(), 
				socketAddress.getPort()
				);
		
		ctx.channel().attr(DefaultAttributeKeys.SESSION).set(session);
		
		//添加到全局channelgroup绑定
		SocketChannelGroups.getGlobalGroup().add(ctx.channel());
		
		config.getTaskExecutor().submit(new SocketEventTask(session, SocketEvents.ChannelState.ACTIVE, config.getEventInvokerManager()));
		
		
		//注册channel关闭事件
		ctx.channel().closeFuture().addListener((ChannelFuture future) -> {
			Channel channel = future.channel();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel Close:{}", ctx.channel());
			}
			SocketSession sezzion = channel.attr(DefaultAttributeKeys.SESSION).getAndSet(null);
			
			sezzion.inActive();
			
			config.getTaskExecutor().submit(new SocketEventTask(sezzion, SocketEvents.ChannelState.CLOSE, config.getEventInvokerManager()));
			
			//移除全局channelgroup绑定
			SocketChannelGroups.getGlobalGroup().remove(ctx.channel());
			
			SocketSession userSession = config.getUserSessonManager().get(sezzion.getRegisteredUserId());
			
			//如果session相同，是断线，可移除session，否则存在被重复登录的情况，不应该移除session关联
			if (sezzion == userSession) {
				config.getUserSessonManager().remove(sezzion.getRegisteredUserId());				
			}
			
			
			SocketChannelGroups.getRegisteredGroup().remove(ctx.channel());
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
	
	



	public SocketServerConfig getConfig() {
		return config;
	}
	
	public void setConfig(SocketServerConfig config) {
		this.config = config;
	}

}
