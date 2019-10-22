package xzcode.ggserver.core.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;
import xzcode.ggserver.core.common.session.event.IGGSessionEventManager;

/**
 * 事件任务
 * 
 * @author zai
 * 2019-03-16 18:53:59
 */
public class GGEventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(GGEventTask.class);
	
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
	
	

	public GGEventTask(GGSession session, String event, Object message, GGConfig config) {
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
			
			config.getEventInvokerManager().invoke(event, message);	
			
			IGGSessionEventManager sessionEventManager = this.session.getGGSessionEventManager();
			if (!sessionEventManager.isEmpty() && sessionEventManager.hasEventListener(event)) {
				sessionEventManager.emitEvent(event, message);
			}
			
		} catch (Exception e) {
			LOGGER.error("GGEvent Task Error!!", e);
		}
		GGSessionUtil.removeSession();
	}

}
