package com.xzcode.socket.core.message.invoker;

/**
 * 统一消息调用接口
 * 
 * @author zai
 * 2019-01-01 22:30:41
 */
public interface IMessageInvoker {
	
	/**
	 * 执行调用
	 * 
	 * @param requestTag
	 * @param message
	 * @return
	 * @throws Exception
	 * @author zai
	 * 2019-01-01 22:32:22
	 */
	public Object invoke(String requestTag, Object message) throws Exception;
	
	public String getRequestTag();
	
	public Class<?> getRequestMessageClass();
	
	public String getSendTag();

}
