package com.xzcode.ggserver.core.common.handler.codec.impl;

import com.xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import com.xzcode.ggserver.core.common.config.GGConfig;
import com.xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import com.xzcode.ggserver.core.common.handler.codec.IDecodeHandler;
import com.xzcode.ggserver.core.common.message.Pack;
import com.xzcode.ggserver.core.common.utils.logger.PackLogger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

/**
 * 自定协议解析
 *  包体总长度    指令长度         指令内容      数据体
 * +----------+-----------+-----------+------------+
 * | 4 byte   |   1 byte  |    tag    |  data body |
 * +----------+-----------+-----------+------------+
 * @author zai
 *
 */
public class DefaultDecodeHandler implements IDecodeHandler {

	/**
	 * 指令长度标识占用字节数
	 */
	public static final int ACTION_TAG_LEN = 1;

	/**
	 * 所有标识长度
	 */
	public static final int ALL_TAG_LEN = ACTION_TAG_LEN;
	
	/**
	 * 协议类型channel key
	 */
	protected static final AttributeKey<String> PROTOCOL_TYPE_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PROTOCOL_TYPE);
	

	private GGConfig config;
	

	public DefaultDecodeHandler() {

	}

	public DefaultDecodeHandler(GGConfig config) {
		super();
		this.config = config;
	}

	public void handle(ChannelHandlerContext ctx, ByteBuf in, String protocolType) {
		
		//读取整个包体长度
		int packLen;
		if (ProtocolTypeConstants.TCP.equals(protocolType)) {
			packLen = in.readInt();
		}else if (ProtocolTypeConstants.WEBSOCKET.equals(protocolType)) {
			packLen = in.readableBytes();
		}else {
			ctx.close();
			throw new RuntimeException("Unknow protocolType !!");
		}

		// 读取指令标识
		int actionLen = in.readByte();
		byte[] action = new byte[actionLen];
		in.readBytes(action);

		// 读取数据体 = 总包长 - 标识长度占用字节 - 标识体占用字节数
		int bodyLen = packLen - ALL_TAG_LEN - actionLen;
		byte[] message = null;
		if (bodyLen != 0) {

			message = new byte[bodyLen];
			// 读取数据体部分byte数组
			in.readBytes(message);

		}

		Pack pack = new Pack(action, message);
		pack.setProtocolType(protocolType);
		
		Channel channel = ctx.channel();
		pack.setChannel(channel);
		
		// 获取session
		pack.setSession(config.getSessionFactory().getSession(channel));

		// 接收包处理
		config.getReceivePackHandler().handle(pack);
		
		if (this.config.isEnablePackLogger()) {
			pack.setChannel(channel);
			pack.setOperType(Pack.OperType.REQUEST);
			pack.setProtocolType(protocolType);
			PackLogger packLogger = this.config.getPackLogger();
			packLogger.logPack(pack);
		}

	}

}
