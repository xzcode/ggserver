package xzcode.ggserver.core.common.handler.codec.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.core.common.config.GGConfig;
import xzcode.ggserver.core.common.handler.codec.IEncodeHandler;
import xzcode.ggserver.core.common.message.Pack;

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
	public ByteBuf handle(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
		
		Channel channel = ctx.channel();
		Pack pack = (Pack) msg;
		
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
		out = ctx.alloc().buffer(packLen);
		
		
		out.writeShort(metaBytes.length);
		if (metaBytes != null) {
			out.writeBytes(metaBytes);			
		}
		
		out.writeByte(tagBytes.length);
		out.writeBytes(tagBytes);
		
		if (bodyBytes != null) {
			out.writeBytes(bodyBytes);			
		}
		
		if (LOGGER.isInfoEnabled()) {
        	LOGGER.info("\nmessage sended ---> \nchannel:{}\ntag:{}\nbytes-length:{}\ndata:{}", channel, new String(tagBytes, config.getCharset()), packLen +4 , new String(bodyBytes));
        }
		return out;
		
	}


	

}
