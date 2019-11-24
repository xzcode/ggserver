package xzcode.ggserver.core.common.message.receive.handler;

import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.receive.action.IRequestMessageAcion;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 * @param <T>
 */
public class RequestMessagerHandler implements IRequestMessageHandler{
	
	
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
	private IRequestMessageAcion<Object> messageAcion;



	public void handle(GGSession session, Request request) throws Exception {
		messageAcion.action(session, request.getMessage());
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


	public IRequestMessageAcion<?> getHandler() {
		return messageAcion;
	}


	@SuppressWarnings("unchecked")
	public void setHandler(IRequestMessageAcion<?> messageAcion) {
		this.messageAcion = (IRequestMessageAcion<Object>) messageAcion;
	}


}
