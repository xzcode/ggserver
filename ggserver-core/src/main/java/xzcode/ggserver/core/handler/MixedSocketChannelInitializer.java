package xzcode.ggserver.core.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.handler.common.InboundCommonHandler;
import xzcode.ggserver.core.handler.common.OutboundCommonHandler;
import xzcode.ggserver.core.handler.idle.IdleHandler;
import xzcode.ggserver.core.handler.tcp.TcpSocketSelectHandler;
import xzcode.ggserver.core.handler.web.WebSocketInboundFrameHandler;
import xzcode.ggserver.core.handler.web.WebSocketOutboundFrameHandler;

/**
 * 混合tcp与websocket 初始化处理器
 * 
 * @author zai
 * 2019-06-15 14:31:58
 */
public class MixedSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(MixedSocketChannelInitializer.class);
	
	private GGServerConfig config;
	
	public MixedSocketChannelInitializer() {
	}
	
	public MixedSocketChannelInitializer(GGServerConfig config) {
		this.config = config;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(this.config));
		   	 
	   	}
	   	
	   	
	   	ch.pipeline().addLast("TcpSocketSelectHandler", new TcpSocketSelectHandler(config));
	   	
	   	/*
	   	
	   	ch.pipeline().addLast("HttpServerCodec", new HttpServerCodec());
	   	ch.pipeline().addLast("HttpObjectAggregator", new HttpObjectAggregator(config.getHttpMaxContentLength()));
	   	ch.pipeline().addLast("WebSocketInboundFrameHandler", new WebSocketInboundFrameHandler(this.config));
	   	*/
	   	
	   	
	   	//inbound异常处理
	   	ch.pipeline().addLast("InboundCommonHandler", new InboundCommonHandler(this.config));
	   	/*
        //Outbound 是反顺序执行
	   	ch.pipeline().addLast("WebSocketOutboundFrameHandler",new WebSocketOutboundFrameHandler(this.config ));
        */
        //outbound异常处理
        ch.pipeline().addLast("OutboundCommonHandler", new OutboundCommonHandler());
        
        
		
	}


	public GGServerConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGServerConfig config) {
		this.config = config;
	}

}
