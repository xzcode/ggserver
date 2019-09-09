package xzcode.ggserver.client.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.client.config.GGClientConfig;

/**
 * 事件任务
 * 
 * @author zai
 * 2019-03-16 18:53:59
 */
public class GGEventTask implements Runnable{
	
private final static Logger LOGGER = LoggerFactory.getLogger(GGEventTask.class);
	
	private GGClientConfig config;
	
	/**
	 * event标识
	 */
	private String eventTag;
	
	/**
	 * 消息对象
	 */
	private Object message;
	
	

	public GGEventTask(String eventTag, Object message, GGClientConfig config) {
		this.eventTag = eventTag;
		this.message = message;
		this.config = config;
	}

	@Override
	public void run() {
		
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
	}

}
