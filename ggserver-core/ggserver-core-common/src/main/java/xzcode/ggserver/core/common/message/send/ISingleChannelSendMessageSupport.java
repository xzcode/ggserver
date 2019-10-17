package xzcode.ggserver.core.common.message.send;

import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.send.future.GGSendFuture;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISingleChannelSendMessageSupport extends IGGConfigSupport{
	
	

	default GGSendFuture send(String action) {
		return getConfig().getSendMessageManager().send(action);
	}

	default GGSendFuture send(String action, Object message) {
		return getConfig().getSendMessageManager().send(action, message);
	}
	
	default GGSendFuture send(String action, long delayMs) {
		return getConfig().getSendMessageManager().send(action, delayMs);
	}
	default GGSendFuture send(String action, Object message, long delayMs) {
		return getConfig().getSendMessageManager().send(action, message, delayMs);
	}
	default GGSendFuture send(PackModel pack) {
		return getConfig().getSendMessageManager().send(pack);
	}


	

}
