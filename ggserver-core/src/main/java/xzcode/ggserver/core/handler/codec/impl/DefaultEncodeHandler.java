package xzcode.ggserver.core.handler.codec.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.executor.task.ExectionTask;
import xzcode.ggserver.core.handler.codec.IGGEncodeHandler;
import xzcode.ggserver.core.message.PackModel;

/**
 * 
 * @author zai
 * 包体总长度       标识长度      标识内容       数据体
 * +--------+--------+-------+------------+
 * | 4 byte | 2 byte | tag   |  data body |
 * +--------+--------+-------+------------+
 * 2018-12-07 13:38:22
 */
public class DefaultEncodeHandler implements IGGEncodeHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEncodeHandler.class);
	    

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
	public ByteBuf handle(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		
		Channel channel = ctx.channel();
		PackModel packModel = (PackModel) msg;
		byte[] tagBytes = packModel.getAction();
		ByteBuf out = null;
		//如果有消息体
		if (packModel.getMessage() != null) {
			
			byte[] bodyBytes = (byte[]) packModel.getMessage();
			
			int packLen = 2 + tagBytes.length + bodyBytes.length;
			
			out = ctx.alloc().buffer(packLen);
			
			out.writeInt(packLen);
			out.writeShort(tagBytes.length);
			out.writeBytes(tagBytes);
			out.writeBytes(bodyBytes);
			if (LOGGER.isInfoEnabled()) {
            	LOGGER.info("\nmessage sended ---> \nchannel:{}\ntag:{}\nbytes-length:{}\ndata:{}", channel, new String(tagBytes, config.getCharset()), packLen +4 , new String(bodyBytes));
            }
		} else {
		
			//如果没消息体
			
			int packLen = 2 + tagBytes.length;
			
			out = ctx.alloc().buffer(packLen);
			
			out.writeInt(packLen);
			out.writeShort(tagBytes.length);
			out.writeBytes(tagBytes);
			
			if (LOGGER.isInfoEnabled()) {
            	LOGGER.info("\nmessage sended ---> \nchannel:{}\ntag:{}\nbytes-length:{}", channel, new String(tagBytes, config.getCharset()), packLen + 4);
            }
			
		}
		return out;
		
	}


	

}
