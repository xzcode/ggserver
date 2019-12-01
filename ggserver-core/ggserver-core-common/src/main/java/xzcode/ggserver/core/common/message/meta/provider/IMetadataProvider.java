package xzcode.ggserver.core.common.message.meta.provider;

import xzcode.ggserver.core.common.session.GGSession;

/**
 * 元数据提供者接口
 * 
 * 
 * @author zai
 * 2019-11-24 18:18:46
 */
public interface IMetadataProvider<T> {
	
	public static final String METADATA_KEY = "METADATA"; 
	
	/**
	 * 提供metadata
	 * @param session
	 * @return
	 * 
	 * @author zai
	 * 2019-12-01 20:36:42
	 */
	T provide(GGSession session);

}
