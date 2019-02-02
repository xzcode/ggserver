package com.xzcode.socket.core.handler.idle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.channel.DefaultAttributeKeys;
import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.event.SocketEventTask;
import com.xzcode.socket.core.event.SocketEvents;
import com.xzcode.socket.core.session.imp.SocketSession;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 空闲触发器
 *
 * @author zai
 * 2017-08-04 23:23:06
 */
public class IdleHandler extends ChannelInboundHandlerAdapter{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IdleHandler.class);

	private SocketServerConfig config;
	
	
	private boolean readerIdleEnabled;
	
	private boolean writerIdleEnabled;
	
	private boolean allIdleEnabled;
	
	public IdleHandler(SocketServerConfig config) {
		this.config = config;
		init();
	}
	
	
	public void init() {
		checkIdleEventMapped();
	}
	
	public void checkIdleEventMapped() {
		if(config.getEventInvokerManager().contains(SocketEvents.IdleState.WRITER_IDLE)) {
			this.writerIdleEnabled = true;
		}
		
		if (config.getEventInvokerManager().contains(SocketEvents.IdleState.READER_IDLE)) {
			this.readerIdleEnabled = true;
		}
		
		if (config.getEventInvokerManager().contains(SocketEvents.IdleState.ALL_IDLE)) {
			this.allIdleEnabled = true;
		}
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		
		if (evt instanceof IdleStateEvent) {
			//LOGGER.debug("Socket Event Triggered: {} ", evt);
			SocketSession session = ctx.channel().attr(DefaultAttributeKeys.SESSION).get();
            switch (((IdleStateEvent) evt).state()) {
            	
				case WRITER_IDLE:
						if(writerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...WRITER_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new SocketEventTask(session, SocketEvents.IdleState.WRITER_IDLE, config.getEventInvokerManager()));
						}
					break;
				case READER_IDLE:
						if (readerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...READER_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new SocketEventTask(session, SocketEvents.IdleState.READER_IDLE, config.getEventInvokerManager()));
						}
					break;
				case ALL_IDLE:
						if (allIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...ALL_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new SocketEventTask(session, SocketEvents.IdleState.ALL_IDLE, config.getEventInvokerManager()));
						}
					break;
				default:
					break;
					
			}
            
        } else {  
            super.userEventTriggered(ctx, evt);  
        } 
		
	}
}
