package xzcode.ggserver.core.common.handler.pack;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.future.GGFuture;
import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.session.GGSession;

public interface IGGSendPackHandler {

	

	GGFuture handle(GGSession session, PackModel packModel);

	GGFuture handle(GGSession session, PackModel packModel, long delay, TimeUnit timeUnit);
	
}
