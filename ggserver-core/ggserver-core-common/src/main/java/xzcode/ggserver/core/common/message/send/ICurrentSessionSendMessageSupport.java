package xzcode.ggserver.core.common.message.send;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.session.GGSessionUtil;
import xzcode.ggserver.core.common.session.IGGSessionSupport;
import xzcode.ggserver.core.common.utils.json.GGServerJsonUtil;

/**
 * 消息发送接口
 * 
 * 
 * @author zai
 * 2019-02-09 14:50:27
 */
public interface ICurrentSessionSendMessageSupport extends IGGConfigSupport, IGGSessionSupport{
	
	default IGGFuture send(String action, Object message, long delayMs) {
		return send(action, message, delayMs);
		
	}
	
	default IGGFuture send(Pack pack) {
		return send(pack, 0);
	}
	
	
	default IGGFuture send(Pack pack, long delayMs) {
		return send(pack, delayMs);
	}

	
	default IGGFuture send(String action) {
		GGSession session = GGSessionUtil.getSession();
		if (session != null) {
			if (!getConfig().getFilterManager().doResponseFilters(Response.create(action, null))) {
				return null;
			}
			return this.send(Pack.create(action.getBytes(), null));
		}
		return null;
	}
	
	default IGGFuture send(String action, Object message) {
		return send(action, message, 0);
	}
	

	default IGGFuture send(String action, long delayMs) {
		return send(action, null, delayMs);
		
	}

	default IGGFuture send(Pack pack, long delay, TimeUnit timeUnit) {
		Channel channel = getSession().getChannel();
		if (channel.isActive()) {
			IGGFuture future = new GGFuture();	
			if (delay <= 0) {
				ChannelFuture channelFuture = channel.writeAndFlush(pack);
				future.setNettyFuture(channelFuture);
							
			}else {
				getConfig().getTaskExecutor().schedule(() -> {
					ChannelFuture channelFuture = channel.writeAndFlush(pack);
					future.setNettyFuture(channelFuture);
				}, delay, timeUnit);
			}
			return future;	
		}
		Logger logger = LoggerFactory.getLogger(ICurrentSessionSendMessageSupport.class);
		if (logger.isDebugEnabled()) {
			logger.debug("Channel is inactived! Message will not be send, SendModel:{}", GGServerJsonUtil.toJson(pack));
		}
		return null;
	}


}
