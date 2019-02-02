package com.xzcode.socket.core.event;

import java.util.HashMap;
import java.util.Map;

import com.xzcode.socket.core.component.SocketComponentObjectManager;

public class EventInvokerManager {
	
	
	
	private final Map<String, IEventInvoker> map = new HashMap<>();
	
	
	public void updateComponentObject(SocketComponentObjectManager componentObjectMapper) {
		for (String key : map.keySet()) {
			IEventInvoker invoker = map.get(key);
			if (invoker instanceof EventMethodInvoker) {
				EventMethodInvoker eventMethodInvoker = (EventMethodInvoker) invoker;
				Class<?> clazz = eventMethodInvoker.getComponentClass();
				eventMethodInvoker.setComponentObj(componentObjectMapper.getSocketComponentObject(clazz));				
			}
			
		}
	}

	/**
	 * 调用被缓存的方法
	 * @param requestTag
	 * @param method
	 * @param message
	 * @throws Exception
	 * 
	 * @author zai
	 * 2017-07-29
	 */
	public void invoke(String eventTag) throws Exception {
		IEventInvoker invoker = map.get(eventTag);
		if (invoker != null) {
			invoker.invoke();
		}
	}
	
	/**
	 * 添加事件调用者
	 * 
	 * @param invoker
	 * @author zai
	 * 2019-01-03 10:14:47
	 */
	public void put(IEventInvoker invoker) {
		IEventInvoker invoker2 = map.get(invoker.getEventTag());
		if (invoker2 != null) {
			if (invoker instanceof EventMethodInvoker) {
				EventMethodInvoker eventMethodInvoker = (EventMethodInvoker) invoker;
				eventMethodInvoker.getMethods().addAll(eventMethodInvoker.getMethods());
				return;
			}
		}
		
		map.put(invoker.getEventTag(), invoker);
	}
	
	
	/**
	 * 获取关联方法模型
	 * @param eventTag
	 * @return
	 * 
	 * @author zai
	 * 2017-08-02
	 */
	public IEventInvoker get(String eventTag){
		return map.get(eventTag);
	}
	
	/**
	 * 是否存在指定事件类型
	 * @param eventTag
	 * @return
	 * 
	 * @author zai
	 * 2018-05-29
	 */
	public boolean contains(String eventTag){
		return map.containsKey(eventTag);
	}
	
	

}
