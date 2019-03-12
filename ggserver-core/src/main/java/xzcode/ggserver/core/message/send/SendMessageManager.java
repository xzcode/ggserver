package xzcode.ggserver.core.message.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.session.GGSession;
import xzcode.ggserver.core.session.GGSessionThreadLocalUtil;
import xzcode.ggserver.core.utils.json.GGServerJsonUtil;

/**
 * 消息发送管理器
 * 
 * 
 * @author zai
 * 2019-02-09 14:30:13
 */
public class SendMessageManager implements ISendMessage{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SendMessageManager.class);
	
	private GGServerConfig config;
	
	
	public SendMessageManager(GGServerConfig config) {
		super();
		this.config = config;
	}

	public void send(Channel channel, SendModel sendModel) {
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(sendModel);			
		}else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(sendModel));
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
		
		GGSession session = this.config.getUserSessonManager().get(userId);
		if (session != null) {
			if (!config.getMessageFilterManager().doResponseFilters(userId, action, message)) {
				return;
			}
			try {
				this.send(session.getChannel(),SendModel.create(action.getBytes(), this.config.getSerializer().serialize(message)));
			} catch (Exception e) {
				LOGGER.error("Send message Error!", e);
			}
		}
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
		
		GGSession session = this.config.getUserSessonManager().get(userId);
		if (session != null) {
			if (!config.getMessageFilterManager().doResponseFilters(userId, action, null)) {
				return;
			}
				this.send(session.getChannel(),SendModel.create(action.getBytes(), null));
		}
	}
	
	/**
	 * 发送消息（无消息体）
	 * 
	 * @param action
	 * @author zai 2018-12-29 14:23:54
	 */
	@Override
	public void send(String action) {
		GGSession session = GGSessionThreadLocalUtil.getSession();
		if (session != null) {
			if (!config.getMessageFilterManager().doResponseFilters(session.getRegisteredUserId(), action, null)) {
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
		GGSession session = GGSessionThreadLocalUtil.getSession();
		if (session != null) {
			if (!config.getMessageFilterManager().doResponseFilters(session.getRegisteredUserId(), action, message)) {
				return;
			}
			try {
				this.send(session.getChannel(),SendModel.create(action.getBytes(), this.config.getSerializer().serialize(message)));
			} catch (Exception e) {
				LOGGER.error("Send message Error!", e);
			}
		}
	}
}
