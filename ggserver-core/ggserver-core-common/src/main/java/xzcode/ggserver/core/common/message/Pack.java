package xzcode.ggserver.core.common.message;

import java.nio.charset.Charset;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * 消息发送模型
 * 
 * @author zai
 * 2019-03-12 19:20:01
 */
public class Pack {
	
	/* 会话 */
	private GGSession session;
	
	/* 元数据 */
	private byte[] metadata;

	/* 消息标识 */
	private byte[] action;

	/* 消息体 */
	private byte[] message;
	

	public Pack(byte[] metadata, byte[] action, byte[] message) {
		this.metadata = metadata;
		this.action = action;
		this.message = message;
	}
	

	public Pack(GGSession session, byte[] metadata, byte[] action, byte[] message) {
		super();
		this.session = session;
		this.metadata = metadata;
		this.action = action;
		this.message = message;
	}
	
	/**
	 * 包操作类型
	 * 
	 * @author zai
	 * 2019-12-18 15:18:33
	 */
	public static interface PackOperType {
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
		return new String(action, Charset.forName("utf-8"));
	}
	public String getActionString(Charset charset) {
		return new String(action,charset);
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
	public byte[] getMetadata() {
		return metadata;
	}
	public void setMetadata(byte[] metadata) {
		this.metadata = metadata;
	}


	public GGSession getSession() {
		return session;
	}


	public void setSession(GGSession session) {
		this.session = session;
	}
	
	

}
