package xzcode.ggserver.core.common.handler.pack;

import xzcode.ggserver.core.common.message.PackModel;
import xzcode.ggserver.core.common.session.GGSession;

public interface IGGReceivePackHandler {
	
	
	void handle(PackModel packModel, GGSession session);
	
}
