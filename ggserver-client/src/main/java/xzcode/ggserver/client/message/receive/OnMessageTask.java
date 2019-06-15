package xzcode.ggserver.client.message.receive;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.message.receive.invoker.IOnMessageInvoker;
import xzcode.ggserver.client.message.send.SendModel;

/**
 * 请求消息任务
 * 
 * 
 * @author zai
 * 2019-02-09 14:26:10
 */
public class OnMessageTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OnMessageTask.class);
	
	/**
	 * 配置
	 */
	private GGClientConfig config;
	
	
	/**
	 * socket消息体对象
	 */
	private byte[] message;
	
	/**
	 * 请求标识
	 */
	private byte[] action;
	
	
	public OnMessageTask() {
		
	}

	public OnMessageTask(byte[] action, byte[] message, GGClientConfig config) {
		this.message = message;
		this.action = action;
		this.config = config;
	}

	@Override
	public void run() {
		
		String actionStr = new String(action, Charset.defaultCharset());
		try {
			
			
			IOnMessageInvoker invoker = config.getMessageInvokerManager().get(actionStr);
			Object msgObj = null;
			if (message != null) {
				msgObj = config.getSerializer().deserialize(message, invoker.getRequestMessageClass());
			}
			
			Object returnObj = config.getRequestMessageManager().invoke(actionStr, msgObj);
			if (returnObj != null) {
				config.getSendMessageManager().send(SendModel.create(config.getSerializer().serialize(config.getRequestMessageManager().getSendAction(actionStr)), config.getSerializer().serialize(returnObj)));
			}
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!! -- actionId: {}, error: {}", actionStr, e);
		}
		
	}
	

}
