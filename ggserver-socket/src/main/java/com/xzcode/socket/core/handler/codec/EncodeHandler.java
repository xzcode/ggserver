package com.xzcode.socket.core.handler.codec;

import com.xzcode.socket.core.sender.SendModel;
import com.xzcode.socket.core.serializer.ISerializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author zai
 * 包体总长度       标识长度      标识内容       数据体
 * +--------+--------+-------+------------+
 * | 4 byte | 2 byte | tag   |  data body |
 * +--------+--------+-------+------------+
 * 2018-12-07 13:38:22
 */
public class EncodeHandler extends MessageToByteEncoder<SendModel> {


	/**
	 * 序列化工具类
	 */
	private ISerializer serializer;
	
	
	public EncodeHandler() {
	}
	
	
	public EncodeHandler(ISerializer serializer) {
		super();
		this.serializer = serializer;
	}


	
	@Override
	protected void encode(ChannelHandlerContext ctx, SendModel sendModel, ByteBuf out) throws Exception {
		
		String sendTag = sendModel.getSendTag();
		byte[] serialize = this.serializer.serialize(sendModel.getMessage());
		
		out.writeInt(4 + sendTag.length() + serialize.length);
		
		out.writeBytes(sendTag.getBytes());
		
		out.writeBytes(serialize);
		
	}
	
	public void setSerializer(ISerializer serializer) {
		this.serializer = serializer;
	}
	
	public ISerializer getSerializer() {
		return serializer;
	}

}
