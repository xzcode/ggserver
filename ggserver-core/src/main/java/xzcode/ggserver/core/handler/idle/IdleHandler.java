package xzcode.ggserver.core.handler.idle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.event.GGEventTask;
import xzcode.ggserver.core.event.GGEvents;
import xzcode.ggserver.core.session.GGSession;

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
		if(config.getEventInvokerManager().contains(GGEvents.IdleState.WRITER_IDLE)) {
			this.writerIdleEnabled = true;
		}
		
		if (config.getEventInvokerManager().contains(GGEvents.IdleState.READER_IDLE)) {
			this.readerIdleEnabled = true;
		}
		
		if (config.getEventInvokerManager().contains(GGEvents.IdleState.ALL_IDLE)) {
			this.allIdleEnabled = true;
		}
		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		
		if (evt instanceof IdleStateEvent) {
			//LOGGER.debug("Socket Event Triggered: {} ", evt);
			GGSession session = ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).get();
            switch (((IdleStateEvent) evt).state()) {
            	
				case WRITER_IDLE:
						if(writerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...WRITER_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new GGEventTask(session, GGEvents.IdleState.WRITER_IDLE, null, config));
						}
					break;
				case READER_IDLE:
						if (readerIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...READER_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new GGEventTask(session, GGEvents.IdleState.READER_IDLE, null, config));
						}
					break;
				case ALL_IDLE:
						if (allIdleEnabled) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("...ALL_IDLE...: channel:{}", ctx.channel());								
							}
							config.getTaskExecutor().submit(new GGEventTask(session, GGEvents.IdleState.ALL_IDLE, null, config));
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
