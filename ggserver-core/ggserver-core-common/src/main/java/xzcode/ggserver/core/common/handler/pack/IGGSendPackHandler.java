package xzcode.ggserver.core.common.handler.pack;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;

public interface IGGSendPackHandler {

	

	IGGFuture handle(GGSession session, Pack pack);

	IGGFuture handle(GGSession session, Pack pack, long delay, TimeUnit timeUnit);
	
}
