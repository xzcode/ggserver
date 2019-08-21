package xzcode.ggserver.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xzcode.ggserver.core.component.GGComponentManager;

public class EventInvokerManager {
	
	
	
	private final Map<String, List<IEventInvoker>> map = new HashMap<>();
	
	
	public void updateComponentObject(GGComponentManager componentObjectMapper) {
		for (String key : map.keySet()) {
			List<IEventInvoker> invokers = map.get(key);
			for (IEventInvoker mapedInvoker : invokers) {
				if (mapedInvoker instanceof EventMethodInvoker) {
					EventMethodInvoker eventMethodInvoker = (EventMethodInvoker) mapedInvoker;
					Class<?> clazz = eventMethodInvoker.getComponentClass();
					eventMethodInvoker.setComponentObj(componentObjectMapper.getComponentObject(clazz));				
				}				
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
		this.invoke(eventTag, null);
	}
	
	public void invoke(String eventTag, Object message) throws Exception {
		List<IEventInvoker> invokers = map.get(eventTag);
		if (invokers != null) {
			if (message != null) {
				for (IEventInvoker invoker : invokers) {
					invoker.invoke(message);
				}
			}else {
				for (IEventInvoker invoker : invokers) {
					invoker.invoke();
				}
			}
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
		List<IEventInvoker> invokers = map.get(invoker.getEventTag());
		if (invokers != null) {
			invokers.add(invoker);
			return;
		}else {
			invokers = new ArrayList<>();
			invokers.add(invoker);
		}
		
		map.put(invoker.getEventTag(), invokers);
	}
	
	
	/**
	 * 获取关联方法模型
	 * @param eventTag
	 * @return
	 * 
	 * @author zai
	 * 2017-08-02
	 */
	public List<IEventInvoker> get(String eventTag){
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
