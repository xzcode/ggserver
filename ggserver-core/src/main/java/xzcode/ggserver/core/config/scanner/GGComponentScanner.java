package xzcode.ggserver.core.config.scanner;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import xzcode.ggserver.core.annotation.GGAction;
import xzcode.ggserver.core.annotation.GGComponent;
import xzcode.ggserver.core.annotation.GGController;
import xzcode.ggserver.core.annotation.GGFilter;
import xzcode.ggserver.core.annotation.GGOnEvent;
import xzcode.ggserver.core.component.GGComponentManager;
import xzcode.ggserver.core.event.EventInvokerManager;
import xzcode.ggserver.core.event.EventMethodInvoker;
import xzcode.ggserver.core.message.filter.MessageFilterManager;
import xzcode.ggserver.core.message.filter.MessageFilterModel;
import xzcode.ggserver.core.message.receive.RequestMessageManager;
import xzcode.ggserver.core.message.receive.invoker.MethodInvoker;

/**
 * controller扫描工具 扫描并缓存被注解的类信息
 * 
 * 
 * @author zai 2017-07-29
 */
public class GGComponentScanner {

	private static final Logger LOGGER = LoggerFactory.getLogger(GGComponentScanner.class);

	public static void scan(
			GGComponentManager componentManager,
			RequestMessageManager requestMessageManager, 
			EventInvokerManager eventInvokerManager, 
			MessageFilterManager filterManager, 
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
		
		Class<?> clazz = null;
		ClassInfoList classInfoList = scanResult.getClassesWithAnnotation(GGComponent.class.getName());
		for (ClassInfo classInfo : classInfoList) {

			
			clazz = classInfo.loadClass();

			try {
				if (clazz == GGController.class) {
					return;
				}
				Object clazzInstance = clazz.newInstance();
				componentManager.put(clazz, clazzInstance);

				GGFilter filter = clazz.getAnnotation(GGFilter.class);
				if (filter != null) {
					MessageFilterModel messageFilterModel = new MessageFilterModel();
					messageFilterModel.setFilterClazz(clazz);
					int value = filter.value();
					int order = filter.order();
					messageFilterModel.setOrder(value);
					if (value == 0) {
						messageFilterModel.setOrder(order);
					}
					filterManager.add(messageFilterModel);

				}
				Set<Method> methods = new HashSet<>();
				Method[] mtds = clazz.getMethods();
				for (Method mtd : mtds) {
					methods.add(mtd);
				}
				
				mtds = clazz.getDeclaredMethods();
				for (Method mtd : mtds) {
					methods.add(mtd);
				}
				
				GGController ggController = clazz.getAnnotation(GGController.class);
				String prefix = "";
				if (ggController != null) {
					prefix = ggController.actionIdPrefix();
					if (prefix != null && prefix.isEmpty()) {
						prefix = ggController.value();
					}
				}
				for (Method mtd : methods) {

					// 扫描GGAction
					GGAction requestMessage = mtd.getAnnotation(GGAction.class);
					if (requestMessage != null) {

						MethodInvoker methodInvoker = new MethodInvoker();
						methodInvoker.setMethod(mtd);
						//methodInvoker.setComponentObj(clazzInstance);
						methodInvoker.setComponentClass(clazz);
						
						String action = prefix + requestMessage.value();
						
						methodInvoker.setAction(action);
						
						if (LOGGER.isInfoEnabled()) {
							LOGGER.info("GGServer Mapped Message Action: {}", action);
						}
						
						Class<?>[] parameterTypes = mtd.getParameterTypes();
						if (parameterTypes != null && parameterTypes.length > 0) {
							methodInvoker.setRequestMessageClass(parameterTypes[0]);
						}

						requestMessageManager.put(action, methodInvoker);
					}

					// 扫描 GGOnEvent
					GGOnEvent gGOnEvent = mtd.getAnnotation(GGOnEvent.class);
					if (gGOnEvent != null) {

						/*if (mtd.getParameterCount() > 0) {
							throw new RuntimeException("Annotation @" + GGOnEvent.class.getSimpleName()
									+ " unsupport methods with parameters! ");
						}*/

						EventMethodInvoker eventMethodInvoker = new EventMethodInvoker();
						eventMethodInvoker.setEventTag(gGOnEvent.value()).addMethod(mtd);
						eventMethodInvoker.setComponentClass(clazz);

						eventInvokerManager.put(eventMethodInvoker);
					}

				}
			} catch (Exception e) {
				throw new RuntimeException(clazz.getName(), e);
			}
		}
	}

}
