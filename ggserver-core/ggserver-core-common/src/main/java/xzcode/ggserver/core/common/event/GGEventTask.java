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
	private String eventTag;
	
	/**
	 * 消息对象
	 */
	private Object message;
	
	

	public GGEventTask(GGSession session, String eventTag, Object message, GGConfig config) {
		super();
		this.session = session;
		this.eventTag = eventTag;
		this.message = message;
		this.config = config;
	}

	@Override
	public void run() {
		
		GGSessionUtil.setSession(this.session);
		try {
			
			config.getEventInvokerManager().invoke(eventTag, message);			
			
		} catch (Exception e) {
			LOGGER.error("GGEvent Task Error!!", e);
		}
		GGSessionUtil.removeSession();
	}

}
