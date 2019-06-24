package xzcode.ggserver.core.message.receive.invoker;

import xzcode.ggserver.core.message.receive.IOnMessageAction;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 * @param <T>
 */
public class OnMessagerInvoker<T> implements IOnMessageInvoker{
	
	
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


	@SuppressWarnings("unchecked")
	@Override
	public void invoke(String requestTag, Object message) throws Exception {
		onMessage.onMessage((T) message);
	}


	public String getAction() {
		return requestTag;
	}


	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}


	public Class<?> getMessageClass() {
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

}
