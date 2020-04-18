package com.xzcode.ggserver.core.common.filter;

/**
 * 会话消息过滤器统一接口
 * 
 * @param <T>
 * @author zzz
 * 2019-10-08 18:20:21
 */
public interface Filter<T> {
	
	boolean doFilter(T data);

}
