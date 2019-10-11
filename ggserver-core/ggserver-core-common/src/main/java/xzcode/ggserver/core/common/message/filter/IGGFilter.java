package xzcode.ggserver.core.common.message.filter;

/**
 * 统一过滤器接口
 * 
 * @param <T>
 * @author zzz
 * 2019-10-08 18:20:21
 */
public interface IGGFilter<T> {
	
	boolean doFilter(T data);

}
