package xzcode.ggserver.core.common.message.receive.handler;

import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 统一消息调用接口
 * 
 * @author zai
 * 2019-01-01 22:30:41
 */
public interface IRequestMessageHandler {
	
	/**
	 * 执行方法调用
	 * @param action
	 * @param message
	 * @throws Exception
	 * 
	 * @author zai
	 * 2019-11-24 16:42:45
	 */
	public void handle(GGSession session, Request request) throws Exception;
	
	/**
	 * 获取请求标识
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 22:32:36
	 */
	public String getAction();
	
	/**
	 * 获取消息对应的类对象
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 22:32:44
	 */
	public Class<?> getMessageClass();

}
