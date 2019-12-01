package xzcode.ggserver.core.common.message.meta.resolver;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.message.meta.UserMetadata;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 *用户id元数据解析器
 * 
 * 
 * @author zai
 * 2019-11-24 18:25:34
 */
public class UserMetadataResolver implements IMetadataResolver<UserMetadata> {
	
	private GGConfig config;

	@Override
	public UserMetadata resolve(byte[] bytes) {
		if (bytes != null) {
			try {
				return config.getSerializer().deserialize(bytes, UserMetadata.class);
			} catch (Exception e) {
				GGLoggerUtil.getLogger().error("Resolve UserMetadata Error!", e);
			}
		}
		return null;
	}

}
