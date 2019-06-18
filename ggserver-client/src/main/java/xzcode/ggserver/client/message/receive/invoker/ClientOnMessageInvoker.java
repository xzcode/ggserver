package xzcode.ggserver.client.message.receive.invoker;

import xzcode.ggserver.client.message.receive.IClientOnMessageAction;

/**
 * 消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 * @param <T>
 */
public class ClientOnMessageInvoker<T> implements IClientOnMessageInvoker{
	
	
	/**
	 * 请求标识
	 */
	private String messageTag;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> messageClass;
	
	
	/**
	 * 消息调用对象
	 */
	private IClientOnMessageAction<T> onMessage;


	@Override
	public Object invoke(String requestTag, Object message) throws Exception {
		onMessage.onMessage((T) message);
		return null;
	}


	public String getReceiveAction() {
		return messageTag;
	}


	public void setRequestTag(String requestTag) {
		this.messageTag = requestTag;
	}


	public Class<?> getMessageClass() {
		return messageClass;
	}


	public void setRequestMessageClass(Class<?> messageClass) {
		this.messageClass = messageClass;
	}


	public IClientOnMessageAction<?> getOnMessage() {
		return onMessage;
	}


	public void setOnMessage(IClientOnMessageAction<T> onMessage) {
		this.onMessage = onMessage;
	}


	@Override
	public String getSendAction() {
		return null;
	}
	
	
	

}
