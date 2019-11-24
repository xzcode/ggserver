package xzcode.ggserver.core.common.message.receive;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * 消息接收模型
 * 
 * @author zai
 * 2019-07-28 15:34:08
 */
public class Request {
	
	//会话对象
	private GGSession session;

	//消息体
	private Object metadata;

	//发送消息标识
	private String action;
	
	//消息体
	private Object message;
	
	
	
	public Request(GGSession session, Object metadata, String action, Object message) {
		this.session = session;
		this.metadata = metadata;
		this.action = action;
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public Object getMessage() {
		return message;
	}

	public GGSession getSession() {
		return session;
	}
	
	public Object getMetadata() {
		return metadata;
	}

}
