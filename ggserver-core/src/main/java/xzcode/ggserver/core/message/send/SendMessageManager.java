package xzcode.ggserver.core.message.send;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionUtil;
import xzcode.ggserver.core.session.GGUserSessonManager;
import xzcode.ggserver.core.utils.json.GGServerJsonUtil;

/**
 * 消息发送管理器
 * 
 * 
 * @author zai
 * 2019-02-09 14:30:13
 */
public class SendMessageManager implements ISendMessageSupport{
	
	private final static Logger logger = LoggerFactory.getLogger(SendMessageManager.class);
	
	private GGConfig config;
	
	
	public SendMessageManager(GGConfig config) {
		super();
		this.config = config;
	}

	public void send(Channel channel, SendModel sendModel) {
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(sendModel);			
		}else {
			if (logger.isDebugEnabled()) {
				logger.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(sendModel));
			}
		}
	}
	
	/**
	 * 发送消息
	 * 
	 * @param userId
	 * @param action
	 * @param message
	 * 
	 * @author zai 2017-08-04
	 */
	@Override
	public void send(Object userId, String action, Object message) {
		send(userId, action, message, 0);
	}
	
	/**
	 * 根据用户id发送消息（无消息体）
	 * 
	 * @param userId  用户id
	 * @param action 发送消息标识
	 * @author zai 2018-12-29 14:25:27
	 */
	@Override
	public void send(Object userId, String action) {
		
		send(userId, action, null, 0);
	}
	
	/**
	 * 发送消息（无消息体）
	 * 
	 * @param action
	 * @author zai 2018-12-29 14:23:54
	 */
	@Override
	public void send(String action) {
		GGSession session = GGSessionUtil.getSession();
		if (session != null) {
			if (!config.getMessageFilterManager().doResponseFilters(session.getRegisteredUserId(), Response.create(action, null))) {
				return;
			}
			this.send(session.getChannel(),SendModel.create(action.getBytes(), null));
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
	@Override
	public void send(String action, Object message) {
		send(null, action, message, 0);
	}

	@Override
	public void send(Object userId, String action, Object message, long delayMs) {
		GGSession session = null;
		if (userId != null) {
			session = this.config.getUserSessonManager().get(userId);
		}else {
			session = GGSessionUtil.getSession();
		}
		if (session != null) {
			//发送过滤器
			if (!config.getMessageFilterManager().doResponseFilters(userId, Response.create(action, message))) {
				return;
			}
			try {
				Channel channel = session.getChannel();
				if (channel != null && channel.isActive()) {
					byte[] actionIdData = action.getBytes(config.getCharset());
					byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
					
					if (delayMs > 0) {
						this.config.getTaskExecutor().schedule(() -> {
							this.send(channel, SendModel.create(actionIdData, messageData));
						}, delayMs, TimeUnit.MILLISECONDS);
					}else {
						this.send(channel, SendModel.create(actionIdData, messageData));
					}
				}
			} catch (Exception e) {
				logger.error("Send message Error!", e);
			}
		}
	}

	@Override
	public void send(Object userId, String action, long delayMs) {
		send(userId, action, null, delayMs);
	}

	@Override
	public void send(String action, long delayMs) {
		send(null, action, null, delayMs);
		
	}

	@Override
	public void send(String action, Object message, long delayMs) {
		send(null, action, message, delayMs);
		
	}

	@Override
	public void sendToAll(String action, Object message) {
		try {
			
			GGUserSessonManager sessonManager = config.getUserSessonManager();
			Set<Entry<Object, GGSession>> entrySet = sessonManager.getSessionMap().entrySet();
			Channel channel = null;
			byte[] actionIdData = action.getBytes();
			byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
			for (Entry<Object, GGSession> entry : entrySet) {
				//发送过滤器
				if (!config.getMessageFilterManager().doResponseFilters(entry.getKey(), Response.create(action, message))) {
					return;
				}
				channel = entry.getValue().getChannel();
				if (channel.isActive()) {
					channel.writeAndFlush(SendModel.create(actionIdData, messageData));
				}
				
			}
		} catch (Exception e) {
			logger.error("GGServer sendToAll ERROR!");
		}
		
	}

	@Override
	public void sendToAll(String action) {
		sendToAll(action, null);
	}

	@Override
	public SendMessageManager getSendMessageManager() {
		return this;
	}

}
