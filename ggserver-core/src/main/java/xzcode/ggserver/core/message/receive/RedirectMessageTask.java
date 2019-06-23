package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.handler.serializer.ISerializer;
import xzcode.ggserver.core.message.receive.invoker.IRequestMessageInvoker;
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
	
	/**
	 * socket消息体对象
	 */
	private GGSession session;
	
	
	
	
	public RedirectMessageTask() {
		
	}
	
	

	public RedirectMessageTask(String action, Object message, GGSession session, GGServerConfig config) {
		this.message = message;
		this.session = session;
		this.action = action;
		this.config = config;
	}



	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		try {
			
			Object returnObj = config.getRequestMessageManager().invoke(action, message);
			if (returnObj != null) {
				ISerializer serializer = config.getSerializer();
				config.getSendMessageManager().send(this.session.getChannel(), SendModel.create(serializer.serialize(config.getRequestMessageManager().getSendAction(action)), serializer.serialize(returnObj)));
			}
		} catch (Exception e) {
			LOGGER.error("Redirect Message Task ERROR!! -- actionId: {}, error: {}", action, e);
		}
		GGSessionThreadLocalUtil.removeSession();
		
	}
	

}
