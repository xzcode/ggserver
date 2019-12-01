package xzcode.ggserver.core.common.message.request;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * 请求消息模型
 * @param <T>
 * 
 * @author zai
 * 2019-12-01 17:15:54
 */
public class Request<T> {
	
	//会话对象
	private GGSession session;

	//消息体
	private Object metadata;

	//发送消息标识
	private String action;
	
	//消息体
	private T message;
	
	
	
	public Request(GGSession session, Object metadata, String action, T message) {
		this.session = session;
		this.metadata = metadata;
		this.action = action;
		this.message = message;
	}
	
	public Request(Object metadata, String action, T message) {
		this.metadata = metadata;
		this.action = action;
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public T getMessage() {
		return message;
	}

	public GGSession getSession() {
		return session;
	}
	
	public Object getMetadata() {
		return metadata;
	}

}
