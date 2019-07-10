package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.message.receive.invoker.IOnMessageInvoker;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

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
	private GGServerConfig config;
	
	/**
	 * 请求标识
	 */
	private byte[] action;
	
	/**
	 * 请求标识
	 */
	private String actionStr;
	
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
	
	

	public RedirectMessageTask(byte[] action, byte[] message, GGSession session, GGServerConfig config) {
		this.message = message;
		this.session = session;
		this.action = action;
		this.config = config;
	}
	
	public RedirectMessageTask(String actionStr, byte[] message, GGSession session, GGServerConfig config) {
		this.message = message;
		this.session = session;
		this.actionStr = actionStr;
		this.config = config;
	}
	
	public RedirectMessageTask(String actionStr, byte[] message, GGSession session, Object syncLock, GGServerConfig config) {
		this.message = message;
		this.session = session;
		this.actionStr = actionStr;
		this.config = config;
		this.syncObj = syncLock;
	}
	

	public RedirectMessageTask(String actionStr, Object message, GGSession session, Object syncLock, GGServerConfig config) {
		this.message = message;
		this.session = session;
		this.actionStr = actionStr;
		this.config = config;
		this.syncObj = syncLock;
	}

	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		try {
			if (action != null) {
				actionStr = new String(action, config.getCharset());				
			}
			
			IOnMessageInvoker invoker = config.getMessageInvokerManager().get(actionStr);
			Object msgObj = message;
			if (invoker != null) {
				if (message != null && message instanceof byte[]) {
					msgObj = config.getSerializer().deserialize((byte[])message, invoker.getMessageClass());					
				}
			}else {
				if (LOGGER.isWarnEnabled()) {
					LOGGER.warn("Action Id:{} is not mapped!", actionStr);					
				}
			}
			
			if (!this.config.getMessageFilterManager().doRequestFilters(actionStr, msgObj)) {
				GGSessionThreadLocalUtil.removeSession();
				return;
			}
			
			if (syncObj != null) {
				synchronized (syncObj) {
					config.getRequestMessageManager().invoke(actionStr, msgObj);
				}
			}else {
				config.getRequestMessageManager().invoke(actionStr, msgObj);				
			}
			
		} catch (Exception e) {
			LOGGER.error("Redirect Message Task ERROR!! -- actionId: {}, error: {}", actionStr, e);
		}
		GGSessionThreadLocalUtil.removeSession();
		
	}
	

}
