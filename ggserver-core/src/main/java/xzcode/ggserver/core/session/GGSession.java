package xzcode.ggserver.core.session;

import io.netty.channel.Channel;

public interface GGSession {

	void addAttribute(String key, Object value);

	Object getAttribute(String key);

	Object reomveAttribute(String key);

	<T> T getAttribute(String key, Class<T> t);

	void disconnect();

	Channel getChannel();

	void setChannel(Channel channel);

	String getHost();

	int getPort();

	Object getRegisteredUserId();

	void setRegisteredUserId(Object registeredUserId);

	void unregister();

	void register(Object registeredUserId);


}