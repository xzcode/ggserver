package xzcode.ggserver.core.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;

/**
 * 事件任务
 * 
 * @author zai
 * 2019-03-16 18:53:59
 */
public class EventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(EventTask.class);
	
	private GGConfig config;
	
	/**
	 * session对象
	 */
	private GGSession session;
	
	/**
	 * event标识
	 */
	private String event;
	
	/**
	 * 消息对象
	 */
	private Object message;
	
	

	public EventTask(GGSession session, String event, Object message, GGConfig config) {
		super();
		this.session = session;
		this.event = event;
		this.message = message;
		this.config = config;
	}

	@Override
	public void run() {
		
		GGSessionUtil.setSession(this.session);
		try {
			
			config.getEventManager().emitEvent(event, message);	
			/*
			 * IEventManager sessionEventManager = this.session.getEventManager(); if
			 * (!sessionEventManager.isEmpty() &&
			 * sessionEventManager.hasEventListener(event)) {
			 * sessionEventManager.emitEvent(event, message); }
			 */
		} catch (Exception e) {
			LOGGER.error("GGEvent Task Error!!", e);
		}
		GGSessionUtil.removeSession();
	}

}
