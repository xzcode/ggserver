package com.xzcode.socket.core.message.invoker;

import java.lang.reflect.Method;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 */
public class MethodInvoker implements IMessageInvoker{
	
	/**
	 * 调用类型
	 */
	private int type;
	
	/**
	 * 请求标识
	 */
	private String requestTag;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> requestMessageClass;
	
	/**
	 * 发送标识
	 */
	private String sendTag;
	
	/**
	 * 发送消息的class类型
	 */
	private Class<?> sendMessageClass;
	
	/**
	 * 方法对象
	 */
	private Method method;
	
	/**
	 * 组件实例对象
	 */
	private Object componentObj;
	
	/**
	 * 组件实例对象的class类型
	 */
	private Class<?> componentClass;
	
	

	@Override
	public Object invoke(String requestTag, Object message) throws Exception {
		//如果消息体为空
		if (message == null) {
			return method.invoke(componentObj);
		}
		//如果消息体不为空
		return method.invoke(componentObj, message);
	}
	
	

	public String getRequestTag() {
		return requestTag;
	}

	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}

	public String getSendTag() {
		return sendTag;
	}

	public void setSendTag(String sendTag) {
		this.sendTag = sendTag;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getRequestMessageClass() {
		return requestMessageClass;
	}

	public void setRequestMessageClass(Class<?> requestMessageClass) {
		this.requestMessageClass = requestMessageClass;
	}

	public Class<?> getSendMessageClass() {
		return sendMessageClass;
	}

	public void setSendMessageClass(Class<?> sendMessageClass) {
		this.sendMessageClass = sendMessageClass;
	}
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public Object getComponentObj() {
		return componentObj;
	}
	
	public void setComponentObj(Object componentObj) {
		this.componentObj = componentObj;
	}
	
	public void setComponentClass(Class<?> componentClass) {
		this.componentClass = componentClass;
	}
	public Class<?> getComponentClass() {
		return componentClass;
	}
}
