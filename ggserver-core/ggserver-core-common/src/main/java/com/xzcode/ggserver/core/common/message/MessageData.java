package com.xzcode.ggserver.core.common.message;

import com.xzcode.ggserver.core.common.session.GGSession;

import io.netty.channel.Channel;

/**
 * 消息模型
 * @param <T>
 * 
 * @author zai
 * 2019-12-01 17:15:54
 */
public class MessageData<T> {
	
	//会话对象
	private GGSession session;

	//发送消息标识
	private String action;
	
	//消息体
	private T message;
	
	//通道
	private Channel channel;
	
	
	
	public MessageData(GGSession session, String action, T message) {
		this.session = session;
		this.action = action;
		this.message = message;
	}
	
	public MessageData(String action, T message) {
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
	
	public void setSession(GGSession session) {
		this.session = session;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setMessage(T message) {
		this.message = message;
	}
	
	
	
}
