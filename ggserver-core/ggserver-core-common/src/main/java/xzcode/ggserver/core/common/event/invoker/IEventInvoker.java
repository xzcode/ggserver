package xzcode.ggserver.core.common.event.invoker;

import xzcode.ggserver.core.common.event.IEventHandler;

/**
 * 事件调用接口
 * 
 * @author zai
 * 2019-03-16 19:06:22
 */
public interface IEventInvoker {
	/**
	 * 获取事件标识
	 * 
	 * @return
	 * @author zai
	 * 2019-03-16 19:06:37
	 */
	public String getEvent();
	
	
	public IEventHandler<?> getEventHandler();

	/**
	 * 调用事件触发
	 * 
	 * @param message 消息体
	 * @throws Exception
	 * @author zai
	 * 2019-03-16 19:07:19
	 */
	void invoke(Object message) throws Exception;

}