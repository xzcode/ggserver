package xzcode.ggserver.core.common.message.meta.resolver;

import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 *空的元数据解析器
 * 
 * 
 * @author zai
 * 2019-11-24 18:25:34
 */
public class VoidMetadataResolver implements IMetadataResolver<Void> {

	@Override
	public Void resolve(byte[] bytes) {
		if (bytes != null && GGLoggerUtil.getLogger().isInfoEnabled()) {
			GGLoggerUtil.getLogger().info("Received 'metadata' is not 'null', but not processed!");
		}
		return null;
	}

}
