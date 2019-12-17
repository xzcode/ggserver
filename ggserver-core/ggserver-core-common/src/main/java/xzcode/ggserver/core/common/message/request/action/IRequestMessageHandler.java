package xzcode.ggserver.core.common.message.request.action;


import xzcode.ggserver.core.common.message.request.Request;

/**
 * 动态请求消息绑定接口
 * 
 * @param <T>
 * @author zai
 * 2019-01-01 22:09:24
 */
public interface IRequestMessageHandler<T> {

	/**
	 * 处理消息
	 * @param session
	 * @param t
	 * 
	 * @author zai
	 * 2019-11-24 22:35:17
	 */
	void handle(Request<T> request);
	
}
