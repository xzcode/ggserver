package com.xzcode.socket.core.config.scanner;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzcode.socket.core.annotation.SocketComponent;
import com.xzcode.socket.core.annotation.SocketFilter;
import com.xzcode.socket.core.annotation.SocketOnEvent;
import com.xzcode.socket.core.annotation.SocketRequest;
import com.xzcode.socket.core.annotation.SocketResponse;
import com.xzcode.socket.core.component.SocketComponentObjectManager;
import com.xzcode.socket.core.event.EventInvokerManager;
import com.xzcode.socket.core.event.EventMethodInvoker;
import com.xzcode.socket.core.filter.MessageFilterManager;
import com.xzcode.socket.core.filter.MessageFilterModel;
import com.xzcode.socket.core.filter.SocketMessageFilter;
import com.xzcode.socket.core.message.MessageInvokerManager;
import com.xzcode.socket.core.message.invoker.MethodInvoker;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

/**
 * controller扫描工具 扫描并缓存被注解的类信息
 * 
 * 
 * @author zai 2017-07-29
 */
public class SocketComponentScanner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketComponentScanner.class);

	public static void scan(
			SocketComponentObjectManager componentObjectMapper,
			MessageInvokerManager messageInvokerManager, 
			EventInvokerManager eventInvokerManager, 
			MessageFilterManager filterMapper, 
			String... packageName
			) {

		if (packageName == null) {
			LOGGER.error(" packageName can not be null !! ");
			return;
		}
		
		// 扫描指定包下的注解并生成关联
		ScanResult scanResult = new ClassGraph()
			// .verbose() // Log to stderr
		.enableAllInfo() // Scan classes, methods, fields, annotations
		.whitelistPackages(packageName)
		.scan();
		
		ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(SocketComponent.class.getName());
		for (ClassInfo classInfo : classInfoList) {

			
			Class<?> clazz = classInfo.loadClass();

			try {
				Object clazzInstance = clazz.newInstance();
				componentObjectMapper.put(clazz, clazzInstance);

				SocketFilter filter = clazz.getAnnotation(SocketFilter.class);
				if (filter != null) {
					if (!(clazzInstance instanceof SocketMessageFilter)) {
						throw new RuntimeException(
								"Class:" + clazz.getName() + " with annotation @" + SocketFilter.class.getSimpleName()
										+ " must implement interface:" + SocketMessageFilter.class.getName() + " ! ");
					}
					int value = filter.value();
					int order = filter.order();
					filterMapper.add(new MessageFilterModel(order != 0 ? order : value, clazz));

				}

				Method[] methods = clazz.getMethods();
				for (Method mtd : methods) {

					// 扫描socketrequest
					SocketRequest requestMessage = mtd.getAnnotation(SocketRequest.class);
					if (requestMessage != null) {

						MethodInvoker methodInvoker = new MethodInvoker();
						methodInvoker.setMethod(mtd);
						//methodInvoker.setComponentObj(clazzInstance);
						methodInvoker.setComponentClass(clazz);
						methodInvoker.setRequestTag(requestMessage.value());
						methodInvoker.setSendMessageClass(mtd.getReturnType());
						Class<?>[] parameterTypes = mtd.getParameterTypes();
						if (parameterTypes != null && parameterTypes.length > 0) {
							methodInvoker.setRequestMessageClass(parameterTypes[0]);
						}

						SocketResponse sendMessage = mtd.getAnnotation(SocketResponse.class);
						if (sendMessage != null) {
							methodInvoker.setSendTag(sendMessage.value());
						}
						messageInvokerManager.put(requestMessage.value(), methodInvoker);
					}

					// 扫描 socketevent
					SocketOnEvent socketOnEvent = mtd.getAnnotation(SocketOnEvent.class);
					if (socketOnEvent != null) {

						if (mtd.getParameterCount() > 0) {
							throw new RuntimeException("Annotation @" + SocketOnEvent.class.getSimpleName()
									+ " unsupport methods with parameters! ");
						}

						EventMethodInvoker eventMethodInvoker = new EventMethodInvoker();
						eventMethodInvoker.setEventTag(socketOnEvent.value()).addMethod(mtd);
						eventMethodInvoker.setComponentClass(clazz);

						eventInvokerManager.put(eventMethodInvoker);
					}

				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
