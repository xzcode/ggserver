package xzcode.ggserver.core.handler.codec.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import xzcode.ggserver.core.channel.DefaultChannelAttributeKeys;
import xzcode.ggserver.core.config.GGConfig;
import xzcode.ggserver.core.handler.codec.IGGDecodeHandler;
import xzcode.ggserver.core.message.PackModel;
import xzcode.ggserver.core.message.receive.RequestMessageTask;

/**
 * TCP自定协议解析
 * 
 *包体总长度   标识长度      标识内容       数据体
 * +--------+--------+-------+------------+
 * | 4 byte | 2 byte | tag   |  data body |
 * +--------+--------+-------+------------+
 * @author zai
 *
 */
public class DefaultDecodeHandler implements IGGDecodeHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDecodeHandler.class);

	/**
	 * 数据包长度标识 字节数
	 */
	public static final int PACKAGE_LENGTH_BYTES = 4;
	
	
	/**
	 * 请求标识字符串长度单位 字节数
	 */
	public static final int REQUEST_TAG_LENGTH_BYTES = 2;
	
	/**
	 * 请求头 长度标识字节数
	 */
	public static final int HEADER_BYTES = PACKAGE_LENGTH_BYTES + REQUEST_TAG_LENGTH_BYTES;
	
	
	private GGConfig config;
	
	public DefaultDecodeHandler() {
		
	}
	
	public DefaultDecodeHandler(GGConfig config) {
		super();
		this.config = config;
	}


	public void handle(ChannelHandlerContext ctx, ByteBuf in){
		
		int packLen = in.readableBytes();
		
		int reqTagSize = in.readUnsignedShort();
		
		byte[] action = new byte[reqTagSize];
		
		
		in.readBytes(action);
		
		//读取数据体 =  总包长 - 标识长度占用字节 - 标识体占用字节数
		int bodyLen = packLen - REQUEST_TAG_LENGTH_BYTES - reqTagSize;
		byte[] message = null;
		if (bodyLen != 0) {
			
			message = new byte[bodyLen];			
			//读取数据体部分byte数组
			in.readBytes(message);
			
		}
		
		//接收包处理
		config.getReceivePackHandler().handle(PackModel.create(action, message), ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).get());
		/*
		config.getTaskExecutor().submit(new RequestMessageTask(PackModel.create(action, message), ctx.channel().attr(DefaultChannelAttributeKeys.SESSION).get(), config));
		
		if(LOGGER.isInfoEnabled()){
        	LOGGER.info("\nReceived binary message  <----,\nchannel:{}\ntag:{}\nbytes-length:{}\ndata:{}", ctx.channel(), action == null ? "" : new String(action), packLen + 4, message == null ? "" : new String(message));
        }
*/	}




}
