package com.xzcode.ggserver.core.common.session.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.xzcode.ggserver.core.common.config.GGConfig;

/**
 * sesson默认实现
 * 
 * 
 * @author zai
 * 2019-10-02 22:48:34
 */
public abstract class AbstractAttrMapSession<C extends GGConfig> extends AbstractSession<C> {
	
	
	private Map<String, Object> attrMap = new ConcurrentHashMap<>(6);
	

	public AbstractAttrMapSession(String sessionId, C config) {
		super(sessionId, config);
	}


	@Override
	public void addAttribute(String key, Object value) {
		attrMap.put(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return attrMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAttribute(String key, Class<T> t) {
		return (T) attrMap.get(key);
	}

	@Override
	public Object reomveAttribute(String key) {
		return attrMap.remove(key);
	}
	

}
