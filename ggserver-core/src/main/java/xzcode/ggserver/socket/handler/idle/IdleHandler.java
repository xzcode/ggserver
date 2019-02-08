package xzcode.ggserver.socket.handler.idle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import xzcode.ggserver.socket.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.socket.config.GGSocketConfig;
import xzcode.ggserver.socket.event.SocketEventTask;
import xzcode.ggserver.socket.event.SocketEvents;
import xzcode.ggserver.socket.session.imp.SocketSession;

/**
 * 空闲触发器
 *
 * @author zai
 * 2017-08-04 23:23:06
 */
public class IdleHandler extends ChannelInboundHandlerAdapter{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IdleHandler.class);

	private GGSocketConfig config;
	
	
	private boolean readerIdleEnabled;
	
	private boolean writerIdleEnabled;
	
	private boolean allIdleEnabled;
	
	public IdleHandler(GGSocketConfig config) {
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
			SocketSession session = ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).get();
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
