package xzcode.ggserver.core.message.receive;

/**
 * 动态请求消息绑定接口
 * 
 * @param <T>
 * @author zai
 * 2019-01-01 22:09:24
 */
public interface IOnMessageAction<T> {

	void onMessage(T t);
	
}
