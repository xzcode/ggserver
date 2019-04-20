package xzcode.ggserver.core.message.send;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISendMessage {
	
	void send(Object userId, String action, Object message, long delayMs);
	void send(Object userId, String action, Object message);

	void send(Object userId, String action);
	void send(Object userId, String action, long delayMs);

	void send(String action);
	void send(String action, long delayMs);

	void send(String action, Object message);
	void send(String action, Object message, long delayMs);

}
