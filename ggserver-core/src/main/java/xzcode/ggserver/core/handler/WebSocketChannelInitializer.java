package xzcode.ggserver.core.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.handler.common.InboundCommonHandler;
import xzcode.ggserver.core.handler.common.OutboundCommonHandler;
import xzcode.ggserver.core.handler.idle.IdleHandler;
import xzcode.ggserver.core.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.core.handler.web.WebSocketOutboundFrameHandler;
import xzcode.ggserver.core.handler.web.WebSocketInboundFrameHandler;

/**
 * WebSocket channel 初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInitializer.class);
	
	private GGServerConfig config;
	
	private SslContext sslCtx;
	
	public WebSocketChannelInitializer() {
	}
	
	public WebSocketChannelInitializer(
			GGServerConfig config,
			SslContext sslCtx
			) {
		super();
		this.config = config;
		this.sslCtx = sslCtx;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		//Inbound 是顺序执行
		if (sslCtx != null) {
			ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
		}
		/*
		ch.pipeline().addLast(new SslHandler(WebSocketServerStarter.sslEngine));
		*/
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(this.config));
		   	 
	   	}
	   	//inbound异常处理
	   	
	   	ch.pipeline().addLast(new HttpServerCodec());
	   	ch.pipeline().addLast(new HttpObjectAggregator(config.getHttpMaxContentLength()));
	   	ch.pipeline().addLast(new WebSocketInboundFrameHandler(this.config));
	   	ch.pipeline().addLast(new InboundCommonHandler(this.config));
	   	
	   	
        
        //Outbound 是反顺序执行
	   	ch.pipeline().addLast("WebSocketOutboundFrameHandler",new WebSocketOutboundFrameHandler(this.config ));
        
        //outbound异常处理
        ch.pipeline().addLast(new OutboundCommonHandler());
        
        
		
	}


	public GGServerConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGServerConfig config) {
		this.config = config;
	}

	public SslContext getSslCtx() {
		return sslCtx;
	}
	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
}
