package xzcode.ggserver.core.common.message.receive.support;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.message.receive.action.IRequestMessageAcion;
import xzcode.ggserver.core.common.message.receive.handler.RequestMessagerHandler;

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
	 * @param messageAcion
	 * @author zai
	 * 2019-01-02 09:41:59
	 * @param <T>
	 */
	default <T> void onMessage(String actionId, IRequestMessageAcion<T> messageAcion) {
		
		RequestMessagerHandler handler = new RequestMessagerHandler();
		handler.setHandler(messageAcion);
		handler.setRequestTag(actionId);
		String typeName = ((ParameterizedType)messageAcion.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
		Class<?> msgClass = null;
		//获取泛型类型参数
		if (typeName.startsWith("java.util.Map")) {
			msgClass = Map.class;
		}else {
			msgClass = (Class<?>) ((ParameterizedType)messageAcion.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];			
		}
		handler.setMessageClass(msgClass );
		
		getConfig().getRequestMessageManager().addMessageHandler(actionId, handler);
	}

}
