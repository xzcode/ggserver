package com.xzcode.ggserver.docs.core.protobuf;

public class ProtoMessage {
	
	/**
	 * 消息名称
	 */
	private String messageName;
	
	/**
	 * 内容
	 */
	private String content;
	
	
	

	public ProtoMessage() {
	}

	public ProtoMessage(String messageName, String content) {
		this.messageName = messageName;
		this.content = content;
	}

	public String getMessageName() {
		return messageName;
	}
	
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
