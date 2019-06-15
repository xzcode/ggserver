package xzcode.ggserver.client.handler.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.event.GGEventTask;
import xzcode.ggserver.client.event.GGClientEvents;

public class InboundCommonHandler extends ChannelInboundHandlerAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InboundCommonHandler.class);
	
	
	private GGClientConfig config;
	
	public InboundCommonHandler() {
	}
	
	
	
	public InboundCommonHandler(GGClientConfig config) {
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
		
		//添加到全局channelgroup绑定
		//ChannelGroupsManager.getGlobalGroup().add(ctx.channel());
		
		config.getTaskExecutor().submit(new GGEventTask(GGClientEvents.ConnectionState.ACTIVE, null, config));
		
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
	
	



	public GGClientConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGClientConfig config) {
		this.config = config;
	}

}
