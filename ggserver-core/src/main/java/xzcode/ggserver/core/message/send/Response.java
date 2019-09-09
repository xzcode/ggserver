package xzcode.ggserver.core.message.send;

/**
 * 消息响应模型
 * 
 * @author zai
 * 2019-07-28 15:34:08
 */
public class Response {
	

	/* 发送消息标识 */
	private String action;

	/* 消息体 */
	private Object message;

	public Response(String action, Object message) {
		this.action = action;
		this.message = message;
	}
	public static Response create(String action, Object message) {
		return new Response(action, message);
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
