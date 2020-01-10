package xzcode.ggserver.core.common.message;

import java.nio.charset.Charset;

import io.netty.channel.Channel;
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
	
	/**
	 * 协议类型
	 */
	private String protocolType;
	
	/* 通道 */
	private Channel channel;
	
	/**
	 * 缓存转换后的actionid
	 */
	private String cachedActionId;
	

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

}
