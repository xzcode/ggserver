package com.xzcode.ggserver.core.common.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import com.xzcode.ggserver.core.common.handler.common.InboundCommonHandler;
import com.xzcode.ggserver.core.common.handler.common.OutboundCommonHandler;
import com.xzcode.ggserver.core.common.handler.idle.IdleHandler;
import com.xzcode.ggserver.core.common.handler.tcp.TcpInboundHandler;
import com.xzcode.ggserver.core.common.handler.tcp.TcpOutboundHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;

/**
 * Tcp channel初始化处理器
 * 
 * 
 * @author zai
 * 2017-08-02
 */
public class TcpChannelInitializer extends ChannelInitializer<Channel> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TcpChannelInitializer.class);
	
	protected static final AttributeKey<String> PROTOCOL_TYPE_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PROTOCOL_TYPE);


	private GGConfig config;
	
	
	public TcpChannelInitializer() {
		
	}
	

	public TcpChannelInitializer(GGConfig config) {
		this.config = config;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		
	   	if (config.getIdleCheckEnabled()) {
	   		
		   	 //空闲事件触发器
		   	 ch.pipeline().addLast(new IdleStateHandler(config.getReaderIdleTime(), config.getWriterIdleTime(), config.getAllIdleTime(), TimeUnit.MILLISECONDS));
		   	 
		   	 //空闲事件处理
		   	 ch.pipeline().addLast(new IdleHandler(config));
	   	}
   	 
        ch.pipeline().addLast(new TcpInboundHandler(this.config));
        
        ch.pipeline().addLast(new InboundCommonHandler(this.config));
        
        
        ch.pipeline().addLast(new TcpOutboundHandler(this.config));
        
        ch.pipeline().addLast(new OutboundCommonHandler(this.config));
        
        ch.attr(PROTOCOL_TYPE_KEY).set(ProtocolTypeConstants.TCP);
	}
	

	public GGConfig getConfig() {
		return config;
	}
	
	public void setConfig(GGConfig config) {
		this.config = config;
	}
	

}
