package xzcode.ggserver.core.message.filter;

import xzcode.ggserver.core.message.send.Response;

/**
 * 发送消息过滤器接口
 *
 * @author zai
 * 2018-12-20 10:16:13
 */
public interface GGResponseFilter {
	
	public boolean doFilter(Object userId, Response sendModel);

}
