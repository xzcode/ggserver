package xzcode.ggserver.core.common.message.meta;

/**
 * 元数据解析器接口
 * 
 * 
 * @author zai
 * 2019-11-24 18:18:46
 */
public interface IMetadataResolver<T> {
	
	/**
	 * 解析元数据
	 * @param <T>
	 * @param bytes
	 * @param clazz
	 * @return
	 * 
	 * @author zai
	 * 2019-11-24 18:18:53
	 */
	T resolve(byte[] bytes);

}
