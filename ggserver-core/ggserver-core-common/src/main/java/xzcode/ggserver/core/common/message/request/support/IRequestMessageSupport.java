package xzcode.ggserver.core.common.message.request.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;
import xzcode.ggserver.core.common.message.request.handler.RequestMessagerHandlerInfo;
import xzcode.ggserver.core.common.message.request.manager.IRequestMessageManager;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface IRequestMessageSupport {
	
	/**
	 * 获取消息请求管理器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 14:19:44
	 */
	IRequestMessageManager getRequestMessageManager();
	
	

	/**
	 * 动态监听消息
	 * 
	 * @param string
	 * @param messageAcion
	 * @author zai
	 * 2019-01-02 09:41:59
	 * @param <T>
	 */
	default <T> void onMessage(String actionId, IRequestMessageHandler<T> messageAcion) {
		
		RequestMessagerHandlerInfo handler = new RequestMessagerHandlerInfo();
		handler.setHandler(messageAcion);
		handler.setRequestTag(actionId);
		Class<?> msgClass = null;
		/*
		String typeName = ((ParameterizedType)messageAcion.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
		//判断参数是否为MAP
		if (typeName.startsWith("java.util.Map")) {
			msgClass = Map.class;
		}else {
			msgClass = (Class<?>) ((ParameterizedType)messageAcion.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];			
		}
		*/
		
		
		Type[] genericInterfaces = messageAcion.getClass().getGenericInterfaces();
		if (genericInterfaces == null || genericInterfaces.length == 0) {
			ParameterizedType superParameterizedType = (ParameterizedType)messageAcion.getClass().getGenericSuperclass();
			msgClass = (Class<?>) superParameterizedType.getActualTypeArguments()[0];
		}else {
			Type type = genericInterfaces[0];
			if (type == IRequestMessageHandler.class) {
				msgClass = IRequestMessageHandler.class;
			}else {
				msgClass = (Class<?>) ((ParameterizedType)genericInterfaces[0]).getActualTypeArguments()[0];
			}
			
		}
		handler.setMessageClass(msgClass );
		
		this.getRequestMessageManager().addMessageHandler(actionId, handler);
	}

}
