package xzcode.ggserver.core.common.session;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.send.ICurrentSessionSendMessageSupport;

public interface GGSession 
extends 
IGGConfigSupport,
ICurrentSessionSendMessageSupport, 
IExecutorSupport
/*
IEventSupport,
IFilterSupport
*/
{
	
	
	void addAttribute(String key, Object value);

	Object getAttribute(String key);

	Object reomveAttribute(String key);

	<T> T getAttribute(String key, Class<T> t);

	IGGFuture disconnect();

	String getHost();

	int getPort();
	
	boolean isActive();

	String getSessonId();
	
	Channel getChannel();
}