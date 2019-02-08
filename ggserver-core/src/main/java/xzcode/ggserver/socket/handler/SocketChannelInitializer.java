package xzcode.ggserver.socket.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import xzcode.ggserver.socket.config.GGSocketConfig;
import xzcode.ggserver.socket.executor.SocketServerTaskExecutor;
import xzcode.ggserver.socket.handler.codec.DecodeHandler;
import xzcode.ggserver.socket.handler.codec.EncodeHandler;
import xzcode.ggserver.socket.handler.idle.IdleHandler;
import xzcode.ggserver.socket.handler.life.InboundLifeCycleHandler;
import xzcode.ggserver.socket.handler.life.OutboundLifeCycleHandler;
import xzcode.ggserver.socket.serializer.factory.SerializerFactory;

/**
 * 默认channel初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class SocketChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketChannelInitializer.class);


	private GGSocketConfig config;
	
	
	private SocketServerTaskExecutor taskExecutor;
	
	public SocketChannelInitializer() {
	}
	

	public SocketChannelInitializer(
			GGSocketConfig config, 
			SocketServerTaskExecutor taskExecutor
			) {
		super();
		this.config = config;
		this.taskExecutor = taskExecutor;
	}
	
	

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		LOGGER.debug("Init Channel:{}", ch);
		
		//Inbound 是顺序执行
   	 
	   	 
	   	 
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //心跳包处理
		   	 ch.pipeline().addLast(new IdleHandler(config));
		   	 
	   	}
	   	
   	 
	   	 //消息解码器
        ch.pipeline().addLast(new DecodeHandler(SerializerFactory.geSerializer(config.getSerializerType()), this.taskExecutor, config.getMessageMethodInvokeManager(),config.getMessageFilterManager()));
        
        //inbound异常处理
        ch.pipeline().addLast(new InboundLifeCycleHandler(this.config));
        
        
        //Outbound 是反顺序执行
        
        //消息编码器
        ch.pipeline().addLast(new EncodeHandler(SerializerFactory.geSerializer(config.getSerializerType())));
        //outbound异常处理
        ch.pipeline().addLast(new OutboundLifeCycleHandler());
	}
	

	public GGSocketConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGSocketConfig config) {
		this.config = config;
	}
	
	public SocketServerTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}
	
	public void setTaskExecutor(SocketServerTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

}
