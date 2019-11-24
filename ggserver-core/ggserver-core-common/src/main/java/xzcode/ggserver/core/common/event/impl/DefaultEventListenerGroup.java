package xzcode.ggserver.core.common.event.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import xzcode.ggserver.core.common.event.IEventListener;
import xzcode.ggserver.core.common.event.IEventListenerGroup;
import xzcode.ggserver.core.common.event.model.EventData;
import xzcode.ggserver.core.common.session.GGSessionUtil;

/**
 * 默认事件监听组
 * 
 * @param <T>
 * @author zai
 * 2019-10-18 11:56:31
 */
public class DefaultEventListenerGroup<T> implements IEventListenerGroup<T>{
	
	protected List<IEventListener<T>> listeners = new CopyOnWriteArrayList<>();
	
	

	@Override
	public void addListener(IEventListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IEventListener<T> listener) {
		listeners.remove(listener);
	}

	@Override
	public void onEvent(T e) {
		for (IEventListener<T> li : listeners) {
			li.onEvent(new EventData<T>(GGSessionUtil.getSession(), e));
		}
	}

	@Override
	public boolean hasListener(IEventListener<T> listener) {
		return listeners.contains(listener);
	}

	@Override
	public boolean isEmpty() {
		return listeners.isEmpty();
	}

}
