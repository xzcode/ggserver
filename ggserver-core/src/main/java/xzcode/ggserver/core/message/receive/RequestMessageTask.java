package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
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
public class RequestMessageTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RequestMessageTask.class);
	
	/**
	 * 配置
	 */
	private GGServerConfig config;
	
	/**
	 * 请求标识
	 */
	private byte[] action;
	
	/**
	 * socket消息体对象
	 */
	private byte[] message;
	
	/**
	 * session
	 */
	private GGSession session;
	
	
	
	
	public RequestMessageTask() {
		
	}
	
	

	public RequestMessageTask(byte[] action, byte[] message, GGSession session, GGServerConfig config) {
		this.message = message;
		this.session = session;
		this.action = action;
		this.config = config;
	}



	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		String actionStr = null;
		try {
			actionStr = new String(action, config.getCharset());
			
			
			IOnMessageInvoker invoker = config.getMessageInvokerManager().get(actionStr);
			Object msgObj = null;
			if (message != null) {
				msgObj = config.getSerializer().deserialize(message, invoker.getMessageClass());
			}
			
			if (!this.config.getMessageFilterManager().doRequestFilters(actionStr, msgObj)) {
				GGSessionThreadLocalUtil.removeSession();
				return;
			}
			
			config.getRequestMessageManager().invoke(actionStr, msgObj);
			
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!! -- actionId: {}, error: {}", actionStr, e);
		}
		GGSessionThreadLocalUtil.removeSession();
		
	}
	

}
