package xzcode.ggserver.core.common.message.send.support;

import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.config.IGGConfigSupport;
import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.send.Response;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 生成字节码数据包支持
 * 
 * 
 * @author zai
 * 2019-11-27 21:59:29
 */
public interface IMakePackSupport extends IGGConfigSupport {
	/**
	 * 生成字节码数据包
	 * 
	 * @param response
	 * @return
	 * 
	 * @author zai 2019-11-24 17:28:57
	 */
	default Pack makePack(Response response) {
		try {
			GGConfig config = getConfig();
			ISerializer serializer = config.getSerializer();
			byte[] metadataBytes = response.getMetadata() == null ? null : serializer.serialize(response.getMetadata());
			byte[] actionIdData = response.getAction().getBytes(config.getCharset());
			byte[] messageData = response.getMessage() == null ? null : serializer.serialize(response.getMessage());
			return new Pack(metadataBytes, actionIdData, messageData);
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("Make pack Error!", e);
		}
		return null;
	}
}
