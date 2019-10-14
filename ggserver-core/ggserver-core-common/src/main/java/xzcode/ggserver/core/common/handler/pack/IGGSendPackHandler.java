package xzcode.ggserver.core.common.handler.pack;

import java.util.concurrent.TimeUnit;

import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.message.send.future.GGSendFuture;
import xzcode.ggserver.core.common.session.GGSession;

public interface IGGSendPackHandler {

	

	GGSendFuture handle(GGSession session, PackModel packModel);

	GGSendFuture handle(GGSession session, PackModel packModel, long delay, TimeUnit timeUnit);
	
}
