package xzcode.ggserver.core.common.message.send;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;

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
	public IGGFuture send(GGSession session, Pack pack) {
		return session.send(pack);
	}

	@Override
	public IGGFuture send(GGSession session, String action, Object message) {
		return send(session, action, message, 0);
	}
	
	@Override
	public IGGFuture send(GGSession session, String action) {
		return send(session, action, null, 0);
	}
	

	@Override
	public IGGFuture send(GGSession session, String action, Object message, long delayMs) {
		return send(session, action, message, delayMs, TimeUnit.MILLISECONDS);
	}
		
	@Override
	public IGGFuture send(GGSession session, String action, Object message, long delay, TimeUnit timeUnit) {
		if (session == null) {
			session = GGSessionUtil.getSession();
		}
		if (session != null) {
			//发送过滤器
			if (!config.getFilterManager().doResponseFilters(Response.create(action, message))) {
				return null;
			}
			try {
				if (session.isActive()) {
					byte[] actionIdData = action.getBytes(config.getCharset());
					byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
					session.send(Pack.create(actionIdData, messageData), delay, timeUnit);
				}
			} catch (Exception e) {
				logger.error("Send message Error!", e);
			}
		}
		return null;
	}
	

	@Override
	public IGGFuture send(GGSession session, String action, long delayMs) {
		return send(session, action, null, delayMs);
	}



	@Override
	public void sendToAll(String action, Object message) {
		try {
			Set<Entry<Object, GGSession>> entrySet = config.getSessionManager().getSessionMap().entrySet();
			byte[] actionIdData = action.getBytes();
			byte[] messageData = message == null ? null : this.config.getSerializer().serialize(message);
			for (Entry<Object, GGSession> entry : entrySet) {
				GGSession sesson = entry.getValue();
				IFilterManager filterManager = sesson.getFilterManager();
				
				Response response = Response.create(action, message);
				//发送过滤器
				if (!config.getFilterManager().doResponseFilters(response)) {
					return;
				}
				
				//会话-发送过滤器
				if (!filterManager.doResponseFilters(response)) {
					return;
				}
				
				if (sesson.isActive()) {
					sesson.send(Pack.create(actionIdData, messageData));
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

	@Override
	public Channel getSendMessageChannel() {
		GGSession session = GGSessionUtil.getSession();
		if (session != null) {
			return session.getSendMessageChannel();
		}
		return null;
	}

}
