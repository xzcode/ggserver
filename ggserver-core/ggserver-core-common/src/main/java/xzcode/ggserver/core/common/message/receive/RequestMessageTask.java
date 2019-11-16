package xzcode.ggserver.core.common.message.receive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.receive.invoker.IOnMessageInvoker;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;

/**
 * 请求消息任务
 * 
 * 
 * @author zai
 * 2019-02-09 14:26:10
 */
public class RequestMessageTask implements Runnable{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RequestMessageTask.class);
	
	/**
	 * 配置
	 */
	private GGConfig config;
	
	/**
	 * 包体模型
	 */
	private Pack pack;
	
	/**
	 * session
	 */
	private GGSession session;
	
	
	
	public RequestMessageTask() {
		
	}
	
	

	public RequestMessageTask(Pack pack, GGSession session, GGConfig config) {
		this.session = session;
		this.pack = pack;
		this.config = config;
	}



	@Override
	public void run() {
		
		GGSessionUtil.setSession(this.session);
		//IFilterManager sessionFilterManager = session.getFilterManager();
		Request request = new Request();
		ISerializer serializer = config.getSerializer();
		IFilterManager messageFilterManager = this.config.getFilterManager();
		String oldAction = null;
		try {
			//反序列化前过滤器
			if (!messageFilterManager.doBeforeDeserializeFilters(pack)) {
				return;
			}
			/*
			 * //会话反序列化前过滤器 if (!sessionFilterManager.doBeforeDeserializeFilters(pack)) {
			 * return; }
			 */
			request.setAction(new String(pack.getAction(), config.getCharset()));
			oldAction = request.getAction();
			if (pack.getMessage() != null) {
				IOnMessageInvoker invoker = config.getMessageInvokerManager().get(request.getAction());
				if (invoker != null) {
					request.setMessage(serializer.deserialize(pack.getMessage(), invoker.getMessageClass()));
				}
			}
			
			//反序列化后的消息过滤器
			if (!messageFilterManager.doRequestFilters(request)) {
				return;
			}
			/*
			 * //会话反序列化后的消息过滤器 if (!sessionFilterManager.doRequestFilters(request)) {
			 * return; }
			 * 
			 */
			while (!oldAction.equals(request.getAction())) {
				oldAction = request.getAction();
				if (pack.getMessage() != null) {
					IOnMessageInvoker invoker = config.getMessageInvokerManager().get(request.getAction());
					if (invoker != null) {
						request.setMessage(serializer.deserialize(pack.getMessage(), invoker.getMessageClass()));
					}else {
						LOGGER.error("Can not invoke action:{}, cause 'invoker' is null!", request.getAction());
						return;
					}
				}
				//如果action发生了改变，再次调用过滤器
				if (!messageFilterManager.doRequestFilters(request)) {
					return;
				}
				/*
				 * //如果action发生了改变，再次调用过滤器 if (!sessionFilterManager.doRequestFilters(request))
				 * { return; }
				 */
			}
			
			config.getRequestMessageManager().invoke(request.getAction(), request.getMessage());
			
		} catch (Exception e) {
			LOGGER.error("Request Message Task ERROR!! -- actionId: {}, error: {}", request.getAction(), e);
		}finally {
			GGSessionUtil.removeSession();			
		}
		
	}
	

}
