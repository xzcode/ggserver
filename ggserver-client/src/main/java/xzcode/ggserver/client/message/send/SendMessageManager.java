package xzcode.ggserver.client.message.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.Channel;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.utils.json.GGJsonUtil;

/**
 * 消息发送管理器
 * 
 * 
 * @author zai
 * 2019-02-09 14:30:13
 */
public class SendMessageManager{
	
	private final static Logger logger = LoggerFactory.getLogger(SendMessageManager.class);
	
	private GGClientConfig config;
	
	
	
	public SendMessageManager(GGClientConfig config) {
		super();
		this.config = config;
	}
	
	public void sendBytes(byte[] bytes) {
		Channel channel = config.getChannel();
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(bytes);			
		}else {
			if (logger.isDebugEnabled()) {
				logger.debug("Channel is inactived! Message will not be send, bytes:{}", bytes);
			}
		}
	}

	public void send(SendModel sendModel) {
		Channel channel = config.getChannel();
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(sendModel);			
		}else {
			if (logger.isDebugEnabled()) {
				logger.debug("Channel is inactived! Message will not be send, SendModel:{}", GGJsonUtil.toJson(sendModel));
			}
		}
	}
	
	
	/**
	 * 发送消息到当前通道
	 * 
	 * @param action
	 * @param message
	 * 
	 * @author zai 2017-09-18
	 */
	public void send(String action, Object message) {
		send(action, message, 0);
	}

	public void send(String action, Object message, long delayMs) {
		try {
			Channel channel = config.getChannel();
			if (channel != null && channel.isActive()) {
				byte[] actionIdData = action.getBytes(config.getCharset());
				byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
				
				if (delayMs > 0) {
					this.config.getTaskExecutor().setTimeout(() -> {
						this.send(SendModel.create(actionIdData, messageData));
					}, delayMs);
				}else {
					this.send(SendModel.create(actionIdData, messageData));
				}
			}
		} catch (Exception e) {
			logger.error("Send message Error!", e);
		}
	}

	public void send(String action, long delayMs) {
		send(action, null, delayMs);
		
	}

}
