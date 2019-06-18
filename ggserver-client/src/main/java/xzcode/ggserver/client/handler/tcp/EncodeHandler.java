package xzcode.ggserver.client.handler.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import xzcode.ggserver.client.config.GGClientConfig;
import xzcode.ggserver.client.message.send.ClientSendModel;

/**
 * 
 * @author zai
 * 包体总长度       标识长度      标识内容       数据体
 * +--------+--------+-------+------------+
 * | 4 byte | 2 byte | tag   |  data body |
 * +--------+--------+-------+------------+
 * 2018-12-07 13:38:22
 */
public class EncodeHandler extends ChannelOutboundHandlerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncodeHandler.class);
	    

	
	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LENGTH_BYTES = 4;
	
	/**
	 * 每个数据包最大长度
	 */
	public static final int MAX_PACKAGE_LENGTH = 65536;
	
	/**
	 * 请求标识字符串长度单位 字节数
	 */
	public static final int REQUEST_TAG_LENGTH_BYTES = 2;
	
	/**
	 * 请求头 长度标识字节数
	 */
	public static final int HEADER_BYTES = PACKAGE_LENGTH_BYTES + REQUEST_TAG_LENGTH_BYTES;
	
	
	
	private GGClientConfig config;
	
	private static final Gson GSON = new GsonBuilder()
    		.serializeNulls()
    		.create();
	
	
	public EncodeHandler() {
	}
	
	
	public EncodeHandler(GGClientConfig config) {
		super();
		this.config = config;
	}

	
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		
		Channel channel = ctx.channel();
		if (!channel.isActive()) {
    		if(LOGGER.isDebugEnabled()){
        		LOGGER.debug("\nWrite channel:{} is inActive...", ctx.channel());        		
        	}
			return;
		}
		ByteBuf out = null;
		
		if (msg instanceof byte[]) {
			byte[] bytes = (byte[]) msg;
			out = ctx.alloc().buffer(bytes.length);
			
			out.writeBytes(bytes);
		}else if (msg instanceof ClientSendModel) {
			ClientSendModel clientSendModel = (ClientSendModel) msg;
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("\nSending message ---> \ntag:{}\nmessage:{}", clientSendModel.getSendTag(), GSON.toJson(clientSendModel));
			}
			
			byte[] tagBytes = clientSendModel.getSendTag();
			
			//如果有消息体
			if (clientSendModel.getMessage() != null) {
				
				byte[] bodyBytes = (byte[]) clientSendModel.getMessage();
				
				int packLen = REQUEST_TAG_LENGTH_BYTES + tagBytes.length + bodyBytes.length;
				
				out = ctx.alloc().buffer(packLen);
				
				out.writeInt(packLen);
				out.writeShort(tagBytes.length);
				out.writeBytes(tagBytes);
				out.writeBytes(bodyBytes);
				
			} else {
				
				//如果没消息体
				
				int packLen = REQUEST_TAG_LENGTH_BYTES + tagBytes.length;
				
				out = ctx.alloc().buffer(packLen);
				
				out.writeInt(packLen);
				out.writeShort(tagBytes.length);
				out.writeBytes(tagBytes);
				
			}
		}
		
		
		
		if(channel.isWritable()){
			ctx.writeAndFlush(out);
		}else {
			try {
				if (LOGGER.isInfoEnabled()) {
                	LOGGER.info("Channel is not writable, change to sync mode! \nchannel:{}", channel);
                }
				channel.writeAndFlush(out).sync();
                if (LOGGER.isInfoEnabled()) {
                	LOGGER.info("Sync message sended. \nchannel:{}\nmessage:{}", channel, GSON.toJson(msg));
                }
            } catch (InterruptedException e) {
            	if (LOGGER.isInfoEnabled()) {
            		LOGGER.info("write and flush msg exception. msg:[{}]", GSON.toJson(msg), e);
            	}
            }
		}
		
	}


	

}
