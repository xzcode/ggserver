package xzcode.ggserver.client.message.send;

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
	
	/**
	 * 发送给所有人
	 * 
	 * @param action
	 * @param message
	 * @author zai
	 * 2019-05-05 17:23:27
	 */
	void sendToAll(String action, Object message);
	void sendToAll(String action);

}
