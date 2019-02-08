package xzcode.ggserver.socket.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.socket.session.SocketSessionUtil;
import xzcode.ggserver.socket.session.imp.SocketSession;

public class SocketEventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(SocketEventTask.class);
	
	private EventInvokerManager eventInvokerManager;
	
	public void setMethodInvokeMapper(EventInvokerManager eventInvokerManager) {
		this.eventInvokerManager = eventInvokerManager;
	}

	/**
	 * session对象
	 */
	private SocketSession session;
	
	/**
	 * event标识
	 */
	private String socketEvent;
	
	

	public SocketEventTask(SocketSession session, String socketEvent, EventInvokerManager eventInvokerManager) {
		super();
		this.session = session;
		this.socketEvent = socketEvent;
		this.eventInvokerManager = eventInvokerManager;
	}

	@Override
	public void run() {
		
		SocketSessionUtil.setSession(this.session);
		try {
			
			LOGGER.debug("Runing  SocketEventTask... tag:{}", socketEvent);
			
			eventInvokerManager.invoke(socketEvent);
			
		} catch (Exception e) {
			LOGGER.error("Socket Event Task Error!!", e);
		}
		SocketSessionUtil.removeSession();
	}

}
