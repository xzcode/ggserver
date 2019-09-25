package xzcode.ggserver.core.handler.common;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.event.GGEventTask;
import xzcode.ggserver.core.event.GGEvents;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.imp.SocketSession;

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
		InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		
		//初始化session
		SocketSession session = new SocketSession(
				ctx.channel(), 
				socketAddress.getHostString(), 
				socketAddress.getPort()
				);
		
		ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).set(session);
		
		//添加到全局channelgroup绑定
		//ChannelGroupsManager.getGlobalGroup().add(ctx.channel());
		
		config.getTaskExecutor().submit(new GGEventTask(session, GGEvents.ConnectionState.ACTIVE, null, config));
		
		
		//注册channel关闭事件
		ctx.channel().closeFuture().addListener((ChannelFuture future) -> {
			Channel channel = future.channel();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel Close:{}", ctx.channel());
			}
			GGSession sezzion = channel.attr(DefaultChannelAttributeKeys.SESSION).getAndSet(null);
			
			//sezzion.inActive();
			
			config.getTaskExecutor().submit(new GGEventTask(sezzion, GGEvents.ConnectionState.CLOSE, null, config));
			
			//移除全局channelgroup绑定
			//ChannelGroupsManager.getGlobalGroup().remove(ctx.channel());
			
			GGSession userSession = config.getUserSessonManager().get(sezzion.getRegisteredUserId());
			
			//如果session相同，是断线，可移除session，否则存在被重复登录的情况，不应该移除session关联
			if (sezzion == userSession) {
				config.getUserSessonManager().remove(sezzion.getRegisteredUserId());				
			}
			
			
			//ChannelGroupsManager.getRegisteredGroup().remove(ctx.channel());
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
