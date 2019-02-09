package xzcode.ggserver.core.message.filter;

/**
 * 接收消息过滤器接口
 *
 * @author zai
 * 2018-12-20 10:16:13
 */
public interface GGRequestFilter {
	
	public boolean doFilter(String actionId, Object message);

}
