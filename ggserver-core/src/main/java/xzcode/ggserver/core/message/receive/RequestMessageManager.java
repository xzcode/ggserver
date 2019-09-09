package xzcode.ggserver.core.message.receive;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.component.GGComponentManager;
import xzcode.ggserver.core.message.receive.invoker.IOnMessageInvoker;
import xzcode.ggserver.core.message.receive.invoker.MethodInvoker;


/**
 * 消息调用者管理器
 * 
 * @author zai
 * 2019-01-01 23:25:21
 */
public class RequestMessageManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestMessageManager.class);

	private final Map<String, IOnMessageInvoker> map = new ConcurrentHashMap<>();

	/**
	 * 更新方法调用者的组件对象
	 * 
	 * @param componentObjectMapper
	 * @author zai
	 * 2019-01-01 23:34:22
	 */
	public void updateComponentObject(GGComponentManager componentObjectMapper) {
		for (String key : map.keySet()) {
			IOnMessageInvoker invoker = map.get(key);
			if (invoker instanceof MethodInvoker) {
				MethodInvoker mInvoker = (MethodInvoker)invoker;
				mInvoker.setComponentObj(componentObjectMapper.getComponentObject(mInvoker.getComponentClass()));
			}
			
		}
	}

	/**
	 * 调用被缓存的方法
	 * @param action
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public void invoke(String action, Object message) throws Exception {
		IOnMessageInvoker invoker = map.get(action);
		if (invoker != null) {
			invoker.invoke(action, message);
			
			return;
		}
		LOGGER.warn("No such action: {} ", action);
	}

	/**
	 * 添加缓存方法
	 * @param action
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public void put(String action, IOnMessageInvoker onMessageInvoker) {
		if (map.containsKey(action)) {
			throw new RuntimeException("action '"+action+"' is already mapped!");
		}
		map.put(action, onMessageInvoker);
	}


	/**
	 * 获取关联方法模型
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public IOnMessageInvoker get(String requestTag){
		return map.get(requestTag);
	}


}
