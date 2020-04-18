package com.xzcode.ggserver.core.common.message.request.manager;

import java.util.List;

import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.request.action.MessageDataHandler;
import com.xzcode.ggserver.core.common.message.request.handler.ReceiveMessageHandlerInfo;
import com.xzcode.ggserver.core.common.message.request.handler.DefaultReceiveMessagerHandlerInfo;
import com.xzcode.ggserver.core.common.utils.GenericClassUtil;

public interface ReceiveMessageManager {

	/**
	 * 调用被缓存的方法
	 * @param action
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	void handle(MessageData<?> request);

	/**
	 * 添加缓存方法
	 * @param action
	 *
	 * @author zai
	 * 2017-07-29
	 */
	void addMessageHandler(String action, ReceiveMessageHandlerInfo receiveMessageHandler);

	/**
	 * 获取关联方法模型
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	ReceiveMessageHandlerInfo getMessageHandler(String action);

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
	List<ReceiveMessageHandlerInfo> getMappedInvokers();
	
	

	/**
	 * 动态监听消息
	 * 
	 * @param string
	 * @param messageAcion
	 * @author zai
	 * 2019-01-02 09:41:59
	 * @param <T>
	 */
	default <T> void onMessage(String actionId, MessageDataHandler<T> messageAcion) {
		
		DefaultReceiveMessagerHandlerInfo handler = new DefaultReceiveMessagerHandlerInfo();
		handler.setHandler(messageAcion);
		handler.setRequestTag(actionId);
		Class<?> msgClass = GenericClassUtil.getGenericClass(messageAcion.getClass());
		
		handler.setMessageClass(msgClass );
		
		this.addMessageHandler(actionId, handler);
	}


}