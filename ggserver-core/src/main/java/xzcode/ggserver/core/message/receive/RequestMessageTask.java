package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGServerConfig;
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
	 * socket消息体对象
	 */
	private Object message;
	
	/**
	 * socket消息体对象
	 */
	private GGSession session;
	
	/**
	 * 请求标识
	 */
	private String action;
	
	
	public RequestMessageTask() {
	}
	
	

	public RequestMessageTask(String action, GGSession session, Object message, GGServerConfig config) {
		super();
		this.message = message;
		this.session = session;
		this.action = action;
	}



	@Override
	public void run() {
		
		GGSessionThreadLocalUtil.setSession(this.session);
		try {
			
			
			if (!this.config.getMessageFilterManager().doRequestFilters(action, message)) {
				GGSessionThreadLocalUtil.removeSession();
				return;
			}
			
			Object returnObj = config.getRequestMessageManager().invoke(action, this.message);
			if (returnObj != null) {
				config.getSendMessageManager().send(this.session.getChannel(), SendModel.create(config.getRequestMessageManager().getSendAction(action), returnObj));
			}
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!!", e);
		}
		GGSessionThreadLocalUtil.removeSession();
		
	}
	

}
