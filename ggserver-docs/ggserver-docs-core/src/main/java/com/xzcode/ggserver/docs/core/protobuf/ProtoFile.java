package com.xzcode.ggserver.docs.core.protobuf;

import java.util.ArrayList;
import java.util.List;

public class ProtoFile {
	
	/**
	 * 文件名
	 */
	private String filename;
	
	/**
	 * 消息集合
	 */
	private List<ProtoMessage> messages = new ArrayList<>();
	
	
	private String contentStart;
	
	private String contentEnd;
	

	public ProtoFile() {
	}

	public ProtoFile(String filename) {
		this.filename = filename;
	}
	
	public void addMessage(ProtoMessage message) {
		this.messages.add(message);
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<ProtoMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ProtoMessage> messages) {
		this.messages = messages;
	}

	public String getContentStart() {
		return contentStart;
	}

	public void setContentStart(String contentStart) {
		this.contentStart = contentStart;
	}

	public String getContentEnd() {
		return contentEnd;
	}

	public void setContentEnd(String contentEnd) {
		this.contentEnd = contentEnd;
	}
	
	
	

}
