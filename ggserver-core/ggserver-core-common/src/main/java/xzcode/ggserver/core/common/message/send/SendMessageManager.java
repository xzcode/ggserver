package xzcode.ggserver.core.common.message.send;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.send.future.GGSendFuture;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;
import xzcode.ggserver.core.common.session.filter.GGSessionMessageFilterManager;

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

	@Override
	public GGSendFuture send(GGSession session, PackModel packModel) {
		return this.config.getSendPackHandler().handle(session, packModel);
	}
	@Override
	public GGSendFuture send(PackModel packModel) {
		return this.config.getSendPackHandler().handle(null, packModel);
	}
	
	@Override
	public GGSendFuture send(GGSession session, String action, Object message) {
		return send(session, action, message, 0);
	}
	
	@Override
	public GGSendFuture send(GGSession session, String action) {
		return send(session, action, null, 0);
	}
	
	/**
	 * 发送消息（无消息体）
	 * 
	 * @param action
	 * @author zai 2018-12-29 14:23:54
	 */
	@Override
	public GGSendFuture send(String action) {
		GGSession session = GGSessionUtil.getSession();
		if (session != null) {
			if (!config.getMessageFilterManager().doResponseFilters(Response.create(action, null))) {
				return null;
			}
			return this.send(session,PackModel.create(action.getBytes(), null));
		}
		return null;
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
	public GGSendFuture send(String action, Object message) {
		return send(null, action, message, 0);
	}

	@Override
	public GGSendFuture send(GGSession session, String action, Object message, long delayMs) {
		return send(session, action, message, delayMs, TimeUnit.MILLISECONDS);
	}
		
	@Override
	public GGSendFuture send(GGSession session, String action, Object message, long delay, TimeUnit timeUnit) {
		if (session == null) {
			session = GGSessionUtil.getSession();
		}
		if (session != null) {
			//发送过滤器
			if (!config.getMessageFilterManager().doResponseFilters(Response.create(action, message))) {
				return null;
			}
			try {
				Channel channel = session.getChannel();
				if (channel != null && channel.isActive()) {
					byte[] actionIdData = action.getBytes(config.getCharset());
					byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
					
					this.config.getSendPackHandler().handle(session, PackModel.create(actionIdData, messageData), delay, timeUnit);
				}
			} catch (Exception e) {
				logger.error("Send message Error!", e);
			}
		}
		return null;
	}

	@Override
	public GGSendFuture send(GGSession session, String action, long delayMs) {
		return send(session, action, null, delayMs);
	}

	@Override
	public GGSendFuture send(String action, long delayMs) {
		return send(null, action, null, delayMs);
		
	}

	@Override
	public GGSendFuture send(String action, Object message, long delayMs) {
		return send(null, action, message, delayMs);
		
	}

	@Override
	public void sendToAll(String action, Object message) {
		try {
			Set<Entry<Object, GGSession>> entrySet = config.getSessionManager().getSessionMap().entrySet();
			Channel channel = null;
			byte[] actionIdData = action.getBytes();
			byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
			for (Entry<Object, GGSession> entry : entrySet) {
				GGSession sesson = entry.getValue();
				GGSessionMessageFilterManager sessionMessageFilterManager = sesson.getGGSessionMessageFilterManager();
				
				Response response = Response.create(action, message);
				//发送过滤器
				if (!config.getMessageFilterManager().doResponseFilters(response)) {
					return;
				}
				
				//会话-发送过滤器
				if (!sessionMessageFilterManager.doResponseFilters(response)) {
					return;
				}
				
				channel = sesson.getChannel();
				if (channel.isActive()) {
					channel.write(PackModel.create(actionIdData, messageData));
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
	public GGConfig getConfig() {
		return getConfig();
	}

}
