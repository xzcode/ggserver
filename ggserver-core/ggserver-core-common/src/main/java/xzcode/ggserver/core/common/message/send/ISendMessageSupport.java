package xzcode.ggserver.core.common.message.send;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISendMessageSupport extends ICurrentSessionSendMessageSupport, IGGConfigSupport{
	
	
	default IGGFuture send(GGSession session, String action, Object message) {
		return getConfig().getSendMessageManager().send(session, action, message);
	}

	default IGGFuture send(GGSession session, String action) {
		return getConfig().getSendMessageManager().send(session, action);
	}
	
	default IGGFuture send(GGSession session, String action, Object message, long delayMs) {
		return getConfig().getSendMessageManager().send(session, action, message, delayMs);
	}
	default IGGFuture send(GGSession session, String action, long delayMs) {
		return getConfig().getSendMessageManager().send(session, action, delayMs);
	}

	default IGGFuture send(GGSession session, String action, Object message, Object metadata, long delay, TimeUnit timeUnit) {
		return getConfig().getSendMessageManager().send(session, action, message, metadata, delay, timeUnit);
	}
	
	default void sendToAll(String action, Object message) {
		getConfig().getSendMessageManager().sendToAll(action, message);
	}
	default void sendToAll(String action) {
		getConfig().getSendMessageManager().sendToAll(action);
	}

	default IGGFuture send(GGSession session, Pack pack) {
		return getConfig().getSendMessageManager().send(session, pack);
	}

	default IGGFuture send(GGSession session, Pack pack, long delayMs) {
		return getConfig().getSendMessageManager().send(session, pack, delayMs);
	}

	default IGGFuture send(GGSession session, Pack pack, long delay, TimeUnit timeUnit) {
		return getConfig().getSendMessageManager().send(session, pack, delay, timeUnit);
	}


	@Override
	default GGSession getSession() {
		return GGSessionUtil.getSession();
	}
	
	

}
