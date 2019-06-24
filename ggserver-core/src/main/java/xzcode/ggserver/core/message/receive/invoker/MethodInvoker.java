package xzcode.ggserver.core.message.receive.invoker;

import java.lang.reflect.Method;

/**
 * 请求消息调用模型
 * 
 * @author zai
 * 2019-01-01 22:11:15
 */
public class MethodInvoker implements IOnMessageInvoker{
	
	/**
	 * 调用类型
	 */
	private int type;
	
	/**
	 * 请求标识
	 */
	private String action;
	
	/**
	 * 接收消息的class类型
	 */
	private Class<?> requestMessageClass;
	
	/**
	 * 方法对象
	 */
	private Method method;
	
	/**
	 * 组件实例对象
	 */
	private Object componentObj;
	
	/**
	 * 组件实例对象的class类型
	 */
	private Class<?> componentClass;
	
	

	@Override
	public void invoke(String requestTag, Object message) throws Exception {
		//如果消息体为空
		if (message == null) {
			if (method.getParameterCount() > 0) {
				method.invoke(componentObj, message);
				return;
			}
			method.invoke(componentObj);
			return;
		}
		//如果消息体不为空
		method.invoke(componentObj, message);
	}
	
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}

	public Class<?> getMessageClass() {
		return requestMessageClass;
	}

	public void setRequestMessageClass(Class<?> requestMessageClass) {
		this.requestMessageClass = requestMessageClass;
	}
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	public Object getComponentObj() {
		return componentObj;
	}
	
	public void setComponentObj(Object componentObj) {
		this.componentObj = componentObj;
	}
	
	public void setComponentClass(Class<?> componentClass) {
		this.componentClass = componentClass;
	}
	public Class<?> getComponentClass() {
		return componentClass;
	}
}
