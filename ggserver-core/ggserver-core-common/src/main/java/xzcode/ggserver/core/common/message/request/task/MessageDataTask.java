package xzcode.ggserver.core.common.message.request.task;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.filter.IFilterManager;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.MessageData;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.request.handler.IRequestMessageHandlerInfo;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 消息数据任务
 *
 * @author zai
 * 2020-04-09 16:02:12
 */
public class MessageDataTask implements Runnable{
	
	/**
	 * 配置
	 */
	private GGConfig config;
	
	/**
	 * 包体模型
	 */
	private Pack pack;
	
	
	public MessageDataTask() {
		
	}

	public MessageDataTask(Pack pack, GGConfig config) {
		this.pack = pack;
		this.config = config;
	}



	@Override
	public void run() {
		ISerializer serializer = config.getSerializer();
		IFilterManager messageFilterManager = this.config.getFilterManager();
		String action = null;
		Object message = null;
		GGSession session = pack.getSession();
		try {
			//反序列化前过滤器
			if (!messageFilterManager.doBeforeDeserializeFilters(pack)) {
				return;
			}
			
			action = new String(pack.getAction(), config.getCharset());
			
			if (pack.getMessage() != null) {
				IRequestMessageHandlerInfo messageHandler = config.getRequestMessageManager().getMessageHandler(action);
				if (messageHandler != null) {
					message = serializer.deserialize(pack.getMessage(), messageHandler.getMessageClass());
				}
			}
			
			Channel channel = pack.getChannel();
			
			MessageData<?> request = new MessageData<>(session, action, message);
			request.setChannel(channel);
			
			//反序列化后的消息过滤器
			if (!messageFilterManager.doRequestFilters(request)) {
				return;
			}
			
			config.getRequestMessageManager().handle(request);
			
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("Request Message Task ERROR!! -- actionId: {}, error: {}", action, e);
		}
		
	}
	

}
