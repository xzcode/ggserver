package xzcode.ggserver.core.common.session.filter;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * 会话消息过滤器统一接口
 * 
 * @param <T>
 * @author zzz
 * 2019-10-08 18:20:21
 */
public interface IGGSessionFilter<T> {
	
	boolean doFilter(GGSession session, T data);

}
