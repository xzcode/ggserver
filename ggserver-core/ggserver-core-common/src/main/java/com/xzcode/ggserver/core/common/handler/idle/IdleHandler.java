package com.xzcode.ggserver.core.common.handler.idle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.event.EventTask;
import com.xzcode.ggserver.core.common.event.GGEvents;
import com.xzcode.ggserver.core.common.session.GGSession;

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

	private GGConfig config;
	
	
	private boolean readerIdleEnabled;
	
	private boolean writerIdleEnabled;
	
	private boolean allIdleEnabled;
	
	public IdleHandler(GGConfig config) {
		this.config = config;
		init();
	}
	
	
	public void init() {
		checkIdleEventMapped();
	}
	
	public void checkIdleEventMapped() {
		
		if(config.getEventManager().hasEventListener(GGEvents.Idle.WRITE)) {
			this.writerIdleEnabled = true;
		}
		
		if (config.getEventManager().hasEventListener(GGEvents.Idle.READE)) {
			this.readerIdleEnabled = true;
		}
		
		if (config.getEventManager().hasEventListener(GGEvents.Idle.ALL)) {
			this.allIdleEnabled = true;
		}
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		
		if (evt instanceof IdleStateEvent) {
			
            switch (((IdleStateEvent) evt).state()) {
            	
				case WRITER_IDLE:
						if(writerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...WRITER_IDLE...: channel:{}", ctx.channel());								
							}
							GGSession session = config.getSessionFactory().getSession(ctx.channel());
							config.getTaskExecutor().submitTask(new EventTask(session, GGEvents.Idle.WRITE, null, config,ctx.channel()));
						}
					break;
				case READER_IDLE:
						if (readerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...READER_IDLE...: channel:{}", ctx.channel());								
							}
							GGSession session = config.getSessionFactory().getSession(ctx.channel());
							config.getTaskExecutor().submitTask(new EventTask(session, GGEvents.Idle.READE, null, config,ctx.channel()));
						}
					break;
				case ALL_IDLE:
						if (allIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...ALL_IDLE...: channel:{}", ctx.channel());								
							}
							GGSession session = config.getSessionFactory().getSession(ctx.channel());
							config.getTaskExecutor().submitTask(new EventTask(session, GGEvents.Idle.ALL, null, config,ctx.channel()));
						}
					break;
				default:
					break;
					
			}
            
        }  
        super.userEventTriggered(ctx, evt);  
	}
}
