package xzcode.ggserver.core.common.handler.codec.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import xzcode.ggserver.core.common.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.constant.ProtocolTypeConstants;
import xzcode.ggserver.core.common.handler.codec.IEncodeHandler;
import xzcode.ggserver.core.common.message.Pack;
import xzcode.ggserver.core.common.utils.logger.GGLoggerUtil;

/**
 * 
 *   包体总长度           元数据长度           元数据体         指令长度           指令内容                   数据体
 * +----------+-----------+----------+----------+-----------+------------+
 * | 4 byte   |  2 byte   | metadata |   1 byte |    tag    |  data body |
 * +----------+-----------+----------+----------+-----------+------------+
 * @author zai
 * 2018-12-07 13:38:22
 */
public class DefaultEncodeHandler implements IEncodeHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEncodeHandler.class);
	
	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LEN = 4;
	
	/**
	 * 元数据标识占用字节数
	 */
	public static final int METADATA_TAG_LEN= 2;
	
	/**
	 * 指令长度标识占用字节数
	 */
	public static final int ACTION_TAG_LEN= 1;
	
	/**
	 * 所有标识长度
	 */
	public static final int ALL_TAG_LEN = METADATA_TAG_LEN + ACTION_TAG_LEN;
	
	
	/**
	 * 协议类型channel key
	 */
	protected static final AttributeKey<String> PROTOCOL_TYPE_KEY = AttributeKey.valueOf(DefaultChannelAttributeKeys.PROTOCOL_TYPE);
	
	    

	private GGConfig config;
	
	private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
	
	
	public DefaultEncodeHandler() {
	}
	
	
	public DefaultEncodeHandler(GGConfig config) {
		super();
		this.config = config;
	}

	
	
	@Override
	public ByteBuf handle(ChannelHandlerContext ctx, Pack pack){
		
		Channel channel = ctx.channel();
		String protocolType = channel.attr(PROTOCOL_TYPE_KEY).get();
		
		if(GGLoggerUtil.getLogger().isInfoEnabled()){
			pack.setProtocolType(protocolType);
			GGLoggerUtil.logPack(pack, Pack.OperType.RESPONSE, channel);
        }
		
		ByteBuf out = null;
		
		byte[] metaBytes = pack.getMetadata();
		byte[] tagBytes = pack.getAction();
		byte[] bodyBytes = (byte[]) pack.getMessage();
		
		int packLen = ALL_TAG_LEN;
		
		
		if (metaBytes != null) {
			packLen += metaBytes.length;		
		}
		
		packLen += tagBytes.length;
		
		if (bodyBytes != null) {
			packLen += bodyBytes.length;			
		}
		
		//判断协议类型
		if (ProtocolTypeConstants.TCP.equals(protocolType)) {
			out = ctx.alloc().buffer(packLen);
			out.writeInt(packLen);
		}else {
			out = ctx.alloc().buffer(packLen);			
		}
		
		
		//metadata
		if (metaBytes != null) {
			out.writeShort(metaBytes.length);
			if (metaBytes != null) {
				out.writeBytes(metaBytes);			
			}			
		}else {
			out.writeShort(0);
		}
		
		//action id
		out.writeByte(tagBytes.length);
		out.writeBytes(tagBytes);
		
		//data body
		if (bodyBytes != null) {
			out.writeBytes(bodyBytes);			
		}
		/*
		int readableBytes = out.readableBytes();
		out.markReaderIndex();
		byte[] bb = new byte[readableBytes];
		out.getBytes(0, bb);
		System.out.println(Arrays.toString(bb));
		out.resetReaderIndex();
		if (packLen + 4 == 101) {
			System.out.println(101);
		}
		*/
		return out;
	}

	

}
