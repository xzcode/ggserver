package xzcode.ggserver.core.message.send;

/**
 * 消息响应模型
 * 
 * @author zai
 * 2019-07-28 15:34:08
 */
public class Response {
	

	/* 发送消息标识 */
	private Object action;

	/* 消息体 */
	private Object message;

	public Response(Object action, Object message) {
		this.action = action;
		this.message = message;
	}
	public static Response create(Object action, Object message) {
		return new Response(action, message);
	}

	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
