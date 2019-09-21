package xzcode.ggserver.client.handler;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.handler.common.InboundCommonHandler;
import xzcode.ggserver.client.handler.common.OutboundCommonHandler;
import xzcode.ggserver.client.handler.idle.IdleHandler;
import xzcode.ggserver.client.handler.tcp.DecodeHandler;
import xzcode.ggserver.client.handler.tcp.EncodeHandler;
import xzcode.ggserver.client.handler.tcp.InitTcpHandler;

/**
 * 默认channel初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketChannelInitializer.class);


	private GGClientConfig config;
	
	
	public SocketChannelInitializer() {
		
	}
	

	public SocketChannelInitializer(GGClientConfig config) {
		this.config = config;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		LOGGER.debug("Init Channel:{}", ch);
		
		//ch.pipeline().addLast("InitTcpHandler", new InitTcpHandler(config));
		//Inbound 是顺序执行
	   	 
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(config));
	   	}
   	 
	   	 //消息解码器
        ch.pipeline().addLast(new DecodeHandler(this.config));
        
        //inbound异常处理
        ch.pipeline().addLast(new InboundCommonHandler(this.config));
        
        //Outbound 是反顺序执行
        
        //消息编码器
        ch.pipeline().addLast(new EncodeHandler(this.config));
        //outbound异常处理
        ch.pipeline().addLast(new OutboundCommonHandler());
	}
	

	public GGClientConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGClientConfig config) {
		this.config = config;
	}
	

}
