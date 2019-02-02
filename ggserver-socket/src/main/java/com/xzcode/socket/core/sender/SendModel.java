package com.xzcode.socket.core.sender;

public class SendModel {

	/* 发送消息标识 */
	private String sendTag;

	/* 消息体 */
	private Object message;

	/* io操作完成回调 */
	Runnable callback;

	public SendModel(String sendTag, Object message) {
		this.sendTag = sendTag;
		this.message = message;
	}

	public SendModel(String sendTag, Object message, Runnable callback) {
		super();
		this.sendTag = sendTag;
		this.message = message;
		this.callback = callback;
	}

	public static SendModel create(String sendTag, Object message) {
		return new SendModel(sendTag, message);
	}

	public static SendModel create(String sendTag, Object message, Runnable callback) {
		return new SendModel(sendTag, message, callback);
	}

	public String getSendTag() {
		return sendTag;
	}

	public void setSendTag(String sendTag) {
		this.sendTag = sendTag;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	public Runnable getCallback() {
		return callback;
	}
	
	public void setCallback(Runnable callback) {
		this.callback = callback;
	}

}
