package xzcode.ggserver.core.common.message;

import java.nio.charset.Charset;

/**
 * 消息发送模型
 * 
 * @author zai
 * 2019-03-12 19:20:01
 */
public class Pack {
	

	
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

}
