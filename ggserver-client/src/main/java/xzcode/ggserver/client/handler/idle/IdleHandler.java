package xzcode.ggserver.client.handler.idle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.event.GGEventTask;
import xzcode.ggserver.client.event.GGClientEvents;

/**
 * 空闲触发器
 *
 * @author zai
 * 2017-08-04 23:23:06
 */
public class IdleHandler extends ChannelInboundHandlerAdapter{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IdleHandler.class);

	private GGClientConfig config;
	
	
	private boolean readerIdleEnabled;
	
	private boolean writerIdleEnabled;
	
	private boolean allIdleEnabled;
	
	public IdleHandler(GGClientConfig config) {
		this.config = config;
		init();
	}
	
	
	public void init() {
		checkIdleEventMapped();
	}
	
	public void checkIdleEventMapped() {
		if(config.getEventInvokerManager().contains(GGClientEvents.IdleState.WRITER_IDLE)) {
			this.writerIdleEnabled = true;
		}
		
		if (config.getEventInvokerManager().contains(GGClientEvents.IdleState.READER_IDLE)) {
			this.readerIdleEnabled = true;
		}
		
		if (config.getEventInvokerManager().contains(GGClientEvents.IdleState.ALL_IDLE)) {
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
							config.getTaskExecutor().submit(new GGEventTask(GGClientEvents.IdleState.WRITER_IDLE, null, config));
						}
					break;
				case READER_IDLE:
						if (readerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...READER_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new GGEventTask(GGClientEvents.IdleState.READER_IDLE, null, config));
						}
					break;
				case ALL_IDLE:
						if (allIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...ALL_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new GGEventTask(GGClientEvents.IdleState.ALL_IDLE, null, config));
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
