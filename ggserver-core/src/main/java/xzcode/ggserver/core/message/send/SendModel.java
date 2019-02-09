package xzcode.ggserver.core.message.send;

public class SendModel {

	/* 发送消息标识 */
	private String action;

	/* 消息体 */
	private Object message;

	/* io操作完成回调 */
	Runnable callback;

	public SendModel(String action, Object message) {
		this.action = action;
		this.message = message;
	}

	public SendModel(String action, Object message, Runnable callback) {
		super();
		this.action = action;
		this.message = message;
		this.callback = callback;
	}

	public static SendModel create(String action, Object message) {
		return new SendModel(action, message);
	}

	public static SendModel create(String action, Object message, Runnable callback) {
		return new SendModel(action, message, callback);
	}

	public String getSendTag() {
		return action;
	}

	public void setSendTag(String sendTag) {
		this.action = sendTag;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	public Runnable getCallback() {
		return callback;
	}
	
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

}
