package xzcode.ggserver.socket.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.socket.config.GGSocketConfig;
import xzcode.ggserver.socket.handler.idle.IdleHandler;
import xzcode.ggserver.socket.handler.life.InboundLifeCycleHandler;
import xzcode.ggserver.socket.handler.life.OutboundLifeCycleHandler;
import xzcode.ggserver.socket.handler.web.WebSocketOutboundFrameHandler;
import xzcode.ggserver.socket.handler.web.WebSocketServerHandler;
import xzcode.ggserver.socket.serializer.factory.SerializerFactory;

/**
 * WebSocket channel 初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInitializer.class);
	
	private GGSocketConfig config;
	
	private SslContext sslCtx;
	
	public WebSocketChannelInitializer() {
	}
	
	public WebSocketChannelInitializer(
			GGSocketConfig config,
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
	   	ch.pipeline().addLast(new WebSocketServerHandler(this.config));
	   	ch.pipeline().addLast(new InboundLifeCycleHandler(this.config));
	   	
	   	
        
        //Outbound 是反顺序执行
	   	ch.pipeline().addLast("WebSocketOutboundFrameHandler",new WebSocketOutboundFrameHandler(this.config ));
        
        //outbound异常处理
        ch.pipeline().addLast(new OutboundLifeCycleHandler());
        
        
		
	}


	public GGSocketConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGSocketConfig config) {
		this.config = config;
	}

	public SslContext getSslCtx() {
		return sslCtx;
	}
	public void setSslCtx(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
}
