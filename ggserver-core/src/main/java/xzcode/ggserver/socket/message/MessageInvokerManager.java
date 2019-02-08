package xzcode.ggserver.socket.message;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.socket.component.SocketComponentObjectManager;
import xzcode.ggserver.socket.message.invoker.IMessageInvoker;
import xzcode.ggserver.socket.message.invoker.MethodInvoker;


/**
 * 消息调用者管理器
 * 
 * @author zai
 * 2019-01-01 23:25:21
 */
public class MessageInvokerManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageInvokerManager.class);

	private final Map<String, IMessageInvoker> map = new HashMap<>();

	/**
	 * 更新方法调用者的组件对象
	 * 
	 * @param componentObjectMapper
	 * @author zai
	 * 2019-01-01 23:34:22
	 */
	public void updateComponentObject(SocketComponentObjectManager componentObjectMapper) {
		for (String key : map.keySet()) {
			IMessageInvoker invoker = map.get(key);
			if (invoker instanceof MethodInvoker) {
				MethodInvoker mInvoker = (MethodInvoker)invoker;
				mInvoker.setComponentObj(componentObjectMapper.getSocketComponentObject(mInvoker.getComponentClass()));
			}
			
		}
	}

	/**
	 * 调用被缓存的方法
	 * @param requestTag
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public Object invoke(String requestTag, Object message) throws Exception {
		IMessageInvoker invoker = map.get(requestTag);
		if (invoker != null) {
			return invoker.invoke(requestTag, message);
		}
		LOGGER.warn("No method mapped with tag: {} ", requestTag);
		return null;
	}

	/**
	 * 添加缓存方法
	 * @param requestTag
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public void put(String requestTag, IMessageInvoker messageInvoker) {
		if (map.containsKey(requestTag)) {
			throw new RuntimeException("requestTag '"+requestTag+"' is already mapped!");
		}
		map.put(requestTag, messageInvoker);
	}

	/**
	 * 获取返还标识
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public String getSendTag(String requestTag){
		return get(requestTag).getSendTag();
	}

	/**
	 * 获取关联方法模型
	 * @param requestTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public IMessageInvoker get(String requestTag){
		return map.get(requestTag);
	}


}
