package xzcode.ggserver.core.message.receive;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import xzcode.ggserver.core.config.IGGConfigSupport;
import xzcode.ggserver.core.message.receive.invoker.OnMessagerInvoker;
import xzcode.ggserver.core.session.GGSessionUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface IRequestMessageSupport extends IGGConfigSupport {
	

	/**
	 * 动态监听消息
	 * 
	 * @param string
	 * @param onMessageAction
	 * @author zai
	 * 2019-01-02 09:41:59
	 * @param <T>
	 */
	default <T> void on(String actionId, IOnMessageAction<T> onMessageAction) {
		
		OnMessagerInvoker<T> invoker = new OnMessagerInvoker<>();
		invoker.setOnMessage(onMessageAction);
		invoker.setRequestTag(actionId);
		String typeName = ((ParameterizedType)onMessageAction.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
		Class<?> msgClass = null;
		//获取泛型类型参数
		if (typeName.startsWith("java.util.Map")) {
			msgClass = Map.class;
		}else {
			msgClass = (Class<?>) ((ParameterizedType)onMessageAction.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];			
		}
		invoker.setRequestMessageClass(msgClass );
		
		getConfig().getMessageInvokerManager().put(actionId, invoker);
	}
	
	

	/**
	 * 重定向消息
	 * 
	 * @param action
	 * @param message
	 * @author zai
	 * 2019-06-23 18:15:41
	 */
	default void redirect(String action, byte[] message) {
		new RedirectMessageTask(action, message, GGSessionUtil.getSession(), getConfig()).run();
	}
	
	/**
	 * 重定向消息
	 * 
	 * @param action
	 * @param message
	 * @author zai
	 * 2019-07-28 16:18:58
	 */
	default void redirect(String action, Object message) {
		new RedirectMessageTask(action, message, GGSessionUtil.getSession(), getConfig()).run();
	}
	
	

}
