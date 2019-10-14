package xzcode.ggserver.core.common.message.send;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.send.future.GGSendFuture;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISendMessageSupport {
	
	SendMessageManager getSendMessageManager();
	
	default GGSendFuture send(GGSession session, String action, Object message) {
		return getSendMessageManager().send(session, action, message);
	}

	default GGSendFuture send(GGSession session, String action) {
		return getSendMessageManager().send(session, action);
	}

	default GGSendFuture send(String action) {
		return getSendMessageManager().send(action);
	}

	default GGSendFuture send(String action, Object message) {
		return getSendMessageManager().send(action, message);
	}
	
	default GGSendFuture send(GGSession session, String action, Object message, long delayMs) {
		return getSendMessageManager().send(session, action, message, delayMs);
	}
	default GGSendFuture send(GGSession session, String action, long delayMs) {
		return getSendMessageManager().send(session, action, delayMs);
	}
	default GGSendFuture send(String action, long delayMs) {
		return getSendMessageManager().send(action, delayMs);
	}
	default GGSendFuture send(String action, Object message, long delayMs) {
		return getSendMessageManager().send(action, message, delayMs);
	}

	default GGSendFuture send(GGSession session, String action, Object message, long delay, TimeUnit timeUnit) {
		return getSendMessageManager().send(session, action, message, delay, timeUnit);
	}
	
	default void sendToAll(String action, Object message) {
		getSendMessageManager().sendToAll(action, message);
	}
	default void sendToAll(String action) {
		getSendMessageManager().sendToAll(action);
	}

	default GGSendFuture send(GGSession session, PackModel pack) {
		return getSendMessageManager().send(session, pack);
	}

	default GGSendFuture send(PackModel pack) {
		return getSendMessageManager().send(pack);
	}


	

}
