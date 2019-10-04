package xzcode.ggserver.core.handler.pack;

import xzcode.ggserver.core.message.PackModel;
import xzcode.ggserver.core.session.GGSession;

public interface IGGSendPackHandler {

	
	void handle(PackModel packModel, GGSession session);
	
}
