package xzcode.ggserver.core.common.message.request.manager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import xzcode.ggserver.core.common.message.request.Request;
import xzcode.ggserver.core.common.message.request.action.IRequestMessageHandler;
import xzcode.ggserver.core.common.message.request.handler.IRequestMessageHandlerInfo;
import xzcode.ggserver.core.common.message.request.handler.RequestMessagerHandlerInfo;

public interface IRequestMessageManager {

	/**
	 * 调用被缓存的方法
	 * @param action
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	void handle(Request<?> request);

	/**
	 * 添加缓存方法
	 * @param action
	 *
	 * @author zai
	 * 2017-07-29
	 */
	void addMessageHandler(String action, IRequestMessageHandlerInfo receiveMessageHandler);

	/**
	 * 获取关联方法模型
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	IRequestMessageHandlerInfo getMessageHandler(String action);

	/**
	 * 获取已注册的action名称集合
	 * 
	 * @return
	 * @author zai
	 * 2019-10-23 16:40:34
	 */
	List<String> getMappedActions();

	/**
	 * 获取已注册的消息调用器集合
	 * 
	 * @return
	 * @author zai
	 * 2019-10-23 16:40:34
	 */
	List<IRequestMessageHandlerInfo> getMappedInvokers();
	
	

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
		
		this.addMessageHandler(actionId, handler);
	}


}