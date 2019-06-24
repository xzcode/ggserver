package xzcode.ggserver.core.message.filter;

/**
 * 重定向消息过滤器接口
 *
 * @author zai
 * 2018-12-20 10:16:13
 */
public interface GGPreDeserializeFilter {
	
	public boolean doFilter(byte[] action, byte[] message) throws Exception;

}
