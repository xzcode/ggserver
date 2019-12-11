package xzcode.ggserver.core.common.message.response.support;

import java.nio.charset.Charset;

import xzcode.ggserver.core.common.handler.serializer.ISerializer;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.message.response.Response;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 生成字节码数据包支持
 * 
 * 
 * @author zai
 * 2019-11-27 21:59:29
 */
public interface IMakePackSupport{
	
	/**
	 * 获取字符串编码对象
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 14:24:30
	 */
	Charset getCharset();
	
	/**
	 * 获取序列化器
	 * 
	 * @return
	 * @author zai
	 * 2019-12-11 14:24:23
	 */
	ISerializer getSerializer();
	
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
			ISerializer serializer = getSerializer();
			byte[] metadataBytes = response.getMetadata() == null ? null : serializer.serialize(response.getMetadata());
			byte[] actionIdData = response.getAction().getBytes(getCharset());
			byte[] messageData = response.getMessage() == null ? null : serializer.serialize(response.getMessage());
			return new Pack(metadataBytes, actionIdData, messageData);
		} catch (Exception e) {
			GGLoggerUtil.getLogger().error("Make pack Error!", e);
		}
		return null;
	}
}
