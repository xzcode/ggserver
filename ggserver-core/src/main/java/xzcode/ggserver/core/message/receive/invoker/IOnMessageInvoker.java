package xzcode.ggserver.core.message.receive.invoker;

/**
 * 统一消息调用接口
 * 
 * @author zai
 * 2019-01-01 22:30:41
 */
public interface IOnMessageInvoker {
	
	/**
	 * 执行调用
	 * 
	 * @param action
	 * @param message
	 * @return
	 * @throws Exception
	 * @author zai
	 * 2019-01-01 22:32:22
	 */
	public void invoke(String action, Object message) throws Exception;
	
	public String getAction();
	
	public Class<?> getMessageClass();

}
