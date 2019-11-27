package xzcode.ggserver.core.common.message.send;

import xzcode.ggserver.core.common.message.receive.Request;
import xzcode.ggserver.core.common.session.GGSession;

/**
 * 消息响应模型
 * 
 * @author zai
 * 2019-07-28 15:34:08
 */
public class Response extends Request{

	public Response(GGSession session, Object metadata, String action, Object message) {
		super(session, metadata, action, message);
	}

	public Response(Object metadata, String action, Object message) {
		super(metadata, action, message);
	}
	
	
	
}
