package com.xzcode.ggserver.core.common.component;

import java.util.HashMap;
import java.util.Map;

/**
 * 组件管理器
 * 
 * 
 * @author zai
 * 2019-02-09 10:14:26
 */
public class GGComponentManager {
	
	private final Map<Class<?>, Object> map = new HashMap<>();
	
	public Object getComponentObject(Class<?> clazz) {
		return map.get(clazz);
	}
	
	public void put(Class<?> key, Object controller) {
		map.put(key, controller);
	}
	
	public Map<Class<?>, Object> getComponentMap() {
		return map;
	}
	
	
	
}
