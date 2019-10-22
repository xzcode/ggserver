package xzcode.ggserver.core.common.message.send;

import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.PackModel;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ISingleChannelSendMessageSupport extends IGGConfigSupport{

	default IGGFuture send(String action) {
		return getConfig().getSendMessageManager().send(action);
	}

	default IGGFuture send(String action, Object message) {
		return getConfig().getSendMessageManager().send(action, message);
	}
	
	default IGGFuture send(String action, long delayMs) {
		return getConfig().getSendMessageManager().send(action, delayMs);
	}
	default IGGFuture send(String action, Object message, long delayMs) {
		return getConfig().getSendMessageManager().send(action, message, delayMs);
	}
	default IGGFuture send(PackModel pack) {
		return getConfig().getSendMessageManager().send(pack);
	}


	

}
