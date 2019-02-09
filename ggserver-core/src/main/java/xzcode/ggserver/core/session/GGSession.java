package xzcode.ggserver.core.session;

import io.netty.channel.Channel;

public interface GGSession {

	void addAttribute(Object key, Object value);

	Object getAttribute(Object key);

	Object reomveAttribute(Object key);

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