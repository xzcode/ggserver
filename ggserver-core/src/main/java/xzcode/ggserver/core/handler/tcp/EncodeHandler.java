package xzcode.ggserver.core.handler.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.message.send.SendModel;

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


	private GGServerConfig config;
	
	
	public EncodeHandler() {
	}
	
	
	public EncodeHandler(GGServerConfig config) {
		super();
		this.config = config;
	}


	
	@Override
	protected void encode(ChannelHandlerContext ctx, SendModel sendModel, ByteBuf out) throws Exception {
		
		String sendTag = sendModel.getSendTag();
		byte[] serialize = this.config.getSerializer().serialize(sendModel.getMessage());
		
		out.writeInt(4 + sendTag.length() + serialize.length);
		
		out.writeBytes(sendTag.getBytes());
		
		out.writeBytes(serialize);
		
	}
	

}
