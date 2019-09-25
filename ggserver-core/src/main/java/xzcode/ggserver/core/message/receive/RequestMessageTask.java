package xzcode.ggserver.core.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.handler.serializer.ISerializer;
import xzcode.ggserver.core.message.filter.MessageFilterManager;
import xzcode.ggserver.core.message.receive.invoker.IOnMessageInvoker;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionUtil;

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
	private GGConfig config;
	
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
	
	

	public RequestMessageTask(byte[] action, byte[] message, GGSession session, GGConfig config) {
		this.message = message;
		this.session = session;
		this.action = action;
		this.config = config;
	}



	@Override
	public void run() {
		
		GGSessionUtil.setSession(this.session);
		Request request = new Request();
		ISerializer serializer = config.getSerializer();
		MessageFilterManager messageFilterManager = this.config.getMessageFilterManager();
		String oldAction = null;
		try {
			
			request.setAction(new String(action, config.getCharset()));
			oldAction = request.getAction();
			if (message != null) {
				IOnMessageInvoker invoker = config.getMessageInvokerManager().get(request.getAction());
				if (invoker != null) {
					request.setMessage(serializer.deserialize(message, invoker.getMessageClass()));
				}
			}
			
			if (!messageFilterManager.doRequestFilters(request)) {
				return;
			}
			while (!oldAction.equals(request.getAction())) {
				oldAction = request.getAction();
				if (message != null) {
					IOnMessageInvoker invoker = config.getMessageInvokerManager().get(request.getAction());
					if (invoker != null) {
						request.setMessage(serializer.deserialize(message, invoker.getMessageClass()));
					}else {
						LOGGER.error("Can not invoke action:{}, cause 'invoker' is null!", request.getAction());
						return;
					}
				}
				//如果action发生了改变，再次调用过滤器
				if (!messageFilterManager.doRequestFilters(request)) {
					return;
				}
			}
			
			config.getRequestMessageManager().invoke(request.getAction(), request.getMessage());
			
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!! -- actionId: {}, error: {}", request.getAction(), e);
		}finally {
			GGSessionUtil.removeSession();			
		}
		
	}
	

}
