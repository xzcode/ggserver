package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.handler.serializer.ISerializer;
import xzcode.ggserver.core.message.receive.invoker.IOnMessageInvoker;
import xzcode.ggserver.core.message.send.SendModel;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;

/**
 * 请求消息任务
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
	 * 消息标识
	 */
	private String action;
	
	/**
	 * socket消息体对象
	 */
	private Object message;
	
	private GGSession session;
	
	
	public RedirectMessageTask() {
		
	}
	
	

	public RedirectMessageTask(String action, Object message, GGSession session, GGServerConfig config) {
		this.message = message;
		this.action = action;
		this.config = config;
		this.session = session;
	}
	
	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(session);
		try {
			config.getRequestMessageManager().invoke(action, message);
		} catch (Exception e) {
			LOGGER.error("Redirect Message Task ERROR!! -- actionId: {}, error: {}", action, e);
		}
		GGSessionThreadLocalUtil.removeSession();
		
	}
	

}
