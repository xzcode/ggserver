package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionUtil;

/**
 * 转发消息任务
 * 
 * 
 * @author zai
 * 2019-02-09 14:26:10
 */
public class RedirectMessageTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RedirectMessageTask.class);
	
	/**
	 * 配置
	 */
	private GGConfig config;
	
	/**
	 * 请求标识
	 */
	private String action;
	
	/**
	 * socket消息体对象
	 */
	private Object message;
	
	/**
	 * session
	 */
	private GGSession session;
	
	/**
	 * 同步对象
	 */
	private Object syncObj;
	
	
	public RedirectMessageTask() {
		
	}
	

	public RedirectMessageTask(String action, Object message, GGSession session, Object syncLock, GGConfig config) {
		this.message = message;
		this.session = session;
		this.action = action;
		this.config = config;
		this.syncObj = syncLock;
	}

	public RedirectMessageTask(String action, Object message, GGSession session, GGConfig config) {
		this.message = message;
		this.session = session;
		this.action = action;
		this.config = config;
	}



	@Override
	public void run() {
		
		GGSessionUtil.setSession(this.session);
		try {
			if (syncObj != null) {
				synchronized (syncObj) {
					config.getRequestMessageManager().invoke(action, message);
				}
			}else {
				config.getRequestMessageManager().invoke(action, message);				
			}
			
		} catch (Exception e) {
			LOGGER.error("Redirect Message Task ERROR!! -- actionId: {}, error: {}", action, e);
		}finally {
			GGSessionUtil.removeSession();
		}
		
	}
	

}
