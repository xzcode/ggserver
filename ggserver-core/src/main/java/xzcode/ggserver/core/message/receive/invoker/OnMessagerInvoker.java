package xzcode.ggserver.core.message.receive.invoker;

import xzcode.ggserver.core.message.receive.IOnMessageAction;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 * @param <T>
 */
public class OnMessagerInvoker<T> implements IRequestMessageInvoker{
	
	
	/**
	 * 请求标识
	 */
	private String requestTag;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> requestMessageClass;
	
	
	/**
	 * 消息调用对象
	 */
	private IOnMessageAction<T> onMessage;


	@Override
	public Object invoke(String requestTag, Object message) throws Exception {
		onMessage.onMessage((T) message);
		return null;
	}


	public String getReceiveAction() {
		return requestTag;
	}


	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}


	public Class<?> getRequestMessageClass() {
		return requestMessageClass;
	}


	public void setRequestMessageClass(Class<?> requestMessageClass) {
		this.requestMessageClass = requestMessageClass;
	}


	public IOnMessageAction<?> getOnMessage() {
		return onMessage;
	}


	public void setOnMessage(IOnMessageAction<T> onMessage) {
		this.onMessage = onMessage;
	}


	@Override
	public String getSendAction() {
		return null;
	}
	
	
	

}
