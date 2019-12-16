package xzcode.ggserver.core.common.session;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.response.support.ISessionSendMessageSupport;

/**
 * 统一会话接口
 * 
 * 
 * @author zai 2019-11-16 23:35:39
 */
public interface GGSession extends ISessionSendMessageSupport, IExecutorSupport {

	void addAttribute(String key, Object value);

	Object getAttribute(String key);

	Object reomveAttribute(String key);

	<T> T getAttribute(String key, Class<T> t);

	IGGFuture disconnect();
	
	String getHost();
	
	void setHost(String host);

	int getPort();
	
	void setPort(int port);

	boolean isActive();

	String getSessonId();

	Channel getChannel();
	
	void setChannel(Channel channel);

	boolean isExpired();

	void updateExpire();
	

}