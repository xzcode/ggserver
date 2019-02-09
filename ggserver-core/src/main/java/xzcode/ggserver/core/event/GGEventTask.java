package xzcode.ggserver.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

public class GGEventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(GGEventTask.class);
	
	private EventInvokerManager eventInvokerManager;
	
	public void setMethodInvokeMapper(EventInvokerManager eventInvokerManager) {
		this.eventInvokerManager = eventInvokerManager;
	}

	/**
	 * session对象
	 */
	private GGSession session;
	
	/**
	 * event标识
	 */
	private String socketEvent;
	
	

	public GGEventTask(GGSession session, String socketEvent, EventInvokerManager eventInvokerManager) {
		super();
		this.session = session;
		this.socketEvent = socketEvent;
		this.eventInvokerManager = eventInvokerManager;
	}

	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		try {
			
			LOGGER.debug("Runing  SocketEventTask... tag:{}", socketEvent);
			
			eventInvokerManager.invoke(socketEvent);
			
		} catch (Exception e) {
			LOGGER.error("Socket Event Task Error!!", e);
		}
		GGSessionThreadLocalUtil.removeSession();
	}

}
