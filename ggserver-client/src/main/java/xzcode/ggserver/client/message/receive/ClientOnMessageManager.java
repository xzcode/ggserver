package xzcode.ggserver.client.message.receive;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.client.message.receive.invoker.IClientOnMessageInvoker;


/**
 * 消息调用者管理器
 * 
 * @author zai
 * 2019-01-01 23:25:21
 */
public class ClientOnMessageManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientOnMessageManager.class);

	private final Map<String, IClientOnMessageInvoker> map = new HashMap<>();


	/**
	 * 调用被缓存的方法
	 * @param messageTag
	 * @param message
	 * @throws Exception
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public Object invoke(String messageTag, Object message) throws Exception {
		IClientOnMessageInvoker invoker = map.get(messageTag);
		if (invoker != null) {
			return invoker.invoke(messageTag, message);
		}
		LOGGER.warn("No method mapped with tag: {} ", messageTag);
		return null;
	}

	/**
	 * 添加缓存方法
	 * @param actionId
	 *
	 * @author zai
	 * 2017-07-29
	 */
	public void put(String actionId, IClientOnMessageInvoker clientOnMessageInvoker) {
		if (map.containsKey(actionId)) {
			LOGGER.error("actionId '"+actionId+"' is already mapped!");
			return;
		}
		map.put(actionId, clientOnMessageInvoker);
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
	 * @param messageTag
	 * @return
	 *
	 * @author zai
	 * 2017-08-02
	 */
	public IClientOnMessageInvoker get(String messageTag){
		return map.get(messageTag);
	}


}
