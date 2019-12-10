package xzcode.ggserver.core.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventMethodInvoker implements IEventInvoker {
	
	private String eventTag;
	
	/**
	 * 组件实例对象
	 */
	private Object componentObj;
	
	/**
	 * 组件实例对象的class类型
	 */
	private Class<?> componentClass;
	
	
	private List<Method> methods = new ArrayList<>(1);
	
	
	
	@Override
	public void invoke() throws Exception{
		Method method = null;
		for (int i = 0; i < methods.size(); i++) {
			method = methods.get(i);
			method.invoke(componentObj);
		}
	}
	

	@Override
	public void invoke(Object message) throws Exception {
		Method method = null;
		for (int i = 0; i < methods.size(); i++) {
			method = methods.get(i);
			if (message != null) {
				method.invoke(componentObj, message);
			}else {
				method.invoke(componentObj);				
			}
		}
	}
	
	public void setComponentClass(Class<?> componentClass) {
		this.componentClass = componentClass;
	}
	
	public Class<?> getComponentClass() {
		return componentClass;
	}
	

	public String getEventTag() {
		return eventTag;
	}

	public EventMethodInvoker setEventTag(String eventTag) {
		this.eventTag = eventTag;
		return this;
	}

	public List<Method> getMethods() {
		return methods;
	}
	
	public IEventInvoker addMethod(Method method) {
		methods.add(method);
		return this;
	}

	public void setComponentObj(Object componentObj) {
		this.componentObj = componentObj;
	}
	
	public Object getComponentObj() {
		return componentObj;
	}

	
}

