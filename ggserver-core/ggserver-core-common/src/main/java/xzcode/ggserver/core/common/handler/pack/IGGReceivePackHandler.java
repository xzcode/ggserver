package xzcode.ggserver.core.common.handler.pack;

import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;

public interface IGGReceivePackHandler {
	
	
	void handle(Pack pack, GGSession session);
	
}
