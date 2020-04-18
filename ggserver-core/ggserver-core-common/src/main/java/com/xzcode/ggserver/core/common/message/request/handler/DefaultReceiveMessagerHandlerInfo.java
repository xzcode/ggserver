package com.xzcode.ggserver.core.common.message.request.handler;

import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.request.action.MessageDataHandler;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 * @param <T>
 */
public class DefaultReceiveMessagerHandlerInfo implements ReceiveMessageHandlerInfo{
	
	
	/**
	 * 请求标识
	 */
	private String action;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> messageClass;
	
	
	/**
	 * 消息调用对象
	 */
	private MessageDataHandler<Object> messageAcion;



	@SuppressWarnings("unchecked")
	public void handle(MessageData<?> request) throws Exception {
		messageAcion.handle((MessageData<Object>) request);
	}


	public String getAction() {
		return action;
	}


	public void setRequestTag(String requestTag) {
		this.action = requestTag;
	}


	public Class<?> getMessageClass() {
		return messageClass;
	}


	public void setMessageClass(Class<?> messageClass) {
		this.messageClass = messageClass;
	}


	public MessageDataHandler<?> getHandler() {
		return messageAcion;
	}


	@SuppressWarnings("unchecked")
	public void setHandler(MessageDataHandler<?> messageAcion) {
		this.messageAcion =  (MessageDataHandler<Object>) messageAcion;
	}


}
