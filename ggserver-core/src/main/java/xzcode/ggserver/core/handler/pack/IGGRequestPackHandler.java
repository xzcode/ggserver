package xzcode.ggserver.core.handler.pack;

import xzcode.ggserver.core.message.PackModel;
import xzcode.ggserver.core.session.GGSession;

public interface IGGRequestPackHandler {
	
	
	void handle(PackModel packModel, GGSession session) throws Exception;
	
}
