package xzcode.ggserver.core.common.session;

import io.netty.channel.Channel;

public interface GGSession {

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