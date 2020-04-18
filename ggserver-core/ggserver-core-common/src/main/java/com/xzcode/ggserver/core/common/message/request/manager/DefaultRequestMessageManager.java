package com.xzcode.ggserver.core.common.message.request.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.xzcode.ggserver.core.common.message.MessageData;
import com.xzcode.ggserver.core.common.message.request.handler.ReceiveMessageHandlerInfo;
import com.xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;


/**
 * 消息调用者管理器
 * 
 * @author zai
 * 2019-01-01 23:25:21
 */
public class DefaultRequestMessageManager implements ReceiveMessageManager {

	private final Map<String, ReceiveMessageHandlerInfo> handlerMap = new ConcurrentHashMap<>();

	/**
	 * 调用被缓存的方法
	 * @param action
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	@Override
	public void handle(MessageData<?> request){
		ReceiveMessageHandlerInfo invoker = handlerMap.get(request.getAction());
		if (invoker != null) {
			try {
				invoker.handle(request);
			} catch (Exception e) {
				GGLoggerUtil.getLogger(this.getClass()).error("Handle request Error!", e);
			}
			return;
		}
		GGLoggerUtil.getLogger().warn("No such action: {} ", request.getAction());
	}

	/**
	 * 添加缓存方法
	 * @param action
	 *
	 * @author zai
	 * 2017-07-29
	 */
	@Override
	public void addMessageHandler(String action, ReceiveMessageHandlerInfo receiveMessageHandler) {
		if (handlerMap.containsKey(action)) {
			throw new RuntimeException("Action '"+action+"' has been mapped!");
		}
		handlerMap.put(action, receiveMessageHandler);
		if (GGLoggerUtil.getLogger().isInfoEnabled()) {
			GGLoggerUtil.getLogger().info("GGServer Mapped Message Action: {}", action);
		}
	}


	/**
	 * 获取关联方法模型
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	@Override
	public ReceiveMessageHandlerInfo getMessageHandler(String action){
		return handlerMap.get(action);
	}
	
	/**
	 * 获取已注册的action名称集合
	 * 
	 * @return
	 * @author zai
	 * 2019-10-23 16:40:34
	 */
	@Override
	public List<String> getMappedActions() {
		return new ArrayList<>(handlerMap.keySet());
	}
	
	/**
	 * 获取已注册的消息调用器集合
	 * 
	 * @return
	 * @author zai
	 * 2019-10-23 16:40:34
	 */
	@Override
	public List<ReceiveMessageHandlerInfo> getMappedInvokers() {
		return handlerMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
	}


}
