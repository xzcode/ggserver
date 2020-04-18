package com.xzcode.ggserver.core.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.xzcode.ggserver.core.common.message.request.action.MessageDataHandler;

/**
 * 泛型类工具
 *
 * @author zai
 * 2020-04-11 23:17:18
 */
public class GenericClassUtil {
	
	/**
	 * 获取泛型的class
	 *
	 * @param targetClass
	 * @return
	 * @author zai
	 * 2020-04-11 23:16:33
	 */
	public static Class<?> getGenericClass(Class<?> targetClass) {
		Class<?> msgClass = null;
		
		Type[] genericInterfaces = targetClass.getGenericInterfaces();
		if (genericInterfaces == null || genericInterfaces.length == 0) {
			ParameterizedType superParameterizedType = (ParameterizedType)targetClass.getGenericSuperclass();
			msgClass = (Class<?>) superParameterizedType.getActualTypeArguments()[0];
		}else {
			Type type = genericInterfaces[0];
			if (type == MessageDataHandler.class) {
				msgClass = MessageDataHandler.class;
			}else {
				msgClass = (Class<?>) ((ParameterizedType)genericInterfaces[0]).getActualTypeArguments()[0];
			}
			
		}
		return msgClass;
	}

}
