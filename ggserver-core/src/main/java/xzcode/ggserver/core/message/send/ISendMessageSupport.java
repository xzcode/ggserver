package xzcode.ggserver.core.message.send;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISendMessageSupport {
	
	SendMessageManager getSendMessageManager();
	
	default void send(Object userId, String action, Object message) {
		getSendMessageManager().send(userId, action, message);;
	}


	default void send(Object userId, String action) {
		getSendMessageManager().send(userId, action);
		
	}

	default void send(String action) {
		getSendMessageManager().send(action);
	}

	default void send(String action, Object message) {
		getSendMessageManager().send(action, message);
	}
	
	default void send(Object userId, String action, Object message, long delayMs) {
		getSendMessageManager().send(userId, action, message, delayMs);
	}
	default void send(Object userId, String action, long delayMs) {
		getSendMessageManager().send(userId, action, delayMs);
	}
	default void send(String action, long delayMs) {
		getSendMessageManager().send(action, delayMs);
	}
	default void send(String action, Object message, long delayMs) {
		getSendMessageManager().send(action, message, delayMs);
	}
	default void sendToAll(String action, Object message) {
		getSendMessageManager().sendToAll(action, message);
	}
	default void sendToAll(String action) {
		getSendMessageManager().sendToAll(action);
	}
	

}
