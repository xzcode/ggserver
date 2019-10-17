package xzcode.ggserver.core.common.event.invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xzcode.ggserver.core.common.component.GGComponentManager;
import xzcode.ggserver.core.common.event.IEventHandler;
import xzcode.ggserver.core.common.event.invoker.impl.EventMethodInvoker;

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

	
	public void invoke(String eventTag, Object message) throws Exception {
		List<IEventInvoker> invokers = map.get(eventTag);
		if (invokers != null) {
			for (IEventInvoker invoker : invokers) {
				invoker.invoke(message);
			}
		}
	}
	
	/**
	 * 清除指定事件监听
	 * 
	 * @param eventTag
	 * @author zai
	 * 2019-10-17 16:47:09
	 */
	public void clear(String eventTag) {
		map.remove(eventTag);
	}
	
	/**
	 * 移除事件监听
	 * 
	 * @param eventTag
	 * @param eventHandler
	 * @author zai
	 * 2019-10-17 16:58:11
	 */
	public void remove(String eventTag, IEventHandler<?> eventHandler) {
		List<IEventInvoker> list = map.get(eventTag);
		if (list != null) {
			Iterator<IEventInvoker> it = list.iterator();
			while (it.hasNext()) {
				it.remove();
				
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
		List<IEventInvoker> invokers = map.get(invoker.getEvent());
		if (invokers != null) {
			invokers.add(invoker);
			return;
		}else {
			invokers = new ArrayList<>();
			invokers.add(invoker);
		}
		
		map.put(invoker.getEvent(), invokers);
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
