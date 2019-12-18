package xzcode.ggserver.core.common.handler.codec.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.codec.IDecodeHandler;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.session.GGSession;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 自定协议解析
 *   包体总长度           元数据长度           元数据体         指令长度           指令内容                   数据体
 * +----------+-----------+----------+----------+-----------+------------+
 * | 4 byte   |  2 byte   | metadata |   1 byte |    tag    |  data body |
 * +----------+-----------+----------+----------+-----------+------------+
 * @author zai
 *
 */
public class DefaultDecodeHandler implements IDecodeHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDecodeHandler.class);

	/**
	 * 元数据标识占用字节数
	 */
	public static final int METADATA_TAG_LEN = 2;

	/**
	 * 指令长度标识占用字节数
	 */
	public static final int ACTION_TAG_LEN = 1;

	/**
	 * 所有标识长度
	 */
	public static final int ALL_TAG_LEN = METADATA_TAG_LEN + ACTION_TAG_LEN;

	private GGConfig config;

	public DefaultDecodeHandler() {

	}

	public DefaultDecodeHandler(GGConfig config) {
		super();
		this.config = config;
	}

	public void handle(ChannelHandlerContext ctx, ByteBuf in) {

		int packLen = in.readableBytes();

		// 读取元数据
		int metadataLen = in.readUnsignedShort();
		byte[] metadata = null;
		if (metadataLen > 0) {
			metadata = new byte[metadataLen];
			in.readBytes(metadata);
		}

		// 读取指令标识
		int actionLen = in.readByte();
		byte[] action = new byte[actionLen];
		in.readBytes(action);

		// 读取数据体 = 总包长 - 标识长度占用字节 - 标识体占用字节数
		int bodyLen = packLen - ALL_TAG_LEN - metadataLen - actionLen;
		byte[] message = null;
		if (bodyLen != 0) {

			message = new byte[bodyLen];
			// 读取数据体部分byte数组
			in.readBytes(message);

		}

		Pack pack = new Pack(metadata, action, message);

		// 获取session
		GGSession session = config.getSessionFactory().getSession(ctx.channel(), pack);
		pack.setSession(session);

		// 接收包处理
		config.getReceivePackHandler().handle(pack);
		
		
		if(GGLoggerUtil.getLogger().isInfoEnabled()){
			GGLoggerUtil.logPack(pack, Pack.PackOperType.REQUEST, ctx.channel());
        }

	}

}
