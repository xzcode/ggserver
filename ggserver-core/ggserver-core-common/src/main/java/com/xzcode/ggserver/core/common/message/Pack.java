package com.xzcode.ggserver.core.common.message;

import java.nio.charset.Charset;

import com.xzcode.ggserver.core.common.session.GGSession;

import io.netty.channel.Channel;

/**
 * 消息发送模型
 * 
 * @author zai
 * 2019-03-12 19:20:01
 */
public class Pack {
	
	/* 会话 */
	private GGSession session;
	
	/* 消息标识 */
	private byte[] action;

	/* 消息体 */
	private byte[] message;
	
	/* 协议类型 */
	private String protocolType;
	
	/* 通道 */
	private Channel channel;
	
	/**
	 * 缓存转换后的actionid
	 */
	private String cachedActionId;
	
	/* 包操作类型*/
	private int operType;
	
	/* 包总长度 */
	private int totalLength;
	

	public Pack(byte[] action, byte[] message) {
		this.action = action;
		this.message = message;
	}
	

	public Pack(GGSession session, byte[] action, byte[] message) {
		super();
		this.session = session;
		this.action = action;
		this.message = message;
	}
	
	/**
	 * 包操作类型
	 * 
	 * @author zai
	 * 2019-12-18 15:18:33
	 */
	public static interface OperType {
		/**
		 * 请求包
		 */
		int REQUEST = 1;
		
		/**
		 * 响应包
		 */
		int RESPONSE = 2;
	}


	public byte[] getAction() {
		return action;
	}
	
	public String getActionString() {
		return getActionString(Charset.forName("utf-8"));
	}
	public String getActionString(Charset charset) {
		if (this.cachedActionId == null) {
			this.cachedActionId = new String(action,charset);
		}
		return this.cachedActionId;
	}

	public void setAction(byte[] sendTag) {
		this.action = sendTag;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}
	
	public GGSession getSession() {
		return session;
	}


	public void setSession(GGSession session) {
		this.session = session;
	}
	
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}
	
	public String getProtocolType() {
		return protocolType;
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public void setOperType(int operType) {
		this.operType = operType;
	}
	
	public int getOperType() {
		return operType;
	}
	
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}
	
	public int getTotalLength() {
		return totalLength;
	}

}
