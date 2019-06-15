package xzcode.ggserver.client.message.receive;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.client.message.receive.invoker.IOnMessageInvoker;


/**
 * 消息调用者管理器
 * 
 * @author zai
 * 2019-01-01 23:25:21
 */
public class OnMessageManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(OnMessageManager.class);

	private final Map<String, IOnMessageInvoker> map = new HashMap<>();


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
		IOnMessageInvoker invoker = map.get(requestTag);
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
	public void put(String requestTag, IOnMessageInvoker onMessageInvoker) {
		if (map.containsKey(requestTag)) {
			throw new RuntimeException("requestTag '"+requestTag+"' is already mapped!");
		}
		map.put(requestTag, onMessageInvoker);
	}

	/**
	 * 获取返还标识
	 * @param requestAction
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public String getSendAction(String requestAction){
		return get(requestAction).getSendAction();
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
