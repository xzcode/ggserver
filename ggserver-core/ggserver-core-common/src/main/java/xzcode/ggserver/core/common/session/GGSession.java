package xzcode.ggserver.core.common.session;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.executor.IExecutorSupport;
import xzcode.ggserver.core.common.message.send.ISingleChannelSendMessageSupport;
import xzcode.ggserver.core.common.session.filter.IGGSessionMessageFilterSupport;

public interface GGSession 
extends 
IGGConfigSupport,
ISingleChannelSendMessageSupport, 
IExecutorSupport,
IGGSessionMessageFilterSupport
{

	void addAttribute(String key, Object value);

	Object getAttribute(String key);

	Object reomveAttribute(String key);

	<T> T getAttribute(String key, Class<T> t);

	void disconnect();

	Channel getChannel();

	String getHost();

	int getPort();

	String getSessonId();
}