package xzcode.ggserver.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

/**
 * 事件任务
 * 
 * @author zai
 * 2019-03-16 18:53:59
 */
public class GGEventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(GGEventTask.class);
	
	private GGServerConfig config;
	

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
	
	

	public GGEventTask(GGSession session, String eventTag, Object message, GGServerConfig config) {
		super();
		this.session = session;
		this.eventTag = eventTag;
		this.message = message;
		this.config = config;
	}

	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		try {
			
			LOGGER.debug("Runing  SocketEventTask... tag:{}", eventTag);
			
			if (this.message == null) {
				config.getEventInvokerManager().invoke(eventTag);				
			}else {
				config.getEventInvokerManager().invoke(eventTag, message);			
			}
			
		} catch (Exception e) {
			LOGGER.error("Socket Event Task Error!!", e);
		}
		GGSessionThreadLocalUtil.removeSession();
	}

}
