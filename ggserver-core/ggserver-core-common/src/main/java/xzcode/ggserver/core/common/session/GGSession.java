package xzcode.ggserver.core.common.session;

import io.netty.channel.Channel;
import xzcode.ggserver.core.common.event.IEventSupport;
import xzcode.ggserver.core.common.executor.support.IExecutorSupport;
import xzcode.ggserver.core.common.future.IGGFuture;
import xzcode.ggserver.core.common.message.response.support.ISessionSendMessageSupport;

/**
 * 统一会话接口
 * 
 * 
 * @author zai 2019-11-16 23:35:39
 */
public interface GGSession extends ISessionSendMessageSupport, IExecutorSupport, IEventSupport {

	/**
	 * 添加属性
	 *
	 * @param key
	 * @param value
	 * @author zai
	 * 2020-04-08 11:30:52
	 */
	void addAttribute(String key, Object value);

	/**
	 * 获取属性
	 *
	 * @param key
	 * @return
	 * @author zai
	 * 2020-04-08 11:30:43
	 */
	Object getAttribute(String key);

	/**
	 * 移除属性
	 *
	 * @param key
	 * @return
	 * @author zai
	 * 2020-04-08 11:30:35
	 */
	Object reomveAttribute(String key);

	/**
	 * 获取属性
	 *
	 * @param <T>
	 * @param key
	 * @param t
	 * @return
	 * @author zai
	 * 2020-04-08 11:30:20
	 */
	<T> T getAttribute(String key, Class<T> t);

	/**
	 * 进行连接断开操作
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:30:08
	 */
	IGGFuture disconnect();
	
	/**
	 * 获取会话域名
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:30:00
	 */
	String getHost();
	
	/**
	 * 设置会话域名
	 *
	 * @param host
	 * @author zai
	 * 2020-04-08 11:29:47
	 */
	void setHost(String host);

	/**
	 * 获取会话端口
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:29:26
	 */
	int getPort();
	
	/**
	 * 设置会话端口
	 *
	 * @param port
	 * @author zai
	 * 2020-04-08 11:29:18
	 */
	void setPort(int port);

	/**
	 * 获取会话id
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:29:10
	 */
	String getSessonId();

	/**
	 * 获取通道对象
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:28:58
	 */
	Channel getChannel();
	
	/**
	 * 设置通道
	 *
	 * @param channel
	 * @author zai
	 * 2020-04-08 11:28:51
	 */
	void setChannel(Channel channel);

	/**
	 * 是否超时
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:27:46
	 */
	boolean isExpired();

	/**
	 * 刷新超时时间
	 *
	 * @author zai
	 * 2020-04-08 11:27:55
	 */
	void updateExpire();
	
	/**
	 * 立即进行会话超时
	 *
	 * @author zai
	 * 2020-04-08 11:28:12
	 */
	void expire();
	
	/**
	 * 会话是否已准备就绪
	 *
	 * @return
	 * @author zai
	 * 2020-04-08 11:28:31
	 */
	boolean isReady();
	
	/**
	 * 设置准备就绪
	 *
	 * @author zai
	 * 2020-04-08 17:05:34
	 */
	void setReady(boolean ready);
	

}