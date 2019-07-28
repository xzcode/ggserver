package xzcode.ggserver.core.message.receive;

/**
 * 消息接收模型
 * 
 * @author zai
 * 2019-07-28 15:34:08
 */
public class Request {
	

	/* 发送消息标识 */
	private String action;

	/* 消息体 */
	private Object message;
	
	

	public Request() {
		super();
	}
	public Request(String action, Object message) {
		this.action = action;
		this.message = message;
	}
	public Request(String action, Object message, int sendType) {
		this.action = action;
		this.message = message;
	}


	public static Request create(String action, Object message) {
		return new Request(action, message);
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
