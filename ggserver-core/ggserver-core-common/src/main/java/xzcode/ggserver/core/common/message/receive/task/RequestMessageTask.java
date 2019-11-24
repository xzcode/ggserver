package xzcode.ggserver.core.common.message.receive.task;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.meta.IMetadataResolver;
import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.message.receive.handler.IRequestMessageHandler;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 请求消息任务
 * 
 * 
 * @author zai
 * 2019-02-09 14:26:10
 */
public class RequestMessageTask implements Runnable{
	
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
		
		ISerializer serializer = config.getSerializer();
		IFilterManager messageFilterManager = this.config.getFilterManager();
		Object metadata = null;
		String action = new String(pack.getAction(), config.getCharset());
		Object message = null;
		try {
			//反序列化前过滤器
			if (!messageFilterManager.doBeforeDeserializeFilters(session, pack)) {
				return;
			}
			
			if (pack.getMetadata() != null) {
				IMetadataResolver<?> metadataResolver = config.getMetadataResolver();
				metadata = metadataResolver.resolve(pack.getMetadata());
			}
			
			if (pack.getMessage() != null) {
				IRequestMessageHandler messageHandler = config.getRequestMessageManager().getMessageHandler(action);
				if (messageHandler != null) {
					message = serializer.deserialize(pack.getMessage(), messageHandler.getMessageClass());
				}
			}
			
			Request request = new Request(session, metadata, action, message);
			
			//反序列化后的消息过滤器
			if (!messageFilterManager.doRequestFilters(request)) {
				return;
			}
			
			config.getRequestMessageManager().handle(this.session, request);
			
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("Request Message Task ERROR!! -- actionId: {}, error: {}", action, e);
		}
		
	}
	

}
