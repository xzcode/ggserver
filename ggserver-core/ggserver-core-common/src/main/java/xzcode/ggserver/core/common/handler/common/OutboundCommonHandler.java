package xzcode.ggserver.core.common.handler.common;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.event.EventTask;
import xzcode.ggserver.core.common.event.GGEvents;

public class OutboundCommonHandler extends ChannelOutboundHandlerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(OutboundCommonHandler.class);
	
	private GGConfig config;
	
	public OutboundCommonHandler(GGConfig config) {
		this.config = config;
	}
	

	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
			ChannelPromise promise) throws Exception {
		promise.addListener((future) -> {
			if (config.getEventManager().hasEventListener(GGEvents.Connection.CONNECTED)) {
				config.getTaskExecutor().submit(new EventTask(null, GGEvents.Connection.CONNECTED, null, config));
			}
		});
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		promise.addListener((future) -> {
			if (config.getEventManager().hasEventListener(GGEvents.Connection.DISCONNECTED)) {
				config.getTaskExecutor().submit(new EventTask(null, GGEvents.Connection.DISCONNECTED, null, config));
			}
		});
		super.disconnect(ctx, promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		promise.addListener((future) -> {
			if (config.getEventManager().hasEventListener(GGEvents.Connection.CLOSED)) {
				config.getTaskExecutor().submit(new EventTask(null, GGEvents.Connection.CLOSED, null, config));				
			}
		});
		super.close(ctx, promise);
		
	}

	
	

}
